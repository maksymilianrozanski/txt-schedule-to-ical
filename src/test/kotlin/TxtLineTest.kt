
import org.junit.Assert
import org.junit.Test
import java.util.stream.Stream
import kotlin.streams.toList


class TxtLineTest {

    @Test
    fun stringToStreamTests() {
        val testString = "fsdffasf\tyrtyrtyrtytryr\tpiopoipiopio"
        val expectedStream = Stream.of("fsdffasf", "yrtyrtyrtytryr", "piopoipiopio")
        val result = stringToStream(testString)
        Assert.assertEquals(expectedStream.toList(), result.toList())
    }
}
