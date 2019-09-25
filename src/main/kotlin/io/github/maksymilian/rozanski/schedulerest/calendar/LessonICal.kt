package io.github.maksymilian.rozanski.schedulerest.calendar

import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.TimeZone
import net.fortuna.ical4j.model.TimeZoneRegistryFactory
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.Description
import net.fortuna.ical4j.model.property.Location
import net.fortuna.ical4j.model.property.Uid
import java.time.Clock
import java.util.*

data class LessonICal(
        val date: String,
        val startTime: String,
        val endTime: String,
        val lecturer: String,
        val lessonTitle: String,
        val lessonType: String,
        val lessonCode: String,
        val classRoom: String,
        val dtStamp: String,
        val uid: String
) {
    fun getYear(): Int {
        return date.substring(0, 4).toInt()
    }

    // 0-11 format
    fun getMonth(): Int {
        return date.substring(5, 7).toInt() -1
    }

    fun getDay(): Int {
        return date.substring(8, 10).toInt()
    }

    fun getStartHour(): Int {
        return if (startTime.length == 5)
            startTime.substring(0, 2).toInt()
        else
            startTime.substring(0, 1).toInt()
    }

    fun getStartMinutes(): Int {
        return if (startTime.length == 5)
            startTime.substring(3, 5).toInt()
        else
            startTime.substring(2, 4).toInt()
    }

    fun getEndHour(): Int {
        return if (endTime.length == 5)
            endTime.substring(0, 2).toInt()
        else
            endTime.substring(0, 1).toInt()
    }

    fun getEndMinutes(): Int {
        return if (endTime.length == 5)
            endTime.substring(3, 5).toInt()
        else
            endTime.substring(2, 4).toInt()
    }
}

var createLessonICal: (LessonTxtLine, String, String) -> LessonICal =
        { lesson: LessonTxtLine, dtStamp: String, uid: String ->
            LessonICal(
                    date = lesson.date,
                    startTime = lesson.startTime,
                    endTime = lesson.endTime,
                    lecturer = lesson.lecturer,
                    lessonTitle = lesson.lessonTitle,
                    classRoom = lesson.classRoom,
                    lessonCode = lesson.lessonCode,
                    lessonType = lesson.lessonType,
                    dtStamp = dtStamp,
                    uid = uid
            )
        }

var dateStamp: (GregorianCalendar) -> String = {
    val year = it.get(Calendar.YEAR).toString()
    var month = (it.get(Calendar.MONTH) + 1).toString()
    if (month.length < 2) month = "0$month"
    var day = it.get(Calendar.DAY_OF_MONTH).toString()
    if (day.length < 2) day = "0$day"
    var hour = it.get(Calendar.HOUR_OF_DAY).toString()
    if (hour.length < 2) hour = "0$hour"
    var minutes = it.get(Calendar.MINUTE).toString()
    if (minutes.length < 2) minutes = "0$minutes"
    var seconds = it.get(Calendar.SECOND).toString()
    if (seconds.length < 2) seconds = "0$seconds"
    year + month + day + "T" + hour + minutes + seconds + "Z"
}

var lastUidTime: Long = 0
var generateUid: (Long) -> String = { "${Thread.currentThread().id}@$it" }

var uniqueUidTime: (Clock) -> Long = {
    var currentTimeInMillis: Long
    synchronized(LessonICal::class.java) {
        currentTimeInMillis = it.millis()
        if (currentTimeInMillis < lastUidTime) currentTimeInMillis = lastUidTime
        if (currentTimeInMillis - lastUidTime < 1) currentTimeInMillis += 1
        lastUidTime = currentTimeInMillis
    }
    currentTimeInMillis
}

var createVEvent: (LessonICal, Clock) -> VEvent = { lesson: LessonICal, clock: Clock ->
    val registry = TimeZoneRegistryFactory.getInstance().createRegistry()
    val timeZone = registry.getTimeZone("Europe/Warsaw")
    val tz = timeZone.vTimeZone

    val startDate = extractDate(lesson, timeZone).setHourAndMin(lesson, true)
    val endDate = extractDate(lesson, timeZone).setHourAndMin(lesson, false)

    val event = VEvent(DateTime(startDate.time), DateTime(endDate.time), summary(lesson))
    event.properties.add(tz.timeZoneId)
    event.properties
            .add(Description(description(lesson)))
    event.properties.add(Location("św. Filipa 17 Kraków"))

    val uid = Uid()
    uid.value = generateUid(uniqueUidTime(clock))
    event.properties.add(uid)
    event
}

private var extractDate: (LessonICal, TimeZone) -> Calendar = { lesson: LessonICal, timeZone: TimeZone ->
    val date = GregorianCalendar()
    date.timeZone = timeZone
    date.set(Calendar.MONTH, lesson.getMonth())
    date.set(Calendar.DAY_OF_MONTH, lesson.getDay())
    date.set(Calendar.YEAR, lesson.getYear())
    date
}

var setHourAndMin: Calendar.(lesson: LessonICal, isStart: Boolean) -> Calendar =
        { lesson: LessonICal, isStart: Boolean ->
            val hour: Int
            val minutes: Int
            if (isStart) {
                hour = lesson.getStartHour(); minutes = lesson.getStartMinutes()
            } else {
                hour = lesson.getEndHour(); minutes = lesson.getEndMinutes()
            }
            this.set(Calendar.HOUR_OF_DAY, hour)
            this.set(Calendar.MINUTE, minutes)
            this.set(Calendar.SECOND, 0)
            this
        }

var summary: (LessonICal) -> String = { "${it.lessonType} ${it.lessonTitle}" }
var description: (LessonICal) -> String = { it.classRoom + "\\, " + it.lecturer + "\\, " + it.lessonCode }
