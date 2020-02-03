package com.requestum.tastomelet.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil(var context: Context) {

    fun checkNetworkAvailable(): Boolean {
        return isOnline() && hasNetwork()
    }

    private fun isOnline(): Boolean {
        try {
            val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            return returnVal == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun hasNetwork(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null && (activeNetwork.type == ConnectivityManager.TYPE_WIFI || activeNetwork.type == ConnectivityManager.TYPE_MOBILE)) {
            return true
        }
        return false
    }
}
