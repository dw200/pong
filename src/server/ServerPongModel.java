package server;

import common.*;

import static common.Global.*;

import java.util.Observable;

/**
 * Model of the game of pong
 * The active object ActiveModel does the work of moving the ball
 */
public class ServerPongModel extends Observable {
    private GameObject ball = new GameObject(windowWidth / 2, windowHeight / 2, ballSize, ballSize);
    private GameObject bats[] = new GameObject[2];

    private Thread activeModel;

    public ServerPongModel() {
        bats[0] = new GameObject(60, windowHeight / 2, batWidth, batHeight);
        bats[1] = new GameObject(windowWidth - 60, windowHeight / 2, batWidth, batHeight);
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
     *
     * @return the ball
     */
    public GameObject getBall() {
        return ball;
    }

    /**
     * Set a new Ball object
     *
     * @param aBall - Ball to be set
     */
    public void setBall(GameObject aBall) {
        ball = aBall;
    }

    /**
     * Return the Game object representing the Bat for player
     *
     * @param player 0 or 1
     */
    public GameObject getBat(int player) {
        return bats[player];
    }

    /**
     * Return the Game object representing the Bats
     *
     * @return Array of two bats
     */
    public GameObject[] getBats() {
        return bats;
    }

    /**
     * Set the Bat for a player
     *
     * @param player 0 or 1
     * @param theBat Players Bat
     */
    public void setBat(int player, GameObject theBat) {
        bats[player] = theBat;
    }

    /**
     * Cause update of view of game
     */
    public void modelChanged() {
        DEBUG.trace("ServerPongModel.modelChanged");
        setChanged();
        notifyObservers();
    }

}
