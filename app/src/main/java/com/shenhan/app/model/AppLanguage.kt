package com.shenhan.app.model

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    THAI("th", "ภาษาไทย", "🇹🇭"),
    ENGLISH("en", "English", "🇬🇧"),
    CHINESE("zh", "中文 (Chinese)", "🇨🇳")
}

enum class ScriptMode(val code: String, val displayName: String) {
    SIMPLIFIED("simplified", "简体 (Simplified)"),
    TRADITIONAL("traditional", "繁体 (Traditional)")
}

enum class SubscriptionTier(
    val titleTh: String,
    val titleEn: String,
    val priceTh: String,
    val badge: String,
    val descriptionTh: String,
    val isPro: Boolean
) {
    FREE(
        titleTh = "Free Tier",
        titleEn = "Free Tier",
        priceTh = "ฟรีตลอดชีพ",
        badge = "FREE",
        descriptionTh = "HSK 1-3, คัดพินอินเบื้องต้น, AI Cinema 5 นาที/วัน",
        isPro = false
    ),
    PRO_MONTHLY(
        titleTh = "ShenHan Pro (รายเดือน)",
        titleEn = "ShenHan Pro Monthly",
        priceTh = "149 บาท / เดือน",
        badge = "PRO",
        descriptionTh = "ปลดล็อก HSK 1-6, AI Cinema ไม่จำกัด, Tone Pitch Graph ละเอียด, Offline 100%",
        isPro = true
    ),
    PRO_YEARLY(
        titleTh = "ShenHan Pro (รายปี)",
        titleEn = "ShenHan Pro Yearly",
        priceTh = "990 บาท / ปี (ประหยัด 45%)",
        badge = "BEST VALUE",
        descriptionTh = "ครบทุกฟีเจอร์ Pro + สิทธิ์ทดสอบคอร์สใหม่และ AI Scenario ล่วงหน้า",
        isPro = true
    ),
    LIFETIME_PASS(
        titleTh = "ShenHan Pass (ซื้อขาด)",
        titleEn = "ShenHan Lifetime Pass",
        priceTh = "2,490 บาท ครั้งเดียว",
        badge = "LIFETIME",
        descriptionTh = "ปลดล็อกทุกฟีเจอร์ บทเรียน และอัปเดตโมเดล AI ตลอดชีพ",
        isPro = true
    )
}
