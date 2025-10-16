import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

import WorkoutLog
import TimedActivity
import RepetitionActivity


fun main() {
    val workoutLog = readWorkoutLogFromXml("workout-log.xml")

    println("🏋️ Lake Huron Workout Summary:")
    for (activity in workoutLog.activities) {
        when (activity) {
            is TimedActivity -> println("• ${activity.name} for ${activity.duration} ${activity.unit}")
            is RepetitionActivity -> println("• ${activity.name} for ${activity.reps} reps")
        }
    }
    /* update the log file:
    “See how we just piped structured semantic data into a CSV file?
    That’s the power of using XML as the backbone of your program’s behavior.
    Data drives the experience — and we can manipulate that data without rewriting a single line of code logic. */

    val csvFile = File("workout-log.csv")
    csvFile.printWriter().use { out ->
        out.println("type,name,reps,duration,unit")
        for (activity in workoutLog.activities) {
            when (activity) {
                is TimedActivity -> out.println("timed,${activity.name},,${activity.duration},${activity.unit}")
                is RepetitionActivity -> out.println("reps,${activity.name},${activity.reps},,")
            }
        }
    }

    println("📄 Exported to workout-log.csv")
}


fun readWorkoutLogFromXml(filename: String): WorkoutLog {
    val file = File(ClassLoader.getSystemResource(filename).file)
    val doc = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(file)

    doc.documentElement.normalize()

    val activityNodes = doc.getElementsByTagName("activity")
    val activities = mutableListOf<WorkoutActivity>()

    for (i in 0 until activityNodes.length) {
        val node = activityNodes.item(i)
        val attrs = node.attributes

        val name = attrs.getNamedItem("name").nodeValue
        val type = attrs.getNamedItem("type").nodeValue

        if (attrs.getNamedItem("duration") != null) {
            val duration = attrs.getNamedItem("duration").nodeValue.toInt()
            val unit = attrs.getNamedItem("unit").nodeValue
            activities.add(TimedActivity(name, duration, unit))
        } else if (attrs.getNamedItem("reps") != null) {
            val reps = attrs.getNamedItem("reps").nodeValue.toInt()
            activities.add(RepetitionActivity(name, reps))
        }
    }

    return WorkoutLog(activities)
}

