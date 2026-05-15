package com.chatglobal.data.network

import com.google.gson.Gson
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class WebSocketManager(private val token: String) {
    private var ws: WebSocketClient? = null
    private val gson = Gson()

    var onMessage: ((MessageResponse) -> Unit)? = null
    var onStatus: ((String, String) -> Unit)? = null
    var onConnected: (() -> Unit)? = null
    var onDisconnected: (() -> Unit)? = null

    fun connect() {
        if (ws?.isOpen == true) return
        val enc = URLEncoder.encode(token, StandardCharsets.UTF_8.name())
        ws = object : WebSocketClient(URI("${ApiConstants.WS_URL}?token=$enc")) {
            override fun onOpen(handshake: ServerHandshake?) {
                onConnected?.invoke()
            }
            override fun onMessage(message: String?) {
                message?.let { m ->
                    try {
                        val map = gson.fromJson(m, Map::class.java) as? Map<String, Any>
                        when (map?.get("type")) {
                            "message:new" -> onMessage?.invoke(gson.fromJson(m, MessageResponse::class.java))
                            "message:status_updated" -> {
                                val msgId = map["message_id"] as? String ?: return
                                val status = map["global_status"] as? String ?: return
                                onStatus?.invoke(msgId, status)
                            }
                            else -> {} // Rama else para exhaustividad
                        }
                    } catch (e: Exception) {
                        // Ignorar errores de parsing
                    }
                }
            }
            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                onDisconnected?.invoke()
            }            override fun onError(ex: Exception?) {
                onDisconnected?.invoke()
            }
        }
        ws?.connect()
    }

    fun disconnect() {
        ws?.close()
    }

    fun sendMessage(content: String) {
        if (ws?.isOpen == true) {
            ws?.send(gson.toJson(mapOf("type" to "message:send", "content" to content)))
        }
    }

    fun ackDeliver(msgId: String) {
        if (ws?.isOpen == true) {
            ws?.send(gson.toJson(mapOf("type" to "message:ack_deliver", "message_id" to msgId)))
        }
    }
}
