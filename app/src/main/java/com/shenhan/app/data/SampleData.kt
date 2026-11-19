package com.shenhan.app.data

import com.shenhan.app.R
import com.shenhan.app.model.AchievementBadge
import com.shenhan.app.model.CinemaScenario
import com.shenhan.app.model.DialogueTurn
import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.MandarinTone
import com.shenhan.app.model.QuestNode
import com.shenhan.app.model.QuestNodeType
import com.shenhan.app.model.QuestZone
import com.shenhan.app.model.QuizQuestion
import com.shenhan.app.model.RoleplayFeedback
import com.shenhan.app.model.ScenarioCategory
import com.shenhan.app.model.ScenarioNpc
import com.shenhan.app.model.SrsMastery
import com.shenhan.app.model.TonePracticeSyllable
import com.shenhan.app.model.TorHumanGate
import com.shenhan.app.model.VocabWord

object SampleData {

    // 1. Tone Syllables Bank
    val toneSyllables = listOf(
        TonePracticeSyllable(
            id = "t1_ma",
            syllablePinyin = "mā",
            tone = MandarinTone.TONE_1,
            hanziSimplified = "妈",
            hanziTraditional = "媽",
            meaningTh = "แม่ (มารดา)",
            meaningEn = "Mother / Mom",
            commonMistakeTh = "คนไทยมักออกเสียงสั้นเกินไป ควรลากระดับเสียงสูง 5-5 ให้ยาวสม่ำเสมอ"
        ),
        TonePracticeSyllable(
            id = "t2_ma",
            syllablePinyin = "má",
            tone = MandarinTone.TONE_2,
            hanziSimplified = "麻",
            hanziTraditional = "麻",
            meaningTh = "ป่าน / ชา (เช่น หมาล่า)",
            meaningEn = "Hemp / Numb (Mala)",
            commonMistakeTh = "ระวังอย่าให้เสียงตก ให้ไต่ระดับจากกลางขึ้นสูง 3-5 เหมือนถาม 'หือ?'"
        ),
        TonePracticeSyllable(
            id = "t3_ma",
            syllablePinyin = "mǎ",
            tone = MandarinTone.TONE_3,
            hanziSimplified = "马",
            hanziTraditional = "馬",
            meaningTh = "ม้า",
            meaningEn = "Horse",
            commonMistakeTh = "ต้องกดเสียงลงต่ำสุดที่ลำคอ (2-1) ก่อนตวัดขึ้น (4) อย่าเพิ่งรีบยกเสียงเร็ว"
        ),
        TonePracticeSyllable(
            id = "t4_ma",
            syllablePinyin = "mà",
            tone = MandarinTone.TONE_4,
            hanziSimplified = "骂",
            hanziTraditional = "罵",
            meaningTh = "ด่า / ว่ากล่าว",
            meaningEn = "Scold / Curse",
            commonMistakeTh = "ต้องเริ่มที่ระดับ 5 สูงสุด แล้วตัดเสียงตกลงมาที่ 1 อย่างเด็ดขาดกระชับ"
        ),
        TonePracticeSyllable(
            id = "t1_ba",
            syllablePinyin = "bā",
            tone = MandarinTone.TONE_1,
            hanziSimplified = "八",
            hanziTraditional = "八",
            meaningTh = "เลข 8 (แปด)",
            meaningEn = "Eight (8)",
            commonMistakeTh = "ออกเสียง 'ปา' ระดับเสียงสูงยาวคงที่ ไม่หย่อน"
        ),
        TonePracticeSyllable(
            id = "t2_ba",
            syllablePinyin = "bá",
            tone = MandarinTone.TONE_2,
            hanziSimplified = "拔",
            hanziTraditional = "拔",
            meaningTh = "ดึง / ถอน",
            meaningEn = "Pull out / Pluck",
            commonMistakeTh = "ยกหางเสียงขึ้นชัดเจน"
        ),
        TonePracticeSyllable(
            id = "t3_ba",
            syllablePinyin = "bǎ",
            tone = MandarinTone.TONE_3,
            hanziSimplified = "把",
            hanziTraditional = "把",
            meaningTh = "ด้าม / โครงสร้างไวยากรณ์ 把",
            meaningEn = "Handle / Structural Particle",
            commonMistakeTh = "กดเสียงลึกต่ำก่อนแล้วค่อยยก"
        ),
        TonePracticeSyllable(
            id = "t4_ba",
            syllablePinyin = "bà",
            tone = MandarinTone.TONE_4,
            hanziSimplified = "爸",
            hanziTraditional = "爸",
            meaningTh = "พ่อ (คุณพ่อ)",
            meaningEn = "Father / Dad",
            commonMistakeTh = "เสียงตกลงแรงกระชับ"
        ),
        TonePracticeSyllable(
            id = "t1_chi",
            syllablePinyin = "chī",
            tone = MandarinTone.TONE_1,
            hanziSimplified = "吃",
            hanziTraditional = "吃",
            meaningTh = "กิน / รับประทาน",
            meaningEn = "Eat",
            commonMistakeTh = "ม้วนลิ้นเล็กน้อยแล้วออกเสียงสูงเรียบ"
        ),
        TonePracticeSyllable(
            id = "t3_hao",
            syllablePinyin = "hǎo",
            tone = MandarinTone.TONE_3,
            hanziSimplified = "好",
            hanziTraditional = "好",
            meaningTh = "ดี / สบายดี",
            meaningEn = "Good / Well",
            commonMistakeTh = "คำทักทายยอดฮิต nǐ hǎo มีกฎเปลี่ยนเสียง 3 เป็นเสียง 2 นำหน้า"
        )
    )

