import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

fun main(args: Array<String>) {
    val stream: Stream<String> = getStreamFromFileFunc("I:\\java\\harm3sem\\src\\main\\resources\\schedule.txt")
    val daysList = createDaysList(stream)
}

var getStreamFromFileFunc: (filePath: String) -> Stream<String> = { filePath ->
    Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
}

var isDayNotLesson: (String) -> Boolean = {
    it.startsWith("Data Zajęć:") || it.startsWith("[Zwęż]\tData Zajęć:")
}
