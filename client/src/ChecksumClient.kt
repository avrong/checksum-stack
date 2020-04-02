import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.net.SocketException

class ChecksumClient(val ip: String, val port: Int) {
    /**
     * Client for checksum server.
     *
     * @param ip server ip
     * @param port server port
     */
    lateinit private var socket: Socket
    private val sout: DataOutputStream
    private val sin: DataInputStream
    val waiting = 2000L

    init {
        establishConnection()

        sout = DataOutputStream(socket.getOutputStream())
        sin = DataInputStream(socket.getInputStream())
    }

    private fun establishConnection() {
        println()

        while (true) {
            try {
                print("Trying to connect to $ip:$port")
                socket = Socket(ip, port)
            } catch (e: SocketException) {
                println(": Failed")
                continue
            }

            println(": Success")
            println()

            break
        }
    }

    fun requestChecksum(bytes: ByteArray): Byte {
        /**
         * Request checksum from server
         *
         * @param bytes byte array to find checksum for
         * @return *signed*-byte checksum
         */

        var result: Byte
        while (true) {
            try {
                sout.write(bytes)
                result = sin.readByte()
            } catch (e: SocketException) {
                println(e.message)
                establishConnection()
                Thread.sleep(waiting)
                continue
            }
            break
        }

        return result
    }

    fun stop() {
        /**
         * Disconnect from server
         */
        sin.close()
        sout.close()
        socket.close()
    }
}