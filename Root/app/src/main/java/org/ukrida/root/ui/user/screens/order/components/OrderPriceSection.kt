package org.ukrida.root.ui.user.screens.order.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import org.ukrida.root.ui.theme.DrawerBackground

@Composable
fun OrderPriceSection(
    price: String
) {
    Column(
        horizontalAlignment = Alignment.End
    ){
        Text(
            text = price,
            style = MaterialTheme.typography.titleLarge,
            color = DrawerBackground,
        )
    }
}