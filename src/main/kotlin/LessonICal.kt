import java.util.*

class LessonICal(
    val date: String,
    val startTime: String,
    val endTime: String,
    val lecturer: String,
    val lessonTitle: String,
    val lessonType: String,
    val lessonCode: String,
    val classRoom: String,
    val dtStamp: String,
    val uid: String
) {
    fun getYear(): Int {
        return date.substring(0, 4).toInt()
    }

    fun getMonth(): Int {
        return date.substring(5, 7).toInt()
    }

    fun getDay(): Int {
        return date.substring(8, 10).toInt()
    }

    fun getStartHour(): Int {
        return startTime.substring(0, 2).toInt()
    }

    fun getStartMinutes(): Int {
        return startTime.substring(3, 5).toInt()
    }

    fun getEndHour(): Int {
        return endTime.substring(0, 2).toInt()
    }

    fun getEndMinutes(): Int {
        return endTime.substring(3, 5).toInt()
    }
}

var createLessonICal: (LessonTxtLine) -> LessonICal = {
    //TODO: create real data stamp and uid
    LessonICal(
        date = it.date,
        startTime = it.startTime,
        endTime = it.endTime,
        lecturer = it.lecturer,
        lessonTitle = it.lessonTitle,
        classRoom = it.classRoom,
        lessonCode = it.lessonCode,
        lessonType = it.lessonType,
        dtStamp = "20190715T172009Z",
        uid = "16@1563211209865"
    )
}

var dateStamp: (GregorianCalendar) -> String = {
    val year = it.get(Calendar.YEAR).toString()
    var month = (it.get(Calendar.MONTH) + 1).toString()
    if (month.length < 2) month = "0$month"
    var day = it.get(Calendar.DAY_OF_MONTH).toString()
    if (day.length < 2) day = "0$day"
    var hour = it.get(Calendar.HOUR_OF_DAY).toString()
    if (hour.length < 2) hour = "0$hour"
    var minutes = it.get(Calendar.MINUTE).toString()
    if (minutes.length < 2) minutes = "0$minutes"
    var seconds = it.get(Calendar.SECOND).toString()
    if (seconds.length < 2) seconds = "0$seconds"
    year + month + day + "T" + hour + minutes + seconds + "Z"
}
