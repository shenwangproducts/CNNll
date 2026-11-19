package com.shenhan.app.model

import androidx.compose.ui.graphics.Color
import com.shenhan.app.ui.theme.Tone1Color
import com.shenhan.app.ui.theme.Tone2Color
import com.shenhan.app.ui.theme.Tone3Color
import com.shenhan.app.ui.theme.Tone4Color
import com.shenhan.app.ui.theme.Tone5Color

enum class MandarinTone(
    val toneNumber: Int,
    val toneNameZh: String,
    val toneNameTh: String,
    val toneNameEn: String,
    val pitchLevel: String, // 55, 35, 214, 51, etc.
    val contourDescriptionTh: String,
    val contourDescriptionEn: String,
    val color: Color
) {
    TONE_1(
        toneNumber = 1,
        toneNameZh = "第一声 (阴平)",
        toneNameTh = "เสียง 1 (ยินผิง - เสียงราบสูง)",
        toneNameEn = "1st Tone (High Level)",
        pitchLevel = "55",
        contourDescriptionTh = "ระดับเสียงสูงคงที่ 5-5 ลากเสียงยาว สม่ำเสมอ ไม่ตก",
        contourDescriptionEn = "High and level (5-5), constant pitch throughout",
        color = Tone1Color
    ),
    TONE_2(
        toneNumber = 2,
        toneNameZh = "第二声 (阳平)",
        toneNameTh = "เสียง 2 (หยางผิง - เสียงขึ้นสูง)",
        toneNameEn = "2nd Tone (Rising)",
        pitchLevel = "35",
        contourDescriptionTh = "เริ่มจากระดับกลาง 3 แล้วลากไต่ขึ้นสูงระดับ 5 คล้ายถามว่า 'หือ?'",
        contourDescriptionEn = "Rising from mid to high (3-5), like asking 'What?'",
        color = Tone2Color
    ),
    TONE_3(
        toneNumber = 3,
        toneNameZh = "第三声 (上声)",
        toneNameTh = "เสียง 3 (ส่างเซิง - เสียงต่ำแล้วยก)",
        toneNameEn = "3rd Tone (Dipping / Low)",
        pitchLevel = "214",
        contourDescriptionTh = "เริ่มกลางต่ำ 2 กดลงต่ำสุด 1 แล้วยกตวัดขึ้น 4 ชัดเจน",
        contourDescriptionEn = "Dipping down low then rising up (2-1-4)",
        color = Tone3Color
    ),
    TONE_4(
        toneNumber = 4,
        toneNameZh = "第四声 (去声)",
        toneNameTh = "เสียง 4 (ชี่เซิง - เสียงตกลงเร็ว)",
        toneNameEn = "4th Tone (High Falling)",
        pitchLevel = "51",
        contourDescriptionTh = "เริ่มจากระดับสูงสุด 5 แล้วทิ้งเสียงดิ่งลงเร็วและหนักแน่นถึง 1",
        contourDescriptionEn = "Sharp drop from top (5) to bottom (1), decisive & short",
        color = Tone4Color
    ),
    TONE_NEUTRAL(
        toneNumber = 5,
        toneNameZh = "轻声 (轻声)",
        toneNameTh = "เสียงเบา (ชิงเซิง - เสียงสั้นเบา)",
        toneNameEn = "Neutral Tone (Light)",
        pitchLevel = "33",
        contourDescriptionTh = "ออกเสียงเบา สั้น ครึ่งจังหวะ",
        contourDescriptionEn = "Short, light and unstressed",
        color = Tone5Color
    )
}

data class TonePracticeSyllable(
    val id: String,
    val syllablePinyin: String,
    val tone: MandarinTone,
    val hanziSimplified: String,
    val hanziTraditional: String,
    val meaningTh: String,
    val meaningEn: String,
    val commonMistakeTh: String
)

data class PitchAnalysisResult(
    val accuracyPercentage: Int,
    val detectedTone: MandarinTone,
    val targetTone: MandarinTone,
    val pitchStabilityScore: Int,
    val durationMs: Long,
    val feedbackTh: String,
    val feedbackEn: String,
    val userPitchPoints: List<Float> // Normalized 0.0f (low) to 1.0f (high)
)
