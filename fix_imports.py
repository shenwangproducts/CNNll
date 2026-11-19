import re

files = [
    "app/src/main/java/com/shenhan/app/ui/screens/QuestMapScreen.kt",
    "app/src/main/java/com/shenhan/app/ui/screens/PlacementTestScreen.kt",
    "app/src/main/java/com/shenhan/app/ui/screens/ProfileScreen.kt"
]

for path in files:
    with open(path, 'r') as f:
        content = f.read()
    
    if "FrostedSlateLight" in content and "import com.shenhan.app.ui.theme.FrostedSlateLight" not in content:
        content = content.replace("import com.shenhan.app.ui.theme.FrostedSlateMuted", "import com.shenhan.app.ui.theme.FrostedSlateMuted\nimport com.shenhan.app.ui.theme.FrostedSlateLight")
        
    with open(path, 'w') as f:
        f.write(content)

print("done")
