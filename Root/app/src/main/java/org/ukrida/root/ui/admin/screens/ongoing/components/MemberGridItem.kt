package org.ukrida.root.ui.admin.screens.ongoing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.admin.screens.ongoing.model.Member

@Composable
fun MemberGridItem(
    member: Member,
    onRemoveClick: (Member) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(modifier = Modifier.size(64.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE6DCD2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = member.avatarRes),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            // Tombol minus merah
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFFD9534F), CircleShape)
                    .align(Alignment.TopEnd)
                    .clickable { onRemoveClick(member) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "-", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = member.name, color = Color.White, fontSize = 12.sp)
    }
}