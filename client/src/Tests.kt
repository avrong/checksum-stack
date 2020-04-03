import org.junit.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun `calculating checksum locally`() {
        assertEquals(15, calculateChecksum(byteArrayOf(1, 2, 3, 4, 5)))
        assertEquals(122, calculateChecksum(byteArrayOf(127, 126, 125)))
        assertEquals(-104, calculateChecksum(byteArrayOf(-10, -20, -30, -40, -50, -60, -70, -80)))
        assertEquals(0, calculateChecksum(byteArrayOf()))
    }

    @Test
    fun `converting to unsigned`() {
        assertEquals(0, toUnsingned(0))
        assertEquals(128, toUnsingned(-128))
        assertEquals(127, toUnsingned(127))
        assertEquals(210, toUnsingned(-46))
        assertEquals(168, toUnsingned(-88))
        assertEquals(129, toUnsingned(-127))
    }

    @Test
    fun `generating random array`() {
        assertEquals(5, randomArray(5).size)
        assertEquals(10, randomArray(10).size)
    }

    @Test
    fun `connect and verify checksum`() {
        val client = ChecksumClient("127.0.0.1", 80)
        val array = randomArray(20)
        assertEquals(calculateChecksum(array), client.requestChecksum(array))
    }
}