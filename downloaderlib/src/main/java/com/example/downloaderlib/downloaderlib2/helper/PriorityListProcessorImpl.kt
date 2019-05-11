package com.example.downloaderlib.downloaderlib2.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.downloaderlib.downloaderlib2.*
import com.example.downloaderlib.downloaderlib2.downloader.DownloadManager
import com.example.downloaderlib.downloaderlib.HandlerWrapper
import com.example.downloaderlib.downloaderlib2.provider.DownloadProvider
import com.example.downloaderlib.downloaderlib2.provider.NetworkInfoProvider
import com.example.downloaderlib.downloaderlib2.util.DEFAULT_PRIORITY_QUEUE_INTERVAL_IN_MILLISECONDS
import com.example.downloaderlib.downloaderlib2.NetworkType
import com.example.downloaderlib.downloaderlib2.fetch.ListenerCoordinator
import com.example.downloaderlib.downloaderlib.Logger
import com.example.downloaderlib.downloaderlib.isFetchFileServerUrl
import java.util.concurrent.TimeUnit

class PriorityListProcessorImpl constructor(private val handlerWrapper: HandlerWrapper,
                                            private val downloadProvider: DownloadProvider,
                                            private val downloadManager: DownloadManager,
                                            private val networkInfoProvider: NetworkInfoProvider,
                                            private val logger: Logger,
                                            private val listenerCoordinator: ListenerCoordinator,
                                            @Volatile
                                            override var downloadConcurrentLimit: Int,
                                            private val context: Context,
                                            private val namespace: String,
                                            private val prioritySort: PrioritySort)
    : PriorityListProcessor<Download> {

    private val lock = Any()
    @Volatile
    override var globalNetworkType = NetworkType.GLOBAL_OFF
    @Volatile
    private var paused = false
    override val isPaused: Boolean
        get() = paused
    @Volatile
    private var stopped = true
    override val isStopped: Boolean
        get() = stopped
    @Volatile
    private var backOffTime = DEFAULT_PRIORITY_QUEUE_INTERVAL_IN_MILLISECONDS
    private val networkChangeListener: NetworkInfoProvider.NetworkChangeListener = object : NetworkInfoProvider.NetworkChangeListener {
        override fun onNetworkChanged() {
            if (!stopped && !paused && networkInfoProvider.isNetworkAvailable
                    && backOffTime > DEFAULT_PRIORITY_QUEUE_INTERVAL_IN_MILLISECONDS) {
                resetBackOffTime()
            }
        }
    }
    private val priorityBackoffResetReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null && intent != null) {
                when (intent.action) {
                    ACTION_QUEUE_BACKOFF_RESET -> {
                        if (!stopped && !paused && namespace == intent.getStringExtra(EXTRA_NAMESPACE)) {
                            resetBackOffTime()
                        }
                    }
                }
            }
        }
    }

    init {
        networkInfoProvider.registerNetworkChangeListener(networkChangeListener)
        context.registerReceiver(priorityBackoffResetReceiver, IntentFilter(ACTION_QUEUE_BACKOFF_RESET))
    }

    private val priorityIteratorRunnable = Runnable {
        if (canContinueToProcess()) {
            if (downloadManager.canAccommodateNewDownload() && canContinueToProcess()) {
                val priorityList = getPriorityList()
                var shouldBackOff = false
                if (priorityList.isEmpty() || !networkInfoProvider.isNetworkAvailable) {
                    shouldBackOff = true
                }
                if (!shouldBackOff) {
                    shouldBackOff = true
                    for (index in 0..priorityList.lastIndex) {
                        if (downloadManager.canAccommodateNewDownload() && canContinueToProcess()) {
                            val download = priorityList[index]
                            val isFetchServerRequest = isFetchFileServerUrl(download.url)
                            if ((isFetchServerRequest || networkInfoProvider.isNetworkAvailable) && canContinueToProcess()) {
                                val networkType = when {
                                    globalNetworkType != NetworkType.GLOBAL_OFF -> globalNetworkType
                                    download.networkType == NetworkType.GLOBAL_OFF -> NetworkType.ALL
                                    else -> download.networkType
                                }
                                val properNetworkConditions = networkInfoProvider.isOnAllowedNetwork(networkType)
                                if (!properNetworkConditions) {
                                    listenerCoordinator.mainListener.onWaitingNetwork(download)
                                }
                                if ((isFetchServerRequest || properNetworkConditions)) {
                                    shouldBackOff = false
                                    if (!downloadManager.contains(download.id) && canContinueToProcess()) {
                                        downloadManager.start(download)
                                    }
                                }
                            } else {
                                break
                            }
                        } else {
                            break
                        }
                    }
                }
                if (shouldBackOff) {
                    increaseBackOffTime()
                }
            }
            if (canContinueToProcess()) {
                registerPriorityIterator()
            }
        }
    }

    override fun start() {
        synchronized(lock) {
            resetBackOffTime()
            stopped = false
            paused = false
            registerPriorityIterator()
            logger.d("PriorityIterator started")
        }
    }

    override fun stop() {
        synchronized(lock) {
            unregisterPriorityIterator()
            paused = false
            stopped = true
            downloadManager.cancelAll()
            logger.d("PriorityIterator stop")
        }
    }

    override fun pause() {
        synchronized(lock) {
            unregisterPriorityIterator()
            paused = true
            stopped = false
            downloadManager.cancelAll()
            logger.d("PriorityIterator paused")
        }
    }

    override fun resume() {
        synchronized(lock) {
            resetBackOffTime()
            paused = false
            stopped = false
            registerPriorityIterator()
            logger.d("PriorityIterator resumed")
        }
    }

    override fun getPriorityList(): List<Download> {
        synchronized(lock) {
            return try {
                downloadProvider.getPendingDownloadsSorted(prioritySort)
            } catch (e: Exception) {
                logger.d("PriorityIterator failed access database", e)
                listOf()
            }
        }
    }

    private fun registerPriorityIterator() {
        if (downloadConcurrentLimit > 0) {
            handlerWrapper.postDelayed(priorityIteratorRunnable, backOffTime)
        }
    }

    private fun unregisterPriorityIterator() {
        if (downloadConcurrentLimit > 0) {
            handlerWrapper.removeCallbacks(priorityIteratorRunnable)
        }
    }

    private fun canContinueToProcess(): Boolean {
        return !stopped && !paused
    }

    override fun resetBackOffTime() {
        synchronized(lock) {
            backOffTime = DEFAULT_PRIORITY_QUEUE_INTERVAL_IN_MILLISECONDS
            unregisterPriorityIterator()
            registerPriorityIterator()
        }
    }

    override fun sendBackOffResetSignal() {
        synchronized(lock) {
            val intent = Intent(ACTION_QUEUE_BACKOFF_RESET)
            intent.putExtra(EXTRA_NAMESPACE, namespace)
            context.sendBroadcast(intent)
        }
    }

    override fun close() {
        synchronized(lock) {
            networkInfoProvider.registerNetworkChangeListener(networkChangeListener)
            context.unregisterReceiver(priorityBackoffResetReceiver)
        }
    }

    private fun increaseBackOffTime() {
        backOffTime = if (backOffTime == DEFAULT_PRIORITY_QUEUE_INTERVAL_IN_MILLISECONDS) {
            ONE_MINUTE_IN_MILLISECONDS
        } else {
            backOffTime * 2L
        }
    }

    private companion object {
        private const val ONE_MINUTE_IN_MILLISECONDS = 60000L
    }

}