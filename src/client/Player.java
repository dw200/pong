package client;
import common.*;
import java.io.IOException;
import java.net.Socket;
/**
 * Individual player run as a separate thread to allow
 * updates immediately as the bat is moved
 */
class Player extends Thread {
    private ClientPongModel currentModel;
    private Socket socket;
    /* initialise player to -1 (not set) */
    private int playerNumber = -1;
    private NetObjectReader playerNetObjectReader;
    private NetObjectWriter playerNetObjectWriter;
    /**
     * Constructor
     * @param model - model of the game
     * @param socket - Socket used to communicate with server
     */
    public Player( ClientPongModel model, Socket socket) {
        // The player needs to know this to be able to work
        currentModel = model;
        this.socket = socket;
        try {
            playerNetObjectReader = new NetObjectReader(socket);
            playerNetObjectWriter = new NetObjectWriter(socket);
        } catch (IOException errorException) {
            errorException.printStackTrace();
        }
    }
    /**
     * Get and update the model with the latest bat movement
     * sent by the server
     */
    /* Execution */
    public void run() {
        /**
         * Listen to network to get the latest state of the game from
         * the server
         * Update model with this information, Redisplay model
         */
        DEBUG.trace( "Player.run" );
        while ( true ) {
            // check to see if player has been initialised > 1
            // this means a game has started and we've been
            if ( playerNumber > -1 ) {
                GameObject bat = currentModel.getBats()[playerNumber];
                String dataString = String.format("%d,%f,%f",
                        playerNumber, bat.getGameObjectPositionX(),
                        bat.getGameObjectPositionY());
                playerNetObjectWriter.put(dataString);
            }
            /* Get the data from the server and put it in a string */
            String dataString = (String)playerNetObjectReader.get();
            /* Split the string, delimeter is a comma (',') */
            String[] dataArray = dataString.split(",");
            /* assume that there are 7 values */
            assert (dataArray.length == 7);
            /* get first element of array (player number) */
            playerNumber = Integer.parseInt(dataArray[0]);
            currentModel.setPlayerNumber(playerNumber);
            double bat1x = Double.parseDouble(dataArray[1]);
            double bat1y = Double.parseDouble(dataArray[2]);
            double bat2x = Double.parseDouble(dataArray[3]);
            double bat2y = Double.parseDouble(dataArray[4]);
            double ballx = Double.parseDouble(dataArray[5]);
            double bally = Double.parseDouble(dataArray[6]);
            /**
             * if playerNumber equals 0 then they are player 1
             * if playerNumber equals 1 then they are player 2
             * Check to see what player number we have received, if
             * playerNumber equals 0 then we need to display the other
             * player's bat
             */
            if ( playerNumber == 0 ) {
                currentModel.getBats()[1].setGameObjectPositionX(bat2x);
                currentModel.getBats()[1].setGameObjectPositionY(bat2y);
            /**
             *
             */
            } else {
                currentModel.getBats()[0].setGameObjectPositionX(bat1x);
                currentModel.getBats()[0].setGameObjectPositionY(bat1y);
            }
            /**
             * It doesn't matter what player we are, we still need to
             * display the position of the ball
             */
            currentModel.getBall().setGameObjectPositionX(ballx);
            currentModel.getBall().setGameObjectPositionY(bally);
            /* state that the current model has changed */
            currentModel.modelChanged();
        }
    }
}