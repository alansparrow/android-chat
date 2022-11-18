package com.trungtran.androidchat.comm

import android.util.Log
import com.trungtran.androidchat.MainActivityCompose
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommModule {
    companion object {
        @Singleton
        @Provides
        fun provideSocketIO(): Socket {
            SocketIO.setSocket()
            SocketIO.connect()
            return SocketIO.getSocket();
        }
    }
}