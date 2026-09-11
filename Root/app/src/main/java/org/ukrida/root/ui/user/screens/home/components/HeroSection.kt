package org.ukrida.root.ui.user.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun HeroSection() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "R",
            style = MaterialTheme.typography.headlineLarge,
            color = TitleColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ROOT",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Walk together in faith and experience unforgettable spiritual journeys with ROOT.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor,
            textAlign = TextAlign.Center
        )
    }
}