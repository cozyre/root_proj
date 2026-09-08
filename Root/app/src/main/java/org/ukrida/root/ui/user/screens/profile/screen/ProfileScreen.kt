package org.ukrida.root.ui.user.screens.profile.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.ukrida.root.ui.user.screens.profile.components.*
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModel
import org.ukrida.root.utils.Resource
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavHostController,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val updateState by viewModel.updateState.collectAsState()

    // ─── Local form state ───────────────────────────────────────────────────
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var hidePhone by remember { mutableStateOf(false) }

    // ─── Initialize form from profile data ───────────────────────────────────
    LaunchedEffect(profile) {
        when (profile) {
            is Resource.Success -> {
                val p = (profile as Resource.Success).data
                firstName = p.firstName
                lastName = p.lastName
                username = p.username
                email = p.email
                phone = p.phone ?: ""
                bio = p.bio ?: ""
                hidePhone = p.hidePhone
            }

            else -> {}
        }
    }

    // ─── Check if profile has been changed ───────────────────────────────────
    val isChanged = remember(profile, firstName, lastName, username, phone, bio, hidePhone) {
        val p = (profile as? Resource.Success)?.data
        p != null && (
            firstName != p.firstName ||
            lastName != p.lastName ||
            username != p.username ||
            phone != (p.phone ?: "") ||
            bio != (p.bio ?: "") ||
            hidePhone != p.hidePhone
        )
    }

    // ─── Image Picker Launcher ──────────────────────────────────────────────
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            val file = getFileFromUri(context, selectedUri)
            if (file != null) {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                viewModel.uploadProfilePhoto(body)
            }
        }
    }

    // ─── Handle update result ────────────────────────────────────────────────
    LaunchedEffect(updateState) {
        when (updateState) {
            is Resource.Success -> {
                // Show toast only if coming from a non-Loading state effectively
                // But we mainly need it for the user actions
                // Avoiding showing Toast for initial load
                // viewModel.clearUpdateState() is handled below or here
            }

            is Resource.Error -> {
                Toast.makeText(context, (updateState as Resource.Error).message, Toast.LENGTH_SHORT).show()
                viewModel.clearUpdateState()
            }

            is Resource.Loading -> {}
        }
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            when (profile) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFE8D8C9)
                    )
                }

                is Resource.Success -> {
                    val loadedProfile = (profile as Resource.Success).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            ProfileHeader(
                                profile = loadedProfile,
                                onChangePhotoClick = {
                                    launcher.launch("image/*")
                                }
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                            Row {
                                ProfileTextField(
                                    label = "First Name",
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                ProfileTextField(
                                    label = "Last Name",
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            ProfileTextField(
                                label = "Username",
                                value = username,
                                onValueChange = { username = it }
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
                                onValueChange = { phone = it }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            HidePhoneCheckbox(
                                checked = hidePhone,
                                onCheckedChange = { hidePhone = it }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            BioTextField(
                                value = bio,
                                onValueChange = { bio = it }
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                            SaveButton(
                                isLoading = updateState is Resource.Loading,
                                enabled = isChanged,
                                onClick = {
                                    viewModel.updateProfile(
                                        firstName = firstName,
                                        lastName = lastName,
                                        username = username,
                                        phone = phone.ifEmpty { null },
                                        bio = bio.ifEmpty { null },
                                        hidePhone = hidePhone
                                    )
                                }
                            )
                            when (updateState) {
                                is Resource.Error -> {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = (updateState as Resource.Error).message,
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                is Resource.Success -> {
                                    // Show success transiently if needed, 
                                    // usually Toast or immediate UI update is enough
                                }
                                else -> {}
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            LogoutButton(
                                onLogout = { onLogout() }
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = (profile as Resource.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

private fun getFileFromUri(context: android.content.Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_profile_photo.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        file
    } catch (e: Exception) {
        null
    }
}
