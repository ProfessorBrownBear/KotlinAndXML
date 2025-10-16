import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.util.Scanner
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main() {
    val scanner = Scanner(System.`in`)
    // val file = File(ClassLoader.getSystemResource("workout-log.xml").file)

    val file = File("src/main/resources/workout-log.xml")

    val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val doc = docBuilder.parse(file)
    val root = doc.documentElement

    println("🏋️ Add a new workout activity")
    print("Type (e.g. cardio, strength, kata): ")
    val type = scanner.nextLine()

    print("Name (e.g. Running, Push-ups, Kata 1): ")
    val name = scanner.nextLine()

    print("Reps (leave blank if timed): ")
    val reps = scanner.nextLine()

    print("Duration (leave blank if reps used): ")
    val duration = scanner.nextLine()

    print("Unit (minutes, seconds): ")
    val unit = scanner.nextLine()

    // Create new activity node
    val activity = doc.createElement("activity")
    activity.setAttribute("type", type)
    activity.setAttribute("name", name)
    if (reps.isNotBlank()) activity.setAttribute("reps", reps)
    if (duration.isNotBlank()) activity.setAttribute("duration", duration)
    if (unit.isNotBlank()) activity.setAttribute("unit", unit)

    root.appendChild(activity)

    // Save updated document
    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.transform(DOMSource(doc), StreamResult(file))

    println("✅ Added and saved to workout-log.xml")
}
