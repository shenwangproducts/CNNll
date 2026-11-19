package com.shenhan.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.model.SubscriptionTier
import com.shenhan.app.ui.theme.FrostedAmberDark
import com.shenhan.app.ui.theme.FrostedGlassBorder
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
fun SubscriptionDialog(
    currentTier: SubscriptionTier,
    onSelectTier: (SubscriptionTier) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTier by remember { mutableStateOf(currentTier) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .fillMaxWidth()
            .testTag("subscription_dialog"),
        containerColor = FrostedGlassWhite80,
        shape = RoundedCornerShape(26.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = FrostedAmberDark,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "อัปเกรด ShenHan Pro & Pass",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FrostedSlateDark
                    )
                    Text(
                        text = "ปลดล็อกคำศัพท์ HSK 1-6 & AI Cinema ไม่จำกัด",
                        style = MaterialTheme.typography.labelSmall,
                        color = FrostedSlateMuted
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SubscriptionTier.values().forEach { tier ->
                    val isSelected = selectedTier == tier
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedTier = tier }
                            .border(
                                1.5.dp,
                                if (isSelected) FrostedPurplePrimary else FrostedGlassBorder,
                                RoundedCornerShape(16.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) FrostedPurpleContainer else FrostedGlassWhite60
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = tier.titleTh,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) FrostedPurplePrimary else FrostedSlateDark
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (tier.isPro) FrostedMatchGreenContainer else FrostedPurpleContainer,
                                        border = BorderStroke(
                                            1.dp,
                                            if (tier.isPro) FrostedMatchGreen.copy(alpha = 0.4f) else FrostedPurplePrimary.copy(alpha = 0.3f)
                                        )
                                    ) {
                                        Text(
                                            text = tier.badge,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (tier.isPro) FrostedMatchGreenDark else FrostedPurplePrimary
                                        )
                                    }
                                }
                                Text(
                                    text = tier.priceTh,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (tier.isPro) FrostedMatchGreenDark else FrostedSlateMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = tier.descriptionTh,
                                    fontSize = 10.sp,
                                    color = FrostedSlateMuted
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = FrostedPurplePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSelectTier(selectedTier)
                    onDismiss()
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
            ) {
                Text("ยืนยันแผนการใช้งาน")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ยกเลิก", color = FrostedSlateMuted)
            }
        }
    )
}

