package com.chatglobal.presentation
import android.app.Application
import android.net.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.chatglobal.data.network.RetrofitClient
import com.chatglobal.data.repository.ChatRepository
import com.chatglobal.domain.model.ConnectionState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val token = "TOKEN_PLACEHOLDER"
    private val repo = ChatRepository(application, token)
    private val conn = application.getSystemService(ConnectivityManager::class.java)
    private val _connState = MutableStateFlow<ConnectionState>(ConnectionState.Waiting)
    val connState = _connState.asStateFlow()
    private val _onlineCount = MutableStateFlow(0)
    val onlineCount = _onlineCount.asStateFlow()
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()
    private val netCb = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { sync(true) }
        override fun onLost(network: Network) { sync(false) }
    }
    init {
        val req = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        conn.registerNetworkCallback(req, netCb)
        viewModelScope.launch { repo.observeMessages().collect { list -> _uiState.value = if (list.isEmpty()) ChatUiState.Empty else ChatUiState.Success(list) } }
        setupWs()
    }
    fun connect() { sync(true) }
    fun disconnect() { repo.disconnectWs(); runCatching { conn.unregisterNetworkCallback(netCb) } }
    fun loadInitial() { viewModelScope.launch { repo.sync() } }
    fun loadMore(cursor: String) { viewModelScope.launch { _isLoadingMore.value = true; repo.sync(cursor); _isLoadingMore.value = false } }
    fun sendMessage(text: String) { viewModelScope.launch { repo.sendLocal(text) } }
    private fun sync(online: Boolean) { if (online) { _connState.value = ConnectionState.Syncing; repo.connectWs(); _connState.value = ConnectionState.Connected; loadInitial() } else { repo.disconnectWs(); _connState.value = ConnectionState.Waiting } }    private fun setupWs() { val w = repo.getWs(); w.onConnected = { _connState.value = ConnectionState.Connected; loadInitial() }; w.onDisconnected = { _connState.value = ConnectionState.Waiting }; w.onMessage = { loadInitial() } }
}
