package io.github.maksymilian.rozanski.schedulerest.calendar

import java.time.Clock
import java.util.*
import java.util.stream.Stream

fun generateICalSchedule(stream: Stream<String>, calendar: GregorianCalendar, clock: Clock): String {
    val daysList: List<SingleDay> = createDaysList(stream)
    val iCalLessonsList = arrayListOf<LessonICal>()
    daysList.forEach {
        iCalLessonsList.addAll(
            it.lessonsOneDay(
                dateStamp(calendar),
                generateUid(clock)
            )
        )
    }

    val singleCalendar = SingleCalendar(iCalLessonsList, clock)
    val iCal = singleCalendar.getICal()
    return iCal.replace("\\", "")
}

var isDayNotLesson: (String) -> Boolean = {
    it.startsWith("Data Zajęć:") || it.startsWith("[Zwęż]\tData Zajęć:")
}
