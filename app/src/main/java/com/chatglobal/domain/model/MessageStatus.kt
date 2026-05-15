package com.chatglobal.domain.model

sealed class MessageStatus(val iconRes: Int, val color: Int) {
    object Sending : MessageStatus(0, 0xFF666666.toInt())
    object Sent : MessageStatus(1, 0xFF666666.toInt())
    object Delivered : MessageStatus(2, 0xFF666666.toInt())
    object Seen : MessageStatus(3, 0xFF722F37.toInt())
}
