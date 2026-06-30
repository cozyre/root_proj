package org.ukrida.root.ui.admin.screens.ongoing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.*
import org.ukrida.root.data.dummy.DummyMemberData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripBottomSheet(
    currentTitle: String,
    currentDescription: String,
    currentMentor: String,
    currentCoordinator: String,
    onClose: () -> Unit
) {

    var title by remember {
        mutableStateOf(currentTitle)
    }

    var description by remember {
        mutableStateOf(currentDescription)
    }

    var mentor by remember {
        mutableStateOf(currentMentor)
    }

    var coordinator by remember {
        mutableStateOf(currentCoordinator)
    }
    val mentorList = remember {
        DummyMemberData.members
            .filter { it.role == "mentor" }
            .map { "${it.firstName} ${it.lastName}" }
    }
    val coordinatorList = remember {
        DummyMemberData.members
            .filter { it.role == "coordinator" }
            .map { "${it.firstName} ${it.lastName}" }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding()
    ) {

        Text(
            text = "Edit Trip",
            color = H1Color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text(
                    text = "Title"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = BackgroundDark,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = BackgroundDark,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = BackgroundDark,

                cursorColor = TitleColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Description")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = BackgroundDark,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = BackgroundDark,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = BackgroundDark,

                cursorColor = TitleColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LeaderDropdown(
            label = "Mentor",
            selectedValue = mentor,
            items = mentorList,
            onSelected = {
                mentor = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LeaderDropdown(
            label = "Coordinator",
            selectedValue = coordinator,
            items = coordinatorList,
            onSelected = {
                coordinator = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // TODO SAVE
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MainButton),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save", color = H1Color)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}