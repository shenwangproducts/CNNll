package com.shenhan.app.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.shenhan.app.data.SampleData
import com.shenhan.app.model.SubscriptionTier
import com.shenhan.app.ui.theme.FrostedAmberDark
import com.shenhan.app.ui.theme.FrostedAmberPill
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite40
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun HumanGatesModal(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("5 ระยะคุมงาน (Human Gates)", "เกณฑ์ SLA & AI Core", "โมเดลราคา (Monetization)")

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .fillMaxWidth()
            .testTag("human_gates_modal_dialog"),
        containerColor = FrostedGlassWhite80,
        shape = RoundedCornerShape(26.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Engineering,
                        contentDescription = "TOR Specifications",
                        tint = FrostedPurplePrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "ShenHan (神漢) TOR Specs",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FrostedSlateDark
                        )
                        Text(
                            text = "Technical Specification & Human Oversight Checkpoints",
                            style = MaterialTheme.typography.labelSmall,
                            color = FrostedSlateMuted
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = FrostedSlateMuted)
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 8.dp,
                    containerColor = FrostedGlassWhite60,
                    contentColor = FrostedPurplePrimary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = FrostedPurplePrimary
                        )
                    },
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) FrostedPurplePrimary else FrostedSlateMuted
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                when (selectedTab) {
                    0 -> {
                        // 5 Development Phases & Human Oversight Gate
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(SampleData.torHumanGates) { gate ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, if (gate.isCurrentPhase) FrostedPurplePrimary else FrostedGlassBorder, RoundedCornerShape(16.dp)),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (gate.isCurrentPhase) FrostedPurpleContainer else FrostedGlassWhite60
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = gate.phaseTitleTh,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = if (gate.isCurrentPhase) FrostedPurplePrimary else FrostedSlateDark
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = if (gate.statusTh.contains("COMPLETED")) FrostedMatchGreenContainer else if (gate.isCurrentPhase) FrostedPurpleContainer else FrostedGlassWhite60,
                                                border = BorderStroke(1.dp, if (gate.statusTh.contains("COMPLETED")) FrostedMatchGreen.copy(alpha = 0.4f) else FrostedGlassBorder)
                                            ) {
                                                Text(
                                                    text = gate.statusTh,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (gate.statusTh.contains("COMPLETED")) FrostedMatchGreenDark else if (gate.isCurrentPhase) FrostedPurplePrimary else FrostedSlateMuted
                                                )
                                            }
                                        }
                                        Text(
                                            text = "ช่วงเวลา: ${gate.timePeriodTh}",
                                            fontSize = 11.sp,
                                            color = FrostedSlateMuted
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "📦 ผลงานส่งมอบ: ${gate.deliverablesTh}",
                                            fontSize = 11.sp,
                                            color = FrostedSlateDark
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = FrostedAmberPill,
                                            border = BorderStroke(1.dp, FrostedAmberDark.copy(alpha = 0.3f)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "🧑✈️ จุดคุมมนุษย์เช็กทำ: ${gate.humanCheckActionTh}",
                                                modifier = Modifier.padding(8.dp),
                                                fontSize = 11.sp,
                                                color = FrostedAmberDark,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        // Latency & AI Core Integration Benchmarks
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = FrostedMatchGreenContainer),
                                border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = FrostedMatchGreenDark, modifier = Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Audio Latency SLA Benchmark", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FrostedMatchGreenDark)
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("• เป้าหมายความหน่วง (Latency): < 150 ms", fontSize = 12.sp, color = FrostedMatchGreenDark)
                                    Text("• Real-time Tone Pitch Analyzer: 45 ms / frame sample", fontSize = 12.sp, color = FrostedMatchGreenDark)
                                    Text("• OpenAI Whisper API & DeepL integration: Proof of Concept ผ่านการทดสอบ", fontSize = 12.sp, color = FrostedMatchGreenDark)
                                }
                            }

                            Card(
                                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                                border = BorderStroke(1.dp, FrostedGlassBorder),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("ข้อมูลสถาปัตยกรรมระบบ (Architecture)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FrostedSlateDark)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("• ผู้รับผิดชอบโครงการ: Shenwang Ent. CO LTD", fontSize = 11.sp, color = FrostedSlateMuted)
                                    Text("• ขนาดติดตั้งตั้งต้น: ควบคุมให้อยู่ในกรอบ 45 MB – 60 MB ด้วย On-Demand Asset Streaming", fontSize = 11.sp, color = FrostedSlateMuted)
                                    Text("• Multi-language: ไทย (หลัก), อังกฤษ, จีนตัวย่อ (简体) / ตัวเต็ม (繁体) สลับใน 1 แตะ", fontSize = 11.sp, color = FrostedSlateMuted)
                                    Text("• Accessibility: รองรับ TalkBack และความเร็วเสียง AI 0.75x - 1.25x", fontSize = 11.sp, color = FrostedSlateMuted)
                                }
                            }
                        }
                    }
                    2 -> {
                        // Monetization Matrix
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(SubscriptionTier.values().toList()) { tier ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (tier == SubscriptionTier.PRO_YEARLY) FrostedAmberPill else FrostedGlassWhite60
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (tier == SubscriptionTier.PRO_YEARLY) FrostedAmberDark.copy(alpha = 0.4f) else FrostedGlassBorder
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = tier.titleTh, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FrostedSlateDark)
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = if (tier.isPro) FrostedMatchGreenContainer else FrostedPurpleContainer,
                                                border = BorderStroke(1.dp, if (tier.isPro) FrostedMatchGreen.copy(alpha = 0.4f) else FrostedPurplePrimary.copy(alpha = 0.3f))
                                            ) {
                                                Text(
                                                    text = tier.priceTh,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Black,
                                                    color = if (tier.isPro) FrostedMatchGreenDark else FrostedPurplePrimary
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = tier.descriptionTh, fontSize = 11.sp, color = FrostedSlateMuted)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
            ) {
                Text("เข้าใจและปิดหน้าต่าง")
            }
        }
    )
}

