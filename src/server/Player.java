package server;
import common.NetObjectReader;
import java.io.IOException;
import java.net.Socket;
/**
 * Individual player run as a separate thread to allow
 * updates to the model when a player moves there bat
 */
public class Player extends Thread {
    private int playerNumber;
    private ServerPongModel currentModel;
    private Socket socket;
    private NetObjectReader reader;
    /**
     * Constructor
     *
     * @param playerNumber Player 1 or 2
     * @param currentModel  Model of the game
     * @param socket      Socket used to communicate the players bat move
     */
    public Player(int playerNumber, ServerPongModel currentModel,
                  Socket socket) throws IOException {
        this.playerNumber = playerNumber;
        this.currentModel = currentModel;
        this.socket = socket;
        this.reader = new NetObjectReader(this.socket);
    }
    /**
     * Get and update the model with the latest bat movement
     */
    public void run() {
        while(true) {
            String updates = (String)reader.get();
           currentModel.queueBatUpdate(updates);
        }
    }
}