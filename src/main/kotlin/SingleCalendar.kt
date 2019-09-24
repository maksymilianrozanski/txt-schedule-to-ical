import java.util.stream.Stream

class SingleCalendar {

}

class SingleDay(dateLine: String, lessons: List<String>) {

    val dayValues = arrayListOf<String>()

    init {
        dayValues.add(dateLine)
        dayValues.addAll(lessons)
    }
}

fun createDaysList(stream: Stream<String>): List<SingleDay> {
    val daysList = arrayListOf<SingleDay>()
    var currentDateLine = ""
    var lessonsInDay = arrayListOf<String>()
    stream.forEach {
        if (isDayNotLesson(it)) {
            if (currentDateLine.isNotEmpty()) {
                daysList.add(SingleDay(currentDateLine, lessonsInDay))
                currentDateLine = ""
            }
            currentDateLine = it
            //clean up after previous
            lessonsInDay = arrayListOf()
        } else {
            lessonsInDay.add(it)
        }
    }
    daysList.add(SingleDay(currentDateLine, lessonsInDay))
    return daysList
}
