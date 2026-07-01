package org.ukrida.root.ui.user.screens.order.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.order.components.OrderButton
import org.ukrida.root.ui.user.screens.order.components.OrderHeader
import org.ukrida.root.ui.user.screens.order.components.OrderInfoSection
import org.ukrida.root.ui.user.screens.order.components.OrderPriceSection
import org.ukrida.root.ui.user.screens.order.viewmodel.OrderViewModel

@Composable
fun OrderScreen(
    navController: NavHostController,
    groupId: Int
) {

    val viewModel: OrderViewModel = viewModel()
    val group by viewModel.group.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.loadOrder(groupId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.PROMISED_LAND,
                onNavigate = { destination ->
                    when (destination) {

                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)

                        PublicDestination.PROMISED_LAND ->
                            navController.popBackStack()

                        PublicDestination.GROUP ->
                            navController.navigate(PublicScreen.Group.route)

                        PublicDestination.PROFILE ->
                            navController.navigate(PublicScreen.Profile.route)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF2A2522))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            PublicTopBar(
                title = "ORDER"
            )

            Spacer(modifier = Modifier.height(20.dp))

            group?.let { tour ->

                OrderHeader(
                    group = tour
                )

                Spacer(modifier = Modifier.height(24.dp))

                OrderInfoSection(
                    group = tour
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dummy dulu, nanti backend tinggal kirim harga
                OrderPriceSection(
                    price = "Rp 25.000.000"
                )

                Spacer(modifier = Modifier.height(36.dp))

                val buttonText = when (tour.statusJoin) {
                    null -> "ORDER"
                    "pending" -> "PENDING"
                    "approved" -> "JOIN"
                    else -> "ORDER"
                }

                OrderButton(
                    text = buttonText,
                    enabled = tour.statusJoin != "Pending",
                    onClick = {

                        // TODO:
                        // ORDER -> Create Order
                        // JOIN -> Join Group
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}