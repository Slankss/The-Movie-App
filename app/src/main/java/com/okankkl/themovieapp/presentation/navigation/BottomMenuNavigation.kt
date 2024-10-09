package com.okankkl.themovieapp.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.okankkl.themovieapp.presentation.MenuItem
import com.okankkl.themovieapp.presentation.components.BottomMenuItem
import com.okankkl.themovieapp.ui.theme.BgColor
import com.okankkl.themovieapp.ui.theme.ShadowColor
import com.okankkl.themovieapp.ui.theme.ShadowColor2

@Composable
fun BottomMenuNavigation(
    navController: NavController
){
    BottomNavigation(
        backgroundColor = BgColor,
        modifier = Modifier
            .shadow(
                elevation = 12.dp,
                ambientColor = ShadowColor,
                spotColor = ShadowColor2,
            )
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        MenuItem.values().forEach { menuItem ->
            val isSelected  = navBackStackEntry?.destination?.hierarchy?.any { it.route == menuItem.screen.route } == true
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(menuItem.screen.route)
                },
                icon = {
                    BottomMenuItem(selected = isSelected, menuItem = menuItem)
                }
            )
        }
    }
}