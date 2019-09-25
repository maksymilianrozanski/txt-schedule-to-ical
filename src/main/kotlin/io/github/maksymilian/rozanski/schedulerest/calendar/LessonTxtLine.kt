package io.github.maksymilian.rozanski.schedulerest.calendar
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern
import java.util.stream.Stream

class LessonTxtLine(textLessonLine: String) {

    init {
        separateValuesFromText(textLessonLine)
    }

    lateinit var date: String
    lateinit var startTime: String
    lateinit var endTime: String
    lateinit var lecturer: String
    lateinit var lessonTitle: String
    lateinit var lessonType: String
    lateinit var lessonCode: String
    lateinit var classRoom: String

    private fun separateValuesFromText(textLessonLine: String) {
        val index = AtomicInteger()
        lineToStream(textLessonLine).forEach {
            when (index.getAndIncrement()) {
                1 -> this.startTime = it.trim()
                2 -> this.endTime = it.trim()
                4 -> this.lecturer = it.trim()
                5 -> this.lessonTitle = it.trim()
                6 -> this.lessonType = it.trim()
                7 -> this.lessonCode = it.trim()
                8 -> this.classRoom = it.trim()
            }
        }
    }
}

fun lineToStream(string: String): Stream<String> {
    val delimiter = "\t"
    val list = string.split(delimiter)
    return list.stream()
}

//example input: [Zwęż]	Data Zajęć: 2019-10-05 sobota
fun extractDateFromString(stringWithDate: String): String {
    val yearStartIndex = stringWithDate.indexOf("20")
    val dateAndDay = stringWithDate.subSequence(yearStartIndex, stringWithDate.length - 1)
    val date = dateAndDay.toString().split(" ")[0]
    val pattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d")
    val matcher = pattern.matcher(date)
    val matches = matcher.matches()
    if (!matches) throw IOException("Bad input")
    return date
}
