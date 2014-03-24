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
     * @param player Player 1 or 2
     * @param model  Model of the game
     * @param s      Socket used to communicate the players bat move
     */
    public Player(int player, ServerPongModel model, Socket s) throws IOException {
        playerNumber = player;
        currentModel = model;
        socket = s;
        this.reader = new NetObjectReader(socket);
    }


    /**
     * Get and update the model with the latest bat movement
     */
    public void run()                             // Execution
    {
        while(true) {

            String updates = (String)reader.get();
           currentModel.queueBatUpdate(updates);

        }
    }
}