    // 2. Offline HSK 1-6 Vocabulary Bank
    val vocabList = listOf(
        // HSK 1
        VocabWord(
            id = "w1_hsk1_nihao",
            hskLevel = HskLevel.HSK1,
            simplified = "你好",
            traditional = "你好",
            pinyin = "nǐ hǎo",
            toneNumbers = listOf(3, 3),
            partOfSpeech = "วลีทักทาย",
            meaningTh = "สวัสดี",
            meaningEn = "Hello / How are you",
            exampleSentenceSimp = "你好！很高兴认识你。",
            exampleSentenceTrad = "你好！很高興認識你。",
            examplePinyin = "Nǐ hǎo! Hěn gāoxìng rènshí nǐ.",
            exampleMeaningTh = "สวัสดี! ยินดีที่ได้รู้จักคุณ",
            exampleMeaningEn = "Hello! Nice to meet you.",
            strokeCount = 7,
            strokeOrderTipTh = "撇 (piě), 竖 (shù), 撇 (piě), 横撇 (héngpiě), 弯钩 (wāngōu)",
            srsStage = 3,
            mastery = SrsMastery.MASTERED
        ),
        VocabWord(
            id = "w2_hsk1_xiexie",
            hskLevel = HskLevel.HSK1,
            simplified = "谢谢",
            traditional = "謝謝",
            pinyin = "xiè xie",
            toneNumbers = listOf(4, 5),
            partOfSpeech = "คำกริยา / คำสุภาพ",
            meaningTh = "ขอบคุณ",
            meaningEn = "Thank you",
            exampleSentenceSimp = "谢谢你的热情帮助。",
            exampleSentenceTrad = "謝謝你的熱情幫助。",
            examplePinyin = "Xièxie nǐ de rèqíng bāngzhù.",
            exampleMeaningTh = "ขอบคุณสำหรับความช่วยเหลืออันอบอุ่นของคุณ",
            exampleMeaningEn = "Thank you for your warm help.",
            strokeCount = 12,
            strokeOrderTipTh = "点 (diǎn), 横折提 (héngzhétí)...",
            srsStage = 2,
            mastery = SrsMastery.REVIEW
        ),
        VocabWord(
            id = "w3_hsk1_zaijian",
            hskLevel = HskLevel.HSK1,
            simplified = "再见",
            traditional = "再見",
            pinyin = "zài jiàn",
            toneNumbers = listOf(4, 4),
            partOfSpeech = "วลีลา",
            meaningTh = "ลาก่อน / พบกันใหม่",
            meaningEn = "Goodbye / See you again",
            exampleSentenceSimp = "明天见，再见！",
            exampleSentenceTrad = "明天見，再見！",
            examplePinyin = "Míngtiān jiàn, zàijiàn!",
            exampleMeaningTh = "เจอกันพรุ่งนี้นะ ลาก่อน!",
            exampleMeaningEn = "See you tomorrow, goodbye!",
            strokeCount = 6,
            srsStage = 1,
            mastery = SrsMastery.LEARNING
        ),
        VocabWord(
            id = "w4_hsk1_duoshao",
            hskLevel = HskLevel.HSK1,
            simplified = "多少",
            traditional = "多少",
            pinyin = "duō shao",
            toneNumbers = listOf(1, 5),
            partOfSpeech = "สรรพนามถาม",
            meaningTh = "เท่าไหร่ / กี่มากน้อย",
            meaningEn = "How much / How many",
            exampleSentenceSimp = "这个苹果多少钱一斤？",
            exampleSentenceTrad = "這個蘋果多少錢一斤？",
            examplePinyin = "Zhège píngguǒ duōshao qián yì jīn?",
            exampleMeaningTh = "แอปเปิ้ลนี้ชั่งละเท่าไหร่?",
            exampleMeaningEn = "How much is half a kilo of these apples?",
            strokeCount = 6,
            srsStage = 0,
            mastery = SrsMastery.NEW
        ),

        // HSK 2
        VocabWord(
            id = "w5_hsk2_pianyi",
            hskLevel = HskLevel.HSK2,
            simplified = "便宜",
            traditional = "便宜",
            pinyin = "pián yi",
            toneNumbers = listOf(2, 5),
            partOfSpeech = "คำคุณศัพท์",
            meaningTh = "ราคาถูก / ย่อมเยา",
            meaningEn = "Cheap / Inexpensive",
            exampleSentenceSimp = "老板，能便宜一点吗？",
            exampleSentenceTrad = "老闆，能便宜一點嗎？",
            examplePinyin = "Lǎobǎn, néng piányi yìdiǎn ma?",
            exampleMeaningTh = "เถ้าแก่ ลดราคาให้หน่อยได้ไหม?",
            exampleMeaningEn = "Boss, could you make it a bit cheaper?",
            strokeCount = 9,
            srsStage = 2,
            mastery = SrsMastery.REVIEW
        ),
        VocabWord(
            id = "w6_hsk2_chuzuche",
            hskLevel = HskLevel.HSK2,
            simplified = "出租车",
            traditional = "出租車",
            pinyin = "chū zū chē",
            toneNumbers = listOf(1, 1, 1),
            partOfSpeech = "คำนาม",
            meaningTh = "แท็กซี่ / รถรับจ้าง",
            meaningEn = "Taxi",
            exampleSentenceSimp = "我们坐出租车去机场吧。",
            exampleSentenceTrad = "我們坐出租車去機場吧。",
            examplePinyin = "Wǒmen zuò chūzūchē qù jīchǎng ba.",
            exampleMeaningTh = "พวกเรานั่งแท็กซี่ไปสนามบินกันเถอะ",
            exampleMeaningEn = "Let's take a taxi to the airport.",
            strokeCount = 5,
            srsStage = 1,
            mastery = SrsMastery.LEARNING
        ),

        // HSK 3
        VocabWord(
            id = "w7_hsk3_youxiao",
            hskLevel = HskLevel.HSK3,
            simplified = "满意",
            traditional = "滿意",
            pinyin = "mǎn yì",
            toneNumbers = listOf(3, 4),
            partOfSpeech = "คำคุณศัพท์",
            meaningTh = "พึงพอใจ / พอใจ",
            meaningEn = "Satisfied / Pleased",
            exampleSentenceSimp = "客户对这次样品非常满意。",
            exampleSentenceTrad = "客戶對這次樣品非常滿意。",
            examplePinyin = "Kèhù duì zhè cì yàngpǐn fēicháng mǎnyì.",
            exampleMeaningTh = "ลูกค้าพึงพอใจตัวอย่างสินค้าครั้งนี้เป็นอย่างมาก",
            exampleMeaningEn = "The client is very satisfied with this sample.",
            strokeCount = 14,
            srsStage = 0,
            mastery = SrsMastery.NEW
        ),
        VocabWord(
            id = "w8_hsk3_yiding",
            hskLevel = HskLevel.HSK3,
            simplified = "要求",
            traditional = "要求",
            pinyin = "yāo qiú",
            toneNumbers = listOf(1, 2),
            partOfSpeech = "คำนาม / คำกริยา",
            meaningTh = "ข้อกำหนด / ข้อเรียกร้อง",
            meaningEn = "Demand / Requirement",
            exampleSentenceSimp = "我们完全符合厂方的质量要求。",
            exampleSentenceTrad = "我們完全符合廠方的質量要求。",
            examplePinyin = "Wǒmen wánquán fúhé chǎngfāng de zhìliàng yāoqiú.",
            exampleMeaningTh = "พวกเราผ่านเกณฑ์ข้อกำหนดคุณภาพของทางโรงงานครบถ้วน",
            exampleMeaningEn = "We fully meet the factory's quality requirements.",
            strokeCount = 7,
            srsStage = 1,
            mastery = SrsMastery.LEARNING
        ),

        // HSK 4
        VocabWord(
            id = "w9_hsk4_tanpan",
            hskLevel = HskLevel.HSK4,
            simplified = "谈判",
            traditional = "談判",
            pinyin = "tán pàn",
            toneNumbers = listOf(2, 4),
            partOfSpeech = "คำกริยา / คำนาม",
            meaningTh = "เจรจาต่อรอง / การเจรจา",
            meaningEn = "Negotiate / Negotiation",
            exampleSentenceSimp = "明天我们和广州工厂进行价格谈判。",
            exampleSentenceTrad = "明天我們和廣州工廠進行價格談判。",
            examplePinyin = "Míngtiān wǒmen hé Guǎngzhōu gōngchǎng jìnxíng jiàgé tánpàn.",
            exampleMeaningTh = "พรุ่งนี้พวกเราจะเจรจาเรื่องราคากับโรงงานที่กวางโจว",
            exampleMeaningEn = "Tomorrow we will hold price negotiations with the Guangzhou factory.",
            strokeCount = 12,
            srsStage = 0,
            mastery = SrsMastery.NEW
        ),
        VocabWord(
            id = "w10_hsk4_hetong",
            hskLevel = HskLevel.HSK4,
            simplified = "合同",
            traditional = "合同",
            pinyin = "hé tong",
            toneNumbers = listOf(2, 5),
            partOfSpeech = "คำนาม",
            meaningTh = "สัญญา / ข้อตกลงสัญญา",
            meaningEn = "Contract / Agreement",
            exampleSentenceSimp = "双方顺利签署了供货合同。",
            exampleSentenceTrad = "雙方順利簽署了供貨合同。",
            examplePinyin = "Shuāngfāng shùnlì qiānshǔ le gōnghuò hétong.",
            exampleMeaningTh = "ทั้งสองฝ่ายได้ลงนามในสัญญาจัดส่งสินค้าอย่างราบรื่น",
            exampleMeaningEn = "Both parties successfully signed the supply contract.",
            strokeCount = 6,
            srsStage = 0,
            mastery = SrsMastery.NEW
        ),

        // HSK 5
        VocabWord(
            id = "w11_hsk5_xiaolv",
            hskLevel = HskLevel.HSK5,
            simplified = "效率",
            traditional = "效率",
            pinyin = "xiào lǜ",
            toneNumbers = listOf(4, 4),
            partOfSpeech = "คำนาม",
            meaningTh = "ประสิทธิภาพ / ประสิทธิผล",
            meaningEn = "Efficiency",
            exampleSentenceSimp = "采用自动化生产线能极大提高效率。",
            exampleSentenceTrad = "採用自動化生產線能極大提高效率。",
            examplePinyin = "Cǎiyòng zìdònghuà shēngchǎnxiàn néng jídà tígāo xiàolǜ.",
            exampleMeaningTh = "การใช้สายการผลิตอัตโนมัติสามารถเพิ่มประสิทธิภาพได้มหาศาล",
            exampleMeaningEn = "Using automated production lines greatly improves efficiency.",
            strokeCount = 11,
            srsStage = 0,
            mastery = SrsMastery.NEW
        ),

        // HSK 6
        VocabWord(
            id = "w12_hsk6_yitong",
            hskLevel = HskLevel.HSK6,
            simplified = "循序渐进",
            traditional = "循序漸進",
            pinyin = "xún xù jiàn jìn",
            toneNumbers = listOf(2, 4, 4, 4),
            partOfSpeech = "สำนวนเฉิงอวี่ (成语)",
            meaningTh = "พัฒนาไปทีละขั้นอย่างเป็นระบบ ไม่ก้าวกระโดด",
            meaningEn = "Proceed step by step in sequence",
            exampleSentenceSimp = "学好中文要循序渐进，不可急于求成。",
            exampleSentenceTrad = "學好中文要循序漸進，不可急於求成。",
            examplePinyin = "Xué hǎo zhōngwén yào xúnxùjiànjìn, bùkě jíyúqiúchéng.",
            exampleMeaningTh = "การเรียนภาษาจีนให้เก่งต้องเป็นขั้นเป็นตอน อย่าใจร้อนหวังผลเร็วเกินไป",
            exampleMeaningEn = "Mastering Chinese requires steady step-by-step progress without rushing.",
            strokeCount = 12,
            srsStage = 0,
            mastery = SrsMastery.NEW
        )
    )

