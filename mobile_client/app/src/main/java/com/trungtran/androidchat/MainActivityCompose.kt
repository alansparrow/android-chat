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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.trungtran.androidchat.comm.SocketIO
import com.trungtran.androidchat.ui.theme.AndroidChatTheme
import kotlinx.coroutines.launch

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var msgs by mutableStateOf<List<String>>(mutableListOf())
        msgs += listOf("Started")

        setContent {
            AndroidChatTheme {
                Scaffold(
                    content = {
                        MultiChat(msgs)
                    }
                )
            }
        }


        SocketIO.setSocket()
        SocketIO.connect()

        val mSocket = SocketIO.getSocket()
        mSocket.emit("Hi from mobile!")

        mSocket.on("chat message") {
            if (it[0] != null) {
                val msg = it[0] as String
                msgs += listOf(msg)
                Log.d(TAG, "received: $msg")
            }
        }


    }

    companion object {
        private const val TAG = "MainActivityCompose"
    }
}

@Composable
fun MultiChat(msgs: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
//            .background(Color.Yellow)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
//                .background(Color.Blue)
        ) {
            NativeChat(msgs)
        }

        // Add a horizontal space between the img and the column
        Spacer(modifier = Modifier.width((8.dp)))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f)
            .background(Color.Yellow))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
//                .background(Color.Red)
        ) {
            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                WebChat()
            }
        }
    }
}

@Composable
fun NativeChat(msgs: List<String>) {
    val messages: MutableList<String> = mutableListOf()
    for (i in 1..50) {
        messages.add("msg $i")
    }

    Messages(msgs)
}

@Composable
fun Messages(messages: List<String>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val corountineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        items(messages) { message ->
            Message(message)
        }
    }

    corountineScope.launch {
        listState.animateScrollToItem(messages.size - 1)
    }
}

@Composable
fun Message(message: String, modifier: Modifier = Modifier) {
    Text(message)
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