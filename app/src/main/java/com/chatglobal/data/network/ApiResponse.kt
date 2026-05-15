package com.chatglobal.data.network
import com.google.gson.annotations.SerializedName
data class AuthResponse(val token: String, val user: UserResponse)
data class UserResponse(val id: String, val name: String, @SerializedName("avatar_url") val avatarUrl: String?, @SerializedName("created_at") val createdAt: String, @SerializedName("last_seen") val lastSeen: String?, @SerializedName("is_online") val isOnline: Boolean?, @SerializedName("is_typing") val isTyping: Boolean?)
data class MessageResponse(val id: String, @SerializedName("user_id") val userId: String, val content: String?, val timestamp: String, val status: String, @SerializedName("reply_to_id") val replyToId: String?, @SerializedName("media_url") val mediaUrl: String?, @SerializedName("media_type") val mediaType: String?, @SerializedName("user_name") val userName: String?, @SerializedName("user_avatar") val userAvatar: String?)
data class MessageDetailsResponse(@SerializedName("message_id") val messageId: String, @SerializedName("global_status") val globalStatus: String, val users: List<UserMessageStatus>)
data class UserMessageStatus(@SerializedName("user_id") val userId: String, val name: String, @SerializedName("avatar_url") val avatarUrl: String?, val status: String, val timestamp: String)
data class UploadResponse(val url: String, @SerializedName("public_id") val publicId: String, val type: String)
