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
    init { RetrofitClient.setToken(token) }
    fun observeMessages(): Flow<List<MessageUiModel>> = db.dao().getAll().map { list -> list.map { e -> MessageUiModel(e.id, e.content, e.timestamp, e.isSent, e.userId, e.senderName, e.senderAvatarUrl, when(e.status) { "sending" -> MessageStatus.Sending; "delivered" -> MessageStatus.Delivered; "seen" -> MessageStatus.Seen; else -> MessageStatus.Sent }) } }
    suspend fun sync(cursor: String? = null) { try { val r = api.getMessages(cursor = cursor); val l = r.map { MessageEntity(it.id, it.userId, it.content?:"", it.timestamp, false, it.status, it.userName?:"", it.userAvatar); db.dao().insertList(l) } catch(_: Exception) {} }
    suspend fun sendLocal(content: String) { val id = System.currentTimeMillis().toString(); db.dao().insertOne(MessageEntity(id, "me", content, "", true, "sending", "Tú", null)); ws.sendMessage(content) }
    fun connectWs() { ws.connect() } fun disconnectWs() { ws.disconnect() } fun getWs() = ws
}EOF

# 6. Kotlin: Presentation & Screens
cat > app/src/main/java/com/chatglobal/presentation/MessageUiModel.kt << 'EOF'
package com.chatglobal.presentation
import com.chatglobal.domain.model.MessageStatus
data class MessageUiModel(val id: String, val content: String, val timestamp: String, val isSent: Boolean, val senderId: String, val senderName: String, val senderAvatarUrl: String?, val status: MessageStatus)
sealed class ChatUiState { object Loading : ChatUiState(); object Empty : ChatUiState(); object Error : ChatUiState(); data class Success(val messages: List<MessageUiModel>) : ChatUiState() }
