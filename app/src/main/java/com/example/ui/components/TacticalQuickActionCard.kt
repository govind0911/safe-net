package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafeNetShapes
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalSurface
import com.example.ui.theme.TacticalText

@Composable
fun TacticalQuickActionCard(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Solid matte dark surfaces (#1A1C22), rounded corners (16dp), high contrast
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(SafeNetShapes.large)
            .background(TacticalSurface)
            .border(1.dp, TacticalBorder, SafeNetShapes.large)
            .testTag(testTag)
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$title action icon",
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = TacticalText.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}
