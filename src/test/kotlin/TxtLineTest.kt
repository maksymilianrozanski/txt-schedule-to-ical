import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.util.stream.Stream
import kotlin.streams.toList
import kotlin.test.assertFailsWith


class TxtLineTest {

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
}
