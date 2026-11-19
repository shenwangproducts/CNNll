package com.shenhan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shenhan.app.audio.AudioEngine
import com.shenhan.app.data.AppRepository
import com.shenhan.app.model.AuthProvider
import com.shenhan.app.model.MandarinTone
import com.shenhan.app.model.PlacementTestResult
import com.shenhan.app.model.UserProfile
import com.shenhan.app.ui.components.FrostedCanvasContainer
import com.shenhan.app.ui.components.HumanGatesModal
import com.shenhan.app.ui.components.ShenHanTopAppBar
import com.shenhan.app.ui.components.SubscriptionDialog
import com.shenhan.app.ui.screens.AiCinemaScreen
import com.shenhan.app.ui.screens.AuthScreen
import com.shenhan.app.ui.screens.PlacementTestScreen
import com.shenhan.app.ui.screens.ProfileScreen
import com.shenhan.app.ui.screens.QuestMapScreen
import com.shenhan.app.ui.screens.VocabLabScreen
import com.shenhan.app.ui.theme.FrostedGlassBorder
import com.shenhan.app.ui.theme.FrostedGlassWhite80
import com.shenhan.app.ui.theme.FrostedPurpleContainer
import com.shenhan.app.ui.theme.FrostedPurplePrimary
import com.shenhan.app.ui.theme.FrostedSlateMuted
import com.shenhan.app.ui.theme.MyApplicationTheme

enum class MainAppFlow {
    MAIN_NAV,
    PLACEMENT_TEST,
    AUTH
}

class MainActivity : ComponentActivity() {

    private lateinit var audioEngine: AudioEngine
    private lateinit var appRepository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioEngine = AudioEngine(this)
        appRepository = AppRepository(this)

