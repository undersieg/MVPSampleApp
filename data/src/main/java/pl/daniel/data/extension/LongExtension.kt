package pl.daniel.data.extension

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

const val PATTERN_DAY_MONTH_YEAR = "dd.MM.yyyy"

fun Long.convertToDate(pattern: String): String = DateTimeFormatter.ofPattern(pattern)
    .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()))