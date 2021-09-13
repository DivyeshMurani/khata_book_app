package io.selfmade.khatabook.utilities

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        var CONTEXT: Context? = null

    }

    override fun onCreate() {
        super.onCreate()

        if (CONTEXT == null) {
            CONTEXT = this
        }

    }
}