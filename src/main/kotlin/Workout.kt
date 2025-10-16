sealed class WorkoutActivity {
    abstract val name: String
}

data class TimedActivity(
    override val name: String,
    val duration: Int,
    val unit: String
) : WorkoutActivity()

data class RepetitionActivity(
    override val name: String,
    val reps: Int
) : WorkoutActivity()

data class WorkoutLog(val activities: List<WorkoutActivity>)
