package com.chatglobal.domain.model
sealed class MessageStatus(val iconRes: Int, val color: Int) {    object Sending : MessageStatus(R.drawable.ic_clock_outline, 0xFF666666.toInt())
    object Sent : MessageStatus(R.drawable.ic_check_single, 0xFF666666.toInt())
    object Delivered : MessageStatus(R.drawable.ic_check_double, 0xFF666666.toInt())
    object Seen : MessageStatus(R.drawable.ic_check_double, 0xFF722F37.toInt())
}
