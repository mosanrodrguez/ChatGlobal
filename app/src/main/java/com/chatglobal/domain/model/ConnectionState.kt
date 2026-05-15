package com.chatglobal.domain.model
sealed class ConnectionState(val label: String) {
    object Waiting : ConnectionState("Esperando red...")
    object Connecting : ConnectionState("Conectando...")
    object Syncing : ConnectionState("Actualizando...")
    object Connected : ConnectionState("ChatGlobal")
}
