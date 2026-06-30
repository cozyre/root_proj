package org.ukrida.root.ui.Public.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class PublicDestination {
    GROUP,
    HOME,
    PROMISED_LAND,
    PROFILE
}
@Composable
fun PublicBottomNavigation(
    currentDestination: PublicDestination,
    onNavigate: (PublicDestination) -> Unit
) {
    val selectedColor = Color(0xFF7A8A4A)
    val unselectedColor = Color(0xFF9A775B)
    val background = Color(0xFF2A2522)
    NavigationBar(
        containerColor = background
    ) {
        NavigationBarItem(
            selected = currentDestination == PublicDestination.GROUP,
            onClick = { onNavigate(PublicDestination.GROUP) },
            icon = {
                Icon(
                    Icons.Outlined.Groups,
                    contentDescription = "Group"
                )
            },
            label = {
                Text("Group")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentDestination == PublicDestination.HOME,
            onClick = { onNavigate(PublicDestination.HOME) },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentDestination == PublicDestination.PROMISED_LAND,
            onClick = { onNavigate(PublicDestination.PROMISED_LAND) },
            icon = {
                Icon(
                    Icons.Outlined.Place,
                    contentDescription = "Promised Land"
                )
            },
            label = {
                Text("Promised")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentDestination == PublicDestination.PROFILE,
            onClick = { onNavigate(PublicDestination.PROFILE) },
            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = Color.Transparent
            )
        )
    }
}