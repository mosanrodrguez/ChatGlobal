package com.chatglobal.presentation

import com.chatglobal.domain.model.MessageStatus

data class MessageUiModel(
    val id: String,
    val content: String,
    val timestamp: String,
    val isSent: Boolean,
    val senderId: String,
    val senderName: String,    val senderAvatarUrl: String?,
    val status: MessageStatus
)

sealed class ChatUiState {
    object Loading : ChatUiState()
    object Empty : ChatUiState()
    object Error : ChatUiState()
    data class Success(val messages: List<MessageUiModel>) : ChatUiState()
}
