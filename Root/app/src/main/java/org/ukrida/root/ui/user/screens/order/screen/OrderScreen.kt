package org.ukrida.root.ui.user.screens.order.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.order.components.OrderButton
import org.ukrida.root.ui.user.screens.order.components.OrderHeader
import org.ukrida.root.ui.user.screens.order.components.OrderInfoSection
import org.ukrida.root.ui.user.screens.order.components.OrderPriceSection
import org.ukrida.root.ui.user.screens.order.viewmodel.OrderViewModel
import org.ukrida.root.utils.Resource

@Composable
fun OrderScreen(
    viewModel: OrderViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(groupId) {
        viewModel.loadOrder(groupId)
    }

    LaunchedEffect(uiState.orderAction) {
        uiState.orderAction?.let { resource ->
            when (resource) {
                is Resource.Success -> {
                    Toast.makeText(context, "Order created successfully", Toast.LENGTH_SHORT).show()
                    viewModel.resetOrderAction()
                }
                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetOrderAction()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { paddingValues ->
        when (val groupRes = uiState.group) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = groupRes.message, color = Color.White)
                }
            }
            is Resource.Success -> {
                val tour = groupRes.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFF2A2522))
                        .verticalScroll(rememberScrollState())
                ) {

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        OrderHeader(group = tour)

                        Spacer(modifier = Modifier.height(24.dp))

                        OrderInfoSection(group = tour)

                        Spacer(modifier = Modifier.height(24.dp))

                        OrderPriceSection(price = "Rp 25.000.000")

                        Spacer(modifier = Modifier.height(36.dp))

                        val buttonText = when (tour.statusJoin?.lowercase()) {
                            null -> "ORDER"
                            "pending" -> "PENDING"
                            "approved" -> "JOIN"
                            "rejected" -> "TRY LATER"
                            else -> "ORDER"
                        }

                        OrderButton(
                            text = buttonText,
                            enabled = tour.statusJoin?.lowercase() != "pending",
                            onClick = {
                                if (tour.statusJoin == null) {
                                    viewModel.createOrder(groupId)
                                } else if (tour.statusJoin.lowercase() == "approved") {
                                    navController.navigate(PublicScreen.Dashboard.createRoute(groupId))
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}
