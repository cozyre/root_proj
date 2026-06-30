package org.ukrida.root.ui.Public.screens.order.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            color = Color(0xFFE8C16B),
        )
    }
}