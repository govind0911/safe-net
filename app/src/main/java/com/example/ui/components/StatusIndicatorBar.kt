package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryStd
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.GpsNotFixed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ConnectionState
import com.example.domain.model.SystemStatus
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.SystemGreen
import com.example.ui.theme.TacticalBlue
import com.example.ui.theme.TacticalOrange
import com.example.ui.theme.TacticalText

@Composable
fun StatusIndicatorBar(
    systemStatus: SystemStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: System Status Pill
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val statusColor = when (systemStatus.networkState) {
                ConnectionState.CONNECTED -> SystemGreen
                ConnectionState.WARNING -> TacticalOrange
                ConnectionState.DISCONNECTED -> EmergencyRed
            }
            val statusText = when (systemStatus.networkState) {
                ConnectionState.CONNECTED -> "System Active"
                ConnectionState.WARNING -> "Net Degrading"
                ConnectionState.DISCONNECTED -> "Offline SOS"
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = statusText.uppercase(),
                color = statusColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )
        }

        // Right: Telemetry Indicators
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.alpha(0.85f)
        ) {
            // GPS Badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (systemStatus.isGpsActive) "GPS (${systemStatus.gpsAccuracyMeters.toInt()}m)" else "NO GPS",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = if (systemStatus.isGpsActive) TacticalBlue else EmergencyRed,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = if (systemStatus.isGpsActive) Icons.Default.GpsFixed else Icons.Default.GpsNotFixed,
                    contentDescription = "GPS Status Indicator",
                    tint = if (systemStatus.isGpsActive) TacticalBlue else EmergencyRed,
                    modifier = Modifier.size(14.dp)
                )
            }

            // Battery Badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${systemStatus.batteryPercentage}%",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = TacticalText,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = if (systemStatus.batteryPercentage > 20) Icons.Default.BatteryChargingFull else Icons.Default.BatteryStd,
                    contentDescription = "Battery Status Indicator",
                    tint = if (systemStatus.batteryPercentage > 20) SystemGreen else EmergencyRed,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
