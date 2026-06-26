package org.ukrida.root

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
import org.ukrida.root.ui.theme.RootTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.utils.Resource
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlexDirection.Companion.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.ukrida.root.ui.admin.navigation.AppNavigation
import org.ukrida.root.ui.admin.screens.RootScreen
import org.ukrida.root.ui.theme.Inter
import org.ukrida.root.ui.admin.screens.RootScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RootTheme {
                RootScreen()

            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(16.dp)
    ) {

        Text(
            text = "TEST INTER",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "INTER TEST",
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp
        )

    }
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RootTheme {
            Greeting("Android")
        }
    }
