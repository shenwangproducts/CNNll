import re

path = "app/src/main/java/com/shenhan/app/ui/screens/QuestMapScreen.kt"
with open(path, 'r') as f:
    content = f.read()

# Make sure it uses FrostedGlassCard and updates text colors for dark mode
content = content.replace("""            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, FrostedGlassBorder, RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite60),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {""")

content = content.replace("""    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(borderWidth, borderColor),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {""", """    com.shenhan.app.ui.components.FrostedGlassCard(
        modifier = modifier.fillMaxWidth().testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        borderColor = borderColor,
        borderWidth = borderWidth,
        backgroundColor = containerColor,
        elevation = elevation
    ) {""")

content = content.replace('color = FrostedSlateDark', 'color = Color.White')
content = content.replace('color = FrostedSlateMuted', 'color = FrostedSlateLight')

with open(path, 'w') as f:
    f.write(content)
print("done")
