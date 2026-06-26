package org.ukrida.root.ui.admin.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.LogoutButton
import org.ukrida.root.ui.theme.*

@Composable
fun CreateTourCard(
    onCreateTourClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DrawerBackground,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "CREATE NEW TOUR!",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onCreateTourClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = LogoutButton
            ),
            shape = RoundedCornerShape(50)
        ) {

            Text(
                text = "CREATE NEW TOUR",
                color = H1Color
            )
        }
    }
}