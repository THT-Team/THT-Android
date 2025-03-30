package tht.feature.chat.chat.screen

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun String.formatToAmPm(): String {
    val instant = try {
        Instant.parse(this)
    } catch (e: Exception) {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"))
            .atZone(ZoneId.systemDefault()).toInstant()
    }

    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
