import io.github.maksymilian.rozanski.schedulerest.calendar.LessonTxtLine
import io.github.maksymilian.rozanski.schedulerest.calendar.extractDateFromString
import io.github.maksymilian.rozanski.schedulerest.calendar.lineToStream
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.util.stream.Stream
import kotlin.streams.toList
import kotlin.test.assertFailsWith


class LessonTxtLineTest {

    @Test
    fun stringToStreamTests() {
        val testString = "fsdffasf\tyrtyrtyrtytryr\tpiopoipiopio"
        val expectedStream = Stream.of("fsdffasf", "yrtyrtyrtytryr", "piopoipiopio")
        val result = lineToStream(testString)
        Assert.assertEquals(expectedStream.toList(), result.toList())
    }

    @Test
    fun extractDateFromStringTest() {
        val input1 = "Data Zajęć: 2019-10-04 piątek"
        val input2 = "[Zwęż]\tData Zajęć: 2019-10-06 niedziela"

        val result1 = extractDateFromString(input1)
        val result2 = extractDateFromString(input2)

        Assert.assertEquals("2019-10-04", result1)
        Assert.assertEquals("2019-10-06", result2)
    }

    @Test
    fun extractDateFromStringBadInputTest() {
        val badInput = "Data Zajęć: 209-10-04 piątek"

        val exception = assertFailsWith<IOException> {
            extractDateFromString(badInput)
        }
        Assert.assertEquals("Bad input", exception.message)
    }

    @Test
    fun separateValuesFromTextTest(){
        val inputLesson = "\t17:30\t19:00\t2h00m\tmgr Pan Nauczyciel \tPlastyka \tWyk\tgr2NS \tF Radom \tEgzamin\tBrak"
        val lessonTxtLine = LessonTxtLine(inputLesson)
        Assert.assertEquals("17:30", lessonTxtLine.startTime)
        Assert.assertEquals("19:00", lessonTxtLine.endTime)
        Assert.assertEquals("mgr Pan Nauczyciel", lessonTxtLine.lecturer)
        Assert.assertEquals("Plastyka", lessonTxtLine.lessonTitle)
        Assert.assertEquals("Wyk", lessonTxtLine.lessonType)
        Assert.assertEquals("gr2NS", lessonTxtLine.lessonCode)
        Assert.assertEquals("F Radom", lessonTxtLine.classRoom)
    }
}
