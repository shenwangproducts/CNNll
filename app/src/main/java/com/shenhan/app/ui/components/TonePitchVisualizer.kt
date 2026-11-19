package com.shenhan.app.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.model.MandarinTone
import com.shenhan.app.model.PitchAnalysisResult
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.TonePracticeSyllable
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPitchNative
import com.shenhan.app.ui.theme.FrostedPitchUser
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurpleOnContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateLight
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun TonePitchVisualizer(
    syllable: TonePracticeSyllable,
    scriptMode: ScriptMode,
    isRecording: Boolean,
    liveFrequencyHz: Float,
    liveSpectrum: List<Float>,
    recordedPitchPoints: List<Float>,
    lastResult: PitchAnalysisResult?,
    onPlayNativeAudio: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pitch_glow")
    val glowAnim by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(32.dp))
            .testTag("tone_pitch_visualizer_card"),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Category Tracker Subtitle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REAL-TIME TONE PITCH ANALYZER",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = FrostedSlateLight,
                    letterSpacing = 1.5.sp
                )

                // Match Badge with Glowing Dot
                val matchScore = lastResult?.accuracyPercentage ?: 92
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = FrostedMatchGreenContainer,
                    border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(FrostedMatchGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (lastResult != null) "$matchScore% Match" else "Live Audio AI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = FrostedMatchGreenDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Syllable Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(FrostedPurpleContainer.copy(alpha = 0.6f))
                            .border(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f), RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (scriptMode == ScriptMode.SIMPLIFIED) syllable.hanziSimplified else syllable.hanziTraditional,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black,
                            color = FrostedPurplePrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = syllable.syllablePinyin,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = FrostedSlateDark
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = FrostedPurpleContainer
                            ) {
                                Text(
                                    text = "Chao ${syllable.tone.pitchLevel}",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurpleOnContainer
                                )
                            }
                        }
                        Text(
                            text = syllable.meaningTh,
                            style = MaterialTheme.typography.bodyMedium,
                            color = FrostedSlateMuted
                        )
                    }
                }

                // Listen to native speaker TTS button (Frosted Glass Pill)
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, FrostedGlassBorder, RoundedCornerShape(20.dp)),
                    color = FrostedGlassWhite80
                ) {
                    OutlinedButton(
                        onClick = onPlayNativeAudio,
                        modifier = Modifier.testTag("play_native_audio_button"),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        border = null
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Listen to Native Speaker",
                            tint = FrostedPurplePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ฟังเสียง",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = FrostedPurplePrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Canvas: 5-Level Chao Pitch Scale Chart with Frosted Glass Surface
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xEEFFFFFF))
                    .border(1.dp, FrostedGlassBorder, RoundedCornerShape(24.dp))
            ) {
                Canvas(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(horizontal = 28.dp, vertical = 18.dp)
                ) {
                    val w = size.width
                    val h = size.height

                    // Draw 5 horizontal pitch lines (Chao Scale 1 to 5)
                    for (i in 0..4) {
                        val y = (h / 4f) * i
                        drawLine(
                            color = Color(0x33CBD5E1),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                        )
                    }

                    // 1. Draw Native Reference Pitch Curve (Primary Violet #6750A4)
                    val targetPath = Path()
                    val targetPoints = when (syllable.tone) {
                        MandarinTone.TONE_1 -> listOf(
                            Offset(0f, (1f - 0.90f) * h),
                            Offset(w * 0.5f, (1f - 0.90f) * h),
                            Offset(w, (1f - 0.90f) * h)
                        )
                        MandarinTone.TONE_2 -> listOf(
                            Offset(0f, (1f - 0.40f) * h),
                            Offset(w * 0.5f, (1f - 0.65f) * h),
                            Offset(w, (1f - 0.92f) * h)
                        )
                        MandarinTone.TONE_3 -> listOf(
                            Offset(0f, (1f - 0.35f) * h),
                            Offset(w * 0.45f, (1f - 0.12f) * h),
                            Offset(w, (1f - 0.70f) * h)
                        )
                        MandarinTone.TONE_4 -> listOf(
                            Offset(0f, (1f - 0.95f) * h),
                            Offset(w * 0.5f, (1f - 0.50f) * h),
                            Offset(w, (1f - 0.12f) * h)
                        )
                        MandarinTone.TONE_NEUTRAL -> listOf(
                            Offset(0f, (1f - 0.45f) * h),
                            Offset(w * 0.5f, (1f - 0.38f) * h),
                            Offset(w, (1f - 0.30f) * h)
                        )
                    }

                    targetPath.moveTo(targetPoints.first().x, targetPoints.first().y)
                    for (i in 1 until targetPoints.size) {
                        targetPath.lineTo(targetPoints[i].x, targetPoints[i].y)
                    }

                    // Draw reference target path with solid primary purple line
                    drawPath(
                        path = targetPath,
                        color = FrostedPitchNative,
                        style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // 2. Draw User Recorded / Live Pitch Trajectory in Warm Tangerine #F27D26
                    if (recordedPitchPoints.size >= 2) {
                        val userPath = Path()
                        val stepX = w / (recordedPitchPoints.size - 1).coerceAtLeast(1)

                        recordedPitchPoints.forEachIndexed { idx, pitchNorm ->
                            val x = idx * stepX
                            val y = (1f - pitchNorm.coerceIn(0f, 1f)) * h
                            if (idx == 0) userPath.moveTo(x, y) else userPath.lineTo(x, y)
                        }

                        // Vibrant Tangerine line
                        drawPath(
                            path = userPath,
                            color = FrostedPitchUser,
                            style = Stroke(
                                width = 4.dp.toPx(),
                                cap = StrokeCap.Round,
                                pathEffect = if (isRecording) PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f) else null
                            )
                        )

                        // Glowing head point
                        val lastPoint = recordedPitchPoints.last()
                        val lastX = (recordedPitchPoints.size - 1) * stepX
                        val lastY = (1f - lastPoint.coerceIn(0f, 1f)) * h
                        drawCircle(
                            color = FrostedPitchUser,
                            radius = 6.dp.toPx() * (if (isRecording) glowAnim else 1f),
                            center = Offset(lastX, lastY)
                        )
                    }
                }

                // Grid labels overlay (5, 4, 3, 2, 1)
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("5", "4", "3", "2", "1").forEach { level ->
                        Text(
                            text = level,
                            color = FrostedSlateLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Legend at Top Left: Native (#6750A4) & You (#F27D26)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 28.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FrostedPitchNative)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Native",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = FrostedPitchNative
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FrostedPitchUser)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "You",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = FrostedPitchUser
                        )
                    }
                }

                // Pitch Spectrum Equalizer Bars
                if (isRecording) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        liveSpectrum.forEach { bandHeight ->
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .height(((bandHeight * 32f).dp).coerceAtLeast(4.dp))
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(FrostedPitchUser.copy(alpha = 0.8f))
                            )
                        }
                    }
                }

                // Live Frequency Status Pill
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isRecording) FrostedPitchUser.copy(alpha = 0.15f) else FrostedGlassWhite80,
                    border = BorderStroke(1.dp, if (isRecording) FrostedPitchUser.copy(alpha = 0.4f) else FrostedGlassBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isRecording) FrostedPitchUser else FrostedPitchNative)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isRecording) "${liveFrequencyHz.toInt()} Hz (กำลังฟัง)" else "Chao 5-Level Grid",
                            color = if (isRecording) FrostedPitchUser else FrostedSlateMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Large Frosted Action Button: Mic Record
            Button(
                onClick = {
                    if (isRecording) onStopRecording() else onStartRecording()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("record_pitch_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecording) FrostedPitchUser else FrostedPurplePrimary
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = "Record pitch",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isRecording) "กดเพื่อหยุดและวิเคราะห์วรรณยุกต์" else "แตะเพื่อเริ่มออกเสียง (Live Pitch)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            // Diagnostic Results Card
            if (lastResult != null && !isRecording) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = FrostedGlassWhite80,
                    border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ผลการวิเคราะห์ระดับเสียง",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = FrostedMatchGreenDark
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = FrostedMatchGreenContainer
                            ) {
                                Text(
                                    text = "เสถียรภาพ ${lastResult.pitchStabilityScore}%",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedMatchGreenDark
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lastResult.feedbackTh,
                            fontSize = 12.sp,
                            color = FrostedSlateDark,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            // Common Mistakes Tip
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FrostedGlassWhite40,
                border = BorderStroke(1.dp, FrostedGlassBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "💡", fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "เทคนิค: ${syllable.commonMistakeTh}",
                        fontSize = 11.sp,
                        color = FrostedSlateMuted,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