    // 3. Quest Map Zones & Nodes
    val questZones = listOf(
        QuestZone(
            id = "zone_1",
            titleTh = "โซนที่ 1: ตะลุยด่านพื้นฐาน & วรรณยุกต์ (HSK 1)",
            titleEn = "Zone 1: HSK 1 Foundations & Tone Mastery",
            titleZhSimp = "第一关：声调与生存汉语",
            titleZhTrad = "第一關：聲調與生存漢語",
            hskLevel = HskLevel.HSK1,
            coverImageRes = R.drawable.banner_quest_hero_1788072081975,
            themeColorHex = 0xFFC62828,
            isUnlocked = true,
            nodes = listOf(
                QuestNode(
                    id = "node_1_1",
                    zoneId = "zone_1",
                    titleTh = "ด่าน 1: ผ่าคลื่นเสียง 4 วรรณยุกต์ (mā má mǎ mà)",
                    titleEn = "Node 1: Master 4 Tones Wave",
                    titleZhSimp = "四声声调基准训练",
                    titleZhTrad = "四聲聲調基準訓練",
                    nodeType = QuestNodeType.TONE_DRILL,
                    iconEmoji = "🎵",
                    xpReward = 50,
                    starsEarned = 3,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "ฝึกออกเสียงวรรณยุกต์พื้นฐานให้เป๊ะตามเส้นกราฟ Chao 5-Level",
                    hskLevel = HskLevel.HSK1
                ),
                QuestNode(
                    id = "node_1_2",
                    zoneId = "zone_1",
                    titleTh = "ด่าน 2: สวัสดี & ขอบคุณ (你好 / 谢谢)",
                    titleEn = "Node 2: Greetings & Politeness",
                    titleZhSimp = "问候与日常礼貌",
                    titleZhTrad = "問候與日常禮貌",
                    nodeType = QuestNodeType.VOCAB_SRS,
                    iconEmoji = "🤝",
                    xpReward = 60,
                    starsEarned = 3,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "ท่องและคัดพินอินคำทักทายชีวิตประจำวัน พร้อมระบบ SRS",
                    hskLevel = HskLevel.HSK1
                ),
                QuestNode(
                    id = "node_1_3",
                    zoneId = "zone_1",
                    titleTh = "ด่าน 3: ซื้อของถามราคา (多少钱？)",
                    titleEn = "Node 3: Asking Prices",
                    titleZhSimp = "购物问价表达",
                    titleZhTrad = "購物問價表達",
                    nodeType = QuestNodeType.SENTENCE_BUILDER,
                    iconEmoji = "🏷️",
                    xpReward = 75,
                    starsEarned = 2,
                    isUnlocked = true,
                    isCurrent = true,
                    descriptionTh = "ต่อประโยคถามราคาและตัวเลขเงินหยวน",
                    hskLevel = HskLevel.HSK1
                ),
                QuestNode(
                    id = "node_1_4",
                    zoneId = "zone_1",
                    titleTh = "บอสประจำโซน: บททดสอบ HSK 1 Checkpoint",
                    titleEn = "Boss: HSK 1 Zone Mastery Exam",
                    titleZhSimp = "HSK 1 突破大挑战",
                    titleZhTrad = "HSK 1 突破大挑戰",
                    nodeType = QuestNodeType.BOSS_CHALLENGE,
                    iconEmoji = "👑",
                    xpReward = 150,
                    starsEarned = 0,
                    isUnlocked = false,
                    isCurrent = false,
                    descriptionTh = "แบบทดสอบรวม 5 ข้อ ปลดล็อกเข้าสู่ HSK 2 ทันที",
                    hskLevel = HskLevel.HSK1
                )
            )
        ),
        QuestZone(
            id = "zone_2",
            titleTh = "โซนที่ 2: เอาตัวรอดในเมืองจีน (HSK 2)",
            titleEn = "Zone 2: Daily Survival in China (HSK 2)",
            titleZhSimp = "第二关：生活出行与就餐",
            titleZhTrad = "第二關：生活出行與就餐",
            hskLevel = HskLevel.HSK2,
            coverImageRes = R.drawable.banner_sichuan_dining_1788072127234,
            themeColorHex = 0xFFD97706,
            isUnlocked = true,
            nodes = listOf(
                QuestNode(
                    id = "node_2_1",
                    zoneId = "zone_2",
                    titleTh = "ด่าน 4: เรียกแท็กซี่และบอกทาง (师傅，去机场)",
                    titleEn = "Node 4: Taxi & Directions",
                    titleZhSimp = "打车指路口语",
                    titleZhTrad = "打車指路口語",
                    nodeType = QuestNodeType.SENTENCE_BUILDER,
                    iconEmoji = "🚕",
                    xpReward = 80,
                    starsEarned = 1,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "การบอกทิศทาง เลี้ยวซ้าย ขวา จอดข้างทาง",
                    hskLevel = HskLevel.HSK2
                ),
                QuestNode(
                    id = "node_2_2",
                    zoneId = "zone_2",
                    titleTh = "ด่าน 5: AI Cinema ร้านอาหารเสฉวน (微辣/不放香菜)",
                    titleEn = "Node 5: Sichuan Restaurant Drama",
                    titleZhSimp = "川菜馆点餐实战",
                    titleZhTrad = "川菜館點餐實戰",
                    nodeType = QuestNodeType.CINEMA_ROLEPLAY,
                    iconEmoji = "🍲",
                    xpReward = 120,
                    starsEarned = 0,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "ฝึกคุยสดกับเด็กเสิร์ฟ AI สั่งอาหาร เลือกระดับเผ็ด และจ่ายผ่าน Alipay",
                    hskLevel = HskLevel.HSK2
                )
            )
        ),
        QuestZone(
            id = "zone_3",
            titleTh = "โซนที่ 3: ตลาดค้าส่ง & เจรจาธุรกิจ (HSK 3 - 4)",
            titleEn = "Zone 3: Wholesale Market & Business (HSK 3-4)",
            titleZhSimp = "第三关：广州十三行与商务谈判",
            titleZhTrad = "第三關：廣州十三行與商務談判",
            hskLevel = HskLevel.HSK3,
            coverImageRes = R.drawable.banner_guangzhou_market_1788072096868,
            themeColorHex = 0xFF059669,
            isUnlocked = true,
            nodes = listOf(
                QuestNode(
                    id = "node_3_1",
                    zoneId = "zone_3",
                    titleTh = "ด่าน 6: ต่อราคากับเถ้าแก่หวังที่กวางโจว",
                    titleEn = "Node 6: Bargain at Guangzhou Shisanhang",
                    titleZhSimp = "十三行服装砍价",
                    titleZhTrad = "十三行服裝砍價",
                    nodeType = QuestNodeType.CINEMA_ROLEPLAY,
                    iconEmoji = "🛍️",
                    xpReward = 150,
                    starsEarned = 0,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "จำลองสถานการณ์ขอราคาส่งล็อต 500 ตัว และคุยเรื่องค่าขนส่ง",
                    hskLevel = HskLevel.HSK3
                ),
                QuestNode(
                    id = "node_3_2",
                    zoneId = "zone_3",
                    titleTh = "ด่าน 7: เยี่ยมชมโรงงานเซินเจิ้น (MOQ & มาตรฐาน QC)",
                    titleEn = "Node 7: Shenzhen Factory Negotiation",
                    titleZhSimp = "深圳电子厂洽谈",
                    titleZhTrad = "深圳電子廠洽談",
                    nodeType = QuestNodeType.CINEMA_ROLEPLAY,
                    iconEmoji = "🏭",
                    xpReward = 180,
                    starsEarned = 0,
                    isUnlocked = true,
                    isCurrent = false,
                    descriptionTh = "คุยกับผู้จัดการเฉินเรื่องรอบส่งสินค้าและตรวจเช็กคุณภาพ",
                    hskLevel = HskLevel.HSK4
                )
            )
        )
    )

