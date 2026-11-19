import re

path = "app/src/main/java/com/shenhan/app/ui/screens/ProfileScreen.kt"
with open(path, 'r') as f:
    content = f.read()

# Make it use FrostedGlassCard and remove FrostedGlassWhite80/60 where applicable.

content = content.replace('Card(', 'com.shenhan.app.ui.components.FrostedGlassCard(')
content = content.replace('import androidx.compose.material3.Card\n', '')
content = content.replace('colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),', '')
content = content.replace('colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),', '')
content = content.replace('elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)', '')
content = content.replace('elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)', '')

# Fix borders on the cards - FrostedGlassCard already has a sleek border by default in our modified version.
content = re.sub(r'\.border\([^)]*FrostedGlassBorder[^)]*\)', '', content)
content = re.sub(r'shape = RoundedCornerShape\(2[46]\.dp\),', '', content)

# Redesign the hero header
hero_pattern = re.compile(r'// User Header Profile Card.*?Box\([^)]*size\(68\.dp\).*?Text\(text = currentUser\?\.avatarEmoji.*?\).*?\}', re.DOTALL)

new_hero = """// User Header Profile Card
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_user_card"),
                backgroundColor = com.shenhan.app.ui.theme.FrostedCanvasBg.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(
                                androidx.compose.ui.graphics.Brush.linearGradient(
                                    listOf(FrostedPurplePrimary, com.shenhan.app.ui.theme.FrostedPurpleLight)
                                )
                            )
                            .border(2.dp, FrostedPurplePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentUser?.avatarEmoji ?: "🐼", fontSize = 38.sp)
                    }"""

content = hero_pattern.sub(new_hero, content)

# Update texts
content = content.replace('color = FrostedSlateDark', 'color = Color.White')
content = content.replace('color = FrostedSlateMuted', 'color = FrostedSlateLight')

with open(path, 'w') as f:
    f.write(content)

print("Updated ProfileScreen.kt")
