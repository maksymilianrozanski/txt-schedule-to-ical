import org.junit.Assert
import org.junit.Test


class MainKotlinKtTest {

    @Test
    fun testTest() {
        Assert.assertEquals(2 + 2, 4)
    }

    @Test
    fun isDayNotLesson() {
        val dayString1 = "Data Zajęć: 2019-10-04 piątek"
        val dayString2 = "[Zwęż]\tData Zajęć: 2019-10-05 sobota"
        val lessonString =
            "8:00\t9:30\t2h00m\tmgr Some Teacher \tJęzyk angielski \tCw\tANGgr2 \tF Brazylia \tZaliczenie ocena\tBrak"

        val expectedTrue1 = isDayNotLesson(dayString1)
        Assert.assertTrue(expectedTrue1)

        val expectedTrue2 = isDayNotLesson(dayString2)
        Assert.assertTrue(expectedTrue2)

        val expectedFalse = isDayNotLesson(lessonString)
        Assert.assertFalse(expectedFalse)
    }
}