    // 4. Sample Zone Quiz
    val sampleQuizQuestions = listOf(
        QuizQuestion(
            id = "q1",
            promptTh = "วรรณยุกต์เสียงใดในภาษาจีนมีลักษณะระดับเสียงเป็น 5-5 (สูงและราบคงที่)?",
            promptEn = "Which Mandarin tone has the pitch contour 5-5 (high and level)?",
            chineseSimp = "第一声 (mā)",
            chineseTrad = "第一聲 (mā)",
            pinyin = "dì-yī shēng",
            options = listOf("เสียง 1 (第一声)", "เสียง 2 (第二声)", "เสียง 3 (第三声)", "เสียง 4 (第四声)"),
            correctIndex = 0,
            explanationTh = "เสียงที่ 1 (ยินผิง) มีระดับเสียงสูง 5-5 ลากเสียงเรียบไม่ตก เช่น mā (妈)",
            explanationEn = "Tone 1 is high level 5-5 throughout."
        ),
        QuizQuestion(
            id = "q2",
            promptTh = "หากต้องการพูดว่า 'ลดราคาให้หน่อยได้ไหม' ควรพูดอย่างไร?",
            promptEn = "How do you say 'Can you make it a bit cheaper?' in Chinese?",
            chineseSimp = "能便宜一点吗？",
            chineseTrad = "能便宜一點嗎？",
            pinyin = "Néng piányi yìdiǎn ma?",
            options = listOf("太贵了不要 (Tài guì le bú yào)", "能便宜一点吗？ (Néng piányi yìdiǎn ma?)", "多少钱一个？ (Duōshao qián yí gè?)", "欢迎光临 (Huānyíng guānglín)"),
            correctIndex = 1,
            explanationTh = "能便宜一点吗？ เป็นสำนวนต่อราคาที่สุภาพและนิยมใช้มากที่สุดในตลาดจีน",
            explanationEn = "Néng piányi yìdiǎn ma? is the standard polite phrase for asking for a discount."
        ),
        QuizQuestion(
            id = "q3",
            promptTh = "คำว่า '谢谢' (ขอบคุณ) พยางค์ที่สองออกเสียงวรรณยุกต์ใด?",
            promptEn = "What tone is the second syllable of 'xièxie' (thank you)?",
            chineseSimp = "轻声 (เสียงเบา)",
            chineseTrad = "輕聲 (聲音輕短)",
            pinyin = "qīng shēng",
            options = listOf("เสียง 1 (High)", "เสียง 4 (Falling)", "เสียงเบา (Neutral / 轻声)", "เสียง 3 (Dipping)"),
            correctIndex = 2,
            explanationTh = "คำซ้ำส่วนใหญ่ในภาษาจีน พยางค์ที่สองจะกลายเป็นเสียงเบา (轻声) ออกเสียงสั้นและเบา",
            explanationEn = "The second syllable of repeated words usually becomes a light neutral tone."
        )
    )

