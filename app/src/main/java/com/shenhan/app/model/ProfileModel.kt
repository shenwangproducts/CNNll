package com.shenhan.app.model

data class HskReadinessScore(
    val overallScore: Int, // 0..100
    val listeningScore: Int,
    val speakingToneScore: Int,
    val readingScore: Int,
    val vocabMasteryScore: Int,
    val targetHskLevel: HskLevel = HskLevel.HSK3,
    val readinessPercentage: Int = 82
)

data class AchievementBadge(
    val id: String,
    val titleTh: String,
    val titleEn: String,
    val iconEmoji: String,
    val descriptionTh: String,
    val isUnlocked: Boolean,
    val progress: Float = 1.0f
)

data class TorHumanGate(
    val phaseNumber: Int,
    val phaseTitleTh: String,
    val timePeriodTh: String,
    val deliverablesTh: String,
    val humanCheckActionTh: String,
    val statusTh: String,
    val isCurrentPhase: Boolean = false
)
