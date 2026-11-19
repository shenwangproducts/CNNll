package com.shenhan.app.data

import android.content.Context
import com.shenhan.app.model.AppLanguage
import com.shenhan.app.model.AuthProvider
import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.HskReadinessScore
import com.shenhan.app.model.MandarinTone
import com.shenhan.app.model.PlacementTestResult
import com.shenhan.app.model.QuestNode
import com.shenhan.app.model.QuestZone
import com.shenhan.app.model.RoleplayFeedback
import com.shenhan.app.model.ScriptMode
import com.shenhan.app.model.SrsMastery
import com.shenhan.app.model.SubscriptionTier
import com.shenhan.app.model.TonePracticeSyllable
import com.shenhan.app.model.UserProfile
import com.shenhan.app.model.VocabWord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppRepository(private val context: Context) {

    // User Profile & Authentication State
    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            id = "user_default_01",
            displayName = "หลี่หยาง (Li Yang)",
            email = "learner@shenhan.app",
            avatarEmoji = "🐼",
            provider = AuthProvider.GOOGLE,
            linkedProviders = listOf(AuthProvider.GOOGLE, AuthProvider.EMAIL),
            startingHskLevel = HskLevel.HSK2,
            dailyGoalMinutes = 15,
            isGuest = false
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    // Placement Test State
    private val _hasCompletedPlacement = MutableStateFlow(true)
    val hasCompletedPlacement: StateFlow<Boolean> = _hasCompletedPlacement.asStateFlow()

    private val _lastPlacementResult = MutableStateFlow<PlacementTestResult?>(null)
    val lastPlacementResult: StateFlow<PlacementTestResult?> = _lastPlacementResult.asStateFlow()

    // Language & Script
    private val _currentLanguage = MutableStateFlow(AppLanguage.THAI)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _scriptMode = MutableStateFlow(ScriptMode.SIMPLIFIED)
    val scriptMode: StateFlow<ScriptMode> = _scriptMode.asStateFlow()

    // Subscription
    private val _subscriptionTier = MutableStateFlow(SubscriptionTier.PRO_YEARLY)
    val subscriptionTier: StateFlow<SubscriptionTier> = _subscriptionTier.asStateFlow()

    // User Progress & Gamification
    private val _streakDays = MutableStateFlow(7)
    val streakDays: StateFlow<Int> = _streakDays.asStateFlow()

    private val _isCheckedInToday = MutableStateFlow(true)
    val isCheckedInToday: StateFlow<Boolean> = _isCheckedInToday.asStateFlow()

    private val _userXp = MutableStateFlow(1420)
    val userXp: StateFlow<Int> = _userXp.asStateFlow()

    private val _userLevel = MutableStateFlow(4)
    val userLevel: StateFlow<Int> = _userLevel.asStateFlow()

    // HSK Readiness Radar
    private val _readinessScore = MutableStateFlow(
        HskReadinessScore(
            overallScore = 84,
            listeningScore = 88,
            speakingToneScore = 82,
            readingScore = 86,
            vocabMasteryScore = 80,
            targetHskLevel = HskLevel.HSK3,
            readinessPercentage = 84
        )
    )
    val readinessScore: StateFlow<HskReadinessScore> = _readinessScore.asStateFlow()

    // Quest Zones
    private val _questZones = MutableStateFlow(SampleData.questZones)
    val questZones: StateFlow<List<QuestZone>> = _questZones.asStateFlow()

    // Vocab SRS Items
    private val _vocabList = MutableStateFlow(SampleData.vocabList)
    val vocabList: StateFlow<List<VocabWord>> = _vocabList.asStateFlow()

    // Tone Practice Syllables
    private val _toneSyllables = MutableStateFlow(SampleData.toneSyllables)
    val toneSyllables: StateFlow<List<TonePracticeSyllable>> = _toneSyllables.asStateFlow()

    // Settings
    private val _audioSpeed = MutableStateFlow(1.0f)
    val audioSpeed: StateFlow<Float> = _audioSpeed.asStateFlow()

    // Methods to manipulate state
    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun toggleScriptMode() {
        _scriptMode.value = if (_scriptMode.value == ScriptMode.SIMPLIFIED) {
            ScriptMode.TRADITIONAL
        } else {
            ScriptMode.SIMPLIFIED
        }
    }

    fun setScriptMode(mode: ScriptMode) {
        _scriptMode.value = mode
    }

    fun setSubscriptionTier(tier: SubscriptionTier) {
        _subscriptionTier.value = tier
    }

    fun setAudioSpeed(speed: Float) {
        _audioSpeed.value = speed.coerceIn(0.75f, 1.25f)
    }

    fun performDailyCheckIn() {
        if (!_isCheckedInToday.value) {
            _isCheckedInToday.value = true
            _streakDays.value += 1
            addXp(50)
        }
    }

    fun addXp(amount: Int) {
        val newXp = _userXp.value + amount
        _userXp.value = newXp
        _userLevel.value = (newXp / 350) + 1
    }

    fun completeQuestNode(nodeId: String, stars: Int) {
        val currentZones = _questZones.value.map { zone ->
            val updatedNodes = zone.nodes.map { node ->
                if (node.id == nodeId) {
                    node.copy(
                        starsEarned = stars.coerceAtLeast(node.starsEarned),
                        isUnlocked = true
                    )
                } else {
                    node
                }
            }
            zone.copy(nodes = updatedNodes)
        }
        _questZones.value = currentZones
        addXp(50 * stars)
    }

    fun updateVocabSrs(wordId: String, easeQuality: Int) {
        // easeQuality: 0 = Again, 1 = Hard, 2 = Good, 3 = Easy
        val updated = _vocabList.value.map { word ->
            if (word.id == wordId) {
                val newStage = when (easeQuality) {
                    0 -> 0
                    1 -> (word.srsStage).coerceAtLeast(1)
                    2 -> (word.srsStage + 1).coerceAtMost(5)
                    3 -> (word.srsStage + 2).coerceAtMost(5)
                    else -> word.srsStage
                }
                val newMastery = when (newStage) {
                    0 -> SrsMastery.NEW
                    1, 2 -> SrsMastery.LEARNING
                    3, 4 -> SrsMastery.REVIEW
                    else -> SrsMastery.MASTERED
                }
                word.copy(
                    srsStage = newStage,
                    mastery = newMastery,
                    lastReviewedTimestamp = System.currentTimeMillis()
                )
            } else {
                word
            }
        }
        _vocabList.value = updated
        addXp(15)
    }

    fun toggleBookmarkVocab(wordId: String) {
        _vocabList.value = _vocabList.value.map {
            if (it.id == wordId) it.copy(isBookmarked = !it.isBookmarked) else it
        }
    }

    // Authentication & Profile Actions
    fun loginUser(profile: UserProfile) {
        _currentUser.value = profile
        _hasCompletedPlacement.value = true
    }

    fun continueAsGuest() {
        val guest = UserProfile(
            id = "guest_${System.currentTimeMillis()}",
            displayName = "ผู้เยี่ยมชม (Guest)",
            email = "guest@shenhan.local",
            avatarEmoji = "👤",
            provider = AuthProvider.GUEST,
            linkedProviders = listOf(AuthProvider.GUEST),
            startingHskLevel = HskLevel.HSK1,
            dailyGoalMinutes = 10,
            isGuest = true
        )
        _currentUser.value = guest
        _hasCompletedPlacement.value = true
    }

    fun logout() {
        _currentUser.value = null
    }

    fun linkThirdPartyProvider(provider: AuthProvider) {
        val current = _currentUser.value ?: return
        if (!current.linkedProviders.contains(provider)) {
            val updatedProviders = current.linkedProviders + provider
            _currentUser.value = current.copy(linkedProviders = updatedProviders)
        }
    }

    fun savePlacementTestResult(result: PlacementTestResult) {
        _lastPlacementResult.value = result
        _hasCompletedPlacement.value = true

        // Update user starting HSK level
        val current = _currentUser.value
        if (current != null) {
            _currentUser.value = current.copy(
                startingHskLevel = result.recommendedHskLevel,
                dailyGoalMinutes = result.suggestedDailyGoalMinutes
            )
        }

        // Focus and unlock recommended Quest Zone
        val updatedZones = _questZones.value.map { zone ->
            if (zone.id == result.recommendedZoneId) {
                zone.copy(isUnlocked = true)
            } else {
                zone
            }
        }
        _questZones.value = updatedZones

        // Update readiness score
        _readinessScore.value = _readinessScore.value.copy(
            targetHskLevel = result.recommendedHskLevel,
            readinessPercentage = result.overallScorePercentage.coerceAtLeast(60)
        )
    }

    fun retakePlacementTest() {
        _hasCompletedPlacement.value = false
    }
}

