package com.shenhan.app.model

enum class QuestNodeType {
    TONE_DRILL,
    VOCAB_SRS,
    SENTENCE_BUILDER,
    CINEMA_ROLEPLAY,
    BOSS_CHALLENGE
}

data class QuestNode(
    val id: String,
    val zoneId: String,
    val titleTh: String,
    val titleEn: String,
    val titleZhSimp: String,
    val titleZhTrad: String,
    val nodeType: QuestNodeType,
    val iconEmoji: String,
    val xpReward: Int,
    val starsEarned: Int = 0, // 0 = not completed, 1..3
    val isUnlocked: Boolean = false,
    val isCurrent: Boolean = false,
    val descriptionTh: String,
    val hskLevel: HskLevel
)

data class QuestZone(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val titleZhSimp: String,
    val titleZhTrad: String,
    val hskLevel: HskLevel,
    val coverImageRes: Int? = null,
    val themeColorHex: Long,
    val isUnlocked: Boolean = true,
    val nodes: List<QuestNode>
)

data class QuizQuestion(
    val id: String,
    val promptTh: String,
    val promptEn: String,
    val chineseSimp: String,
    val chineseTrad: String,
    val pinyin: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationTh: String,
    val explanationEn: String
)
