import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Clock
import java.util.*
import java.util.stream.Stream

fun main(args: Array<String>) {
    val calendar = GregorianCalendar()
    val clock = Clock.systemUTC()

    val stream: Stream<String> = getStreamFromFileFunc("I:\\java\\harm3sem\\src\\main\\resources\\schedule.txt")
    val daysList: List<SingleDay> = createDaysList(stream)
    val iCalLessonsList = arrayListOf<LessonICal>()
    daysList.forEach {
        iCalLessonsList.addAll(
            it.lessonsOneDay(
                dateStamp(calendar),
                generateUid(uniqueUidTime(clock))
            )
        )
    }

    val singleCalendar = SingleCalendar(iCalLessonsList, clock)
    val iCal = singleCalendar.getICal()
    val iCalRemovedBackSlashes = iCal.replace("\\\\\\", "")

    writeToFile(iCalRemovedBackSlashes)

    println("End")
}

var getStreamFromFileFunc: (filePath: String) -> Stream<String> = { filePath ->
    Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
}

var isDayNotLesson: (String) -> Boolean = {
    it.startsWith("Data Zajęć:") || it.startsWith("[Zwęż]\tData Zajęć:")
}

fun writeToFile(text: String) {
    val path = Paths.get("MySchedule.ical")
    Files.write(path, text.toByteArray())
}
