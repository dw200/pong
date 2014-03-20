package server;

import java.net.Socket;

/**
 * Individual player run as a separate thread to allow
 * updates to the model when a player moves there bat
 */
public class Player extends Thread
{
    /**
     * Constructor
     * @param player Player 0 or 1
     * @param model Model of the game
     * @param s Socket used to communicate the players bat move
     */
    public Player( int player, ServerPongModel model, Socket s  )
    {
    }


    /**
     * Get and update the model with the latest bat movement
     */
    public void run()                             // Execution
    {
    }
}
