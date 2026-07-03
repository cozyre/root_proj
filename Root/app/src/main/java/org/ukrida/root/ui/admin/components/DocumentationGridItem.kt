package org.ukrida.root.ui.admin.components

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
import org.ukrida.root.ui.admin.screens.finished.viewmodel.DocumentationUiModel
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun DocumentationGridItem(
    documentation: DocumentationUiModel,
    onRemoveClick: (DocumentationUiModel) -> Unit = {},
    showRemoveButton: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
    ) {
        if (!documentation.imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = documentation.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (showRemoveButton) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .background(
                        Color(0xFFD9534F),
                        CircleShape
                    )
                    .align(Alignment.TopEnd)
                    .clickable {
                        onRemoveClick(documentation)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "-",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}