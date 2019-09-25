
import io.github.maksymilian.rozanski.schedulerest.calendar.LessonICal
import io.github.maksymilian.rozanski.schedulerest.calendar.SingleDay
import io.github.maksymilian.rozanski.schedulerest.calendar.createDaysList
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.time.Clock
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream

class SingleCalendarKtTest {

    val calendarMock = Mockito.mock(GregorianCalendar::class.java)
    val clockMock = Mockito.mock(Clock::class.java)

    @Test
    fun createDaysListTest() {
        val numOfItems = AtomicInteger()
        val stream = Stream.of(
            "Data Zajęć: 2019-10-04 piątek",
            " \t17:30\t19:00\t2h00m\tMr. Brown \tPhysics \tWyk\tgr1Civ \tF Piła \tExam\tBrak\n",
            " \t19:15\t20:45\t2h00m\tMr. Blue \tBiology and Geography \tWyk\tgr2Civ \tF Radom \tExam\tBrak\n",
            "[Zwęż]\tData Zajęć: 2019-10-05 sobota\n",
            " \t8:00\t9:30\t2h00m\tMr. Smith \tLatin lesson \tCw\tgr17IT \tF Gdańsk \tZaliczenie ocena\tBrak\n",
            " \t11:20\t14:30\t4h00m\tMrs. Smiths \tHistory \tWyk\tgr17IT \tF Gdynia \tEgzamin\tBrak\n",
            "[Zwęż]\tData Zajęć: 2019-10-06 niedziela\n",
            " \t8:00\t9:30\t2h00m\tMr. Black \tCivil Engineering \tLab\tlab2gr3 \tF Szczecin \tZaliczenie ocena\tBrak\n",
            " \t9:40\t11:10\t2h00m\tMr. Yellow \tIT \tLab\tlab2gr3 \tF Katowice \tZaliczenie ocena\tBrak\n",
            " \t16:20\t17:50\t2h00m\tMr. Purple \tMath \tLab\tlab2gr3 \tF Bytom \tZaliczenie ocena\tBrak\n",
            " \t18:00\t19:30\t2h00m\tMr. Orange \tSwimming \tLab\tgr4phys \tF Zakopane \tZaliczenie ocena\tBrak\n",
            " \t19:40\t21:10\t2h00m\tMr. White \tChemistry \tKonw\tchem2 \tF Lódz \tZaliczenie ocena\tBrak\n",
            "[Zwęż]\tData Zajęć: 2020-02-07 piątek\n",
            " \t17:30\t19:00\t2h00m\tMr. Brown \tNeurobiology \tKonw\tbiolgr3 \tF Rzeszów \tZaliczenie ocena\tBrak\n",
            "[Zwęż]\tData Zajęć: 2020-02-08 sobota\n",
            " \t8:00\t9:30\t2h00m\tMrs. Brown \tSinging \tCw\tbio5gr \tF Lublin \tZaliczenie ocena\tBrak\n"
        ).map {
            numOfItems.addAndGet(1)
            it
        }

        val daysList = createDaysList(stream)
        Assert.assertEquals(
            numOfItems.get(),
            daysList.map
            { it.dayValues.size }.sum()
        )
        Assert.assertEquals(3, daysList[0].dayValues.size)
        Assert.assertEquals(3, daysList[1].dayValues.size)
        Assert.assertEquals(6, daysList[2].dayValues.size)
        Assert.assertEquals(2, daysList[3].dayValues.size)
        Assert.assertEquals(2, daysList[4].dayValues.size)
    }

    @Test
    fun lessonsOneDayTest() {
        val day = SingleDay(
            dateLine = "Data Zajęć: 2019-10-04 piątek", lessons = listOf(
                " \t8:00\t9:30\t2h00m\tMr. Smith \tLatin lesson \tCw\tgr17IT \tF Gdańsk \tZaliczenie ocena\tBrak\n",
                " \t11:20\t14:30\t4h00m\tMrs. Smiths \tHistory \tWyk\tgr17IT \tF Gdynia \tEgzamin\tBrak\n"
            )
        )

        val lessonsICal = day.lessonsOneDay("20190715T172009Z", "16@1563211209867")
        Assert.assertEquals(2, lessonsICal.size)
        val lessonExpected0 = LessonICal(
            date = "2019-10-04",
            startTime = "8:00",
            endTime = "9:30",
            lecturer = "Mr. Smith",
            lessonTitle = "Latin lesson",
            lessonType = "Cw",
            lessonCode = "gr17IT",
            classRoom = "F Gdańsk",
            dtStamp = "20190715T172009Z",
            uid = "16@1563211209867"
        )
        Assert.assertEquals(lessonExpected0, lessonsICal[0])
        val lessonExpected1 = LessonICal(
            date = "2019-10-04",
            startTime = "11:20",
            endTime = "14:30",
            lecturer = "Mrs. Smiths",
            lessonTitle = "History",
            lessonType = "Wyk",
            lessonCode = "gr17IT",
            classRoom = "F Gdynia",
            dtStamp = "20190715T172009Z",
            uid = "16@1563211209867"
        )
        Assert.assertEquals(lessonExpected1, lessonsICal[1])
    }
}
