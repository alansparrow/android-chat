package com.trungtran.androidchat

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.trungtran.androidchat.comm.SocketIO
import com.trungtran.androidchat.ui.theme.AndroidChatTheme

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidChatTheme {
                Scaffold(
                    content = {
                        MultiChat()
                    }
                )
            }
        }

//        val myWebView: WebView = findViewById(R.id.webview)
//        myWebView.webViewClient = WebViewClient()
//        myWebView.webChromeClient = WebChromeClient()
//        myWebView.settings.javaScriptEnabled = true
//        myWebView.loadUrl(Constant.CHAT_SERVER_URI)
////        myWebView.loadUrl("https://youtube.com")
//
//        SocketIO.setSocket()
//        SocketIO.connect()
//
//        val mSocket = SocketIO.getSocket()
//        mSocket.emit("Hi from mobile!")
//
//        mSocket.on("chat message") {
//            if (it[0] != null) {
//                val msg = it[0] as String
//                Log.d(MainActivityCompose.TAG, "received: $msg")
//            }
//        }
    }

    companion object {
        private const val TAG = "MainActivityCompose"
    }
}

@Composable
fun MultiChat() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .background(Color.Blue)
        ) {
            NativeChat()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .background(Color.Red)
        ) {
            WebChat()
        }
    }
}

@Composable
fun NativeChat() {
    Text("NativeChat1")
    Text("NativeChat2")
}

@Composable
fun WebChat() {
    Text("WebChat1")
    Text("WebChat2")
    Text("WebChat3")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidChatTheme {
        Greeting("Android")
    }
}