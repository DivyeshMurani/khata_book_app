package io.selfmade.khatabook.utilities

import android.util.Log

class Logs {
    companion object {

        private val developer = "developer"

        fun e(msg: String): Unit {
            Log.e(developer, msg)

        }
    }
}