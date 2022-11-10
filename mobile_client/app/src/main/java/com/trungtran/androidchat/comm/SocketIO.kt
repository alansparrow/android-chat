package com.trungtran.androidchat.comm

import android.util.Log
import com.trungtran.androidchat.Constant
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketIO {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(Constant.CHAT_SERVER_URI)
        } catch (e: URISyntaxException) {
            Log.e(TAG, "Error");
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun connect() {
        mSocket.connect()
    }

    @Synchronized
    fun close() {
        mSocket.disconnect()
    }

    private const val TAG = "SocketIO";
}