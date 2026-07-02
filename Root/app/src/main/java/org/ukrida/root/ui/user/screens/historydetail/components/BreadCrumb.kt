package org.ukrida.root.ui.user.screens.historydetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun Breadcrumb(
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Promised Land",
            modifier = Modifier.clickable {
                navController.navigate("promised_land")
            },
            color = Color.White
        )

        Text(
            text = " > ",
            color = Color.White
        )

        Text(
            text = "History",
            modifier = Modifier.clickable {
                navController.popBackStack() // or navigate("history")
            },
            color = Color.White
        )

        Text(
            text = " > ",
            color = Color.White
        )

        Text(
            text = "History Detail",
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}