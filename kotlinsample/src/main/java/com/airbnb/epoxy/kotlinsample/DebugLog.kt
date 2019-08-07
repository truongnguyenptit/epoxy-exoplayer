package com.airbnb.epoxy.kotlinsample

import android.util.Log

class DebugLog {
    companion object {
        fun logD(message: String) {
            if (BuildConfig.DEBUG)
                Log.d("TAG", message)
        }

        fun logD(tag: String, message: String) {
            if (BuildConfig.DEBUG) Log.d(tag, message)
        }
    }
}