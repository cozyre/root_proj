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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //remove this later, this is only for testing
            CoroutineScope(Dispatchers.IO).launch {
                val repo = AuthRepository(RetrofitClient.instance)
                val result = repo.login("admin@email.com", "your_password")
                when (result) {
                    is Resource.Success -> Log.d("AUTH_TEST", "Token: ${result.data.token}")
                    is Resource.Error   -> Log.d("AUTH_TEST", "Error: ${result.message}")
                    is Resource.Loading -> {}
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RootTheme {
        Greeting("Android")
    }
}