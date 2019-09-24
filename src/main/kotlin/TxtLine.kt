
import java.io.IOException
import java.util.regex.Pattern
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
