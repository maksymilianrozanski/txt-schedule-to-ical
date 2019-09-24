import java.util.stream.Stream

class TxtLine(val textLessonLine: String) {

    init {

    }

    val date: String = ""
    val startTime: String = ""
    val endTime: String = ""
    val lecturer: String = ""
    val lessonTitle: String = ""
    val lessonType: String = ""
    val lessonCode: String = ""
    val classRoom: String = ""

    fun separateValuesFromText(textLessonLine: String) {

    }
}

fun stringToStream(string: String): Stream<String> {
    val delimiter = "\t"
    val list = string.split(delimiter)
    return list.stream()
}
