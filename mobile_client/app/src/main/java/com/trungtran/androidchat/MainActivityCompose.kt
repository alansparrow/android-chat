package com.trungtran.androidchat

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.trungtran.androidchat.comm.SocketIO
import com.trungtran.androidchat.ui.theme.AndroidChatTheme
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import javax.inject.Inject

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {

    @Inject
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var msgs by mutableStateOf<List<String>>(mutableListOf())

        setContent {
            AndroidChatTheme {
                Scaffold(content = {
                    MultiChat(msgs)
                })
            }
        }

        mSocket.emit("chat message", "Hi from mobile!")
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            NativeChat(msgs)
        }

        // Add a horizontal space between the img and the column
        Spacer(modifier = Modifier.width((8.dp)))

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
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
        ) {
            Messages(msgs)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                var text by remember { mutableStateOf("") }
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .padding(start = 5.dp, bottom = 5.dp)

                )
                Button(
                    onClick = {
                        SocketIO.getSocket().emit("chat message", text)
                        text = ""
                    }, modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(text = "Send")
                }
            }

        }
    }

}

@Composable
fun Messages(messages: List<String>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(messages) { message ->
            Message(message)
        }
    }

    LaunchedEffect(key1 = messages.size) {
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