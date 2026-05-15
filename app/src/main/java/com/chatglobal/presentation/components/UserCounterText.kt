package com.chatglobal.presentation.components
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chatglobal.presentation.theme.GrayText
@Composable fun UserCounterText(count: Int, isTyping: Boolean = false, modifier: Modifier = Modifier) {
    val txt = if (isTyping) if (count == 1) "1 usuario está escribiendo..." else "$count usuarios están escribiendo..." else if (count == 1) "1 usuario en línea" else "$count usuarios en línea"
    Text(text = txt, color = GrayText, modifier = modifier)
}
