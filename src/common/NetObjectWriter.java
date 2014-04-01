package common;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
/**
 * Wrapper for reading an object from a socket
 */
public class NetObjectWriter extends ObjectOutputStream {
    public NetObjectWriter(Socket socket) throws IOException {
        super(socket.getOutputStream());
        socket.setTcpNoDelay(true); // Send data immediately
    }
    // write object to socket returning false on error
    public synchronized boolean put(Object data) {
        try {
            writeObject(data); // Write object
            flush(); // Flush
            /* Reset stream or otherwise we keep writing the
             same object, as we're using strings,
             it will cache the string */
            reset();
            return true; // Ok
        } catch (IOException errorException) {
            DEBUG.error("NetObjectWriter.get %s",
                    errorException.getMessage());
            return false; // Failed write
        }
    }
}