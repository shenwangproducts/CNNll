import re

# 1. Update ShenHanTopAppBar.kt to use the app icon and neon colors
app_bar_path = "app/src/main/java/com/shenhan/app/ui/components/ShenHanTopAppBar.kt"
with open(app_bar_path, 'r') as f:
    content = f.read()

content = content.replace('import androidx.compose.material.icons.filled.Translate', 'import androidx.compose.material.icons.filled.Translate\nimport androidx.compose.foundation.Image\nimport androidx.compose.ui.res.painterResource\nimport com.shenhan.app.R')

# Replace the circular emblem with the app icon
emblem_pattern = re.compile(r'Box\(\s*modifier\s*=\s*Modifier[\s\S]*?Text\(\s*text\s*=\s*"神"[\s\S]*?\)\s*\}')

app_icon_code = """Image(
                        painter = painterResource(id = R.drawable.img_app_icon_1788077123859),
                        contentDescription = "ShenHan App Logo",
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.5.dp, FrostedPurplePrimary, RoundedCornerShape(12.dp))
                    )"""

content = emblem_pattern.sub(app_icon_code, content)

with open(app_bar_path, 'w') as f:
    f.write(content)

print("Updated ShenHanTopAppBar.kt")
