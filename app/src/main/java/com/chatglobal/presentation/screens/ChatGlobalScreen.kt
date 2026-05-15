package com.chatglobal.presentation.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatglobal.presentation.ChatUiState
import com.chatglobal.presentation.ChatViewModel
import com.chatglobal.presentation.components.*import com.chatglobal.R
@Composable fun ChatGlobalScreen(vm: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val cs by vm.connState.collectAsState(); val oc by vm.onlineCount.collectAsState(); val it by vm.isTyping.collectAsState(); val us by vm.uiState.collectAsState(); val im by vm.isLoadingMore.collectAsState()
    var txt by remember { mutableStateOf("") }; val ls = rememberLazyListState()
    LaunchedEffect(Unit) { vm.connect() }
    Column(Modifier.fillMaxSize()) {
        Surface(shadowElevation = 2.dp) { Column(Modifier.padding(12.dp)) { Row(Modifier.fillMaxWidth(), Alignment.CenterVertically) { IconButton({}) { Icon(painter = painterResource(R.drawable.ic_menu_hamburger), "Menú") }; Spacer(Modifier.width(8.dp)); ConnectionStatusBanner(cs) }; Spacer(Modifier.height(4.dp)); UserCounterText(oc, it) } }
        Box(Modifier.weight(1f)) {
            when (val s = us) {
                is ChatUiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator(color = com.chatglobal.presentation.theme.WineBlue) }
                is ChatUiState.Empty -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text("No hay mensajes aún", color = com.chatglobal.presentation.theme.GrayText) }
                is ChatUiState.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Error de conexión", color = com.chatglobal.presentation.theme.WineBlue) }
                is ChatUiState.Success -> LazyColumn(state = ls, Modifier.fillMaxSize().padding(vertical = 8.dp), reverseLayout = true) {
                    items(s.messages, key = { it.id }) { m ->
                        if (m.isSent) SentBubble(m.content, m.timestamp, m.status) else { val idx = s.messages.indexOf(m); val show = idx == s.messages.lastIndex || s.messages[idx + 1].senderId != m.senderId; ReceivedBubble(m.content, m.timestamp, m.senderName, m.senderAvatarUrl, show) }
                    }
                    if (im) item { Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) { CircularProgressIndicator(color = com.chatglobal.presentation.theme.WineBlue) } }
                }
            }
        }
        Surface(shadowElevation = 4.dp) { Row(Modifier.fillMaxWidth().padding(8.dp), Alignment.CenterVertically) { TextField(txt, { txt = it }, { Text("Escribe un mensaje...") }, Modifier.weight(1f), singleLine = true, colors = TextFieldDefaults.colors(focusedContainerColor = com.chatglobal.presentation.theme.White, unfocusedContainerColor = com.chatglobal.presentation.theme.White)); Spacer(Modifier.width(8.dp)); IconButton({ if (txt.isNotBlank()) { vm.sendMessage(txt); txt = "" } }, txt.isNotBlank()) { Icon(painter = painterResource(R.drawable.ic_send), "Enviar") } } }
    }
    LaunchedEffect(ls) { snapshotFlow { ls.layoutInfo.visibleItemsInfo }.collect { v -> if (v.isNotEmpty() && v.first().index == 0 && us is ChatUiState.Success && !im) vm.loadMore((us as ChatUiState.Success).messages.last().id) } }
}
