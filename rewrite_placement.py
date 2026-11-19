import re

path = "app/src/main/java/com/shenhan/app/ui/screens/PlacementTestScreen.kt"
with open(path, 'r') as f:
    content = f.read()

# Replace Card blocks explicitly
content = content.replace("""            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp)
            ) {""")

content = content.replace("""            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {""", """            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            ) {""")

content = content.replace('color = FrostedSlateDark', 'color = Color.White')
content = content.replace('color = FrostedSlateMuted', 'color = FrostedSlateLight')

with open(path, 'w') as f:
    f.write(content)
print("done")
