package com.chatglobal.presentation.screens
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickableimport androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chatglobal.presentation.components.AvatarPlaceholder
import com.chatglobal.presentation.theme.WineBlue
import com.chatglobal.presentation.theme.White
import com.chatglobal.R
@Composable fun RegisterScreen(onReg: (String, String, Uri?) -> Unit, back: () -> Unit) {
    var name by remember { mutableStateOf("") }; var pass by remember { mutableStateOf("") }; var uri by remember { mutableStateOf<Uri?>(null) }
    val pick = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri = it }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth().padding(bottom = 24.dp), verticalAlignment = Alignment.CenterVertically) { IconButton(back) { Icon(painter = painterResource(R.drawable.ic_arrow_back), "Regresar") } }
        Box(Modifier.size(100.dp).clip(androidx.compose.foundation.shape.CircleShape).clickable { pick.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, Alignment.Center) {
            if (uri != null) AsyncImage(uri, "Avatar", Modifier.fillMaxSize(), ContentScale.Crop) else AvatarPlaceholder(if (name.isNotBlank()) name else "?", 100)
            Icon(painter = painterResource(R.drawable.ic_camera), "Foto", Color.White, Modifier.size(32.dp).align(Alignment.BottomEnd))
        }
        Spacer(Modifier.height(8.dp)); Text("Agregar foto de perfil", color = com.chatglobal.presentation.theme.GrayText, fontSize = 12.sp); Spacer(Modifier.height(32.dp))
        OutlinedTextField(name, { name = it }, { Text("Nombre completo") }, Modifier.fillMaxWidth().padding(bottom = 16.dp), singleLine = true)
        OutlinedTextField(pass, { pass = it }, { Text("Contraseña") }, Modifier.fillMaxWidth().padding(bottom = 24.dp), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(KeyboardType.Password), singleLine = true)
        Button({ if (name.isNotBlank() && pass.isNotBlank()) onReg(name, pass, uri) }, Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(WineBlue)) { Text("Registrarse", color = White) }
    }
}
