package com.example.sample.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class BaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}