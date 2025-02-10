package dev.gafilianog.tikil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.gafilianog.tikil.ui.screens.TikilHadirScreen
import dev.gafilianog.tikil.ui.theme.TikilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TikilTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TikilHadirScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
