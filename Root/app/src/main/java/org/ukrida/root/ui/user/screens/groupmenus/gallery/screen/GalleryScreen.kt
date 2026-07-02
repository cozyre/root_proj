package org.ukrida.root.ui.user.screens.groupmenus.gallery.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import org.ukrida.root.ui.user.screens.groupmenus.gallery.components.GalleryGrid
import org.ukrida.root.ui.user.screens.groupmenus.gallery.viewmodel.GalleryViewModel
import org.ukrida.root.utils.FileUtil
import org.ukrida.root.utils.Resource

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val context = LocalContext.current
    val imagesState by viewModel.images.collectAsState()
    val uploadStatus by viewModel.uploadStatus.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.loadGallery(groupId)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                val file = FileUtil.uriToFile(context, it)
                if (file != null) {
                    viewModel.uploadImage(groupId, file)
                } else {
                    Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    LaunchedEffect(uploadStatus) {
        when (uploadStatus) {
            is Resource.Success -> {
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                viewModel.resetUploadStatus()
            }
            is Resource.Error -> {
                Toast.makeText(context, (uploadStatus as Resource.Error).message, Toast.LENGTH_SHORT).show()
                viewModel.resetUploadStatus()
            }
            else -> {}
        }
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (imagesState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFE8D8C9)
                    )
                }
                is Resource.Success -> {
                    val images = (imagesState as Resource.Success).data
                    if (images.isEmpty()) {
                        Text(
                            text = "No images found in gallery",
                            color = Color.LightGray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        GalleryGrid(
                            images = images,
                            modifier = Modifier.weight(1f),
                            onImageClick = { image ->
                                // TODO: Full screen preview
                            },
                            onUploadClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        )
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = (imagesState as Resource.Error).message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }

            if (uploadStatus is Resource.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}
