package com.trungtran.androidchat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.trungtran.androidchat.comm.SocketIO

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.webViewClient = WebViewClient()
        myWebView.settings.javaScriptEnabled = true
//        myWebView.loadUrl(Constant.CHAT_SERVER_URI)
        myWebView.loadUrl("https://youtube.com")

        SocketIO.setSocket()
        SocketIO.connect()

        val mSocket = SocketIO.getSocket()
        mSocket.emit("Hi from mobile!")

        mSocket.on("chat message") {
            if (it[0] != null) {
                val msg = it[0] as String
                Log.d(TAG, "received: $msg")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
