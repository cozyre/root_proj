package org.ukrida.root.ui.admin.screens.newtrip.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewDailyBreadViewModel
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun NewDailyBreadScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: NewDailyBreadViewModel
) {
    val dailyBreadList by viewModel.dailyBreadList.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val availableDays by viewModel.availableDays.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val submitSuccess by viewModel.submitSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    val currentItem = dailyBreadList.find {
        it.day == selectedDay
    }

    var showDayDropdown by remember {
        mutableStateOf(false)
    }

    val titleFocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    LaunchedEffect(submitSuccess) {
        if (submitSuccess) {
            Toast.makeText(
                context,
                "Berhasil membuat tour baru",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Dashboard.route) {
                    inclusive = false
                }

                launchSingleTop = true
            }

            viewModel.resetSubmitSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(
            title = "ADD DAILY BREAD",
            onMenuClick = onMenuClick
        )

        if (isLoading && dailyBreadList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MainButton
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "< Back",
                        color = TitleColor,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TimelineBar(
                    onStageClick = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box {
                    OutlinedButton(
                        onClick = {
                            showDayDropdown = true
                        },
                        modifier = Modifier
                            .width(110.dp)
                            .height(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MainButton,
                            contentColor = H1Color
                        ),
                        border = null,
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 0.dp
                        )
                    ) {
                        Text(
                            text = "DAY $selectedDay",
                            style = MaterialTheme.typography.titleLarge,
                            color = H1Color,
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = H1Color
                        )
                    }

                    DropdownMenu(
                        expanded = showDayDropdown,
                        onDismissRequest = {
                            showDayDropdown = false
                        },
                        containerColor = DrawerBackground
                    ) {
                        if (availableDays.isEmpty()) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Day belum tersedia",
                                        color = H1Color
                                    )
                                },
                                onClick = {
                                    showDayDropdown = false
                                }
                            )
                        } else {
                            availableDays.forEach { day ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "DAY $day",
                                            color = H1Color
                                        )
                                    },
                                    onClick = {
                                        viewModel.selectDay(day)
                                        showDayDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "DAILY BREAD",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = currentItem?.date ?: "",
                    color = BodyColor,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = currentItem?.title ?: "",
                    onValueChange = {
                        viewModel.updateTitle(
                            day = selectedDay,
                            value = it
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Title here",
                            color = Color(0xFF6B5C4E),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(titleFocusRequester),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = H1Color,
                        unfocusedTextColor = H1Color,
                        focusedBorderColor = TitleColor,
                        unfocusedBorderColor = Color(0xFF6B5C4E),
                        focusedContainerColor = Color(0xFF2A2018),
                        unfocusedContainerColor = Color(0xFF2A2018)
                    ),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = H1Color,
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = currentItem?.content ?: "",
                    onValueChange = {
                        viewModel.updateContent(
                            day = selectedDay,
                            value = it
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Tulis renungan harian di sini...",
                            color = Color(0xFF6B5C4E),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 300.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BodyColor,
                        unfocusedTextColor = BodyColor,
                        focusedBorderColor = TitleColor,
                        unfocusedBorderColor = Color(0xFF6B5C4E),
                        focusedContainerColor = Color(0xFF2A2018),
                        unfocusedContainerColor = Color(0xFF2A2018)
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.submitDailyBread()
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
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
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}