package org.ukrida.root.ui.admin.screens.approval.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.admin.components.*

@Composable
fun ApprovalScreen(onMenuClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        Row(){
            TopBar(
                title = "Approval",
                onMenuClick = {
                    onMenuClick()
                }
            )

        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {

        }
    }
}