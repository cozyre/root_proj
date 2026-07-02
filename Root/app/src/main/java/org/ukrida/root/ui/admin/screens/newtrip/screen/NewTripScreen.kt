package org.ukrida.root.ui.admin.screens.newtrip.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.LeaderOptionUiState
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewTripViewModel
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor
import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NewTripScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: NewTripViewModel
) {
    val form by viewModel.form.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val createdTripId by viewModel.createdTripId.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
    }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun showDatePicker(
        currentValue: String,
        onDateSelected: (String) -> Unit
    ) {
        val currentDate = try {
            LocalDate.parse(currentValue, dateFormatter)
        } catch (e: Exception) {
            LocalDate.now()
        }

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(
                    year,
                    month + 1,
                    dayOfMonth
                )

                onDateSelected(selectedDate.format(dateFormatter))
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    LaunchedEffect(createdTripId) {
        createdTripId?.let { tripId ->
            viewModel.clearCreatedTripId()

            navController.navigate("new_itinerary/$tripId") {
                popUpTo("new_trip") {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            title = "NEW TRIP",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Promised Land > Create New Trip",
                color = BodyColor,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            TimelineBar(
                onStageClick = {}
            )

            Spacer(modifier = Modifier.height(28.dp))

            SectionTitle(text = "TITLE")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Specify your information for user",
                color = BodyColor,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            NewTripTextField(
                value = form.title,
                onValueChange = viewModel::updateTitle,
                placeholder = "Trip title",
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            NewTripTextField(
                value = form.description,
                onValueChange = viewModel::updateDescription,
                placeholder = "Trip description",
                minHeight = 140.dp
            )

            Spacer(modifier = Modifier.height(18.dp))

            SectionTitle(text = "DATE")

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DatePickerField(
                    value = form.startDate,
                    placeholder = "Start date",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showDatePicker(
                            currentValue = form.startDate,
                            onDateSelected = viewModel::updateStartDate
                        )
                    }
                )

                DatePickerField(
                    value = form.endDate,
                    placeholder = "End date",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showDatePicker(
                            currentValue = form.endDate,
                            onDateSelected = viewModel::updateEndDate
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "CARD PICTURE",
                color = H1Color,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        width = 1.dp,
                        color = TitleColor,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedImageUri?.let { "IMAGE SELECTED" } ?: "INSERT HERE",
                    color = H1Color,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "PRICE",
                color = H1Color,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            NewTripTextField(
                value = form.pricePlaceholder,
                onValueChange = {},
                placeholder = "xxx.xxx.xxx.xxx",
                singleLine = true,
                enabled = false
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "MENTOR",
                color = H1Color,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            LeaderDropdown(
                selectedName = form.selectedMentorName.ifBlank { "Select mentor" },
                leaders = form.mentorOptions,
                onLeaderSelected = { leader ->
                    viewModel.updateSelectedMentor(
                        id = leader.id,
                        name = leader.name
                    )
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "COORDINATOR",
                color = H1Color,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            LeaderDropdown(
                selectedName = form.selectedCoordinatorName.ifBlank { "Select coordinator" },
                leaders = form.coordinatorOptions,
                onLeaderSelected = { leader ->
                    viewModel.updateSelectedCoordinator(
                        id = leader.id,
                        name = leader.name
                    )
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "OPTIONAL DETAILS",
                color = H1Color,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            NewTripTextField(
                value = form.location,
                onValueChange = viewModel::updateLocation,
                placeholder = "Location",
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            NewTripTextField(
                value = form.dresscode,
                onValueChange = viewModel::updateDresscode,
                placeholder = "Dresscode",
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            NewTripTextField(
                value = form.meetupTime,
                onValueChange = viewModel::updateMeetupTime,
                placeholder = "Meetup time, contoh 08:00",
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            NewTripTextField(
                value = form.meetupAddress,
                onValueChange = viewModel::updateMeetupAddress,
                placeholder = "Meetup address"
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                    viewModel.submitGeneralInformation()
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainButton
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = H1Color,
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "SUBMIT",
                        color = H1Color,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionTitle(
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = H1Color,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
private fun NewTripTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = false,
    minHeight: Dp = 48.dp,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = singleLine,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFB8A48E),
                fontSize = 12.sp
            )
        },
        modifier = modifier.heightIn(min = minHeight),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = H1Color,
            unfocusedTextColor = H1Color,
            disabledTextColor = H1Color,
            focusedBorderColor = TitleColor,
            unfocusedBorderColor = TitleColor,
            disabledBorderColor = TitleColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    )
}
@Composable
private fun DatePickerField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFB8A48E),
                    fontSize = 12.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = H1Color,
                disabledBorderColor = TitleColor,
                disabledContainerColor = Color.Transparent,
                disabledPlaceholderColor = Color(0xFFB8A48E)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeaderDropdown(
    selectedName: String,
    leaders: List<LeaderOptionUiState>,
    onLeaderSelected: (LeaderOptionUiState) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = H1Color,
                unfocusedTextColor = H1Color,
                focusedBorderColor = TitleColor,
                unfocusedBorderColor = TitleColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.background(DrawerBackground)
        ) {
            if (leaders.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Data belum tersedia",
                            color = H1Color
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
            } else {
                leaders.forEach { leader ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = leader.name,
                                color = H1Color
                            )
                        },
                        onClick = {
                            onLeaderSelected(leader)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}