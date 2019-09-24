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

    @Test
    fun createLessonICalTest() {
        val inputLesson = "\t17:30\t19:00\t2h00m\tmgr Pan Nauczyciel \tPlastyka \tWyk\tgr2NS \tF Radom \tEgzamin\tBrak"
        val lessonTxtLine = LessonTxtLine(inputLesson)
        lessonTxtLine.date = "2019-10-05"

        val lessonICalCreated = createLessonICal(lessonTxtLine)
        Assert.assertEquals("2019-10-05", lessonICalCreated.date)
        Assert.assertEquals("17:30", lessonICalCreated.startTime)
        Assert.assertEquals("19:00", lessonICalCreated.endTime)
        Assert.assertEquals("mgr Pan Nauczyciel", lessonICalCreated.lecturer)
        Assert.assertEquals("Plastyka", lessonICalCreated.lessonTitle)
        Assert.assertEquals("Wyk", lessonICalCreated.lessonType)
        Assert.assertEquals("gr2NS", lessonICalCreated.lessonCode)
        Assert.assertEquals("F Radom", lessonICalCreated.classRoom)
    }
}
