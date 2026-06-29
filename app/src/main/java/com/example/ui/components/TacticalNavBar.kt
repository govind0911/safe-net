package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalNav
import com.example.ui.theme.TacticalText

enum class TacticalNavDestination {
    HOME,
    MAP,
    GUARDIANS,
    SETTINGS
}

@Composable
fun TacticalNavBar(
    selectedDestination: TacticalNavDestination,
    onDestinationSelected: (TacticalNavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(TacticalNav)
            .border(width = 1.dp, color = TacticalBorder)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        NavItem(
            destination = TacticalNavDestination.HOME,
            title = "Home",
            icon = Icons.Default.Home,
            isSelected = selectedDestination == TacticalNavDestination.HOME,
            onSelect = { onDestinationSelected(TacticalNavDestination.HOME) }
        )
        NavItem(
            destination = TacticalNavDestination.MAP,
            title = "Map",
            icon = Icons.Default.Map,
            isSelected = selectedDestination == TacticalNavDestination.MAP,
            onSelect = { onDestinationSelected(TacticalNavDestination.MAP) }
        )
        NavItem(
            destination = TacticalNavDestination.GUARDIANS,
            title = "Guardians",
            icon = Icons.Default.Security,
            isSelected = selectedDestination == TacticalNavDestination.GUARDIANS,
            onSelect = { onDestinationSelected(TacticalNavDestination.GUARDIANS) }
        )
        NavItem(
            destination = TacticalNavDestination.SETTINGS,
            title = "Settings",
            icon = Icons.Default.Settings,
            isSelected = selectedDestination == TacticalNavDestination.SETTINGS,
            onSelect = { onDestinationSelected(TacticalNavDestination.SETTINGS) }
        )
    }
}

@Composable
private fun NavItem(
    destination: TacticalNavDestination,
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val contentColor = if (isSelected) TacticalText else TacticalText.copy(alpha = 0.4f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .testTag("nav_${destination.name.lowercase()}")
            .clickable(onClick = onSelect)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$title navigation tab",
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}
