package com.chatglobal.data.network
object ApiConstants {
    const val BASE_URL = "https://chat-globalbeta.onrender.com/api"
    const val WS_URL = "wss://chat-globalbeta.onrender.com/ws"
    const val ENDPOINT_REGISTER = "auth/register"
    const val ENDPOINT_LOGIN = "auth/login"
    const val ENDPOINT_MESSAGES = "messages"
    const val ENDPOINT_USERS = "users"
    const val ENDPOINT_UPLOAD = "upload"
    const val PARAM_LIMIT = "limit"
    const val PARAM_CURSOR = "cursor"
    const val DEFAULT_LIMIT = 15
}
