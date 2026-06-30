package org.ukrida.root.ui.Public.screens.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.Public.components.PublicBottomNavigation
import org.ukrida.root.ui.Public.components.PublicDestination
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardTopBar
import org.ukrida.root.ui.Public.screens.profile.components.*
import org.ukrida.root.ui.Public.screens.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val viewModel: ProfileViewModel = viewModel()

    val profile by viewModel.profile.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val bio by viewModel.bio.collectAsState()
    val hidePhone by viewModel.hidePhone.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.PROFILE,
                onNavigate = { destination ->
                    when(destination){
                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)
                        PublicDestination.PROMISED_LAND ->
                            navController.navigate(PublicScreen.PromisedLand.route)
                        PublicDestination.GROUP ->
                            navController.popBackStack()
                        PublicDestination.PROFILE -> {}
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            profile?.let { profile ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DashboardTopBar(
                        title = "PROFILE",
                        expanded = expanded,
                        onExpandClick = {
                            expanded = !expanded
                        }
                    )
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        ProfileHeader(
                            profile = profile,
                            onChangePhotoClick = {
                                // TODO Backend Integration
                                // Open Image Picker
                            }
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Row {
                            ProfileTextField(
                                label = "First Name",
                                value = firstName,
                                onValueChange = viewModel::onFirstNameChange,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            ProfileTextField(
                                label = "Last Name",
                                value = lastName,
                                onValueChange = viewModel::onLastNameChange,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileTextField(
                            label = "Username",
                            value = username,
                            onValueChange = viewModel::onUsernameChange
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileTextField(
                            label = "Email",
                            value = email,
                            onValueChange = {},
                            readOnly = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileTextField(
                            label = "Phone Number",
                            value = phone,
                            onValueChange = viewModel::onPhoneChange
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        HidePhoneCheckbox(
                            checked = hidePhone,
                            onCheckedChange = viewModel::onHidePhoneChange
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        BioTextField(
                            value = bio,
                            onValueChange = viewModel::onBioChange
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        SaveButton(
                            isLoading = isSaving,
                            onClick = {
                                viewModel.saveProfile()
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        LogoutButton(
                            onLogout = {
                                // TODO Backend Integration
                                // Clear Session
                                // Navigate Login
                            }
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}