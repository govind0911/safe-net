package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screen.HomeScreen
import com.example.ui.screen.HomeViewModel
import com.example.ui.theme.SafeNetTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SafeNetTheme {
        val homeViewModel: HomeViewModel = viewModel()
        HomeScreen(
          viewModel = homeViewModel,
          modifier = Modifier.fillMaxSize()
        )
      }
    }
  }
}
