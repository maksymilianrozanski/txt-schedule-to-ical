package io.github.maksymilian.rozanski.schedulerest.calendar

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.time.Clock
import java.util.*


class LessonICalTest {

    private val lessonICal = LessonICal(
        "2019-04-05", "17:00", "18:30", "Mr. Smith",
        "Physics", "Lab", "gr2/IT", "London", "20190715T172009Z",
        "16@1563211209865"
    )

    private val lessonICal2 = LessonICal(
        "2019-04-05", "7:00", "8:30", "Mr. Smith",
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
        val expected = 3
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
    fun getStartHourSingleDigitTest() {
        val expected = 7
        val result = lessonICal2.getStartHour()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getStartMinutesTest() {
        val expected = 0
        val result = lessonICal.getStartMinutes()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getStartMinutesTest2() {
        val expected = 0
        val result = lessonICal2.getStartMinutes()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndHourTest() {
        val expected = 18
        val result = lessonICal.getEndHour()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndHourSingleDigitTest() {
        val expected = 8
        val result = lessonICal2.getEndHour()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndMinutesTest() {
        val expected = 30
        val result = lessonICal.getEndMinutes()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getEndMinutesTest2() {
        val expected = 30
        val result = lessonICal2.getEndMinutes()
        Assert.assertEquals(expected, result)
    }

    @Test
    fun createLessonICalTest() {
        val inputLesson = "\t17:30\t19:00\t2h00m\tmgr Pan Nauczyciel \tPlastyka \tWyk\tgr2NS \tF Radom \tEgzamin\tBrak"
        val lessonTxtLine = LessonTxtLine(inputLesson)
        lessonTxtLine.date = "2019-10-05"

        val lessonICalCreated = createLessonICal(lessonTxtLine, "20190715T172009Z", "16@1563211209865")
        Assert.assertEquals("2019-10-05", lessonICalCreated.date)
        Assert.assertEquals("17:30", lessonICalCreated.startTime)
        Assert.assertEquals("19:00", lessonICalCreated.endTime)
        Assert.assertEquals("mgr Pan Nauczyciel", lessonICalCreated.lecturer)
        Assert.assertEquals("Plastyka", lessonICalCreated.lessonTitle)
        Assert.assertEquals("Wyk", lessonICalCreated.lessonType)
        Assert.assertEquals("gr2NS", lessonICalCreated.lessonCode)
        Assert.assertEquals("F Radom", lessonICalCreated.classRoom)
        Assert.assertEquals("20190715T172009Z", lessonICalCreated.dtStamp)
        Assert.assertEquals("16@1563211209865", lessonICalCreated.uid)
    }

    @Test
    fun generateDateStampTest() {
        val calendar = Mockito.mock(GregorianCalendar::class.java)
        Mockito.`when`(calendar.get(Calendar.YEAR)).thenReturn(2019)
        Mockito.`when`(calendar.get(Calendar.MONTH)).thenReturn(3)  //April (starts from 0)
        Mockito.`when`(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(2)
        Mockito.`when`(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(4)
        Mockito.`when`(calendar.get(Calendar.MINUTE)).thenReturn(8)
        Mockito.`when`(calendar.get(Calendar.SECOND)).thenReturn(2)

        val dateStamp = dateStamp(calendar)
        Assert.assertEquals("20190402T040802Z", dateStamp)
    }

    @Test
    fun generateDateStampTest2() {
        val calendar = Mockito.mock(GregorianCalendar::class.java)
        Mockito.`when`(calendar.get(Calendar.YEAR)).thenReturn(2019)
        Mockito.`when`(calendar.get(Calendar.MONTH)).thenReturn(10)  //November (starts from 0)
        Mockito.`when`(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(11)
        Mockito.`when`(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(12)
        Mockito.`when`(calendar.get(Calendar.MINUTE)).thenReturn(13)
        Mockito.`when`(calendar.get(Calendar.SECOND)).thenReturn(14)

        val dateStamp = dateStamp(calendar)
        Assert.assertEquals("20191111T121314Z", dateStamp)
    }

    @Test
    fun uniqueUidTimeTest() {
        val clockMock = Mockito.mock(Clock::class.java)
        val mockedTime = 10L
        Mockito.`when`(clockMock.millis()).thenReturn(mockedTime)

        lastUidTime = 8L
        val firstObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(10L, firstObtainedTime)

        val secondObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(11L, secondObtainedTime)
    }

    @Test
    fun uniqueUidTimeTest2() {
        val clockMock = Mockito.mock(Clock::class.java)
        Mockito.`when`(clockMock.millis()).thenReturn(10L)

        lastUidTime = 8L
        val firstObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(10L, firstObtainedTime)

        Mockito.`when`(clockMock.millis()).thenReturn(11L)

        val secondObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(11L, secondObtainedTime)
    }

    @Test
    fun uniqueUidTimeTest3() {
        val clockMock = Mockito.mock(Clock::class.java)
        Mockito.`when`(clockMock.millis()).thenReturn(10L)

        lastUidTime = 8L
        val firstObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(10L, firstObtainedTime)

        Mockito.`when`(clockMock.millis()).thenReturn(12L)

        val secondObtainedTime = uniqueUidTime(clockMock)
        Assert.assertEquals(12L, secondObtainedTime)
    }

    @Test
    fun addStartTimeToCalendarTest() {
        val result = GregorianCalendar().setHourAndMin(lessonICal, true)
        Assert.assertEquals(17, result.get(Calendar.HOUR_OF_DAY))
        Assert.assertEquals(0, result.get(Calendar.MINUTE))
        Assert.assertEquals(0, result.get(Calendar.SECOND))
    }

    @Test
    fun addEndTimeToCalendarTest() {
        val result = GregorianCalendar().setHourAndMin(lessonICal, false)
        Assert.assertEquals(18, result.get(Calendar.HOUR_OF_DAY))
        Assert.assertEquals(30, result.get(Calendar.MINUTE))
        Assert.assertEquals(0, result.get(Calendar.SECOND))
    }
}
