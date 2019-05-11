package com.example.downloaderlib.downloaderlib

import android.content.Context

/** The default Storage Resolver used by Fetch. Extend this class if you want to provide your
 * own implementation.*/
open class DefaultStorageResolver(
        /* Context*/
        protected val context: Context,
        /**The default temp directory used by Fetch for Parallel Downloaders.*/
        protected val defaultTempDir: String) : StorageResolver {

    override fun createFile(file: String, increment: Boolean): String {
        return createFileAtPath(file, increment, context)
    }

    override fun deleteFile(file: String): Boolean {
        return deleteFile(file, context)
    }

    override fun getRequestOutputResourceWrapper(request: Downloader.ServerRequest): OutputResourceWrapper {
        return getOutputResourceWrapper(request.file, context.contentResolver)
    }

    override fun getDirectoryForFileDownloaderTypeParallel(request: Downloader.ServerRequest): String {
        return defaultTempDir
    }

    override fun fileExists(file: String): Boolean {
        if (file.isEmpty()) {
            return false
        }
        return try {
            getOutputResourceWrapper(file, context.contentResolver)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun renameFile(oldFile: String, newFile: String): Boolean {
        if (oldFile.isEmpty() || newFile.isEmpty()) {
            return false
        }
        return renameFile(oldFile, newFile, context)
    }

}