package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// High Contrast Dark-First Tactical Scheme
private val TacticalDarkColorScheme = darkColorScheme(
    primary = EmergencyRed,
    onPrimary = Color.White,
    primaryContainer = EmergencyRedDark,
    onPrimaryContainer = Color.White,
    secondary = TacticalBlue,
    onSecondary = Color.Black,
    secondaryContainer = TacticalBlueDark,
    onSecondaryContainer = Color.White,
    tertiary = TacticalOrange,
    onTertiary = Color.Black,
    tertiaryContainer = TacticalOrangeDark,
    onTertiaryContainer = Color.White,
    error = EmergencyRedLight,
    onError = Color.Black,
    errorContainer = EmergencyRedDark,
    onErrorContainer = Color.White,
    background = TacticalBackground,
    onBackground = TacticalText,
    surface = TacticalSurface,
    onSurface = TacticalText,
    surfaceVariant = TacticalSurfaceActive,
    onSurfaceVariant = TacticalTextMuted,
    outline = TacticalBorder,
    outlineVariant = TacticalBorderStrong
)

@Composable
fun SafeNetTheme(
    content: @Composable () -> Unit
) {
    // Strictly adhere to matte dark tactical emergency theme
    MaterialTheme(
        colorScheme = TacticalDarkColorScheme,
        typography = TacticalTypography,
        shapes = SafeNetShapes,
        content = content
    )
}
