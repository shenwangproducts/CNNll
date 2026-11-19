package com.shenhan.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.data.PlacementTestData
import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.PlacementQuestion
import com.shenhan.app.model.PlacementTestResult
import com.shenhan.app.model.QuestionCategory
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.SkillScore
import com.shenhan.app.ui.theme.FrostedAmberDark
import com.shenhan.app.ui.theme.FrostedAmberPill
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPitchTarget
import com.shenhan.app.ui.theme.FrostedPitchUser
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateLight
import com.shenhan.app.ui.theme.FrostedSlateMuted

enum class PlacementScreenState {
    INTRO,
    QUIZ,
    RESULT
}

@Composable
fun PlacementTestScreen(
    scriptMode: ScriptMode,
    onSpeakAudio: (String) -> Unit,
    onFinishAndNavigateToAuth: (PlacementTestResult) -> Unit,
    onFinishAndStartApp: (PlacementTestResult) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var screenState by remember { mutableStateOf(PlacementScreenState.INTRO) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionIndex -> selectedOptionIndex
    var hasSubmittedCurrent by remember { mutableStateOf(false) }

    val questions = remember { PlacementTestData.diagnosticQuestions }
    var computedResult by remember { mutableStateOf<PlacementTestResult?>(null) }

    fun evaluateResult(): PlacementTestResult {
        var correctCount = 0
        val categoryTotals = mutableMapOf<QuestionCategory, Int>()
        val categoryCorrects = mutableMapOf<QuestionCategory, Int>()

        QuestionCategory.values().forEach { cat ->
            categoryTotals[cat] = 0
            categoryCorrects[cat] = 0
        }

        questions.forEachIndexed { index, question ->
            val total = categoryTotals.getOrDefault(question.category, 0) + 1
            categoryTotals[question.category] = total

            val selected = userAnswers[index]
            if (selected != null && selected == question.correctIndex) {
                correctCount++
                val correct = categoryCorrects.getOrDefault(question.category, 0) + 1
                categoryCorrects[question.category] = correct
            }
        }

        val percentage = ((correctCount.toFloat() / questions.size) * 100).toInt()
        val recommendedLevel = when {
            percentage >= 85 -> HskLevel.HSK4
            percentage >= 60 -> HskLevel.HSK3
            percentage >= 35 -> HskLevel.HSK2
            else -> HskLevel.HSK1
        }

        val recommendedZoneId = when (recommendedLevel) {
            HskLevel.HSK1 -> "zone_hsk1"
            HskLevel.HSK2 -> "zone_hsk2"
            HskLevel.HSK3 -> "zone_hsk3"
            HskLevel.HSK4 -> "zone_hsk4"
            else -> "zone_hsk1"
        }

        val recommendedZoneTitle = when (recommendedLevel) {
            HskLevel.HSK1 -> "โซนที่ 1: ตรอกมังกรหยก (HSK 1 พื้นฐานเริ่มต้น)"
            HskLevel.HSK2 -> "โซนที่ 2: โรงน้ำชาเสฉวน (HSK 2 สนทนาประจำวัน)"
            HskLevel.HSK3 -> "โซนที่ 3: หอตำรามณฑล (HSK 3 สื่อสารคล่องแคล่ว)"
            HskLevel.HSK4 -> "โซนที่ 4: มหาพิภพราชสำนัก (HSK 4 ระดับสูง)"
            else -> "โซนที่ 1: ตรอกมังกรหยก"
        }

        val summary = when (recommendedLevel) {
            HskLevel.HSK4 -> "ยอดเยี่ยมมาก! คุณมีความรู้ภาษาจีนในระดับสูง แนะนำให้ลุยเนื้อหา HSK 4 และฝึก AI Cinema แบบขั้นสูง"
            HskLevel.HSK3 -> "ดีมาก! คุณเข้าใจโครงสร้างและคำศัพท์หลักได้ดี แนะนำเริ่มที่โซน HSK 3 เพื่อเสริมความลื่นไหลในการพูดและการอ่าน"
            HskLevel.HSK2 -> "เก่งมาก! คุณมีพื้นฐานคำศัพท์และวรรณยุกต์แล้ว แนะนำเริ่มที่ HSK 2 เพื่อเชื่อมโยงประโยคและบทสนทนาจริง"
            HskLevel.HSK1 -> "เริ่มต้นการเดินทางที่ยอดเยี่ยม! แนะนำเริ่มปูพื้นฐานวรรณยุกต์ 4 เสียงและคำศัพท์หลักใน HSK 1 อย่างมั่นใจ"
            else -> "พร้อมสำหรับการเริ่มต้นเรียนรู้ภาษาจีนอย่างสนุกสนาน!"
        }

        val skillBreakdown = QuestionCategory.values().map { cat ->
            val total = categoryTotals.getOrDefault(cat, 1)
            val corr = categoryCorrects.getOrDefault(cat, 0)
            val pct = if (total > 0) ((corr.toFloat() / total) * 100).toInt() else 0
            SkillScore(category = cat, correctCount = corr, totalCount = total, percentage = pct)
        }

        return PlacementTestResult(
            recommendedHskLevel = recommendedLevel,
            overallScorePercentage = percentage,
            correctAnswersCount = correctCount,
            totalQuestionsCount = questions.size,
            skillBreakdown = skillBreakdown,
            recommendedZoneId = recommendedZoneId,
            recommendedZoneTitleTh = recommendedZoneTitle,
            evaluationSummaryTh = summary
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("placement_test_screen")
    ) {
        when (screenState) {
            PlacementScreenState.INTRO -> {
                PlacementIntroView(
                    onStartFullTest = {
                        screenState = PlacementScreenState.QUIZ
                        currentQuestionIndex = 0
                        userAnswers.clear()
                        hasSubmittedCurrent = false
                    },
                    onStartAsBeginner = {
                        val beginnerResult = PlacementTestResult(
                            recommendedHskLevel = HskLevel.HSK1,
                            overallScorePercentage = 0,
                            correctAnswersCount = 0,
                            totalQuestionsCount = questions.size,
                            skillBreakdown = QuestionCategory.values().map {
                                SkillScore(it, 0, 2, 0)
                            },
                            recommendedZoneId = "zone_hsk1",
                            recommendedZoneTitleTh = "โซนที่ 1: ตรอกมังกรหยก (HSK 1 พื้นฐานเริ่มต้น)",
                            evaluationSummaryTh = "เริ่มต้นจากศูนย์! เราจะพาคุณฝึกฝนตั้งแต่เสียงวรรณยุกต์ พินอิน และคำศัพท์พื้นฐานอย่างเป็นระบบ",
                            suggestedDailyGoalMinutes = 15
                        )
                        computedResult = beginnerResult
                        screenState = PlacementScreenState.RESULT
                    },
                    onBack = onBack
                )
            }

            PlacementScreenState.QUIZ -> {
                val currentQuestion = questions[currentQuestionIndex]
                val selectedOption = userAnswers[currentQuestionIndex]

                PlacementQuizView(
                    question = currentQuestion,
                    questionIndex = currentQuestionIndex,
                    totalQuestions = questions.size,
                    selectedOptionIndex = selectedOption,
                    hasSubmitted = hasSubmittedCurrent,
                    scriptMode = scriptMode,
                    onSelectOption = { index ->
                        if (!hasSubmittedCurrent) {
                            userAnswers[currentQuestionIndex] = index
                            hasSubmittedCurrent = true
                        }
                    },
                    onPlayAudio = {
                        val textToSpeak = if (currentQuestion.audioCue.isNotEmpty()) {
                            currentQuestion.audioCue
                        } else {
                            currentQuestion.chineseSimp
                        }
                        onSpeakAudio(textToSpeak)
                    },
                    onNext = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            hasSubmittedCurrent = userAnswers.containsKey(currentQuestionIndex)
                        } else {
                            computedResult = evaluateResult()
                            screenState = PlacementScreenState.RESULT
                        }
                    },
                    onPrevious = {
                        if (currentQuestionIndex > 0) {
                            currentQuestionIndex--
                            hasSubmittedCurrent = userAnswers.containsKey(currentQuestionIndex)
                        }
                    }
                )
            }

            PlacementScreenState.RESULT -> {
                val result = computedResult ?: evaluateResult()
                PlacementResultView(
                    result = result,
                    onRetakeTest = {
                        screenState = PlacementScreenState.INTRO
                        userAnswers.clear()
                        currentQuestionIndex = 0
                        hasSubmittedCurrent = false
                    },
                    onContinueToAuth = {
                        onFinishAndNavigateToAuth(result)
                    },
                    onStartLearningNow = {
                        onFinishAndStartApp(result)
                    }
                )
            }
        }
    }
}

