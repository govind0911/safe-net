package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.EmergencyPriority
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.EmergencyRedDark
import com.example.ui.theme.EmergencyRedGlow

@Composable
fun SosButton(
    priority: EmergencyPriority,
    onSosTriggered: () -> Unit,
    onSosCancelled: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "sos_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (priority == EmergencyPriority.ACTIVE_SOS || priority == EmergencyPriority.CRITICAL_COUNTDOWN) 1.15f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (priority == EmergencyPriority.ACTIVE_SOS) 600 else 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_ring"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(320.dp)
    ) {
        // Outer SOS Ring
        Box(
            modifier = Modifier
                .size(288.dp)
                .scale(pulseScale)
                .border(2.dp, EmergencyRed.copy(alpha = if (priority == EmergencyPriority.ACTIVE_SOS) 0.5f else 0.15f), CircleShape)
        )

        // Middle SOS Ring
        Box(
            modifier = Modifier
                .size(256.dp)
                .scale(pulseScale * 0.98f)
                .border(1.5.dp, EmergencyRed.copy(alpha = if (priority == EmergencyPriority.ACTIVE_SOS) 0.7f else 0.25f), CircleShape)
        )

        // Glow Layer behind button
        if (priority == EmergencyPriority.ACTIVE_SOS || priority == EmergencyPriority.CRITICAL_COUNTDOWN) {
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(EmergencyRedGlow, CircleShape)
            )
        }

        // Actual Action Button
        val buttonBg = when {
            isPressed -> EmergencyRedDark
            priority == EmergencyPriority.ACTIVE_SOS -> EmergencyRedDark
            else -> EmergencyRed
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(208.dp)
                .scale(if (isPressed) 0.95f else 1f)
                .background(buttonBg, CircleShape)
                .border(4.dp, Color.Black.copy(alpha = 0.25f), CircleShape)
                .testTag("sos_button")
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        if (priority == EmergencyPriority.ACTIVE_SOS || priority == EmergencyPriority.CRITICAL_COUNTDOWN) {
                            onSosCancelled()
                        } else {
                            onSosTriggered()
                        }
                    }
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (priority == EmergencyPriority.ACTIVE_SOS) "ACTIVE" else "SOS",
                    fontSize = if (priority == EmergencyPriority.ACTIVE_SOS) 38.sp else 56.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1.5).sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (priority) {
                        EmergencyPriority.ACTIVE_SOS -> "Tap to DISARM"
                        EmergencyPriority.CRITICAL_COUNTDOWN -> "SENDING SOS..."
                        else -> "TAP INSTANT SOS"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}
