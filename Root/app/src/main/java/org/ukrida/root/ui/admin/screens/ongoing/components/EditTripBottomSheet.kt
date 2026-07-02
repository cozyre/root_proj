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
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.LeaderOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripBottomSheet(
    currentTitle: String,
    currentDescription: String,

    currentMentorId: Int?,
    currentCoordinatorId: Int?,

    mentorOptions: List<LeaderOption>,
    coordinatorOptions: List<LeaderOption>,

    onSave: (
        title: String,
        description: String,
        mentorId: Int,
        coordinatorId: Int
    ) -> Unit,
    onClose: () -> Unit
) {

    var title by remember {
        mutableStateOf(currentTitle)
    }

    var description by remember {
        mutableStateOf(currentDescription)
    }

    var selectedMentor by remember(
        currentMentorId,
        mentorOptions
    ) {
        mutableStateOf(
            mentorOptions.find {
                it.id == currentMentorId
            }
        )
    }

    var selectedCoordinator by remember(
        currentCoordinatorId,
        coordinatorOptions
    ) {
        mutableStateOf(
            coordinatorOptions.find {
                it.id == currentCoordinatorId
            }
        )
    }

    var validationError by remember {
        mutableStateOf<String?>(null)
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

            selectedValue =
                selectedMentor?.name ?: "",

            items =
                mentorOptions.map { it.name },

            onSelected = { name ->

                selectedMentor =
                    mentorOptions.find {
                        it.name == name
                    }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LeaderDropdown(
            label = "Coordinator",

            selectedValue =
                selectedCoordinator?.name ?: "",

            items =
                coordinatorOptions.map { it.name },

            onSelected = { name ->

                selectedCoordinator =
                    coordinatorOptions.find {
                        it.name == name
                    }
            }
        )

        if (validationError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = validationError!!,
                color = Color(0xFFD9534F),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val mentorId = selectedMentor?.id
                val coordinatorId = selectedCoordinator?.id

                validationError = when {
                    mentorId == null && coordinatorId == null ->
                        "Pilih mentor dan koordinator terlebih dahulu"
                    mentorId == null ->
                        "Pilih mentor terlebih dahulu"
                    coordinatorId == null ->
                        "Pilih koordinator terlebih dahulu"
                    else -> null
                }

                if (mentorId != null && coordinatorId != null) {
                    onSave(
                        title,
                        description,
                        mentorId,
                        coordinatorId
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MainButton
            ),
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(
                text = "Save",
                color = H1Color
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}