package common;
import java.io.*;
import java.net.Socket;
/**
 * Wrapper to allow reading of objects from a socket
 */
public class NetObjectReader extends ObjectInputStream {
    public NetObjectReader(Socket socket) throws IOException {
        super(socket.getInputStream());
    }
    // Get object return null on 'error'
    public synchronized Object get() { // Get object from stream
        try {
            return readObject(); // Return read object
        } catch (Exception errorException) { // Reading error
            DEBUG.error("NetObjectReader.get %s",
                    errorException.getMessage());
            return null; //  On error return null
        }
    }
}