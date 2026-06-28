package org.ukrida.root.ui.admin.screens.ongoing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.admin.screens.ongoing.model.Documentation

@Composable
fun DocumentationGridItem(
    documentation: Documentation,
    onRemoveClick: (Documentation) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(20.dp)
                .background(Color(0xFFD9534F), CircleShape)
                .align(Alignment.TopEnd)
                .clickable { onRemoveClick(documentation) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}