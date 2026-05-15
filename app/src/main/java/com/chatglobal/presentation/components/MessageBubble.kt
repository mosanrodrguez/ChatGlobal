package com.chatglobal.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatglobal.domain.model.MessageStatusimport com.chatglobal.presentation.theme.*
@Composable fun ReceivedBubble(msg: String, ts: String, name: String, avatar: String?, showInfo: Boolean, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 8.dp, end = 60.dp, top = 4.dp, bottom = 4.dp)) {
        if (showInfo) Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 2.dp)) { AvatarPlaceholder(name = name, size = 20); Spacer(Modifier.width(4.dp)); Text(text = name, fontSize = 11.sp, color = GrayText) }
        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(WineBlue).padding(horizontal = 12.dp, vertical = 8.dp)) { Column { Text(text = msg, color = White, fontSize = 14.sp); Spacer(Modifier.height(4.dp)); Text(text = ts, color = White.copy(alpha = 0.8f), fontSize = 10.sp, modifier = Modifier.align(Alignment.End)) } }
    }
}
@Composable fun SentBubble(msg: String, ts: String, status: MessageStatus, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(start = 60.dp, end = 8.dp, top = 4.dp, bottom = 4.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(White).padding(horizontal = 12.dp, vertical = 8.dp)) {
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                Column(horizontalAlignment = Alignment.End) { Text(text = msg, color = BlackText, fontSize = 14.sp); Spacer(Modifier.height(4.dp)); Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) { Icon(painter = painterResource(id = status.iconRes), contentDescription = null, tint = androidx.compose.ui.graphics.Color(status.color), modifier = Modifier.size(14.dp)); Spacer(Modifier.width(2.dp)); Text(text = ts, color = GrayText, fontSize = 10.sp) } }
            }
        }
    }
}
