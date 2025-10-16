import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val loopConfig = readConfigFromXml()

    // Use the values from our data class, which came from the XML file
    for (i in loopConfig.start..loopConfig.end) {
        println(i)
    }
}

// Function to read the XML file and return our data class
fun readConfigFromXml(): LoopConfig {
    val xmlFile = File(ClassLoader.getSystemResource("config.xml").file)
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    val doc = dBuilder.parse(xmlFile)

    // Normalize the document for consistent parsing
    doc.documentElement.normalize()

    // Read the values from the XML tags
    val startValue = doc.getElementsByTagName("start").item(0).textContent.toInt()
    val endValue = doc.getElementsByTagName("end").item(0).textContent.toInt()

    // Create and return an instance of our data class
    return LoopConfig(startValue, endValue)
}


