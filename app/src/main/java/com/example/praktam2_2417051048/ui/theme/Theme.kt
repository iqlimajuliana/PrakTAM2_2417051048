package com.example.praktam2_2417051048.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = BlueBackground,
    surface = WhiteSurface,
    onPrimary = OnPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun praktam2_2417051048Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
