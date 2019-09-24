data class LessonICal(
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
}
