package com.chatglobal.data.local
import androidx.room.Entityimport androidx.room.PrimaryKey
@Entity(tableName = "messages")
data class MessageEntity(@PrimaryKey val id: String, val userId: String, val content: String, val timestamp: String, val isSent: Boolean, val status: String = "sent", val senderName: String = "", val senderAvatarUrl: String? = null)
