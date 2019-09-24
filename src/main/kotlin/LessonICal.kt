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
