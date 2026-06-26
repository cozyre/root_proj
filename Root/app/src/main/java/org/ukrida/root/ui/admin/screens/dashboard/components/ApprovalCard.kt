package org.ukrida.root.ui.admin.screens.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.theme.*

@Composable
fun ApprovalCard() {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = "👤")

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {

            Text(text= "NAME", color = H1Color)

            Text("Want to be part of ...", color = BodyColor)
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors
                (containerColor = ApproveButton),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(30.dp)
                .width(60.dp),
            shape = RoundedCornerShape(50.dp),

        ) {
            Text(text = "APPROVE",
                color = ApproveText,
                fontSize = 8.sp,
                textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors
                (containerColor = RejectButton),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(30.dp)
                .width(60.dp),
            shape = RoundedCornerShape(50.dp),
        ) {
            Text(
                text = "REJECT",
                color = RejectText,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,)
        }
    }
}