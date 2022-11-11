package com.trungtran.androidchat

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .background(Color.Blue)
        ) {
            NativeChat()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .background(Color.Red)
        ) {
            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                WebChat()
            }
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
    AndroidView(factory = { ctx ->
        WebView(ctx).apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(Constant.CHAT_SERVER_URI)
        }
    }, update = {})
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