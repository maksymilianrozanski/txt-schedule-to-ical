import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Clock
import java.util.*
import java.util.stream.Stream
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val calendar = GregorianCalendar()
    val clock = Clock.systemUTC()

    println("Please enter input file path.")
    val scanner = Scanner(System.`in`)
    val txtFilePath: String = scanner.nextLine()
    println(txtFilePath)

//    "I:\\java\\harm3sem\\src\\main\\resources\\schedule.txt"
    val stream: Stream<String>
    try {
        stream = getStreamFromFileFunc(txtFilePath)
    } catch (e: java.nio.file.NoSuchFileException) {
        println("File not found")
        exitProcess(1)
    }
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

    println("iCal file created successfully.")
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
