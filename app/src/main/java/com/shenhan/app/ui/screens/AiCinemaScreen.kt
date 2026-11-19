package com.shenhan.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.data.SampleData
import com.shenhan.app.model.CinemaScenario
import com.shenhan.app.model.DialogueTurn
import com.shenhan.app.model.RoleplayFeedback
import com.shenhan.app.model.ScriptMode
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
fun AiCinemaScreen(
    scriptMode: ScriptMode,
    audioSpeed: Float,
    isAudioRecording: Boolean,
    onSpeak: (String) -> Unit,
    onChangeAudioSpeed: (Float) -> Unit,
    onSimulateUserSpeech: (String) -> Unit,
    onAddXp: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scenarios = SampleData.cinemaScenarios
    var selectedScenarioIndex by remember { mutableIntStateOf(0) }
    val currentScenario = scenarios[selectedScenarioIndex]

    // Active conversation dialogue history for current session
    val dialogueList = remember(selectedScenarioIndex) {
        mutableStateListOf<DialogueTurn>().apply {
            addAll(currentScenario.initialDialogues)
        }
    }

    var textInput by remember { mutableStateOf("") }
    var activeFeedback by remember { mutableStateOf<RoleplayFeedback?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_cinema_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Scenario Selection Horizontal Row
            Text(
                text = "เลือกสถานการณ์จำลอง AI Cinema:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = FrostedSlateDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                itemsIndexed(scenarios) { index, scen ->
                    val isSelected = selectedScenarioIndex == index
                    Card(
                        modifier = Modifier
                            .width(185.dp)
                            .clickable { selectedScenarioIndex = index }
                            .testTag("scenario_card_$index"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) FrostedPurpleContainer else FrostedGlassWhite60
                        ),
                        border = BorderStroke(
                            1.5.dp,
                            if (isSelected) FrostedPurplePrimary else FrostedGlassBorder
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column {
                            if (scen.coverImageRes != null) {
                                Image(
                                    painter = painterResource(id = scen.coverImageRes),
                                    contentDescription = scen.titleTh,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(75.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = if (scriptMode == ScriptMode.SIMPLIFIED) scen.titleZhSimp else scen.titleZhTrad,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) FrostedPurplePrimary else FrostedSlateDark
                                )
                                Text(
                                    text = scen.titleTh,
                                    fontSize = 10.sp,
                                    color = FrostedSlateMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Scenario Header & NPC Info
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(FrostedPurpleContainer)
                                .border(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = currentScenario.npc.avatarEmoji, fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (scriptMode == ScriptMode.SIMPLIFIED) currentScenario.npc.nameZhSimp else currentScenario.npc.nameZhTrad,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = FrostedSlateDark
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "(${currentScenario.npc.nameTh})",
                                    fontSize = 12.sp,
                                    color = FrostedSlateMuted
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = FrostedMatchGreenContainer,
                                    border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = "AI NPC Online",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FrostedMatchGreenDark
                                    )
                                }
                            }
                            Text(
                                text = "บทบาท: ${currentScenario.npc.roleTitleTh} • ${currentScenario.plotIntroTh}",
                                fontSize = 11.sp,
                                color = FrostedSlateMuted
                            )
                        }
                    }

                    // Audio speed control toggle (0.75x, 1.0x, 1.25x)
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                val nextSpeed = when (audioSpeed) {
                                    0.75f -> 1.0f
                                    1.0f -> 1.25f
                                    else -> 0.75f
                                }
                                onChangeAudioSpeed(nextSpeed)
                            },
                        shape = RoundedCornerShape(14.dp),
                        color = FrostedGlassWhite80,
                        border = BorderStroke(1.dp, FrostedGlassBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(14.dp), tint = FrostedPurplePrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${audioSpeed}x",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary
                            )
                        }
                    }
                }
            }
        }

        // Dialogue Turns
        items(dialogueList) { turn ->
            DialogueTurnBubble(
                turn = turn,
                npcName = if (scriptMode == ScriptMode.SIMPLIFIED) currentScenario.npc.nameZhSimp else currentScenario.npc.nameZhTrad,
                scriptMode = scriptMode,
                onPlayAudio = {
                    onSpeak(if (scriptMode == ScriptMode.SIMPLIFIED) turn.textZhSimp else turn.textZhTrad)
                }
            )
        }

        // Live Roleplay Feedback Scorecard
        if (activeFeedback != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = FrostedMatchGreenContainer),
                    border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = FrostedMatchGreenDark, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("AI Real-time Feedback & Diagnostics", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FrostedMatchGreenDark)
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "CEFR: B1",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = FrostedMatchGreenDark
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${activeFeedback!!.toneScore}%", fontWeight = FontWeight.Black, fontSize = 16.sp, color = FrostedMatchGreenDark)
                                Text(text = "ความถูกต้องวรรณยุกต์", fontSize = 10.sp, color = FrostedSlateDark)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${activeFeedback!!.fluencyScore}%", fontWeight = FontWeight.Black, fontSize = 16.sp, color = FrostedMatchGreenDark)
                                Text(text = "ความลื่นไหลเป็นธรรมชาติ", fontSize = 10.sp, color = FrostedSlateDark)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "${activeFeedback!!.grammarScore}%", fontWeight = FontWeight.Black, fontSize = 16.sp, color = FrostedMatchGreenDark)
                                Text(text = "ไวยากรณ์ & การใช้คำ", fontSize = 10.sp, color = FrostedSlateDark)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "คำแนะนำ: ${activeFeedback!!.grammarAdviceTh}", fontSize = 11.sp, color = FrostedSlateDark)
                    }
                }
            }
        }

        // Quick Suggestion Responses from last turn
        val lastTurn = dialogueList.lastOrNull { it.speakerIsNpc }
        if (lastTurn != null && lastTurn.suggestedRepliesSimp.isNotEmpty()) {
            item {
                Column {
                    Text(
                        text = "ประโยคแนะนำสำหรับตอบ AI (Tap to respond):",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = FrostedSlateMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        itemsIndexed(lastTurn.suggestedRepliesSimp) { replyIdx, replySimp ->
                            val replyTrad = lastTurn.suggestedRepliesTrad.getOrElse(replyIdx) { replySimp }
                            val replyPinyin = lastTurn.suggestedRepliesPinyin.getOrElse(replyIdx) { "" }
                            val replyTh = lastTurn.suggestedRepliesTh.getOrElse(replyIdx) { "" }

                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        val userTurn = DialogueTurn(
                                            id = "turn_${System.currentTimeMillis()}",
                                            speakerIsNpc = false,
                                            textZhSimp = replySimp,
                                            textZhTrad = replyTrad,
                                            pinyin = replyPinyin,
                                            textTh = replyTh,
                                            textEn = "",
                                            emotionEmoji = "😊"
                                        )
                                        dialogueList.add(userTurn)
                                        onSimulateUserSpeech(replySimp)
                                        onAddXp(30)

                                        activeFeedback = RoleplayFeedback(
                                            toneScore = 92,
                                            grammarScore = 95,
                                            fluencyScore = 88,
                                            vocabHighlightsTh = listOf(replySimp),
                                            grammarAdviceTh = "ออกเสียงวรรณยุกต์ได้แม่นยำและถูกต้องตามบริบท!",
                                            grammarAdviceEn = "Excellent tone pronunciation and vocabulary context.",
                                            culturalTipTh = "คนท้องถิ่นนิยมตอบแบบกระชับและสุภาพ"
                                        )

                                        // NPC Next response
                                        val npcTurn = DialogueTurn(
                                            id = "turn_npc_${System.currentTimeMillis()}",
                                            speakerIsNpc = true,
                                            textZhSimp = "好的，没问题！马上为您准备，请稍等一下。",
                                            textZhTrad = "好的，沒問題！馬上為您準備，請稍等一下。",
                                            pinyin = "Hǎo de, méi wèntí! Mǎshàng wèi nín zhǔnbèi, qǐng shāoděng yíxià.",
                                            textTh = "ได้เลยครับ ไม่มีปัญหา! เดี๋ยวจัดเตรียมให้ทันที กรุณารอสักครู่นะครับ",
                                            textEn = "Sure, no problem! Preparing it right away, please wait a moment.",
                                            suggestedRepliesSimp = listOf("谢谢！", "麻烦你了。"),
                                            suggestedRepliesTrad = listOf("謝謝！", "麻煩你了。"),
                                            suggestedRepliesPinyin = listOf("Xièxie!", "Máfán nǐ le."),
                                            suggestedRepliesTh = listOf("ขอบคุณครับ/ค่ะ!", "รบกวนด้วยนะครับ")
                                        )
                                        dialogueList.add(npcTurn)
                                    },
                                shape = RoundedCornerShape(16.dp),
                                color = FrostedPurpleContainer,
                                border = BorderStroke(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = if (scriptMode == ScriptMode.SIMPLIFIED) replySimp else replyTrad,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FrostedPurplePrimary
                                    )
                                    Text(
                                        text = replyTh,
                                        fontSize = 10.sp,
                                        color = FrostedSlateMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Live Voice & Text Input Bar
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(26.dp)),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("roleplay_text_input"),
                        placeholder = { Text("พิมพ์ภาษาจีน หรือกดเพื่อตอบกลับ...", fontSize = 12.sp, color = FrostedSlateMuted) },
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FrostedPurplePrimary,
                            unfocusedBorderColor = FrostedGlassBorder,
                            focusedContainerColor = FrostedGlassWhite60,
                            unfocusedContainerColor = FrostedGlassWhite60
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                val userTurn = DialogueTurn(
                                    id = "user_${System.currentTimeMillis()}",
                                    speakerIsNpc = false,
                                    textZhSimp = textInput,
                                    textZhTrad = textInput,
                                    pinyin = "",
                                    textTh = "ประโยคที่คุณพิมพ์",
                                    textEn = ""
                                )
                                dialogueList.add(userTurn)
                                onAddXp(25)
                                textInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(FrostedPurplePrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send message", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun DialogueTurnBubble(
    turn: DialogueTurn,
    npcName: String,
    scriptMode: ScriptMode,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isUser = !turn.speakerIsNpc

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(FrostedPurpleContainer)
                    .border(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = turn.emotionEmoji, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (isUser) 20.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 20.dp
            ),
            color = if (isUser) FrostedPurplePrimary else FrostedGlassWhite80,
            border = BorderStroke(1.dp, if (isUser) FrostedPurplePrimary else FrostedGlassBorder),
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isUser) "คุณ (User)" else npcName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = if (isUser) Color.White.copy(alpha = 0.9f) else FrostedPurplePrimary
                    )
                    IconButton(
                        onClick = onPlayAudio,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Play Dialogue Audio",
                            tint = if (isUser) Color.White else FrostedPurplePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (scriptMode == ScriptMode.SIMPLIFIED) turn.textZhSimp else turn.textZhTrad,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isUser) Color.White else FrostedSlateDark
                )

                if (turn.pinyin.isNotBlank()) {
                    Text(
                        text = turn.pinyin,
                        fontSize = 11.sp,
                        color = if (isUser) Color.White.copy(alpha = 0.8f) else FrostedPurplePrimary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = turn.textTh,
                    fontSize = 12.sp,
                    color = if (isUser) Color.White.copy(alpha = 0.95f) else FrostedSlateMuted
                )
            }
        }
    }
}

