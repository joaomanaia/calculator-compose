package domain.time

import kotlinx.datetime.*
import kotlinx.datetime.format.*

object DateTimeUtil {
    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMilli(dateTime: LocalDateTime): Long {
        return dateTime
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    }

    fun fromEpochMilli(epochMilli: Long): LocalDateTime {
        return Instant
            .fromEpochMilliseconds(epochMilli)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun formatDate(dateTime: LocalDateTime): String {
        return LocalDateTime.Format {
            dayOfMonth()
            chars(" of ")
            monthName(MonthNames.ENGLISH_FULL)
        }.format(dateTime)
    }
}