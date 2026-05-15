package com.chatglobal.presentation
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chatglobal.presentation.screens.*
sealed class Screen(val route: String) { object Login : Screen("login"); object Register : Screen("register"); object ChatGlobal : Screen("chat_global") }
@Composable fun AppNavHost(nav: NavHostController, start: String) {
    com.chatglobal.presentation.theme.ChatGlobalTheme {
        NavHost(nav, start) {
            composable(Screen.Login.route) { LoginScreen(onLogin = { _,_ -> nav.navigate(Screen.ChatGlobal.route) { popUpTo(Screen.Login.route) { inclusive = true } } }, toReg = { nav.navigate(Screen.Register.route) }) }
            composable(Screen.Register.route) { RegisterScreen(onReg = { _,_,_ -> nav.navigate(Screen.ChatGlobal.route) { popUpTo(Screen.Register.route) { inclusive = true } } }, back = { nav.popBackStack() }) }
            composable(Screen.ChatGlobal.route) { ChatGlobalScreen(viewModel = viewModel()) }
        }
    }
}