        setContent {
            MyApplicationTheme {
                ShenHanMainApp(
                    repository = appRepository,
                    audioEngine = audioEngine
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioEngine.shutdown()
    }
}

@Composable
fun ShenHanMainApp(
    repository: AppRepository,
    audioEngine: AudioEngine
) {
    // Repository States
    val currentUser by repository.currentUser.collectAsState()
    val lastPlacementResult by repository.lastPlacementResult.collectAsState()
    val currentLanguage by repository.currentLanguage.collectAsState()
    val scriptMode by repository.scriptMode.collectAsState()
    val subscriptionTier by repository.subscriptionTier.collectAsState()
    val streakDays by repository.streakDays.collectAsState()
    val isCheckedInToday by repository.isCheckedInToday.collectAsState()
    val userXp by repository.userXp.collectAsState()
    val userLevel by repository.userLevel.collectAsState()
    val readinessScore by repository.readinessScore.collectAsState()
    val questZones by repository.questZones.collectAsState()
    val vocabList by repository.vocabList.collectAsState()
    val toneSyllables by repository.toneSyllables.collectAsState()
    val audioSpeed by repository.audioSpeed.collectAsState()

    // Audio Engine States
    val isPitchRecording by audioEngine.isRecording.collectAsState()
    val liveFrequencyHz by audioEngine.currentFrequencyHz.collectAsState()
    val liveSpectrum by audioEngine.audioSpectrum.collectAsState()
    val recordedPitchPoints by audioEngine.recordedPitchPoints.collectAsState()
    val lastPitchResult by audioEngine.lastAnalysisResult.collectAsState()

    // App Flow & Dialog States
    var currentFlow by remember { mutableStateOf(MainAppFlow.MAIN_NAV) }
    var tempPlacementResult by remember { mutableStateOf<PlacementTestResult?>(null) }
    var selectedNavIndex by remember { mutableIntStateOf(0) }
    var showHumanGatesModal by remember { mutableStateOf(false) }
    var showSubscriptionModal by remember { mutableStateOf(false) }

    val navItems = listOf(
        Triple("แผนที่เควสต์", Icons.Default.Map, "nav_quest_map"),
        Triple("ห้องแล็บคำศัพท์", Icons.Default.Science, "nav_vocab_lab"),
        Triple("AI Cinema", Icons.Default.Movie, "nav_ai_cinema"),
        Triple("โปรไฟล์ & เรดาร์", Icons.Default.Person, "nav_profile")
    )

    FrostedCanvasContainer {
        when (currentFlow) {
            MainAppFlow.PLACEMENT_TEST -> {
                PlacementTestScreen(
                    scriptMode = scriptMode,
                    onSpeakAudio = { text -> audioEngine.speak(text, audioSpeed) },
                    onFinishAndNavigateToAuth = { result ->
                        tempPlacementResult = result
                        repository.savePlacementTestResult(result)
                        currentFlow = MainAppFlow.AUTH
                    },
                    onFinishAndStartApp = { result ->
                        repository.savePlacementTestResult(result)
                        currentFlow = MainAppFlow.MAIN_NAV
                    },
                    onBack = { currentFlow = MainAppFlow.MAIN_NAV }
                )
            }

            MainAppFlow.AUTH -> {
                AuthScreen(
                    placementResult = tempPlacementResult ?: lastPlacementResult,
                    onAuthSuccess = { profile ->
                        repository.loginUser(profile)
                        tempPlacementResult?.let { repository.savePlacementTestResult(it) }
                        currentFlow = MainAppFlow.MAIN_NAV
                    },
                    onContinueAsGuest = {
                        repository.continueAsGuest()
                        tempPlacementResult?.let { repository.savePlacementTestResult(it) }
                        currentFlow = MainAppFlow.MAIN_NAV
                    },
                    onBack = { currentFlow = MainAppFlow.MAIN_NAV }
                )
            }

            MainAppFlow.MAIN_NAV -> {
                Scaffold(
                    containerColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("shenhan_main_scaffold"),
                    topBar = {
                        ShenHanTopAppBar(
                            scriptMode = scriptMode,
                            streakDays = streakDays,
                            userXp = userXp,
                            subscriptionTier = subscriptionTier,
                            onToggleScript = { repository.toggleScriptMode() },
                            onOpenHumanGates = { showHumanGatesModal = true },
                            onOpenSubscription = { showSubscriptionModal = true }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = FrostedGlassWhite80,
                            tonalElevation = 2.dp,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = FrostedGlassBorder,
                                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                                )
                                .testTag("bottom_navigation_bar")
                        ) {
                            navItems.forEachIndexed { index, (label, icon, testTag) ->
                                val isSelected = selectedNavIndex == index
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { selectedNavIndex = index },
                                    modifier = Modifier.testTag(testTag),
                                    icon = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = label
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = label,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) FrostedPurplePrimary else FrostedSlateMuted
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = FrostedPurplePrimary,
                                        selectedTextColor = FrostedPurplePrimary,
                                        indicatorColor = FrostedPurpleContainer,
                                        unselectedIconColor = FrostedSlateMuted,
                                        unselectedTextColor = FrostedSlateMuted
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (selectedNavIndex) {
                            0 -> QuestMapScreen(
                                questZones = questZones,
                                scriptMode = scriptMode,
                                streakDays = streakDays,
                                isCheckedInToday = isCheckedInToday,
                                userXp = userXp,
                                userLevel = userLevel,
                                onDailyCheckIn = { repository.performDailyCheckIn() },
                                onCompleteNode = { nodeId, stars -> repository.completeQuestNode(nodeId, stars) },
                                onNavigateToLab = { selectedNavIndex = 1 },
                                onNavigateToCinema = { selectedNavIndex = 2 }
                            )
                            1 -> VocabLabScreen(
                                toneSyllables = toneSyllables,
                                vocabList = vocabList,
                                scriptMode = scriptMode,
                                audioSpeed = audioSpeed,
                                isRecording = isPitchRecording,
                                liveFrequencyHz = liveFrequencyHz,
                                liveSpectrum = liveSpectrum,
                                recordedPitchPoints = recordedPitchPoints,
                                lastPitchResult = lastPitchResult,
                                onPlayNativeAudio = { text -> audioEngine.speak(text, audioSpeed) },
                                onStartPitchRecording = { targetTone ->
                                    audioEngine.startPitchRecording(targetTone)
                                },
                                onStopPitchRecording = {
                                    audioEngine.stopPitchRecording()
                                },
                                onSrsRateWord = { wordId, ease -> repository.updateVocabSrs(wordId, ease) },
                                onToggleBookmark = { wordId -> repository.toggleBookmarkVocab(wordId) }
                            )
                            2 -> AiCinemaScreen(
                                scriptMode = scriptMode,
                                audioSpeed = audioSpeed,
                                isAudioRecording = isPitchRecording,
                                onSpeak = { text -> audioEngine.speak(text, audioSpeed) },
                                onChangeAudioSpeed = { speed ->
                                    repository.setAudioSpeed(speed)
                                    audioEngine.setSpeechRate(speed)
                                },
                                onSimulateUserSpeech = { text -> audioEngine.speak(text, audioSpeed) },
                                onAddXp = { amount -> repository.addXp(amount) }
                            )
                            3 -> ProfileScreen(
                                currentUser = currentUser,
                                currentLanguage = currentLanguage,
                                scriptMode = scriptMode,
                                subscriptionTier = subscriptionTier,
                                streakDays = streakDays,
                                userXp = userXp,
                                userLevel = userLevel,
                                readinessScore = readinessScore,
                                audioSpeed = audioSpeed,
                                onLanguageChange = { lang -> repository.setLanguage(lang) },
                                onToggleScript = { repository.toggleScriptMode() },
                                onAudioSpeedChange = { speed ->
                                    repository.setAudioSpeed(speed)
                                    audioEngine.setSpeechRate(speed)
                                },
                                onOpenSubscription = { showSubscriptionModal = true },
                                onOpenHumanGates = { showHumanGatesModal = true },
                                onRetakePlacementTest = { currentFlow = MainAppFlow.PLACEMENT_TEST },
                                onSwitchAccount = { currentFlow = MainAppFlow.AUTH },
                                onLinkThirdParty = { provider -> repository.linkThirdPartyProvider(provider) }
                            )
                        }
                    }

                    // TOR Specifications & Human Gates Dialog
                    if (showHumanGatesModal) {
                        HumanGatesModal(
                            onDismiss = { showHumanGatesModal = false }
                        )
                    }

                    // Subscription Tier Selector Dialog
                    if (showSubscriptionModal) {
                        SubscriptionDialog(
                            currentTier = subscriptionTier,
                            onSelectTier = { tier -> repository.setSubscriptionTier(tier) },
                            onDismiss = { showSubscriptionModal = false }
                        )
                    }
                }
            }
        }
    }
}


