package com.shenhan.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.R
import com.shenhan.app.model.AuthProvider
import com.shenhan.app.model.AuthScreenMode
import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.PlacementTestResult
import com.shenhan.app.model.UserProfile
import com.shenhan.app.ui.theme.FrostedAmberDark
import com.shenhan.app.ui.theme.FrostedAmberPill
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite60
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedMatchGreen
import com.shenhan.app.ui.theme.FrostedMatchGreenContainer
import com.shenhan.app.ui.theme.FrostedMatchGreenDark
import com.shenhan.app.ui.theme.FrostedPitchTarget
import com.shenhan.app.ui.theme.FrostedPitchUser
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateDark
import com.shenhan.app.ui.theme.FrostedSlateLight
import com.shenhan.app.ui.theme.FrostedSlateMuted

@Composable
fun AuthScreen(
    placementResult: PlacementTestResult?,
    onAuthSuccess: (UserProfile) -> Unit,
    onContinueAsGuest: () -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var authMode by remember { mutableStateOf(AuthScreenMode.REGISTER) }

    // Form fields
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedDailyGoalMinutes by remember { mutableIntStateOf(placementResult?.suggestedDailyGoalMinutes ?: 15) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    val initialHskLevel = placementResult?.recommendedHskLevel ?: HskLevel.HSK1

    fun handleEmailAuth() {
        if (emailInput.isBlank() || !emailInput.contains("@")) {
            errorMessage = "กรุณากรอกอีเมลให้ถูกต้อง"
            return
        }
        if (passwordInput.length < 6) {
            errorMessage = "รหัสผ่านต้องมีความยาวอย่างน้อย 6 ตัวอักษร"
            return
        }

        val displayName = if (nameInput.isNotBlank()) nameInput else emailInput.substringBefore("@")
        val user = UserProfile(
            id = "user_${System.currentTimeMillis()}",
            displayName = displayName,
            email = emailInput,
            avatarEmoji = if (authMode == AuthScreenMode.REGISTER) "🎓" else "🐼",
            provider = AuthProvider.EMAIL,
            linkedProviders = listOf(AuthProvider.EMAIL),
            startingHskLevel = initialHskLevel,
            dailyGoalMinutes = selectedDailyGoalMinutes,
            isGuest = false
        )
        successMessage = if (authMode == AuthScreenMode.REGISTER) "สมัครสมาชิกสำเร็จ ยินดีต้อนรับ!" else "เข้าสู่ระบบสำเร็จ!"
        onAuthSuccess(user)
    }

    fun handleThirdPartyAuth(provider: AuthProvider) {
        val (mockName, mockEmail, mockEmoji) = when (provider) {
            AuthProvider.GOOGLE -> Triple("Google User", "user@gmail.com", "🌐")
            AuthProvider.APPLE -> Triple("Apple ID Member", "user@icloud.com", "🍏")
            AuthProvider.WECHAT -> Triple("WeChat จอมยุทธ์", "wechat_user_88", "💬")
            AuthProvider.LINE -> Triple("LINE Member", "line_user_th", "🟢")
            else -> Triple("ShenHan Scholar", "user@shenhan.app", "🐼")
        }

        val user = UserProfile(
            id = "auth_${provider.name.lowercase()}_${System.currentTimeMillis()}",
            displayName = mockName,
            email = mockEmail,
            avatarEmoji = mockEmoji,
            provider = provider,
            linkedProviders = listOf(provider),
            startingHskLevel = initialHskLevel,
            dailyGoalMinutes = selectedDailyGoalMinutes,
            isGuest = false
        )
        onAuthSuccess(user)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))

            if (onBack != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("auth_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = FrostedSlateDark
                        )
                    }
                }
            }

            // Top Emblem & Logo
            Image(
                painter = painterResource(id = R.drawable.img_app_icon_1788077123859),
                contentDescription = "ShenHan App Logo",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .border(2.dp, FrostedPurplePrimary, RoundedCornerShape(26.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (authMode == AuthScreenMode.REGISTER) "สมัครสมาชิก ShenHan" else "เข้าสู่ระบบ ShenHan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = FrostedSlateDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "บันทึกความก้าวหน้า เชื่อมต่อหลายอุปกรณ์ และฝึกฝนภาษาจีนอย่างต่อเนื่อง",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = FrostedSlateMuted
            )
        }

        // Placement Test Context Banner (if user came from placement test)
        if (placementResult != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = FrostedMatchGreenContainer,
                    border = BorderStroke(1.dp, FrostedMatchGreen.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎯", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "พร้อมเชื่อมต่อผลประเมิน HSK ของคุณ!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = FrostedMatchGreenDark
                            )
                            Text(
                                text = "ระดับเริ่มต้น: ${placementResult.recommendedHskLevel.title} (${placementResult.overallScorePercentage}%)",
                                fontSize = 12.sp,
                                color = FrostedSlateDark
                            )
                        }
                    }
                }
            }
        }

        // Auth Tabs: Sign In / Sign Up
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                TabRow(
                    selectedTabIndex = if (authMode == AuthScreenMode.REGISTER) 0 else 1,
                    containerColor = Color.Transparent,
                    contentColor = FrostedPurplePrimary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[if (authMode == AuthScreenMode.REGISTER) 0 else 1]),
                            color = FrostedPurplePrimary,
                            height = 3.dp
                        )
                    },
                    divider = {}
                ) {
                    Tab(
                        selected = authMode == AuthScreenMode.REGISTER,
                        onClick = {
                            authMode = AuthScreenMode.REGISTER
                            errorMessage = null
                        },
                        modifier = Modifier.testTag("tab_register"),
                        text = {
                            Text(
                                text = "สมัครสมาชิกใหม่",
                                fontSize = 14.sp,
                                fontWeight = if (authMode == AuthScreenMode.REGISTER) FontWeight.Bold else FontWeight.Medium,
                                color = if (authMode == AuthScreenMode.REGISTER) FrostedPurplePrimary else FrostedSlateMuted
                            )
                        }
                    )
                    Tab(
                        selected = authMode == AuthScreenMode.LOGIN,
                        onClick = {
                            authMode = AuthScreenMode.LOGIN
                            errorMessage = null
                        },
                        modifier = Modifier.testTag("tab_login"),
                        text = {
                            Text(
                                text = "เข้าสู่ระบบ",
                                fontSize = 14.sp,
                                fontWeight = if (authMode == AuthScreenMode.LOGIN) FontWeight.Bold else FontWeight.Medium,
                                color = if (authMode == AuthScreenMode.LOGIN) FrostedPurplePrimary else FrostedSlateMuted
                            )
                        }
                    )
                }
            }
        }

        // Input Fields Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (authMode == AuthScreenMode.REGISTER) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("ชื่อหรือชื่อเล่น (Nickname)") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = FrostedPurplePrimary)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_name_input"),
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FrostedPurplePrimary,
                                unfocusedBorderColor = FrostedGlassBorder
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                        )
                    }

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = {
                            emailInput = it
                            errorMessage = null
                        },
                        label = { Text("อีเมล (Email Address)") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = FrostedPurplePrimary)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_email_input"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FrostedPurplePrimary,
                            unfocusedBorderColor = FrostedGlassBorder
                        )
                    )

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = {
                            passwordInput = it
                            errorMessage = null
                        },
                        label = { Text("รหัสผ่าน (Password)") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = FrostedPurplePrimary)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_password_input"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            handleEmailAuth()
                        }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FrostedPurplePrimary,
                            unfocusedBorderColor = FrostedGlassBorder
                        )
                    )

                    // Daily Study Goal (for Register)
                    if (authMode == AuthScreenMode.REGISTER) {
                        Column {
                            Text(
                                text = "เป้าหมายการเรียนต่อวัน:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = FrostedSlateDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(10, 15, 30, 45).forEach { minutes ->
                                    val isSelected = selectedDailyGoalMinutes == minutes
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { selectedDailyGoalMinutes = minutes },
                                        color = if (isSelected) FrostedPurpleContainer else Color(0xFFF1F5F9),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isSelected) FrostedPurplePrimary else Color.Transparent
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = "$minutes นาที",
                                            modifier = Modifier.padding(vertical = 8.dp),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) FrostedPurplePrimary else FrostedSlateMuted,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            fontSize = 12.sp,
                            color = Color(0xFFDC2626),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = { handleEmailAuth() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("auth_submit_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FrostedPurplePrimary)
                    ) {
                        Text(
                            text = if (authMode == AuthScreenMode.REGISTER) "สร้างบัญชีผู้ใช้ใหม่" else "เข้าสู่ระบบ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        // 3rd-Party Social Sign-In Divider
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCBD5E1))
                Text(
                    text = "  หรือเข้าสู่ระบบด้วยบุคคลที่สาม  ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FrostedSlateMuted
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFCBD5E1))
            }
        }

        // 3rd-Party Social Sign-In Buttons
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = FrostedGlassWhite80),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Google Sign In
                    ThirdPartyLoginButton(
                        iconEmoji = "🌐",
                        title = "ดำเนินการต่อด้วย Google (Google Sign-In)",
                        subtitle = "ซิงก์ข้อมูลผ่านบัญชี Google Account",
                        containerColor = Color(0xFFF8FAFC),
                        borderColor = Color(0xFFCBD5E1),
                        testTag = "login_google_btn",
                        onClick = { handleThirdPartyAuth(AuthProvider.GOOGLE) }
                    )

                    // Apple ID Sign In
                    ThirdPartyLoginButton(
                        iconEmoji = "🍏",
                        title = "ดำเนินการต่อด้วย Apple (Sign in with Apple)",
                        subtitle = "ปลอดภัยด้วยระบบ Apple ID",
                        containerColor = Color.White,
                        textColor = Color.Black,
                        borderColor = Color.Black,
                        testTag = "login_apple_btn",
                        onClick = { handleThirdPartyAuth(AuthProvider.APPLE) }
                    )

                    // WeChat / LINE Sign In
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // WeChat
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { handleThirdPartyAuth(AuthProvider.WECHAT) }
                                .testTag("login_wechat_btn"),
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF07C160).copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Color(0xFF07C160).copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "💬", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "WeChat (微信)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF07C160)
                                )
                            }
                        }

                        // LINE
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { handleThirdPartyAuth(AuthProvider.LINE) }
                                .testTag("login_line_btn"),
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF06C755).copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Color(0xFF06C755).copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "🟢", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "LINE Login",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF06C755)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Guest Mode fallback CTA
        item {
            OutlinedButton(
                onClick = onContinueAsGuest,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("auth_guest_button"),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, FrostedSlateLight.copy(alpha = 0.4f))
            ) {
                Text(
                    text = "ทดลองใช้งานแบบไม่ระบุตัวตน (Continue as Guest)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = FrostedSlateMuted
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ThirdPartyLoginButton(
    iconEmoji: String,
    title: String,
    subtitle: String,
    containerColor: Color,
    borderColor: Color,
    testTag: String,
    textColor: Color = FrostedSlateDark,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = iconEmoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = textColor
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (textColor == Color.White) Color.White.copy(alpha = 0.7f) else FrostedSlateMuted
                )
            }
        }
    }
}
