package com.shenhan.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.SrsMastery
import com.shenhan.app.model.VocabWord
import com.shenhan.app.ui.theme.FrostedAmberDark
import com.shenhan.app.ui.theme.FrostedAmberPill
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPitchUser
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurpleOnContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateLight
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun SrsFlashcardCard(
    vocabWord: VocabWord,
    scriptMode: ScriptMode,
    audioSpeed: Float,
    onSpeak: (String) -> Unit,
    onSrsRate: (Int) -> Unit, // 0: Again, 1: Hard, 2: Good, 3: Easy
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFlipped by remember(vocabWord.id) { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500),
        label = "flip_card"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("srs_flashcard_container")
    ) {
        // The Flip Card with Frosted Glass styling
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 320.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(32.dp))
                .clickable { isFlipped = !isFlipped }
                .testTag("srs_flip_card"),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            if (rotation <= 90f) {
                // FRONT SIDE: Hanzi, Pinyin, Audio, Mastery Stage
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar: Level, Mastery, Bookmark
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = FrostedPurpleContainer,
                            border = BorderStroke(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = vocabWord.hskLevel.title,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurpleOnContainer
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = when (vocabWord.mastery) {
                                SrsMastery.NEW -> FrostedGlassWhite80
                                SrsMastery.LEARNING -> FrostedAmberPill
                                SrsMastery.REVIEW -> FrostedPurpleContainer
                                SrsMastery.MASTERED -> FrostedMatchGreenContainer
                            },
                            border = BorderStroke(
                                1.dp,
                                when (vocabWord.mastery) {
                                    SrsMastery.NEW -> FrostedGlassBorder
                                    SrsMastery.LEARNING -> FrostedPitchUser.copy(alpha = 0.4f)
                                    SrsMastery.REVIEW -> FrostedPurplePrimary.copy(alpha = 0.4f)
                                    SrsMastery.MASTERED -> FrostedMatchGreen.copy(alpha = 0.4f)
                                }
                            )
                        ) {
                            Text(
                                text = "SRS: ${vocabWord.mastery.labelTh}",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (vocabWord.mastery) {
                                    SrsMastery.NEW -> FrostedSlateDark
                                    SrsMastery.LEARNING -> FrostedAmberDark
                                    SrsMastery.REVIEW -> FrostedPurplePrimary
                                    SrsMastery.MASTERED -> FrostedMatchGreenDark
                                }
                            )
                        }

                        IconButton(onClick = onToggleBookmark, modifier = Modifier.size(34.dp)) {
                            Icon(
                                imageVector = if (vocabWord.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (vocabWord.isBookmarked) FrostedPitchUser else FrostedSlateLight
                            )
                        }
                    }

                    // Center: Large Chinese Character & Pinyin
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val displayText = if (scriptMode == ScriptMode.SIMPLIFIED) vocabWord.simplified else vocabWord.traditional
                        Text(
                            text = displayText,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Black,
                            color = FrostedSlateDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = vocabWord.pinyin,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = FrostedPurplePrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = vocabWord.partOfSpeech,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = FrostedSlateMuted
                        )
                    }

                    // Bottom: Audio Playback & Flip Hint
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = FrostedGlassWhite80,
                            border = BorderStroke(1.dp, FrostedGlassBorder)
                        ) {
                            OutlinedButton(
                                onClick = { onSpeak(if (scriptMode == ScriptMode.SIMPLIFIED) vocabWord.simplified else vocabWord.traditional) },
                                shape = RoundedCornerShape(20.dp),
                                border = null,
                                modifier = Modifier.testTag("speak_flashcard_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Speak Chinese",
                                    tint = FrostedPurplePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ออกเสียง (${audioSpeed}x)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurplePrimary
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flip,
                                contentDescription = "Tap to flip",
                                tint = FrostedSlateMuted,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "แตะเพื่อดูคำแปล",
                                fontSize = 11.sp,
                                color = FrostedSlateMuted
                            )
                        }
                    }
                }
            } else {
                // BACK SIDE (Mirrored on Y-axis for correct text reading): Definitions, Example Sentence
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                        .padding(22.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top: Thai & English Meaning
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ความหมาย & บริบทการใช้งาน",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary,
                                letterSpacing = 0.5.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = FrostedGlassWhite80,
                                border = BorderStroke(1.dp, FrostedGlassBorder)
                            ) {
                                Text(
                                    text = "${vocabWord.strokeCount} ขีด",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedSlateDark
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = vocabWord.meaningTh,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = FrostedSlateDark
                        )
                        Text(
                            text = "English: ${vocabWord.meaningEn}",
                            fontSize = 13.sp,
                            color = FrostedSlateMuted
                        )
                    }

                    // Example Sentence Box
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = FrostedGlassWhite80,
                        border = BorderStroke(1.dp, FrostedGlassBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (scriptMode == ScriptMode.SIMPLIFIED) vocabWord.exampleSentenceSimp else vocabWord.exampleSentenceTrad,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedSlateDark
                                )
                                IconButton(
                                    onClick = { onSpeak(if (scriptMode == ScriptMode.SIMPLIFIED) vocabWord.exampleSentenceSimp else vocabWord.exampleSentenceTrad) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Play example audio",
                                        tint = FrostedPurplePrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Text(
                                text = vocabWord.examplePinyin,
                                fontSize = 11.sp,
                                color = FrostedPurplePrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = vocabWord.exampleMeaningTh,
                                fontSize = 11.sp,
                                color = FrostedSlateMuted
                            )
                        }
                    }

                    // Tap to flip back
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "แตะเพื่อพลิกกลับ ↺",
                            fontSize = 11.sp,
                            color = FrostedSlateMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // SRS Rating Buttons (Again, Hard, Good, Easy) with Frosted Pill styles
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Again (🔴 1 วัน)
            OutlinedButton(
                onClick = { onSrsRate(0) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = FrostedGlassWhite60,
                    contentColor = Color(0xFFDC2626)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("อีกรอบ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("< 1 วัน", fontSize = 9.sp, color = FrostedSlateMuted)
                }
            }

            // Hard (🟠 3 วัน)
            OutlinedButton(
                onClick = { onSrsRate(1) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, FrostedPitchUser.copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = FrostedGlassWhite60,
                    contentColor = FrostedPitchUser
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("ยาก", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("3 วัน", fontSize = 9.sp, color = FrostedSlateMuted)
                }
            }

            // Good (🟢 7 วัน)
            Button(
                onClick = { onSrsRate(2) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedMatchGreenDark)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("พอดี", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                    Text("7 วัน", fontSize = 9.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }

            // Easy (🔵 14 วัน)
            Button(
                onClick = { onSrsRate(3) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("ง่าย", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                    Text("14 วัน", fontSize = 9.sp, color = Color.White.copy(alpha = 0.8f))
                }
            }
        }
    }
}

