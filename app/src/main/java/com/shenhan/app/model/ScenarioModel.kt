package com.shenhan.app.model

enum class ScenarioCategory(val titleTh: String, val titleEn: String, val titleZh: String) {
    BUSINESS_NEGOTIATION("การเจรจาธุรกิจ", "Business Negotiation", "商务洽谈"),
    MARKET_BARGAINING("ต่อรองราคาตลาด", "Market Bargaining", "市场砍价"),
    DINING_FOOD("สั่งอาหารและเครื่องดื่ม", "Dining & Food", "地道餐饮"),
    TRAVEL_AIRPORT("เดินทางและสนามบิน", "Travel & Airport", "旅游出行"),
    DAILY_LIVING("ชีวิตประจำวัน & ที่พัก", "Daily Living & Housing", "生活租房")
}

data class ScenarioNpc(
    val id: String,
    val nameZhSimp: String,
    val nameZhTrad: String,
    val nameTh: String,
    val roleTitleTh: String,
    val avatarEmoji: String,
    val personalityTh: String
)

data class DialogueTurn(
    val id: String,
    val speakerIsNpc: Boolean,
    val textZhSimp: String,
    val textZhTrad: String,
    val pinyin: String,
    val textTh: String,
    val textEn: String,
    val suggestedRepliesSimp: List<String> = emptyList(),
    val suggestedRepliesTrad: List<String> = emptyList(),
    val suggestedRepliesPinyin: List<String> = emptyList(),
    val suggestedRepliesTh: List<String> = emptyList(),
    val audioSpeed: Float = 1.0f,
    val emotionEmoji: String = "😊"
)

data class RoleplayFeedback(
    val toneScore: Int, // 0..100
    val grammarScore: Int, // 0..100
    val fluencyScore: Int, // 0..100
    val vocabHighlightsTh: List<String>,
    val grammarAdviceTh: String,
    val grammarAdviceEn: String,
    val culturalTipTh: String
)

data class CinemaScenario(
    val id: String,
    val category: ScenarioCategory,
    val titleTh: String,
    val titleEn: String,
    val titleZhSimp: String,
    val titleZhTrad: String,
    val hskLevel: HskLevel,
    val coverImageRes: Int? = null,
    val npc: ScenarioNpc,
    val plotIntroTh: String,
    val plotIntroEn: String,
    val initialDialogues: List<DialogueTurn>,
    val targetKeyPhrasesSimp: List<String>,
    val targetKeyPhrasesTrad: List<String>,
    val targetKeyPhrasesTh: List<String>
)
