package server;
import common.*;
import static common.Global.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * Model of the game of pong
 * The active object ActiveModel does the work of moving the ball
 */
public class ServerPongModel extends Observable {
    /* Place a ball in the middle of the screen. */
    private GameObject ball = new GameObject(windowWidth / 2,
            windowHeight / 2, ballSize, ballSize);
    /* Create array of bats. */
    private GameObject bats[] = new GameObject[2];
    /** Create the update queue. This will hold all of the movements
     * of the bats that are to be processed. As each one has to go
     * through this queue it doesn't lock. It will block any updates
     * when being mutated that 256 movements can be passed to it
     * before it is full. As the server processes the movements much
     * quicker than this we don't have to worry. */
    private volatile ArrayBlockingQueue<String> updateQueue = new
            ArrayBlockingQueue<String>(256);
    private Thread activeModel;
    /* Create the server pong model and position the bats accordingly
     on the screen. */
    public ServerPongModel() {
        bats[0] = new GameObject(60, windowHeight / 2, batWidth,
                batHeight);
        bats[1] = new GameObject(windowWidth - 60, windowHeight / 2,
                batWidth, batHeight);
        activeModel = new Thread(new ServerActiveModel(this));
    }
    /**
     * Start the thread that moves the ball and detects collisions
     */
    public void makeActiveObject() {
        activeModel.start();
    }
    /**
     * Return the Game object representing the ball
     * @return the ball
     */
    public GameObject getBall() {
        return ball;
    }
    /**
     * Return the Game object representing the Bat for player
     * @param player 0 or 1
     */
    public GameObject getBat(int player) {
        return bats[player];
    }
    /**
     * Return the Game object representing the Bats
     * @return Array of two bats
     */
    public GameObject[] getBats() {
        return bats;
    }
    /**
     * Cause update of view of game
     */
    public void modelChanged() {
        DEBUG.trace("ServerPongModel.modelChanged");
        setChanged();
        notifyObservers();
    }
    public void queueBatUpdate(String updateString) {
        updateQueue.offer(updateString);
    }
    public void processUpdates() {
        ArrayList<String> updates = new ArrayList<String>();
        updateQueue.drainTo(updates);
        for (String update:updates) {
            // split the string by the delimiter ',' and put into array
            String[] data = update.split(",");
            assert(data.length != 3);
            int playerNumber = Integer.parseInt(data[0]);
            double batPositionX = Double.parseDouble(data[1]);
            double batPositionY = Double.parseDouble(data[2]);
            getBat(playerNumber).setGameObjectPositionX(batPositionX);
            getBat(playerNumber).setGameObjectPositionY(batPositionY);
        }
    }
}