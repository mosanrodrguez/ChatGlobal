package com.chatglobal.data.repository

import android.content.Context
import com.chatglobal.data.local.AppDatabase
import com.chatglobal.data.local.MessageEntity
import com.chatglobal.data.network.*
import com.chatglobal.domain.model.MessageStatus
import com.chatglobal.presentation.MessageUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepository(context: Context, token: String) {
    private val db = AppDatabase.getInstance(context)
    private val api = RetrofitClient.api
    private val ws = WebSocketManager(token)

    init {
        RetrofitClient.setToken(token)
    }

    fun observeMessages(): Flow<List<MessageUiModel>> {
        return db.dao().getAll().map { list ->
            list.map { e ->                MessageUiModel(
                    id = e.id,
                    content = e.content,
                    timestamp = e.timestamp,
                    isSent = e.isSent,
                    senderId = e.userId,
                    senderName = e.senderName,
                    senderAvatarUrl = e.senderAvatarUrl,
                    status = when (e.status) {
                        "sending" -> MessageStatus.Sending
                        "delivered" -> MessageStatus.Delivered
                        "seen" -> MessageStatus.Seen
                        else -> MessageStatus.Sent
                    }
                )
            }
        }
    }

    suspend fun sync(cursor: String? = null) {
        try {
            val remote = api.getMessages(cursor = cursor)
            val local = remote.map { r ->
                MessageEntity(
                    id = r.id,
                    userId = r.userId,
                    content = r.content ?: "",
                    timestamp = r.timestamp,
                    isSent = false,
                    status = r.status,
                    senderName = r.userName ?: "",
                    senderAvatarUrl = r.userAvatar
                )
            }
            db.dao().insertList(local)
        } catch (e: Exception) {
            // Fail silently, cache remains active
        }
    }

    suspend fun sendLocal(content: String) {
        val id = System.currentTimeMillis().toString()
        db.dao().insertOne(
            MessageEntity(
                id = id,
                userId = "me",
                content = content,
                timestamp = "",
                isSent = true,
                status = "sending",                senderName = "Tú",
                senderAvatarUrl = null
            )
        )
        ws.sendMessage(content)
    }

    fun connectWs() { ws.connect() }
    fun disconnectWs() { ws.disconnect() }
    fun getWs() = ws
}
