package org.ukrida.root.ui.admin.screens.trip.screen

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.components.TimelineBar
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.trip.viewmodel.NewTripViewModel
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor
import java.util.Calendar

@Composable
fun NewTripScreen(
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    onItineraryClick: () -> Unit,
    viewModel: NewTripViewModel
) {

    val context = LocalContext.current

    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            viewModel.updateImage(uri)
        }

    fun showDatePicker(
        onDateSelected: (String) -> Unit
    ) {

        val calendar = Calendar.getInstance()

        DatePickerDialog(
            context,
            { _, year, month, day ->

                val date =
                    "%02d/%02d/%04d".format(
                        day,
                        month + 1,
                        year
                    )

                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        TopBar(
            title = "NEW TRIP",
            onMenuClick = onMenuClick
        )

        TimelineBar(
            onStageClick = { stage ->

                when (stage) {

                    "General Information" -> {
                        // tetap di screen ini
                    }

                    "Itinerary" -> {
                        onItineraryClick()
                    }

                    "Hymn for Him" -> {
                        // navigate hymn
                    }

                    "Daily Bread" -> {
                        // navigate daily bread
                    }
                }
            },
            modifier = Modifier.padding(
                horizontal = 24.dp
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            Text(
                text = "TITLE",
                style = MaterialTheme.typography.titleMedium,
                color = H1Color
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = viewModel::updateTitle,
                placeholder = {
                    Text("Type here")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedColors()
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "TRIP DATE",
                style = MaterialTheme.typography.titleMedium,
                color = H1Color
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    OutlinedTextField(
                        value = viewModel.startDate,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Start")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                showDatePicker(
                                    viewModel::updateStartDate
                                )
                            }
                    )
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    OutlinedTextField(
                        value = viewModel.endDate,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("End")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedColors()
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                showDatePicker(
                                    viewModel::updateEndDate
                                )
                            }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "CARD PICTURE",
                style = MaterialTheme.typography.titleMedium,
                color = H1Color
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .border(
                        width = 1.dp,
                        color = TitleColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {

                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts
                                    .PickVisualMedia
                                    .ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {

                if (viewModel.imageUri != null) {

                    val bitmap =
                        viewModel.imageUri?.let { uri ->

                            context.contentResolver
                                .openInputStream(uri)
                                ?.use { stream ->
                                    BitmapFactory.decodeStream(stream)
                                }
                        }

                    bitmap?.let {

                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                } else {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = null,
                            tint = H1Color,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "INSERT HERE",
                            color = H1Color
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "PRICE",
                style = MaterialTheme.typography.titleMedium,
                color = H1Color
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = viewModel.price,
                onValueChange = viewModel::updatePrice,
                placeholder = {
                    Text("Input price")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedColors()
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Button(
                onClick = {
                    // submit
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainButton
                )
            ) {

                Text(
                    text = "SUBMIT",
                    color = H1Color
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

@Composable
private fun outlinedColors() =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = BodyColor,
        unfocusedTextColor = BodyColor,

        focusedBorderColor = TitleColor,
        unfocusedBorderColor = DrawerBackground,

        focusedLabelColor = TitleColor,
        unfocusedLabelColor = DrawerBackground,

        cursorColor = TitleColor
    )