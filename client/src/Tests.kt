import org.junit.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun `calculating checksum locally`() {
        assertEquals(calculateChecksum(byteArrayOf(1, 2, 3, 4, 5)), 15)
        assertEquals(calculateChecksum(byteArrayOf(127, 126, 125)), 122)
        assertEquals(calculateChecksum(byteArrayOf(-10, -20, -30, -40, -50, -60, -70, -80)), -104)
        assertEquals(calculateChecksum(byteArrayOf()), 0)
    }

    @Test
    fun `converting to unsigned`() {
        assertEquals(toUnsingned(0), 0)
        assertEquals(toUnsingned(-128), 128)
        assertEquals(toUnsingned(127), 127)
        assertEquals(toUnsingned(-46), 210)
        assertEquals(toUnsingned(-88), 168)
        assertEquals(toUnsingned(-127), 129)
    }

    @Test
    fun `generating random array`() {
        assertEquals(randomArray(5).size, 5)
        assertEquals(randomArray(10).size, 10)
    }

    @Test
    fun `connect and verify checksum`() {
        val client = ChecksumClient("127.0.0.1", 80)
        val array = randomArray(20)
        assertEquals(client.requestChecksum(array), calculateChecksum(array))
    }
}