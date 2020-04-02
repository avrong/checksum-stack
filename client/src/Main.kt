import java.util.Arrays
import kotlin.random.Random

fun calculateChecksum(bytes: ByteArray): Byte {
    return (bytes.sum() % 256).toByte()
}

fun toUnsingned(byte: Byte): Int {
    return byte.toInt().and(0xFF)
}

fun randomArray(arraySize: Int): ByteArray {
    val bytes = ByteArray(20) { 1 }
    Random.nextBytes(bytes, 0)
    return bytes
}

fun main() {
    val client = ChecksumClient("127.0.0.1", 80)

    while (true) {
        val bytes = randomArray(20)
        println("Generated array: ${bytes.map { toUnsingned(it) }}")

        val checksum = toUnsingned(client.requestChecksum(bytes))
        val calculatedChecksum = toUnsingned(calculateChecksum(bytes))
        println("Checksum: $checksum (server); $calculatedChecksum (local).")

        Thread.sleep(2000)
    }

//    client.stop()
}