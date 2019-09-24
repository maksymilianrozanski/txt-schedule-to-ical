import org.junit.Assert
import org.junit.Test


class LessonICalTest {

    private val lessonICal = LessonICal(
        "2019-04-05", "17:00", "18:30", "Mr. Smith",
        "Physics", "Lab", "gr2/IT", "London", "20190715T172009Z",
        "16@1563211209865"
    )

    @Test
    fun getYearTest() {
        val expected = 2019
        val result = lessonICal.getYear()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getMonthTest() {
        val expected = 4
        val result = lessonICal.getMonth()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getDayTest() {
        val expected = 5
        val result = lessonICal.getDay()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getStartHourTest() {
        val expected = 17
        val result = lessonICal.getStartHour()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getStartMinutesTest() {
        val expected = 0
        val result = lessonICal.getStartMinutes()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndHourTest() {
        val expected = 18
        val result = lessonICal.getEndHour()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndMinutesTest() {
        val expected = 30
        val result = lessonICal.getEndMinutes()
        Assert.assertEquals(expected, result)
    }
}
