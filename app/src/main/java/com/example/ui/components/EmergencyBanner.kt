package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.EmergencyPriority
import com.example.domain.model.EmergencyState
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SystemGreen
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalOrange
import com.example.ui.theme.TacticalText
import com.example.ui.theme.TacticalTextMuted

@Composable
fun EmergencyBanner(
    emergencyState: EmergencyState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SafeNet Protocol",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TacticalTextMuted,
            letterSpacing = 3.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(1.dp)
                .background(TacticalBorder)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val statusColor = when (emergencyState.priority) {
            EmergencyPriority.NORMAL -> SystemGreen
            EmergencyPriority.WARNING -> TacticalOrange
            EmergencyPriority.CRITICAL_COUNTDOWN, EmergencyPriority.ACTIVE_SOS -> EmergencyRed
        }

        val statusWord = when (emergencyState.priority) {
            EmergencyPriority.NORMAL -> "Secure"
            EmergencyPriority.WARNING -> "Alerted"
            EmergencyPriority.CRITICAL_COUNTDOWN -> "SOS (${emergencyState.countdownSeconds}s)"
            EmergencyPriority.ACTIVE_SOS -> "ACTIVE SOS"
        }

        Text(
            text = buildAnnotatedString {
                append("Current Status: ")
                withStyle(SpanStyle(color = statusColor, fontWeight = FontWeight.Bold)) {
                    append(statusWord)
                }
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = TacticalText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Answering "Where am I?" per UI Guidelines
        Text(
            text = emergencyState.locationDescription,
            fontSize = 11.sp,
            color = TacticalTextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
    }
}