    // 5. AI Cinema Scenarios Bank
    val cinemaScenarios = listOf(
        CinemaScenario(
            id = "sc_guangzhou_bargaining",
            category = ScenarioCategory.MARKET_BARGAINING,
            titleTh = "ต่อรองราคาเสื้อผ้าตลาดกวางโจว 13 ห้าง (十三行)",
            titleEn = "Bargaining at Guangzhou Wholesale Market",
            titleZhSimp = "广州十三行服装批发砍价实战",
            titleZhTrad = "廣州十三行服裝批發砍價實戰",
            hskLevel = HskLevel.HSK3,
            coverImageRes = R.drawable.banner_guangzhou_market_1788072096868,
            npc = ScenarioNpc(
                id = "npc_boss_wang",
                nameZhSimp = "王老板 (เถ้าแก่หวัง)",
                nameZhTrad = "王老闆",
                nameTh = "เถ้าแก่หวัง",
                roleTitleTh = "เจ้าของร้านค้าส่งผ้าไหมกวางโจว",
                avatarEmoji = "👨💼",
                personalityTh = "ใจดีแต่เขี้ยวลากดิน เน้นขายล็อตใหญ่ ชอบลูกค้าที่พูดจีนคล่อง"
            ),
            plotIntroTh = "คุณกำลังเดินทางมาคัดเลือกเสื้อผ้าล็อต 500 ตัว เพื่อนำเข้าไทย เถ้าแก่หวังเสนอราคาเริ่มต้นตัวละ 85 หยวน คุณต้องเจรจาต่อรองให้เหลือ 65 หยวน พร้อมแถมป้ายแบรนด์!",
            plotIntroEn = "Negotiate with Boss Wang for 500 silk garments. Target price: 65 RMB per piece.",
            initialDialogues = listOf(
                DialogueTurn(
                    id = "d1",
                    speakerIsNpc = true,
                    textZhSimp = "哎呀，帅哥/美女！来看看这批最新款的丝绸衬衫，面料极好，专供外贸的！单件拿85块。",
                    textZhTrad = "哎呀，帥哥/美女！來看看這批最新款的絲綢襯衫，面料極好，專供外貿的！單件拿85塊。",
                    pinyin = "Āiyā, shuàigē/měinǚ! Lái kànkan zhè pī zuìxīn kuǎn de sīchóu chènshān, miànliào jí hǎo, zhuāngōng wàimào de! Dān jiàn ná bāshíwǔ kuài.",
                    textTh = "อัยยา! พ่อรูปหล่อ/คนสวย มาดูเสื้อเชิ้ตผ้าไหมคอลเลกชันใหม่ล่าสุดนี่สิ ผ้าดีเลิศ เกรดส่งออกนอกเลยนะ ชิ้นละ 85 หยวนเท่านั้น",
                    textEn = "Hey there! Come check out our latest silk shirts. Top export quality! 85 RMB each.",
                    suggestedRepliesSimp = listOf(
                        "王老板，我们这次拿500件大货，能给个实诚批发价吗？",
                        "85块太贵了！隔壁档口只开70块呢。",
                        "老板，这布料确实不错，65块一手走，我今天就下定金。"
                    ),
                    suggestedRepliesTrad = listOf(
                        "王老闆，我們這次拿500件大貨，能給個實誠批發價嗎？",
                        "85塊太貴了！隔壁檔口只開70塊呢。",
                        "老闆，這布料確實不錯，65塊一手走，我今天就下定金。"
                    ),
                    suggestedRepliesPinyin = listOf(
                        "Wáng lǎobǎn, wǒmen zhè cì ná wǔbǎi jiàn dàhuò, néng gěi gè shíchéng pīfā jià ma?",
                        "Bāshíwǔ kuài tài guì le! Gébì dàngkǒu zhǐ kāi qīshí kuài ne.",
                        "Lǎobǎn, zhè bùliào quèshí búcuò, liùshíwǔ kuài yì shǒu zǒu, wǒ jīntiān jiù xià dìngjīn."
                    ),
                    suggestedRepliesTh = listOf(
                        "เถ้าแก่หวัง พวกเราสั่งล็อตใหญ่ 500 ชิ้น ขอราคาขายส่งแบบจริงใจหน่อยได้ไหม?",
                        "85 หยวนแพงไป! ร้านข้างๆ เปิดแค่ 70 เองนะ",
                        "เถ้าแก่ ผ้านี้ดีจริง 65 หยวนเหมาหมด ผมวางมัดจำวันนี้เลย"
                    ),
                    emotionEmoji = "😃"
                )
            ),
            targetKeyPhrasesSimp = listOf("批发价 (pīfā jià - ราคาส่ง)", "大货 (dàhuò - สินค้าล็อตใหญ่)", "下定金 (xià dìngjīn - วางมัดจำ)", "便宜一点 (piányi yìdiǎn)"),
            targetKeyPhrasesTrad = listOf("批發價", "大貨", "下定金", "便宜一點"),
            targetKeyPhrasesTh = listOf("ราคาส่ง", "สินค้าล็อตใหญ่", "วางมัดจำ", "ลดราคาหน่อย")
        ),
        CinemaScenario(
            id = "sc_shenzhen_factory",
            category = ScenarioCategory.BUSINESS_NEGOTIATION,
            titleTh = "เจรจาธุรกิจกับโรงงานไฮเทคเซินเจิ้น (深圳高新科技厂)",
            titleEn = "Shenzhen High-Tech Factory Business Negotiation",
            titleZhSimp = "深圳科技工厂OEM与交货期商务谈判",
            titleZhTrad = "深圳科技工廠OEM與交貨期商務談判",
            hskLevel = HskLevel.HSK4,
            coverImageRes = R.drawable.banner_tech_factory_1788072110290,
            npc = ScenarioNpc(
                id = "npc_manager_chen",
                nameZhSimp = "陈经理 (ผู้จัดการเฉิน)",
                nameZhTrad = "陳經理",
                nameTh = "ผู้จัดการเฉิน",
                roleTitleTh = "หัวหน้าฝ่ายผลิตและส่งออก OEM",
                avatarEmoji = "👩💼",
                personalityTh = "มืออาชีพ เป๊ะเรื่องสเปกทางเทคนิค และใส่ใจเงื่อนไขระยะเวลาส่งมอบ"
            ),
            plotIntroTh = "คุณกำลังประชุมเจรจาการผลิต OEM หูฟังไร้สาย 2,000 ชิ้น กำหนดส่งมอบภายใน 25 วัน และคุยเรื่องเงื่อนไขการรับประกัน QC",
            plotIntroEn = "Negotiate OEM production terms, 25-day delivery window, and strict QC testing.",
            initialDialogues = listOf(
                DialogueTurn(
                    id = "d2",
                    speakerIsNpc = true,
                    textZhSimp = "欢迎各位来到我们深圳研发中心！关于这次蓝牙耳机的定制需求，我们标准的交货周期通常是35天。",
                    textZhTrad = "歡迎各位來到我們深圳研發中心！關於這次藍牙耳機的定制需求，我們標準的交貨週期通常是35天。",
                    pinyin = "Huānyíng gèwèi lái dào wǒmen Shēnzhèn yánfā zhōngxīn! Guānyú zhè cì Lányá ěrjī de dìngzhì xūqiú, wǒmen biāozhǔn de jiāohuò zhōuqī tōngcháng shì sānshíwǔ tiān.",
                    textTh = "ยินดีต้อนรับทุกท่านสู่ศูนย์ R&D เซินเจิ้นของเรา! เกี่ยวกับออเดอร์ผลิตหูฟังบลูทูธครั้งนี้ รอบส่งมอบมาตรฐานปกติคือ 35 วันครับ",
                    textEn = "Welcome to our Shenzhen R&D center! Our standard lead time for this custom earphone order is 35 days.",
                    suggestedRepliesSimp = listOf(
                        "陈经理，我们需要在下个月电商大促前上架，25天能加急赶出来吗？",
                        "请问如果首批先交货800台，剩下的分批发出可行吗？",
                        "我们在合同里需要注明不良品率必须控制在0.3%以内。"
                    ),
                    suggestedRepliesTrad = listOf(
                        "陳經理，我們需要在下個月電商大促前上架，25天能加急趕出來嗎？",
                        "請問如果首批先交貨800台，剩下的分批發出可行嗎？",
                        "我們在合同裏需要註明不良品率必須控制在0.3%以內。"
                    ),
                    suggestedRepliesPinyin = listOf(
                        "Chén jīnglǐ, wǒmen xūyào zài xià gè yuè diànshāng dàcù qián shàngjià, èrshíwǔ tiān néng jiājí gǎn chūlái ma?",
                        "Qǐngwèn rúguǒ shǒupī xiān jiāohuò bābǎi tái, shèngxià de fēnpī fāchū kěxíng ma?",
                        "Wǒmen zài hétong lǐ xūyào zhùmíng bùliángpǐn lǜ bìxū kòngzhì zài líng diǎn sān bǎifēnzhī yǐnèi."
                    ),
                    suggestedRepliesTh = listOf(
                        "ผู้จัดการเฉิน เราต้องวางขายก่อนเทศกาลเซลออนไลน์เดือนหน้า ส่งด่วน 25 วันได้ไหม?",
                        "ถ้าล็อตแรกส่ง 800 ชิ้นก่อน ที่เหลือทยอยส่งตามมา พอจะเป็นไปได้ไหม?",
                        "ในสัญญาต้องระบุอัตราสินค้าชำรุดไม่เกิน 0.3% นะครับ"
                    ),
                    emotionEmoji = "🤝"
                )
            ),
            targetKeyPhrasesSimp = listOf("交货期 (jiāohuò qī - กำหนดส่งมอบ)", "加急 (jiājí - ด่วนพิเศษ)", "合同 (hétong - สัญญา)", "质量检测 (zhìliàng jiǎncè)"),
            targetKeyPhrasesTrad = listOf("交貨期", "加急", "合同", "質量檢測"),
            targetKeyPhrasesTh = listOf("กำหนดส่งมอบ", "เร่งด่วน", "สัญญา", "ตรวจเช็กคุณภาพ")
        ),
        CinemaScenario(
            id = "sc_sichuan_dining",
            category = ScenarioCategory.DINING_FOOD,
            titleTh = "สั่งอาหารที่ร้านอาหารเสฉวนต้นตำรับ (地道川菜馆)",
            titleEn = "Authentic Sichuan Dining Experience",
            titleZhSimp = "成都地道川菜馆点餐与口味要求",
            titleZhTrad = "成都地道川菜館點餐與口味要求",
            hskLevel = HskLevel.HSK2,
            coverImageRes = R.drawable.banner_sichuan_dining_1788072127234,
            npc = ScenarioNpc(
                id = "npc_waiter_xiaoli",
                nameZhSimp = "服务员小李 (เสี่ยวหลี่)",
                nameZhTrad = "服務員小李",
                nameTh = "บริกรเสี่ยวหลี่",
                roleTitleTh = "พนักงานบริการร้านอาหารเฉิงตู",
                avatarEmoji = "👨🍳",
                personalityTh = "คล่องแคล่ว อัธยาศัยดี แนะนำเมนูขึ้นชื่อและคอยถามระดับความเผ็ด"
            ),
            plotIntroTh = "คุณกำลังนั่งในร้านอาหารเสฉวนชื่อดังใจกลางเมืองเฉิงตู ต้องการสั่งเต้าหู้มาโผว ไก่กังเปา ขอเผ็ดน้อย ไม่ใส่ผักชี และชำระเงินผ่าน QR Code",
            plotIntroEn = "Order authentic Mapo Tofu and Kung Pao Chicken with custom spice levels and no cilantro.",
            initialDialogues = listOf(
                DialogueTurn(
                    id = "d3",
                    speakerIsNpc = true,
                    textZhSimp = "几位里面请！请问想吃点什么？我们店的麻婆豆腐和水煮牛肉是招牌菜，非常地道！",
                    textZhTrad = "幾位裏面請！請問想吃點什麼？我們店的麻婆豆腐和水煮牛肉是招牌菜，非常地道！",
                    pinyin = "Jǐ wèi lǐmiàn qǐng! Qǐngwèn xiǎng chī diǎn shénme? Wǒmen diàn de mápó dòufu hé shuǐzhǔ niúròu shì zhāopái cài, fēicháng dìdao!",
                    textTh = "เชิญด้านในครับกี่ท่าน! วันนี้รับอะไรดีครับ? เต้าหู้มาโผวกับเนื้อต้มพริกเสฉวนเป็นเมนูเด็ดประจำร้าน รสชาติต้นตำรับแท้ๆ เลยครับ!",
                    textEn = "Welcome! What would you like to eat today? Our Mapo Tofu and Boiled Beef are house specialties!",
                    suggestedRepliesSimp = listOf(
                        "服务员，来一份麻婆豆腐和宫保鸡丁，请做微辣，不要放香菜。",
                        "请问可以扫桌上的二维码点餐吗？另外米饭是免费续吗？",
                        "我们不太能吃辣，有没有推荐的不辣汤品？"
                    ),
                    suggestedRepliesTrad = listOf(
                        "服務員，來一份麻婆豆腐和宮保雞丁，請做微辣，不要放香菜。",
                        "請問可以掃桌上的二維碼點餐嗎？另外米飯是免費續嗎？",
                        "我們不太能吃辣，有沒有推薦的不辣湯品？"
                    ),
                    suggestedRepliesPinyin = listOf(
                        "Fúwùyuán, lái yí fèn mápó dòufu hé gōngbǎo jīdīng, qǐng zuò wēilà, bú yào fàng xiāngcài.",
                        "Qǐngwèn kěyǐ sǎo zhuō shàng de èrwéimǎ diǎncān ma? Lìngwài mǐfàn shì miǎnfèi xù ma?",
                        "Wǒmen bú tài néng chī là, yǒu méiyǒu tuījiàn de bú là tāngpǐn?"
                    ),
                    suggestedRepliesTh = listOf(
                        "น้องครับ ขอเต้าหู้มาโผวกับไก่กังเปาอย่างละ 1 ขอเผ็ดน้อย ไม่ใส่ผักชีนะครับ",
                        "สแกนคิวอาร์โค้ดบนโต๊ะสั่งได้ไหมครับ? แล้วข้าวสวยเติมฟรีหรือเปล่า?",
                        "พวกเราทานเผ็ดไม่ค่อยเก่ง มีซุปที่ไม่เผ็ดแนะนำไหมครับ?"
                    ),
                    emotionEmoji = "🌶️"
                )
            ),
            targetKeyPhrasesSimp = listOf("微辣 (wēilà - เผ็ดน้อย)", "不要放香菜 (bú yào fàng xiāngcài - ไม่ใส่ผักชี)", "买单 (mǎidān - เช็กบิล)", "扫码 (sǎomǎ - สแกนโค้ด)"),
            targetKeyPhrasesTrad = listOf("微辣", "不要放香菜", "買單", "掃碼"),
            targetKeyPhrasesTh = listOf("เผ็ดน้อย", "ไม่ใส่ผักชี", "เช็กบิล", "สแกนคิวอาร์")
        )
    )

