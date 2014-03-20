package client;

import common.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Individual player run as a separate thread to allow
 * updates immediately the bat is moved
 */
class Player extends Thread {

    private ClientPongModel currentModel;
    private Socket socket;
    private int playerNumber = -1;
    private NetObjectReader playerNetObjectReader;
    private NetObjectWriter playerNetObjectWriter;

    /**
     * Constructor
     *
     * @param model - model of the game
     * @param s     - Socket used to communicate with server
     */
    public Player(ClientPongModel model, Socket s) {
        // The player needs to know this to be able to work
        currentModel = model;
        socket = s;

        try {
            playerNetObjectReader = new NetObjectReader(s);
            playerNetObjectWriter = new NetObjectWriter(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Get and update the model with the latest bat movement
     * sent by the server
     */
    public void run()                             // Execution
    {
        // Listen to network to get the latest state of the
        //  game from the server
        // Update model with this information, Redisplay model
        DEBUG.trace("Player.run");

        while(true) {
            // check to see if player has been initialised > 1
            // this means a game has started and we've been
            if (playerNumber > -1) {

            }

            String dataString = (String)playerNetObjectReader.get();
            String[] dataArray = dataString.split(",");
            assert (dataArray.length == 7); // assume that there are 7 values
            playerNumber = Integer.parseInt(dataArray[0]); // get first element of array (player number)
            int bat1x = Integer.parseInt(dataArray[1]);
            int bat1y = Integer.parseInt(dataArray[2]);
            int bat2x = Integer.parseInt(dataArray[3]);
            int bat2y = Integer.parseInt(dataArray[4]);
            int ballx = Integer.parseInt(dataArray[5]);
            int bally = Integer.parseInt(dataArray[6]);

        }
    }
}
