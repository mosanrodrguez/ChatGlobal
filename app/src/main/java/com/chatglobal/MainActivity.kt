package com.chatglobal
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chatglobal.presentation.AppNavHost
import com.chatglobal.presentation.Screen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Surface(modifier = Modifier.fillMaxSize()) { AppNavHost(nav = rememberNavController(), start = Screen.Login.route) } }
    }
}
