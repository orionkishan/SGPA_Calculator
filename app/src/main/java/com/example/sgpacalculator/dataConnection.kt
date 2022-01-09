package com.example.sgpacalculator

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

class dataConnection  {
    var connectivityManager: ConnectivityManager? = null
    var connected = false
    val isOnline: Boolean
        get() {
            try {
                connectivityManager = context
                    ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager!!.activeNetworkInfo
                connected = networkInfo != null && networkInfo.isAvailable &&
                        networkInfo.isConnected
                return connected
            } catch (e: Exception) {
                println("CheckConnectivity Exception: " + e.message)
                Log.v("connectivity", e.toString())
            }
            return connected
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val instance = dataConnection()

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        fun getInstance(ctx: Context): dataConnection{
            context = ctx.applicationContext
            return instance
        }
    }
}