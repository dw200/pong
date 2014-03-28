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
                GameObject bat = currentModel.getBats()[playerNumber];
                String dataString = String.format("%d,%f,%f", playerNumber, bat.getGameObjectPositionX(), bat.getGameObjectPositionY());
                playerNetObjectWriter.put(dataString);
            }


            String dataString = (String)playerNetObjectReader.get();

            String[] dataArray = dataString.split(",");
            assert (dataArray.length == 7); // assume that there are 7 values
            playerNumber = Integer.parseInt(dataArray[0]); // get first element of array (player number)

            currentModel.setPlayerNumber(playerNumber);

            double bat1x = Double.parseDouble(dataArray[1]);
            double bat1y = Double.parseDouble(dataArray[2]);
            double bat2x = Double.parseDouble(dataArray[3]);
            double bat2y = Double.parseDouble(dataArray[4]);
            double ballx = Double.parseDouble(dataArray[5]);
            double bally = Double.parseDouble(dataArray[6]);


            if(playerNumber == 0){
                currentModel.getBats()[1].setGameObjectPositionX(bat2x);
                currentModel.getBats()[1].setGameObjectPositionY(bat2y);
            }else {
                currentModel.getBats()[0].setGameObjectPositionX(bat1x);
                currentModel.getBats()[0].setGameObjectPositionY(bat1y);
            }

            currentModel.getBall().setGameObjectPositionX(ballx);
            currentModel.getBall().setGameObjectPositionY(bally);

            currentModel.modelChanged();

        }
    }
}