@Composable
private fun PlacementIntroView(
    onStartFullTest: () -> Unit,
    onStartAsBeginner: () -> Unit,
    onBack: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            if (onBack != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("placement_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = FrostedSlateDark
                        )
                    }
                }
            }

            // Hero Graphic & Title
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(FrostedPurplePrimary, Color(0xFF818CF8))
                        )
                    )
                    .border(3.dp, FrostedAmberPill, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎯",
                    fontSize = 44.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "แบบทดสอบวัดระดับภาษาจีน\nAI Smart Placement Test",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "ค้นพบระดับ HSK ที่ตรงกับคุณที่สุด เพื่อปรับแต่งแผนที่เควสต์ บทเรียนเสียงวรรณยุกต์ และคลังศัพท์ให้เหมาะกับคุณโดยเฉพาะ",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = FrostedSlateLight,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Highlight Cards
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "สิ่งที่คุณจะได้รับจากการประเมิน:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = FrostedPurplePrimary
                    )

                    IntroFeatureRow(
                        icon = "🎙️",
                        title = "วิเคราะห์ความแม่นยำ 4 มิติ",
                        desc = "วัดผลทั้งวรรณยุกต์ คำศัพท์ ไวยากรณ์ และการฟังบทสนทนา"
                    )

                    IntroFeatureRow(
                        icon = "🗺️",
                        title = "จัดตำแหน่งจุดเริ่มต้นในแผนที่",
                        desc = "ปลดล็อกโซน HSK 1 - 4 ที่เหมาะสมทันที ไม่ต้องเริ่มซ้ำซ้อน"
                    )

                    IntroFeatureRow(
                        icon = "⏱️",
                        title = "กระชับและรวดเร็ว",
                        desc = "มีเพียง 8 ข้อคำถาม ใช้เวลาเพียง 2-3 นาทีเท่านั้น"
                    )
                }
            }
        }

        // Action Buttons
        item {
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onStartFullTest,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("start_placement_test_button"),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "เริ่มทำแบบทดสอบวัดระดับ (8 ข้อ)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onStartAsBeginner,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("start_as_beginner_button"),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.5.dp, FrostedPurplePrimary.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = FrostedPurplePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ฉันเป็นผู้เริ่มต้นจากศูนย์ (เริ่ม HSK 1)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FrostedPurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun IntroFeatureRow(
    icon: String,
    title: String,
    desc: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = icon, fontSize = 22.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Color.White
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = FrostedSlateLight
            )
        }
    }
}

