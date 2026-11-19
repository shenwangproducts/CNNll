import re

path = "app/src/main/java/com/shenhan/app/ui/screens/ProfileScreen.kt"
with open(path, 'r') as f:
    content = f.read()

# Replace Card blocks explicitly
# Block 1
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(26.dp))
                    .testTag("profile_user_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_user_card"),
                backgroundColor = com.shenhan.app.ui.theme.FrostedCanvasBg.copy(alpha = 0.6f)
            ) {""")

# Block 2
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(24.dp))
                    .testTag("profile_placement_test_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_placement_test_card")
            ) {""")

# Block 3
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(26.dp))
                    .testTag("hsk_readiness_radar_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hsk_readiness_radar_card")
            ) {""")

# Block 4
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(26.dp)),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {""")

# Block 5
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(26.dp))
                    .testTag("profile_linked_accounts_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_linked_accounts_card")
            ) {""")

# Also fix the avatar styling to look neon
avatar_old = """Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(FrostedPurpleContainer)
                            .border(1.5.dp, FrostedPurplePrimary.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentUser?.avatarEmoji ?: "🐼", fontSize = 34.sp)
                    }"""

avatar_new = """Box(
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
                    }"""
content = content.replace(avatar_old, avatar_new)

# Fix typography colors to pop in dark mode
content = content.replace('color = FrostedSlateDark', 'color = Color.White')
content = content.replace('color = FrostedSlateMuted', 'color = FrostedSlateLight')

with open(path, 'w') as f:
    f.write(content)
print("done")
