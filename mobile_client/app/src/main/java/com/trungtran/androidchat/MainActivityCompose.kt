package com.trungtran.androidchat

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Native Client",
            fontSize = 20.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            NativeChat(msgs)
        }

        Text(
            text = "Webview Client",
            fontSize = 20.sp
        )
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
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                placeholder = {
                    Text("Enter message")
                },
                value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(25.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send_msg1),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            SocketIO.getSocket().emit("chat message", text)
                            text = ""
                        }
                    )
                }

            )
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
        if (messages.size - 1 >= 0) {
            listState.animateScrollToItem(messages.size - 1)
        }
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