    // 6. Project TOR Human Gates / Production Specification
    val torHumanGates = listOf(
        TorHumanGate(
            phaseNumber = 1,
            phaseTitleTh = "Phase 1: Wireframing & Tech Setup",
            timePeriodTh = "เดือนที่ 1",
            deliverablesTh = "UX/UI Prototype, Supabase Schema, Whisper API Proof-of-Concept & Latency SLA (<150ms)",
            humanCheckActionTh = "อนุมัติ Design System UI Kit, โครงสร้าง Database & ตรวจสอบ Latency การเชื่อมต่อ AI Core",
            statusTh = "COMPLETED ✅",
            isCurrentPhase = false
        ),
        TorHumanGate(
            phaseNumber = 2,
            phaseTitleTh = "Phase 2: Core Development",
            timePeriodTh = "เดือนที่ 2 - 4",
            deliverablesTh = "Quest Map Engine, Offline SRS Vocab Lab, Tone Pitch Spectrum Analyzer, AI Cinema Engine",
            humanCheckActionTh = "ทดสอบความแม่นยำ F0 Tone Pitch Graph, อัลกอริทึม Spaced Repetition และความสมจริงของเสียง AI NPC",
            statusTh = "IN PRODUCTION 🚀",
            isCurrentPhase = true
        ),
        TorHumanGate(
            phaseNumber = 3,
            phaseTitleTh = "Phase 3: Closed Beta Testing",
            timePeriodTh = "เดือนที่ 5",
            deliverablesTh = "Beta Testing Report กับผู้ทดสอบ 500 คน (TestFlight / Google Play Internal Testing)",
            humanCheckActionTh = "วิเคราะห์คะแนนความแม่นยำการประเมินเสียง และความพึงพอใจการโต้ตอบแบบเรียลไทม์",
            statusTh = "PLANNED 📋",
            isCurrentPhase = false
        ),
        TorHumanGate(
            phaseNumber = 4,
            phaseTitleTh = "Phase 4: Optimization & Localization",
            timePeriodTh = "เดือนที่ 6",
            deliverablesTh = "Asset Streaming ควบคุมขนาด APK 45-60MB, รองรับ 3 ภาษา (ไทย/อังกฤษ/จีนตัวย่อ-เต็ม) & TalkBack",
            humanCheckActionTh = "ตรวจสอบขนาด Bundle, ทดสอบ Accessibility และความถูกต้องของการแปลภาษา",
            statusTh = "PLANNED 📋",
            isCurrentPhase = false
        ),
        TorHumanGate(
            phaseNumber = 5,
            phaseTitleTh = "Phase 5: Soft Launch & Store Approval",
            timePeriodTh = "เดือนที่ 7 - 8",
            deliverablesTh = "ยื่นขออนุมัติ App Store / Google Play Store, ระบบชำระเงิน Monetization Matrix",
            humanCheckActionTh = "ตรวจสอบ Security & Payment Compliance, อนุมัติการเปิดให้บริการระดับ Global",
            statusTh = "PLANNED 📋",
            isCurrentPhase = false
        )
    )