@Composable
private fun PlacementQuizView(
    question: PlacementQuestion,
    questionIndex: Int,
    totalQuestions: Int,
    selectedOptionIndex: Int?,
    hasSubmitted: Boolean,
    scriptMode: ScriptMode,
    onSelectOption: (Int) -> Unit,
    onPlayAudio: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (questionIndex + 1).toFloat() / totalQuestions

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            // Progress Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = FrostedPurpleContainer
                ) {
                    Text(
                        text = "${question.category.iconEmoji} ${question.category.titleTh}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FrostedPurplePrimary
                    )
                }

                Text(
                    text = "ข้อที่ ${questionIndex + 1} / $totalQuestions",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FrostedSlateLight
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = FrostedPurplePrimary,
                trackColor = Color(0xFFE2E8F0)
            )
        }

        // Question Card
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Target HSK tag
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFEEF2F6)
                    ) {
                        Text(
                            text = "ระดับคำถาม: ${question.targetHskLevel.title}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FrostedSlateLight
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = question.promptTh,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Chinese Prompt Showcase Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(FrostedPurpleContainer.copy(alpha = 0.5f))
                            .border(1.dp, FrostedPurplePrimary.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = question.pinyin,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = FrostedPitchUser
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (scriptMode == ScriptMode.SIMPLIFIED) question.chineseSimp else question.chineseTrad,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = FrostedPurplePrimary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            FilledTonalButton(
                                onClick = onPlayAudio,
                                shape = RoundedCornerShape(20.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("placement_play_audio_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Listen",
                                    tint = FrostedPurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ฟังเสียงออกเสียง",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurplePrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Option Cards
        items(question.options.indices.toList()) { optionIndex ->
            val optionText = question.options[optionIndex]
            val isSelected = selectedOptionIndex == optionIndex
            val isCorrect = optionIndex == question.correctIndex

            val borderColor = when {
                !hasSubmitted && isSelected -> FrostedPurplePrimary
                hasSubmitted && isCorrect -> FrostedMatchGreenDark
                hasSubmitted && isSelected && !isCorrect -> Color(0xFFDC2626)
                else -> FrostedGlassBorder
            }

            val containerColor = when {
                !hasSubmitted && isSelected -> FrostedPurpleContainer
                hasSubmitted && isCorrect -> FrostedMatchGreenContainer
                hasSubmitted && isSelected && !isCorrect -> Color(0xFFFEE2E2)
                else -> FrostedGlassWhite80
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(enabled = !hasSubmitted) {
                        onSelectOption(optionIndex)
                    }
                    .testTag("placement_option_$optionIndex"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(if (isSelected || (hasSubmitted && isCorrect)) 2.dp else 1.dp, borderColor),
                colors = CardDefaults.cardColors(containerColor = containerColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected || (hasSubmitted && isCorrect)) borderColor else Color(0xFFE2E8F0)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${('A'.code + optionIndex).toChar()}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected || (hasSubmitted && isCorrect)) Color.White else FrostedSlateDark
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = optionText,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected || (hasSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    if (hasSubmitted) {
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correct",
                                tint = FrostedMatchGreenDark,
                                modifier = Modifier.size(24.dp)
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Incorrect",
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Explanation (after selection)
        if (hasSubmitted) {
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedOptionIndex == question.correctIndex)
                                FrostedMatchGreenContainer else FrostedAmberPill
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selectedOptionIndex == question.correctIndex)
                                FrostedMatchGreen.copy(alpha = 0.5f) else FrostedAmberDark.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Tip",
                                tint = if (selectedOptionIndex == question.correctIndex)
                                    FrostedMatchGreenDark else FrostedAmberDark,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (selectedOptionIndex == question.correctIndex) "ตอบถูกต้อง! 🎉" else "คำอธิบายข้อนี้:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (selectedOptionIndex == question.correctIndex)
                                        FrostedMatchGreenDark else FrostedAmberDark
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = question.explanationTh,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Navigation CTA
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (questionIndex > 0) {
                    OutlinedButton(
                        onClick = onPrevious,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.testTag("placement_prev_btn")
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Prev")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ก่อนหน้า")
                    }
                } else {
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Button(
                    onClick = onNext,
                    enabled = hasSubmitted,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary),
                    modifier = Modifier.testTag("placement_next_btn")
                ) {
                    Text(
                        text = if (questionIndex == totalQuestions - 1) "ดูผลการประเมิน 📊" else "ข้อถัดไป",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PlacementResultView(
    result: PlacementTestResult,
    onRetakeTest: () -> Unit,
    onContinueToAuth: () -> Unit,
    onStartLearningNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Result Hero Badge
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(FrostedPurplePrimary, Color(0xFF6366F1), FrostedMatchGreen)
                        )
                    )
                    .border(3.dp, FrostedAmberDark, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "HSK",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FrostedAmberPill
                    )
                    Text(
                        text = when (result.recommendedHskLevel) {
                            HskLevel.HSK1 -> "1"
                            HskLevel.HSK2 -> "2"
                            HskLevel.HSK3 -> "3"
                            HskLevel.HSK4 -> "4"
                            HskLevel.HSK5 -> "5"
                            HskLevel.HSK6 -> "6"
                        },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ผลการวิเคราะห์ระดับความพร้อม",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = FrostedPurplePrimary
            )

            Text(
                text = "ระดับที่เหมาะสม: ${result.recommendedHskLevel.title}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "คะแนนประเมินรวม: ${result.overallScorePercentage}% (${result.correctAnswersCount}/${result.totalQuestionsCount} ข้อ)",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = FrostedSlateLight
            )
        }

        // Summary Card
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = FrostedPurplePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "บทสรุปและคำแนะนำจาก AI:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = result.evaluationSummaryTh,
                        fontSize = 13.sp,
                        color = FrostedSlateLight,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = FrostedPurpleContainer,
                        border = BorderStroke(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Explore,
                                contentDescription = null,
                                tint = FrostedPurplePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "โซนเริ่มต้นในแผนที่เควสต์ของคุณ:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = FrostedSlateLight
                                )
                                Text(
                                    text = result.recommendedZoneTitleTh,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurplePrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4-Dimension Skill Breakdown
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "คะแนนทักษะแยก 4 ด้าน:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    result.skillBreakdown.forEach { skill ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = skill.category.iconEmoji, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = skill.category.titleTh,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = "${skill.percentage}% (${skill.correctCount}/${skill.totalCount})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurplePrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { skill.percentage / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (skill.percentage >= 60) FrostedMatchGreenDark else FrostedPitchUser,
                                trackColor = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }
        }

        // CTAs: Sign up/Login to save vs Start now
        item {
            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = onContinueToAuth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("placement_to_auth_btn"),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "สร้างบัญชี / เข้าสู่ระบบ เพื่อบันทึกผล",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onStartLearningNow,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("placement_start_learning_btn"),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.5.dp, FrostedPurplePrimary.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "เริ่มเรียนรู้ทันที (โหมดผู้เยี่ยมชม)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FrostedPurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            FilledTonalButton(
                onClick = onRetakeTest,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.testTag("placement_retake_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "ทำแบบทดสอบใหม่อีกครั้ง", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
