package com.chatglobal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatglobal.presentation.theme.White

@Composable
fun AvatarPlaceholder(
    name: String,
    modifier: Modifier = Modifier,
    size: Int = 40,
    bg: Color = Color(0xFF722F37)
) {
    val initial = name.firstOrNull()?.uppercase() ?: "?"
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = White,            fontSize = (size * 0.4).sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
