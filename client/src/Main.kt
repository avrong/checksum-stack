import java.util.Arrays
import kotlin.random.Random

fun toUnsingned(byte: Byte): Int {
    return byte.toInt().and(0xFF)
}

fun calculateChecksum(bytes: ByteArray): Byte {
    return (bytes.sum() % 256).toByte()
}

fun main() {
    val client = ChecksumClient("127.0.0.1", 80)

    val bytes = ByteArray(20) { 1 }
    Random.nextBytes(bytes, 0)

    println(Arrays.toString(bytes))

    val checksum = toUnsingned(client.requestChecksum(bytes))
    val calculatedChecksum = toUnsingned(calculateChecksum(bytes))

    println("$checksum $calculatedChecksum")

    client.stop()
}