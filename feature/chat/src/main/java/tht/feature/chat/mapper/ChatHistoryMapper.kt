package tht.feature.chat.mapper

import com.tht.tht.domain.chat.model.ChatHistoryModel
import tht.feature.chat.model.ChatHistoryUiModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun ChatHistoryModel.toModel() = ChatHistoryUiModel(
    chatIdx = chatIdx,
    sender = sender,
    senderUuid = senderUuid,
    msg = msg,
    imgUrl = imgUrl,
    dateTime = dateTime.parseTimeString(ampm = true),
)

fun String.parseTimeString(ampm: Boolean = false): String {
    // 날짜와 시간 부분 분리
    val dateTimeParts = this.split("T")
    val dateString = dateTimeParts[0]
    val timePartWithMs = dateTimeParts[1]

    // 시간 부분 분리
    val timeParts = timePartWithMs.split(":")
    val hours = timeParts[0].toInt()
    val minutes = timeParts[1].toInt()
    val seconds = timeParts[2].split(".")[0].toInt()

    // 12시간제 변환
    val isPM = hours >= 12
    val hour12 = if (hours > 12) hours - 12 else if (hours == 0) 12 else hours
    val amPm = if (isPM) "PM" else "AM"

    // 날짜 처리
    val currentDate = LocalDate.now()
    val inputDate = LocalDate.parse(dateString)
    val daysBetween = ChronoUnit.DAYS.between(inputDate, currentDate)
    return if (ampm) {
        "%02d:%02d %s".format(hour12, minutes, amPm)
    } else {
        // 결과 문자열 생성
        when (daysBetween) {
            0L -> "%02d:%02d %s".format(hour12, minutes, amPm)
            1L -> "어제"
            else -> "%04d.%02d.%02d".format(inputDate.year, inputDate.monthValue, inputDate.dayOfMonth)
        }
    }
}
