import org.junit.Assert
import org.junit.Test


class LessonICalTest {

    private val lessonICal = LessonICal(
        "2019-04-05", "17:00", "18:00", "Mr. Smith",
        "Physics", "Lab", "gr2/IT", "London", "20190715T172009Z",
        "16@1563211209865"
    )

    @Test
    fun getYearTest() {
        val expected = 2019
        val result = lessonICal.getYear()
        Assert.assertEquals(expected, result)
    }
}
