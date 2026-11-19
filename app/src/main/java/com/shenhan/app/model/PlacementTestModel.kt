package com.shenhan.app.model

enum class QuestionCategory(val titleTh: String, val iconEmoji: String) {
    TONE_PITCH("วรรณยุกต์และโทนเสียง", "🎙️"),
    VOCAB_MEANING("ความหมายคำศัพท์", "📇"),
    SENTENCE_ORDER("โครงสร้างและไวยากรณ์", "🧩"),
    DIALOGUE_COMPREHENSION("บทสนทนาสถานการณ์จริง", "💬")
}

data class PlacementQuestion(
    val id: String,
    val category: QuestionCategory,
    val targetHskLevel: HskLevel,
    val promptTh: String,
    val promptEn: String,
    val chineseSimp: String,
    val chineseTrad: String,
    val pinyin: String,
    val audioCue: String = "",
    val options: List<String>,
    val correctIndex: Int,
    val explanationTh: String
)

data class SkillScore(
    val category: QuestionCategory,
    val correctCount: Int,
    val totalCount: Int,
    val percentage: Int
)

data class PlacementTestResult(
    val recommendedHskLevel: HskLevel,
    val overallScorePercentage: Int,
    val correctAnswersCount: Int,
    val totalQuestionsCount: Int,
    val skillBreakdown: List<SkillScore>,
    val recommendedZoneId: String,
    val recommendedZoneTitleTh: String,
    val evaluationSummaryTh: String,
    val suggestedDailyGoalMinutes: Int = 15
)