    // 7. Achievement Badges
    val badges = listOf(
        AchievementBadge(
            id = "b1_tone_master",
            titleTh = "เทพวรรณยุกต์ 4 เสียง",
            titleEn = "Tone Master",
            iconEmoji = "🎯",
            descriptionTh = "ฝึกออกเสียงวรรณยุกต์เป๊ะเกิน 90% ครบ 10 คำ",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "b2_streak_7",
            titleTh = "ไฟลุก 7 วันติด",
            titleEn = "7-Day Flame Streak",
            iconEmoji = "🔥",
            descriptionTh = "ล็อกอินฝึกภาษาจีนต่อเนื่อง 7 วัน",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "b3_guangzhou_pro",
            titleTh = "เซียนต่อราคากวางโจว",
            titleEn = "Guangzhou Bargain Pro",
            iconEmoji = "🛍️",
            descriptionTh = "พิชิตด่านเจรจาต่อรองเสื้อผ้ากับเถ้าแก่หวังได้สำเร็จ",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "b4_srs_conqueror",
            titleTh = "คลังศัพท์ SRS ขั้นเซียน",
            titleEn = "SRS Vocab Conqueror",
            iconEmoji = "🧠",
            descriptionTh = "ทบทวนคำศัพท์ HSK ต่อเนื่องจนจำได้ขึ้นใจ 50 คำ",
            isUnlocked = false,
            progress = 0.65f
        ),
        AchievementBadge(
            id = "b5_handwriting_guru",
            titleTh = "ปรมาจารย์คัดลายมือ",
            titleEn = "Calligraphy Master",
            iconEmoji = "✍️",
            descriptionTh = "คัดอักษรจีนตามลำดับขีดถูกต้องครบ 20 ตัว",
            isUnlocked = false,
            progress = 0.40f
        )
    )
}
