import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ChecksumClient(ip: String, port: Int) {
    /**
     * Client for checksum server.
     *
     * @param ip server ip
     * @param port server port
     */
    private val socket: Socket
    private val sout: DataOutputStream
    private val sin: DataInputStream

    init {
        socket = Socket(ip, port)
        sout = DataOutputStream(socket.getOutputStream())
        sin = DataInputStream(socket.getInputStream())
    }

    fun requestChecksum(bytes: ByteArray): Byte {
        /**
         * Request checksum from server
         *
         * @param bytes byte array to find checksum for
         * @return *signed*-byte checksum
         */
        sout.write(bytes)
        return sin.readBytes().first()
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