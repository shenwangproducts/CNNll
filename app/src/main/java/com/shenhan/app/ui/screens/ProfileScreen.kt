package com.shenhan.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.data.SampleData
import com.shenhan.app.model.AchievementBadge
import com.shenhan.app.model.AppLanguage
import com.shenhan.app.model.AuthProvider
import com.shenhan.app.model.HskReadinessScore
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.SubscriptionTier
import com.shenhan.app.model.UserProfile
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
import com.shenhan.app.ui.theme.FrostedPurpleOnContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateLight
import com.shenhan.app.ui.theme.FrostedSlateMuted
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProfileScreen(
    currentUser: UserProfile?,
    currentLanguage: AppLanguage,
    scriptMode: ScriptMode,
    subscriptionTier: SubscriptionTier,
    streakDays: Int,
    userXp: Int,
    userLevel: Int,
    readinessScore: HskReadinessScore,
    audioSpeed: Float,
    onLanguageChange: (AppLanguage) -> Unit,
    onToggleScript: () -> Unit,
    onAudioSpeedChange: (Float) -> Unit,
    onOpenSubscription: () -> Unit,
    onOpenHumanGates: () -> Unit,
    onRetakePlacementTest: () -> Unit,
    onSwitchAccount: () -> Unit,
    onLinkThirdParty: (AuthProvider) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // User Header Profile Card
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_user_card"),
                backgroundColor = com.shenhan.app.ui.theme.FrostedCanvasBg.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(
                                androidx.compose.ui.graphics.Brush.linearGradient(
                                    listOf(FrostedPurplePrimary.copy(alpha=0.6f), FrostedPurpleContainer)
                                )
                            )
                            .border(2.dp, FrostedPurplePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentUser?.avatarEmoji ?: "🐼", fontSize = 38.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currentUser?.displayName ?: "ผู้เรียนรู้ ShenHan",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (subscriptionTier.isPro) FrostedMatchGreenContainer else FrostedPurpleContainer,
                                border = BorderStroke(
                                    1.dp,
                                    if (subscriptionTier.isPro) FrostedMatchGreen.copy(alpha = 0.4f) else FrostedPurplePrimary.copy(alpha = 0.3f)
                                )
                            ) {
                                Text(
                                    text = subscriptionTier.badge,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (subscriptionTier.isPro) FrostedMatchGreenDark else FrostedPurplePrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${currentUser?.email ?: "guest@shenhan.local"} • Level $userLevel",
                            style = MaterialTheme.typography.bodySmall,
                            color = FrostedSlateLight,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "ระดับเริ่มต้น: ${currentUser?.startingHskLevel?.title ?: "HSK 1"} • $userXp Total XP",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FrostedPurplePrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // XP Progress to next level
                        val progress = (userXp % 350) / 350f
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(7.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = FrostedPurplePrimary,
                            trackColor = FrostedGlassWhite80
                        )
                    }
                }
            }
        }

        // Diagnostic Placement Test CTA Card
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_placement_test_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(FrostedPurpleContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎯", fontSize = 26.sp)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "แบบทดสอบวัดระดับ HSK (Placement)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "ระดับปัจจุบัน: ${currentUser?.startingHskLevel?.title ?: "HSK 1"}",
                            fontSize = 11.sp,
                            color = FrostedSlateLight
                        )
                    }
                    Button(
                        onClick = onRetakePlacementTest,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary),
                        modifier = Modifier.testTag("profile_retake_placement_btn")
                    ) {
                        Text("วัดระดับใหม่", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // HSK Readiness Radar Visualizer Card
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hsk_readiness_radar_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "HSK Readiness Radar & AI Evaluation",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary
                            )
                            Text(
                                text = "ประเมินความพร้อมสอบ ${readinessScore.targetHskLevel.title}",
                                style = MaterialTheme.typography.bodySmall,
                                color = FrostedSlateLight
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = FrostedMatchGreenContainer,
                            border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "${readinessScore.readinessPercentage}% พร้อมสอบ",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = FrostedMatchGreenDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Radar Chart Canvas (Listening, Speaking Tone, Reading, Vocab)
                    Box(
                        modifier = Modifier
                            .size(210.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(FrostedGlassWhite80)
                            .border(1.dp, FrostedGlassBorder, RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize().padding(26.dp)) {
                            val center = Offset(size.width / 2f, size.height / 2f)
                            val radius = size.width / 2f

                            // Draw concentric radar webs
                            val webSteps = 4
                            for (s in 1..webSteps) {
                                val r = radius * (s / webSteps.toFloat())
                                val webPath = Path()
                                for (i in 0 until 4) {
                                    val angle = (i * 90 - 45) * (Math.PI / 180.0)
                                    val x = (center.x + r * cos(angle)).toFloat()
                                    val y = (center.y + r * sin(angle)).toFloat()
                                    if (i == 0) webPath.moveTo(x, y) else webPath.lineTo(x, y)
                                }
                                webPath.close()
                                drawPath(
                                    path = webPath,
                                    color = FrostedGlassBorder,
                                    style = Stroke(width = 1.dp.toPx())
                                )
                            }

                            // Draw Radar Score Polygon
                            val scores = listOf(
                                readinessScore.listeningScore / 100f,
                                readinessScore.speakingToneScore / 100f,
                                readinessScore.readingScore / 100f,
                                readinessScore.vocabMasteryScore / 100f
                            )

                            val scorePath = Path()
                            scores.forEachIndexed { i, sc ->
                                val angle = (i * 90 - 45) * (Math.PI / 180.0)
                                val r = radius * sc
                                val x = (center.x + r * cos(angle)).toFloat()
                                val y = (center.y + r * sin(angle)).toFloat()
                                if (i == 0) scorePath.moveTo(x, y) else scorePath.lineTo(x, y)
                            }
                            scorePath.close()

                            // Fill
                            drawPath(
                                path = scorePath,
                                color = FrostedPurplePrimary.copy(alpha = 0.25f),
                                style = Fill
                            )
                            // Stroke
                            drawPath(
                                path = scorePath,
                                color = FrostedPurplePrimary,
                                style = Stroke(width = 2.5.dp.toPx())
                            )
                        }

                        // Labels around radar
                        Column(
                            modifier = Modifier.fillMaxSize().padding(6.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("การฟัง (${readinessScore.listeningScore}%)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("คลังศัพท์ (${readinessScore.vocabMasteryScore}%)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("วรรณยุกต์ (${readinessScore.speakingToneScore}%)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Text("การอ่าน (${readinessScore.readingScore}%)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }

        // Achievements & Badges
        item {
            Column {
                Text(
                    text = "เหรียญรางวัลเกียรติยศ (Badges)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(SampleData.badges) { badge ->
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = if (badge.isUnlocked) FrostedAmberPill else FrostedGlassWhite60,
                            border = BorderStroke(
                                1.dp,
                                if (badge.isUnlocked) FrostedAmberDark.copy(alpha = 0.4f) else FrostedGlassBorder
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(135.dp)
                                    .padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = badge.iconEmoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = badge.titleTh,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (badge.isUnlocked) FrostedAmberDark else FrostedSlateMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = badge.descriptionTh,
                                    fontSize = 9.sp,
                                    color = FrostedSlateLight,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // App Settings & Preferences
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "การตั้งค่าระบบ (Settings & Accessibility)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FrostedPurplePrimary
                    )

                    // 1. Script Mode Toggle (Simplified / Traditional)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Translate, contentDescription = null, tint = FrostedPurplePrimary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("รูปแบบตัวอักษรจีน (Script Mode)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(
                                    text = if (scriptMode == ScriptMode.SIMPLIFIED) "简体 (จีนตัวย่อ)" else "繁体 (จีนตัวเต็ม)",
                                    fontSize = 11.sp,
                                    color = FrostedSlateLight
                                )
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onToggleScript() },
                            shape = RoundedCornerShape(12.dp),
                            color = FrostedPurpleContainer,
                            border = BorderStroke(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = if (scriptMode == ScriptMode.SIMPLIFIED) "สลับเป็นตัวเต็ม" else "สลับเป็นตัวย่อ",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary
                            )
                        }
                    }

                    // 2. Audio Speed Slider (0.75x - 1.25x)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = FrostedPurplePrimary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("ความเร็วเสียง AI TTS:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Text("${audioSpeed}x", fontWeight = FontWeight.Black, fontSize = 13.sp, color = FrostedPurplePrimary)
                        }
                        Slider(
                            value = audioSpeed,
                            onValueChange = onAudioSpeedChange,
                            valueRange = 0.75f..1.25f,
                            steps = 1,
                            colors = SliderDefaults.colors(
                                thumbColor = FrostedPurplePrimary,
                                activeTrackColor = FrostedPurplePrimary,
                                inactiveTrackColor = FrostedGlassWhite80
                            )
                        )
                    }

                    // 3. Subscription Tier Management
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CardMembership, contentDescription = null, tint = FrostedAmberDark)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("แพ็กเกจสมาชิก:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(subscriptionTier.titleTh, fontSize = 11.sp, color = FrostedSlateLight)
                            }
                        }
                        Button(
                            onClick = onOpenSubscription,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
                        ) {
                            Text("จัดการแพ็กเกจ", fontSize = 11.sp)
                        }
                    }

                    // 4. View TOR Specs & Human Gates
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Assessment, contentDescription = null, tint = FrostedPurplePrimary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("TOR Roadmap & Human Gates", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("5 ระยะคุมงาน & สเปกระบบ", fontSize = 11.sp, color = FrostedSlateLight)
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onOpenHumanGates() },
                            shape = RoundedCornerShape(12.dp),
                            color = FrostedPurpleContainer,
                            border = BorderStroke(1.dp, FrostedPurplePrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "ดูข้อกำหนด",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary
                            )
                        }
                    }
                    
                    HorizontalDivider(color = FrostedGlassBorder)
                    
                    // 3rd-Party Linked Accounts & Multi-User Management
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = FrostedPurplePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "บัญชีบุคคลที่สาม & การเชื่อมต่อ (Linked Accounts)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FrostedPurplePrimary
                        )
                    }

                    Text(
                        text = "ผูกบัญชีเพื่อสำรองข้อมูลและเข้าสู่ระบบจากหลายเครื่อง:",
                        fontSize = 12.sp,
                        color = FrostedSlateLight
                    )

                    val linkedList = currentUser?.linkedProviders ?: emptyList()

                    // Providers List
                    listOf(
                        AuthProvider.GOOGLE,
                        AuthProvider.APPLE,
                        AuthProvider.WECHAT,
                        AuthProvider.LINE
                    ).forEach { provider ->
                        val isLinked = linkedList.contains(provider)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = provider.iconEmoji, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = provider.displayName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = if (isLinked) "เชื่อมต่อแล้ว ✓" else "ยังไม่ได้เชื่อมต่อ",
                                        fontSize = 11.sp,
                                        color = if (isLinked) FrostedMatchGreenDark else FrostedSlateMuted
                                    )
                                }
                            }

                            if (isLinked) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = FrostedMatchGreenContainer
                                ) {
                                    Text(
                                        text = "เชื่อมต่อแล้ว",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FrostedMatchGreenDark
                                    )
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { onLinkThirdParty(provider) },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.testTag("link_${provider.name.lowercase()}_btn")
                                ) {
                                    Text("ผูกบัญชี", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    HorizontalDivider(color = FrostedGlassBorder)

                    // Account Switching / Log Out
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = onSwitchAccount,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_switch_account_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("สลับบัญชี", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        FilledTonalButton(
                            onClick = onSwitchAccount,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("profile_logout_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ออกจากระบบ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}


