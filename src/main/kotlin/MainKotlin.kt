import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

fun main(args: Array<String>) {
    val string: Stream<String> = getStreamFromFileFunc("I:\\java\\harm3sem\\src\\main\\resources\\schedule.txt")
    string.forEach(System.out::println)
}

var getStreamFromFileFunc: (String) -> Stream<String> = { filePath ->
    Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
}

var isDayNotLesson: (String) -> Boolean = {
    it.startsWith("Data Zajęć:") || it.startsWith("[Zwęż]\tData Zajęć:")
}

