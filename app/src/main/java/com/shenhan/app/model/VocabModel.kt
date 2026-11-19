package com.shenhan.app.model

enum class HskLevel(val level: Int, val title: String, val wordsCount: Int, val descriptionTh: String) {
    HSK1(1, "HSK 1", 150, "คำศัพท์พื้นฐาน 150 คำ (ชีวิตประจำวันง่ายๆ)"),
    HSK2(2, "HSK 2", 300, "คำศัพท์สื่อสาร 300 คำ (สนทนาพื้นฐานในสังคม)"),
    HSK3(3, "HSK 3", 600, "คำศัพท์ใช้งาน 600 คำ (ท่องเที่ยว & การทำงานเบื้องต้น)"),
    HSK4(4, "HSK 4", 1200, "คำศัพท์คล่องแคล่ว 1,200 คำ (คุยธุรกิจ & อภิปรายทั่วไป)"),
    HSK5(5, "HSK 5", 2500, "คำศัพท์ขั้นสูง 2,500 คำ (อ่านข่าว & ชมซีรีส์จีนเข้าใจ)"),
    HSK6(6, "HSK 6", 5000, "คำศัพท์ระดับเซียน 5,000+ คำ (สำนวน สุภาษิต & วรรณกรรม)")
}

enum class SrsMastery(val labelTh: String, val labelEn: String) {
    NEW("คำใหม่", "New"),
    LEARNING("กำลังเรียน", "Learning"),
    REVIEW("ทบทวน", "Review"),
    MASTERED("แม่นยำแล้ว", "Mastered")
}

data class VocabWord(
    val id: String,
    val hskLevel: HskLevel,
    val simplified: String,
    val traditional: String,
    val pinyin: String,
    val toneNumbers: List<Int>,
    val partOfSpeech: String,
    val meaningTh: String,
    val meaningEn: String,
    val exampleSentenceSimp: String,
    val exampleSentenceTrad: String,
    val examplePinyin: String,
    val exampleMeaningTh: String,
    val exampleMeaningEn: String,
    val strokeCount: Int = 0,
    val strokeOrderTipTh: String = "",
    val srsStage: Int = 0, // 0: New, 1: 1 day, 2: 3 days, 3: 7 days, 4: 14 days, 5: 30 days
    val lastReviewedTimestamp: Long = 0L,
    val nextReviewTimestamp: Long = 0L,
    val mastery: SrsMastery = SrsMastery.NEW,
    val isBookmarked: Boolean = false
)
