package com.chatglobal.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatglobal.presentation.theme.WineBlue
import com.chatglobal.presentation.theme.White

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    toReg: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ChatGlobal", fontSize = 32.sp, color = WineBlue, modifier = Modifier.padding(bottom = 48.dp))
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        
        Button(            onClick = { if (name.isNotBlank() && pass.isNotBlank()) onLogin(name, pass) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WineBlue)
        ) {
            Text("Iniciar Sesión", color = White)
        }
        
        Spacer(Modifier.height(16.dp))
        TextButton(onClick = toReg) {
            Text("¿No tienes cuenta? Regístrate", color = WineBlue)
        }
    }
}
