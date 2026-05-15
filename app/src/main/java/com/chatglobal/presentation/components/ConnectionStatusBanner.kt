package com.chatglobal.presentation.components
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chatglobal.domain.model.ConnectionState
import com.chatglobal.presentation.theme.WineBlue
@Composable fun ConnectionStatusBanner(state: ConnectionState, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(text = state.label, color = WineBlue, fontWeight = if (state is ConnectionState.Connected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Normal) }
}
