package com.shenhan.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Translate
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.shenhan.app.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.SubscriptionTier
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassBorderSlate
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPitchUser
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurpleLight
import com.shenhan.app.ui.theme.FrostedPurpleOnContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun ShenHanTopAppBar(
    scriptMode: ScriptMode,
    streakDays: Int,
    userXp: Int,
    subscriptionTier: SubscriptionTier,
    onToggleScript: () -> Unit,
    onOpenHumanGates: () -> Unit,
    onOpenSubscription: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .border(
                width = 1.dp,
                color = FrostedGlassBorder,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .testTag("shenhan_top_app_bar"),
        color = FrostedGlassWhite80,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Title with Frosted Purple Gradient Avatar
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon_1788077123859),
                        contentDescription = "ShenHan App Logo",
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.5.dp, FrostedPurplePrimary, RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "神漢",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = FrostedPurplePrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ShenHan",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = FrostedSlateMuted
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = FrostedPurpleContainer
                            ) {
                                Text(
                                    text = "HSK LEVEL 4",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FrostedPurpleOnContainer,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Text(
                                text = "• Live-Talk",
                                style = MaterialTheme.typography.labelSmall,
                                color = FrostedSlateMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Action controls: 1-Tap Script Switcher, Streak Badge, TOR Gate Specs
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 1-Tap Simplified / Traditional Switcher
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                1.dp,
                                FrostedGlassBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { onToggleScript() }
                            .testTag("script_toggle_button"),
                        color = FrostedGlassWhite60
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Switch Script",
                                tint = FrostedPurplePrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (scriptMode == ScriptMode.SIMPLIFIED) "简 ⇄ 繁" else "繁 ⇄ 简",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = FrostedPurplePrimary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Daily Streak Fire Badge
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, FrostedGlassBorder, RoundedCornerShape(16.dp)),
                        color = Color(0x33FFCC80)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Daily Streak",
                                tint = FrostedPitchUser,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "$streakDays",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black,
                                color = FrostedPitchUser,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Subscription / Pro Badge
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, FrostedGlassBorder, RoundedCornerShape(16.dp))
                            .clickable { onOpenSubscription() }
                            .testTag("subscription_badge_button"),
                        color = if (subscriptionTier.isPro) FrostedMatchGreenContainer else FrostedGlassWhite60
                    ) {
                        Text(
                            text = subscriptionTier.badge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Black,
                            color = if (subscriptionTier.isPro) FrostedMatchGreenDark else FrostedSlateMuted,
                            fontSize = 10.sp
                        )
                    }

                    // TOR Specs / Human Checkpoints Info Button
                    IconButton(
                        onClick = onOpenHumanGates,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .border(1.dp, FrostedGlassBorder, CircleShape)
                            .background(FrostedGlassWhite60)
                            .testTag("human_gates_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = "Project TOR Roadmap & Human Gates",
                            tint = FrostedPurplePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

