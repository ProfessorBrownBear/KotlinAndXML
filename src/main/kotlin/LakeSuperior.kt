import Routine
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val morningRoutine = readRoutineFromXml()

    println("🤖 Starting Robot Butler Morning Routine:")
    for (task in morningRoutine.tasks) {
        println("• Executing: $task")
    }
}

// Reads the XML and returns a data class holding the routine
fun readRoutineFromXml(): Routine {
    val xmlFile = File(ClassLoader.getSystemResource("routine.xml").file)

    val doc = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(xmlFile)

    doc.documentElement.normalize()

    val taskNodes = doc.getElementsByTagName("task")
    val tasks = mutableListOf<String>()

    for (i in 0 until taskNodes.length) {
        val task = taskNodes.item(i).textContent.trim()
        tasks.add(task)
    }

    return Routine(tasks)
}
