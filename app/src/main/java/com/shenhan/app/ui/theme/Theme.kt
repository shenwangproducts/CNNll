package com.shenhan.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = FrostedPurpleLight,
    onPrimary = FrostedPurpleOnContainer,
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = FrostedPurpleContainer,
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = FrostedMatchGreen,
    onTertiary = Color.White,
    background = Color(0xFF141218),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1D1B20),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = FrostedPurplePrimary,
    onPrimary = Color.White,
    primaryContainer = FrostedPurpleContainer,
    onPrimaryContainer = FrostedPurpleOnContainer,
    secondary = FrostedPurpleLight,
    onSecondary = FrostedPurpleOnContainer,
    secondaryContainer = Color(0xFFF3EDF7),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = FrostedMatchGreen,
    onTertiary = Color.White,
    tertiaryContainer = FrostedMatchGreenContainer,
    onTertiaryContainer = FrostedMatchGreenDark,
    background = FrostedCanvasBg,
    onBackground = FrostedSlateDark,
    surface = Color.White,
    onSurface = FrostedSlateDark,
    surfaceVariant = Color(0xFFF1F0FA),
    onSurfaceVariant = FrostedSlateMuted
  )

@Composable
fun ShenHanTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  ShenHanTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}


