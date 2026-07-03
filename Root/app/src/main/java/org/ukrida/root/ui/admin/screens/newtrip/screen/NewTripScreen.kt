package org.ukrida.root.ui.admin.screens.newtrip.screen

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.LeaderOptionUiState
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewTripViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.TitleColor
import org.ukrida.root.utils.uriToTempFile
import java.io.File
import java.util.Calendar

@Composable
fun NewTripScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: NewTripViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val form = uiState.form
    val context = LocalContext.current

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var selectedImageFile by remember {
        mutableStateOf<File?>(null)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                selectedImageFile = uriToTempFile(context, uri)
            }
        }
    )

    LaunchedEffect(uiState.isSuccess, uiState.createdTripId) {
        val tripId = uiState.createdTripId

        if (uiState.isSuccess && tripId != null) {
            navController.navigate("new_itinerary/$tripId") {
                popUpTo(Screen.NewTrip.route) {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(
            title = "NEW TRIP",
            onMenuClick = onMenuClick
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Create New Trip",
                        color = H1Color,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Upload hero image and complete the trip information.",
                        color = BodyColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                TripHeroImagePicker(
                    imageUri = selectedImageUri,
                    onClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
            }

            item {
                NewTripSection(title = "Basic Information") {
                    NewTripTextField(
                        label = "Trip Title",
                        value = form.title,
                        onValueChange = viewModel::updateTitle
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NewTripTextField(
                        label = "Description",
                        value = form.description,
                        onValueChange = viewModel::updateDescription,
                        minLines = 4
                    )
                }
            }

            item {
                NewTripSection(title = "Date & Location") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            NewTripDateField(
                                label = "Start Date",
                                value = form.startDate,
                                onClick = {
                                    openDatePicker(context) { selectedDate ->
                                        viewModel.updateStartDate(selectedDate)
                                    }
                                }
                            )
                        }

                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            NewTripDateField(
                                label = "End Date",
                                value = form.endDate,
                                onClick = {
                                    openDatePicker(context) { selectedDate ->
                                        viewModel.updateEndDate(selectedDate)
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    NewTripTextField(
                        label = "Location",
                        value = form.location,
                        onValueChange = viewModel::updateLocation
                    )
                }
            }

            item {
                NewTripSection(title = "Meeting Details") {
                    NewTripTextField(
                        label = "Dresscode",
                        value = form.dresscode,
                        onValueChange = viewModel::updateDresscode
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NewTripTextField(
                        label = "Meetup Time",
                        value = form.meetupTime,
                        onValueChange = viewModel::updateMeetupTime,
                        placeholder = "Example: 08:00"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NewTripTextField(
                        label = "Meetup Address",
                        value = form.meetupAddress,
                        onValueChange = viewModel::updateMeetupAddress,
                        minLines = 2
                    )
                }
            }

            item {
                NewTripSection(title = "Trip Leaders") {
                    LeaderDropdownField(
                        label = "Mentor",
                        options = uiState.mentorOptions,
                        selectedId = form.mentorId,
                        onSelected = viewModel::updateMentorId
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LeaderDropdownField(
                        label = "Coordinator",
                        options = uiState.coordinatorOptions,
                        selectedId = form.coordinatorId,
                        onSelected = viewModel::updateCoordinatorId
                    )
                }
            }

            item {
                Column {
                    uiState.errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color(0xFFFF6B6B),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Button(
                        onClick = {
                            viewModel.createTrip(
                                imageFile = selectedImageFile
                            )
                        },
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DrawerBackground,
                            contentColor = TitleColor
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = TitleColor,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Create Trip",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NewTripSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundDark
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                color = H1Color,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
private fun TripHeroImagePicker(
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF4A4A4A))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected Hero Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Insert Hero Image Here",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Tap to choose from gallery",
                    color = Color.White.copy(alpha = 0.75f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun NewTripTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            if (placeholder.isNotBlank()) {
                Text(text = placeholder)
            }
        },
        minLines = minLines,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = newTripTextFieldColors()
    )
}

@Composable
private fun NewTripDateField(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = BodyColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = newTripTextFieldColors()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    onClick()
                }
        )
    }
}

@Composable
private fun LeaderDropdownField(
    label: String,
    options: List<LeaderOptionUiState>,
    selectedId: Int?,
    onSelected: (Int) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val selectedName = options
        .firstOrNull { option ->
            option.id == selectedId
        }
        ?.name
        .orEmpty()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
    ) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = BodyColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = newTripTextFieldColors()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    expanded = true
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 260.dp)
        ) {
            if (options.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(text = "No data")
                    },
                    onClick = {
                        expanded = false
                    }
                )
            } else {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option.name)
                        },
                        onClick = {
                            onSelected(option.id)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun newTripTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = H1Color,
    unfocusedTextColor = H1Color,
    focusedLabelColor = TitleColor,
    unfocusedLabelColor = BodyColor,
    focusedPlaceholderColor = BodyColor,
    unfocusedPlaceholderColor = BodyColor,
    cursorColor = TitleColor,
    focusedBorderColor = TitleColor,
    unfocusedBorderColor = BodyColor,
    focusedTrailingIconColor = BodyColor,
    unfocusedTrailingIconColor = BodyColor
)

private fun openDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = "%04d-%02d-%02d".format(
                year,
                month + 1,
                dayOfMonth
            )

            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}