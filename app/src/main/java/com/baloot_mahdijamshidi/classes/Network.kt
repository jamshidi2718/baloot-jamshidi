package com.baloot_mahdijamshidi.classes

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.Nullable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.net.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Network @Inject constructor(@ApplicationContext private val context: Context) {


    suspend fun check() : Boolean {
        return  withContext(Dispatchers.IO) {
            try {
                ping(URL("https://www.google.com:443/"), context)!!
            } catch (e: Exception) {
               false
            }
        }
    }

    fun ping(url: URL, ctx: Context): Boolean? {
        try {
            val r = Ping()
            if (isNetworkConnected(ctx)) {
                r.net = getNetworkType(ctx)

                val start = System.currentTimeMillis()
                val hostAddress = InetAddress.getByName(url.host).hostAddress
                val dnsResolved = System.currentTimeMillis()
                val socketAddress: SocketAddress = InetSocketAddress(hostAddress, url.port)
                val socket = Socket()
                socket.connect(socketAddress, 30000)
                val probeFinish = System.currentTimeMillis()
                r.dns = (dnsResolved - start).toInt()
                r.cnt = (probeFinish - dnsResolved).toInt()
                r.host = url.host
                r.ip = hostAddress

            } else {
                Log.e("location", "notconnect")
                r.ip = "0"
                return false
            }

            return r.ip != "0"

        } catch (ex: Exception) {
            Log.e("location", ex.message.toString())
            return false
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    @Nullable
    fun getNetworkType(context: Context): String? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.typeName
    }

}
class Ping {

    var net: String? = "NO_CONNECTION"
    var host = ""
    var ip = ""
    var dns = Int.MAX_VALUE
    var cnt = Int.MAX_VALUE

}
