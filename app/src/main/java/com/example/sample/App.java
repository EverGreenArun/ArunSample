package com.example.sample;

import android.app.Application;
import com.example.downloaderlib.downloaderlib.Downloader;
import com.example.downloaderlib.downloaderlib2.Fetch;
import com.example.downloaderlib.downloaderlib2.FetchConfiguration;
import com.example.downloaderlib.downloaderlib2.HttpUrlConnectionDownloader;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .enableRetryOnNetworkGain(true)
                .setDownloadConcurrentLimit(3)
                .setHttpDownloader(new HttpUrlConnectionDownloader(Downloader.FileDownloaderType.PARALLEL))
                .build();
        Fetch.Impl.setDefaultInstanceConfiguration(fetchConfiguration);
    }

}
