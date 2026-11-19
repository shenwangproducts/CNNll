package com.shenhan.app.model

enum class AuthProvider(val displayName: String, val iconEmoji: String) {
    EMAIL("อีเมล / รหัสผ่าน", "✉️"),
    GOOGLE("Google", "🌐"),
    APPLE("Apple ID", "🍏"),
    WECHAT("WeChat", "💬"),
    LINE("LINE", "🟢"),
    GUEST("ผู้เยี่ยมชม (Guest)", "👤")
}

data class UserProfile(
    val id: String,
    val displayName: String,
    val email: String,
    val avatarEmoji: String = "🐼",
    val provider: AuthProvider = AuthProvider.EMAIL,
    val linkedProviders: List<AuthProvider> = listOf(AuthProvider.EMAIL),
    val startingHskLevel: HskLevel = HskLevel.HSK1,
    val dailyGoalMinutes: Int = 15,
    val createdAtFormatted: String = "ส.ค. 2026",
    val isGuest: Boolean = false
)

enum class AuthScreenMode {
    LOGIN,
    REGISTER
}
