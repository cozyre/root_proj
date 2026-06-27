package org.ukrida.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.ukrida.root.ui.Public.screens.login.components.AuthNavigation
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //remove this later, this is only for testing
            setContent {
                AuthNavigation()
            }
        }
    }
}