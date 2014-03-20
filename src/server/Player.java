package server;

import java.net.Socket;

/**
 * Individual player run as a separate thread to allow
 * updates to the model when a player moves there bat
 */
public class Player extends Thread {
    private int playerNumber;
    private ServerPongModel currentModel;
    private Socket socket;

    /**
     * Constructor
     *
     * @param player Player 1 or 2
     * @param model  Model of the game
     * @param s      Socket used to communicate the players bat move
     */
    public Player(int player, ServerPongModel model, Socket s) {
        playerNumber = player;
        currentModel = model;
        socket = s;
    }


    /**
     * Get and update the model with the latest bat movement
     */
    public void run()                             // Execution
    {

    }
}
