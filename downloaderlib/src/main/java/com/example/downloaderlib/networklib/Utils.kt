@file:JvmName("OkHttpUtils")

package com.example.downloaderlib.networklib

import com.example.downloaderlib.downloaderlib.getDefaultCookieManager
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar

fun getDefaultCookieJar(): CookieJar {
    val cookieManager = getDefaultCookieManager()
    return JavaNetCookieJar(cookieManager)
}