package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.EmergencyPriority
import com.example.ui.components.EmergencyBanner
import com.example.ui.components.SosButton
import com.example.ui.components.StatusIndicatorBar
import com.example.ui.components.TacticalNavBar
import com.example.ui.components.TacticalQuickActionCard
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.TacticalBackground
import com.example.ui.theme.TacticalBlue
import com.example.ui.theme.TacticalOrange
import com.example.ui.theme.TacticalText

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val emergencyState by viewModel.emergencyState.collectAsStateWithLifecycle()
    val systemStatus by viewModel.systemStatus.collectAsStateWithLifecycle()
    val currentNavDestination by viewModel.currentNavDestination.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            StatusIndicatorBar(systemStatus = systemStatus)
        },
        bottomBar = {
            TacticalNavBar(
                selectedDestination = currentNavDestination,
                onDestinationSelected = viewModel::selectNavDestination
            )
        },
        containerColor = TacticalBackground,
        contentColor = TacticalText,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            // Top: Banner answering Where am I? & Current Status
            EmergencyBanner(emergencyState = emergencyState)

            // Active Protocol Indicator if SOS notified
            if (emergencyState.emergencyContactNotified) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(EmergencyRed.copy(alpha = 0.25f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "EMERGENCY CONTACTS & GUARDIANS ALERTED",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Middle: Centered dominant SOS button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                SosButton(
                    priority = emergencyState.priority,
                    onSosTriggered = viewModel::triggerSos,
                    onSosCancelled = viewModel::cancelSos
                )
            }

            // Bottom: Tactical Quick Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TacticalQuickActionCard(
                        title = "Crisis Line",
                        icon = Icons.Default.PhoneInTalk,
                        iconTint = TacticalBlue,
                        testTag = "crisis_line_button",
                        onClick = viewModel::triggerCrisisLine
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    TacticalQuickActionCard(
                        title = "Silent Alert",
                        icon = Icons.Default.NotificationsActive,
                        iconTint = TacticalOrange,
                        testTag = "silent_alert_button",
                        onClick = viewModel::triggerSilentAlert
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
