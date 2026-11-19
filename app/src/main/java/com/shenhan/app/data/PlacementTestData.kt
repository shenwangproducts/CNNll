package com.shenhan.app.data

import com.shenhan.app.model.HskLevel
import com.shenhan.app.model.PlacementQuestion
import com.shenhan.app.model.QuestionCategory

object PlacementTestData {

    val diagnosticQuestions: List<PlacementQuestion> = listOf(
        // 1. TONE & PITCH (Basic HSK 1)
        PlacementQuestion(
            id = "pq_tone_1",
            category = QuestionCategory.TONE_PITCH,
            targetHskLevel = HskLevel.HSK1,
            promptTh = "คำว่า 妈 (mā - คุณแม่) ในภาษาจีนกลาง ออกเสียงวรรณยุกต์เสียงใด?",
            promptEn = "Which tone is the word 妈 (mā - mother)?",
            chineseSimp = "妈",
            chineseTrad = "媽",
            pinyin = "mā",
            audioCue = "mā",
            options = listOf(
                "เสียงที่ 1 (เสียงสูงราบ 55 - High Flat)",
                "เสียงที่ 2 (เสียงจัตวายกขึ้น 35 - Rising)",
                "เสียงที่ 3 (เสียงเอกต่ำหักขึ้น 214 - Dipping)",
                "เสียงที่ 4 (เสียงโทตกฮวบ 51 - Falling)"
            ),
            correctIndex = 0,
            explanationTh = "เสียงที่ 1 (mā) คือเสียงสูงราบ เส้น Pitch อยู่ที่ระดับ 55 สม่ำเสมอ"
        ),

        // 2. VOCAB MEANING (HSK 1)
        PlacementQuestion(
            id = "pq_vocab_1",
            category = QuestionCategory.VOCAB_MEANING,
            targetHskLevel = HskLevel.HSK1,
            promptTh = "คำว่า 谢谢 (xièxie) มีความหมายตรงกับข้อใด?",
            promptEn = "What is the meaning of 谢谢 (xièxie)?",
            chineseSimp = "谢谢",
            chineseTrad = "謝謝",
            pinyin = "xiè xie",
            audioCue = "xièxie",
            options = listOf(
                "สวัสดี",
                "ขอบคุณ",
                "ลาก่อน",
                "ขอโทษ"
            ),
            correctIndex = 1,
            explanationTh = "谢谢 (xièxie) แปลว่า 'ขอบคุณ' เป็นคำทักทายพื้นฐานใน HSK 1"
        ),

        // 3. TONE & PITCH (HSK 2 Tone Change Rule)
        PlacementQuestion(
            id = "pq_tone_2",
            category = QuestionCategory.TONE_PITCH,
            targetHskLevel = HskLevel.HSK2,
            promptTh = "เมื่อเสียงที่ 3 สองตัวมาเจอกัน เช่น 你好 (nǐ hǎo) คำแรกจะเปลี่ยนเป็นเสียงใด?",
            promptEn = "When two 3rd tones meet like 你好, what tone does the first syllable change to?",
            chineseSimp = "你好",
            chineseTrad = "你好",
            pinyin = "nǐ hǎo -> ní hǎo",
            audioCue = "nǐ hǎo",
            options = listOf(
                "คงเป็นเสียง 3 เหมือนเดิม",
                "เปลี่ยนเป็นเสียง 2 (เสียงยกขึ้น)",
                "เปลี่ยนเป็นเสียง 1 (เสียงราบ)",
                "เปลี่ยนเป็นเสียงเบา (Neutral Tone)"
            ),
            correctIndex = 1,
            explanationTh = "กฎการเปลี่ยนเสียงวรรณยุกต์ (Tone Sandhi): เสียง 3 + เสียง 3 คำแรกจะผันเป็นเสียง 2 (ní hǎo)"
        ),

        // 4. SENTENCE ORDER (HSK 2)
        PlacementQuestion(
            id = "pq_sent_1",
            category = QuestionCategory.SENTENCE_ORDER,
            targetHskLevel = HskLevel.HSK2,
            promptTh = "ประโยค 'ฉันกำลังดื่มชาจีน' เขียนเรียงลำดับคำในภาษาจีนได้ถูกต้องที่สุดข้อใด?",
            promptEn = "Which sentence correctly translates 'I am drinking Chinese tea'?",
            chineseSimp = "我正在喝中国茶。",
            chineseTrad = "我正在喝中國茶。",
            pinyin = "Wǒ zhèngzài hē Zhōngguó chá.",
            audioCue = "Wǒ zhèngzài hē Zhōngguó chá.",
            options = listOf(
                "我 中国茶 正在 喝 (Wǒ Zhōngguó chá zhèngzài hē)",
                "我 正在 喝 中国茶 (Wǒ zhèngzài hē Zhōngguó chá)",
                "正在 我 喝 中国茶 (Zhèngzài wǒ hē Zhōngguó chá)",
                "喝 中国茶 正在 我 (Hē Zhōngguó chá zhèngzài wǒ)"
            ),
            correctIndex = 1,
            explanationTh = "โครงสร้างประโยคภาษาจีน: ประธาน (我) + กริยาวิเศษณ์บอกกาล (正在) + กริยา (喝) + กรรม (中国茶)"
        ),

        // 5. VOCAB MEANING (HSK 3)
        PlacementQuestion(
            id = "pq_vocab_2",
            category = QuestionCategory.VOCAB_MEANING,
            targetHskLevel = HskLevel.HSK3,
            promptTh = "คำว่า 简单 (jiǎndān) ในประโยค '这个问题很简单' แปลว่าอะไร?",
            promptEn = "What does 简单 (jiǎndān) mean in 'This question is very simple'?",
            chineseSimp = "这个问题很简单。",
            chineseTrad = "這個問題很簡單。",
            pinyin = "Zhège wèntí hěn jiǎndān.",
            audioCue = "Zhège wèntí hěn jiǎndān.",
            options = listOf(
                "ซับซ้อน / ยาก",
                "สำคัญมาก",
                "เรียบง่าย / ง่าย",
                "น่าสนใจ"
            ),
            correctIndex = 2,
            explanationTh = "简单 (jiǎndān) แปลว่า เรียบง่าย หรือง่ายดาย (ตรงข้ามกับ 复杂 - ซับซ้อน)"
        ),

        // 6. DIALOGUE COMPREHENSION (HSK 3 Situational)
        PlacementQuestion(
            id = "pq_dial_1",
            category = QuestionCategory.DIALOGUE_COMPREHENSION,
            targetHskLevel = HskLevel.HSK3,
            promptTh = "A: '请问，去火车站怎么走？' (ขอถามหน่อย ไปสถานีรถไฟไปยังไง?)\nB ควรตอบข้อใดจึงถูกต้องและเป็นธรรมชาติที่สุด?",
            promptEn = "How should B respond naturally to A asking for directions to the railway station?",
            chineseSimp = "向前一直走，在十字路口向右拐。",
            chineseTrad = "向前一直走，在十字路口向右拐。",
            pinyin = "Xiàng qián yìzhí zǒu, zài shízì lùkǒu xiàng yòu guǎi.",
            audioCue = "Xiàng qián yìzhí zǒu, zài shízì lùkǒu xiàng yòu guǎi.",
            options = listOf(
                "向前一直走，在十字路口向右拐 (เดินตรงไปข้างหน้า ถึงสี่แยกเลี้ยวขวา)",
                "我昨天已经买票了 (เมื่อวานฉันซื้อตั๋วแล้ว)",
                "火车站的苹果很好吃 (แอปเปิลที่สถานีรถไฟอร่อยมาก)",
                "没关系，不客气 (ไม่เป็นไร ไม่ต้องเกรงใจ)"
            ),
            correctIndex = 0,
            explanationTh = "เป็นการบอกทาง: 向前一直走 (เดินตรงไป) + 在十字路口向右拐 (ถึงสี่แยกเลี้ยวขวา)"
        ),

        // 7. SENTENCE & GRAMMAR (HSK 4 把字句 / Structure)
        PlacementQuestion(
            id = "pq_sent_2",
            category = QuestionCategory.SENTENCE_ORDER,
            targetHskLevel = HskLevel.HSK4,
            promptTh = "ประโยคโครงสร้าง 把 (Bǎ) ข้อใดถูกต้องตามหลักไวยากรณ์?",
            promptEn = "Which sentence correctly uses the 把 (bǎ) disposal construction?",
            chineseSimp = "他把作业做完了。",
            chineseTrad = "他把作業做完了。",
            pinyin = "Tā bǎ zuòyè zuò wán le.",
            audioCue = "Tā bǎ zuòyè zuò wán le.",
            options = listOf(
                "他把作业做完了 (เขาทำการบ้านเสร็จเรียบร้อยแล้ว)",
                "他把做完作业了 (เขาเอาเสร็จการบ้านแล้ว - ผิดไวยากรณ์)",
                "作业把他做完了 (การบ้านเอาเขาทำเสร็จ - สลับประธาน)",
                "他作业把做完了 (วางคำช่วย 把 ผิดตำแหน่ง)"
            ),
            correctIndex = 0,
            explanationTh = "โครงสร้าง 把字句: ประธาน (他) + 把 + กรรม (作业) + กริยา (做) + ผลลัพธ์ (完了)"
        ),

        // 8. ADVANCED VOCAB & IDIOM (HSK 4+)
        PlacementQuestion(
            id = "pq_vocab_3",
            category = QuestionCategory.VOCAB_MEANING,
            targetHskLevel = HskLevel.HSK4,
            promptTh = "คำว่า 坚持 (jiānchí) ในประโยค '只要坚持学习，就一定能成功' มีความหมายว่าอะไร?",
            promptEn = "What does 坚持 (jiānchí) mean in 'As long as you persist in studying, you will surely succeed'?",
            chineseSimp = "只要坚持学习，就一定能成功。",
            chineseTrad = "只要堅持學習，就一定能成功。",
            pinyin = "Zhǐyào jiānchí xuéxí, jiù yídìng néng chénggōng.",
            audioCue = "Zhǐyào jiānchí xuéxí, jiù yídìng néng chénggōng.",
            options = listOf(
                "หยุดพักชั่วคราว",
                "ยืนหยัด / พยายามอย่างต่อเนื่องไม่ท้อถอย",
                "เริ่มทำอย่างรวดเร็ว",
                "ตัดสินใจเลือก"
            ),
            correctIndex = 1,
            explanationTh = "坚持 (jiānchí) แปลว่า ยืนหยัด มุ่งมั่นทำต่อเนื่องอย่างไม่ย่อท้อ"
        )
    )
}
