package server;
import common.*;
import java.util.Observable;
import java.util.Observer;
/**
 * Displays a graphical view of the game of pong
 */
class ServerPongView implements Observer {
    private GameObject ball;
    private GameObject[] bats;
    private NetObjectWriter client1, client2;
    public ServerPongView(NetObjectWriter client1,
                          NetObjectWriter client2) {
        this.client1 = client1;
        this.client2 = client2;
    }
    /**
     * Called from the model when its state is changed
     * @param aPongModel Model of game
     * @param arg        Arguments - not used
     */
    public void update(Observable aPongModel, Object arg) {
        ServerPongModel model = (ServerPongModel) aPongModel;
        ball = model.getBall();
        bats = model.getBats();
        /**
         * Now need to send position of game objects to the client
         * as the model on the server has changed
         * Left off first parameter so we can reuse
         */
        String dataString = String.format(",%f,%f,%f,%f,%f,%f",
                bats[0].getGameObjectPositionX(),
                bats[0].getGameObjectPositionY(),
                bats[1].getGameObjectPositionX(),
                bats[1].getGameObjectPositionY(),
                ball.getGameObjectPositionX(),
                ball.getGameObjectPositionY());
        /**
         * reusing the dataString above and appending the player
         * number to the beginning
         */
        client1.put("0" + dataString);
        client2.put("1" + dataString);
    }
}