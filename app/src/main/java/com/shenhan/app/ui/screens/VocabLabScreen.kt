package com.shenhan.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.MandarinTone
import com.shenhan.app.model.PitchAnalysisResult
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.TonePracticeSyllable
import com.shenhan.app.model.VocabWord
import com.shenhan.app.ui.components.HandwritingCanvas
import com.shenhan.app.ui.components.SrsFlashcardCard
import com.shenhan.app.ui.components.TonePitchVisualizer
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun VocabLabScreen(
    toneSyllables: List<TonePracticeSyllable>,
    vocabList: List<VocabWord>,
    scriptMode: ScriptMode,
    audioSpeed: Float,
    isRecording: Boolean,
    liveFrequencyHz: Float,
    liveSpectrum: List<Float>,
    recordedPitchPoints: List<Float>,
    lastPitchResult: PitchAnalysisResult?,
    onPlayNativeAudio: (String) -> Unit,
    onStartPitchRecording: (MandarinTone) -> Unit,
    onStopPitchRecording: () -> Unit,
    onSrsRateWord: (String, Int) -> Unit,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLabTab by remember { mutableIntStateOf(0) }
    val labTabs = listOf(
        Triple("วิเคราะห์วรรณยุกต์ (Tone Pitch)", Icons.Default.GraphicEq, 0),
        Triple("คลังศัพท์ SRS (HSK 1-6)", Icons.Default.Style, 1),
        Triple("ฝึกคัดลายมือ (Stroke)", Icons.Default.Create, 2)
    )

    // Tone Lab state
    var selectedSyllableIndex by remember { mutableIntStateOf(0) }
    val currentSyllable = toneSyllables.getOrElse(selectedSyllableIndex) { toneSyllables.first() }

    // Vocab SRS state
    var selectedHskFilter by remember { mutableStateOf<HskLevel?>(null) }
    var currentFlashcardIndex by remember { mutableIntStateOf(0) }

    val filteredVocab = remember(vocabList, selectedHskFilter) {
        if (selectedHskFilter == null) vocabList else vocabList.filter { it.hskLevel == selectedHskFilter }
    }
    val currentVocabWord = filteredVocab.getOrElse(currentFlashcardIndex % filteredVocab.size.coerceAtLeast(1)) { vocabList.first() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("vocab_lab_screen")
    ) {
        // Frosted Tab Row
        Surface(
            color = FrostedGlassWhite80,
            modifier = Modifier.fillMaxWidth()
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedLabTab,
                edgePadding = 16.dp,
                containerColor = Color.Transparent,
                contentColor = FrostedPurplePrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedLabTab]),
                        color = FrostedPurplePrimary,
                        height = 3.dp
                    )
                },
                divider = {}
            ) {
                labTabs.forEachIndexed { index, (title, icon, _) ->
                    val isSelected = selectedLabTab == index
                    Tab(
                        selected = isSelected,
                        onClick = { selectedLabTab = index },
                        modifier = Modifier.testTag("lab_tab_$index"),
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (isSelected) FrostedPurplePrimary else FrostedSlateMuted
                                )
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) FrostedPurplePrimary else FrostedSlateMuted
                                )
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (selectedLabTab) {
            0 -> {
                // SUB-TAB 1: REAL-TIME TONE PITCH ANALYZER
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        // Syllable Picker Horizontal Row
                        Text(
                            text = "เลือกพยางค์ฝึกวรรณยุกต์ 4 เสียง:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = FrostedSlateDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(toneSyllables) { index, item ->
                                val isSelected = selectedSyllableIndex == index
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { selectedSyllableIndex = index }
                                        .testTag("tone_chip_${item.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    color = if (isSelected) FrostedPurplePrimary else FrostedGlassWhite60,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) FrostedPurplePrimary else FrostedGlassBorder
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = if (scriptMode == ScriptMode.SIMPLIFIED) item.hanziSimplified else item.hanziTraditional,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else FrostedPurplePrimary
                                        )
                                        Text(
                                            text = item.syllablePinyin,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (isSelected) Color.White.copy(alpha = 0.9f) else FrostedSlateMuted
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Main Pitch Spectrum Analyzer Canvas
                    item {
                        TonePitchVisualizer(
                            syllable = currentSyllable,
                            scriptMode = scriptMode,
                            isRecording = isRecording,
                            liveFrequencyHz = liveFrequencyHz,
                            liveSpectrum = liveSpectrum,
                            recordedPitchPoints = recordedPitchPoints,
                            lastResult = lastPitchResult,
                            onPlayNativeAudio = {
                                onPlayNativeAudio(if (scriptMode == ScriptMode.SIMPLIFIED) currentSyllable.hanziSimplified else currentSyllable.hanziTraditional)
                            },
                            onStartRecording = {
                                onStartPitchRecording(currentSyllable.tone)
                            },
                            onStopRecording = onStopPitchRecording
                        )
                    }

                    // Tone Pitch Guide Reference Card
                    item {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                            border = BorderStroke(1.dp, FrostedGlassBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "ตารางอ้างอิงวรรณยุกต์ภาษาจีนมาตรฐาน (Chao 5-Level Pitch)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = FrostedPurplePrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("• เสียง 1 (阴平 55): เสียงสูงคงที่ ลากยาวไม่ตก (เช่น mā 妈)", fontSize = 12.sp, color = Color(0xFFDC2626))
                                Text("• เสียง 2 (阳平 35): เสียงไต่ระดับจากกลางขึ้นสูง (เช่น má 麻)", fontSize = 12.sp, color = Color(0xFF16A34A))
                                Text("• เสียง 3 (上声 214): เสียงกดต่ำลึกก่อนแล้วตวัดยก (เช่น mǎ 马)", fontSize = 12.sp, color = Color(0xFFD97706))
                                Text("• เสียง 4 (去声 51): เสียงทิ้งดิ่งลงเร็วและหนักแน่น (เช่น mà 骂)", fontSize = 12.sp, color = FrostedPurplePrimary)
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }

            1 -> {
                // SUB-TAB 2: OFFLINE HSK 1-6 SRS FLASHCARDS
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        // HSK Level Filters
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ระดับ HSK (${filteredVocab.size} คำ)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = FrostedSlateDark
                            )
                            Text(
                                text = "คำที่ ${(currentFlashcardIndex % filteredVocab.size.coerceAtLeast(1)) + 1} จาก ${filteredVocab.size}",
                                fontSize = 12.sp,
                                color = FrostedSlateMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            item {
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable { selectedHskFilter = null },
                                    color = if (selectedHskFilter == null) FrostedPurplePrimary else FrostedGlassWhite60,
                                    border = BorderStroke(1.dp, if (selectedHskFilter == null) FrostedPurplePrimary else FrostedGlassBorder)
                                ) {
                                    Text(
                                        text = "ทั้งหมด",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedHskFilter == null) Color.White else FrostedSlateDark
                                    )
                                }
                            }
                            items(HskLevel.values()) { level ->
                                val isSelected = selectedHskFilter == level
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable { selectedHskFilter = level },
                                    color = if (isSelected) FrostedPurplePrimary else FrostedGlassWhite60,
                                    border = BorderStroke(1.dp, if (isSelected) FrostedPurplePrimary else FrostedGlassBorder)
                                ) {
                                    Text(
                                        text = level.title,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else FrostedSlateDark
                                    )
                                }
                            }
                        }
                    }

                    // 3D Flip Flashcard with Spaced Repetition Buttons
                    item {
                        SrsFlashcardCard(
                            vocabWord = currentVocabWord,
                            scriptMode = scriptMode,
                            audioSpeed = audioSpeed,
                            onSpeak = { text -> onPlayNativeAudio(text) },
                            onSrsRate = { ease ->
                                onSrsRateWord(currentVocabWord.id, ease)
                                currentFlashcardIndex++
                            },
                            onToggleBookmark = { onToggleBookmark(currentVocabWord.id) }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }

            2 -> {
                // SUB-TAB 3: CHINESE STROKE & PINYIN HANDWRITING CANVAS
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        // Word selector for calligraphy
                        Text(
                            text = "เลือกตัวอักษรเพื่อฝึกคัดลายมือ:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = FrostedSlateDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            itemsIndexed(vocabList) { idx, word ->
                                val isSelected = currentVocabWord.id == word.id
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable { currentFlashcardIndex = idx },
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) FrostedPurplePrimary else FrostedGlassWhite60,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) FrostedPurplePrimary else FrostedGlassBorder
                                    )
                                ) {
                                    Text(
                                        text = if (scriptMode == ScriptMode.SIMPLIFIED) word.simplified else word.traditional,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else FrostedSlateDark
                                    )
                                }
                            }
                        }
                    }

                    item {
                        HandwritingCanvas(
                            vocabWord = currentVocabWord,
                            scriptMode = scriptMode
                        )
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

