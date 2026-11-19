package com.shenhan.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shenhan.app.ui.theme.FrostedCanvasBg
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite20
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurpleLight
import com.shenhan.app.ui.theme.FrostedPurplePrimary

/**
 * Atmospheric background container with soft luminous gradient blobs
 * that refract through frosted glass surfaces.
 */
@Composable
fun FrostedCanvasContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FrostedCanvasBg)
    ) {
        // Decorative background gradient orbs for neon glow refraction
        Box(
            modifier = Modifier
                .offset(x = (-60).dp, y = (-60).dp)
                .size(300.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            FrostedPurpleContainer.copy(alpha = 0.8f),
                            FrostedPurpleLight.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .offset(x = 180.dp, y = 180.dp)
                .size(250.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFBBF24).copy(alpha = 0.25f), // Gold Neon
                            Color(0xFFD97706).copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = 560.dp)
                .size(280.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            FrostedPurplePrimary.copy(alpha = 0.25f), // Cyan Glow
                            FrostedPurpleContainer.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )
        content()
    }
}

/**
 * Standard Frosted Glass Card with translucent slate fill and sleek border.
 */
@Composable
fun FrostedGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(26.dp),
    backgroundColor: Color = FrostedGlassWhite60,
    borderColor: Color = FrostedPurplePrimary.copy(alpha = 0.3f), // Cyan sleek border
    borderWidth: Dp = 1.dp,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .border(borderWidth, borderColor, shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        content()
    }
}
