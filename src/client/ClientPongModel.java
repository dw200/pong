package client;

import java.util.Observable;

import common.GameObject;

import static common.Global.*;

/**
 * Model of the game of pong (Client)
 */
public class ClientPongModel extends Observable {
    private GameObject ball = new GameObject(windowWidth / 2, windowHeight / 2, ballSize, ballSize);
    private GameObject bats[] = new GameObject[2];
    private int playerNumber = -1;


    public ClientPongModel(int playerNumber) {
        this.playerNumber = playerNumber;
        bats[0] = new GameObject(60, windowHeight / 2, batWidth, batHeight);
        bats[1] = new GameObject(windowWidth - 60, windowHeight / 2, batWidth, batHeight);
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
     * Return the Game object representing the Bats for player
     *
     * @return Array of two bats
     */
    public GameObject[] getBats() {
        return bats;
    }

    /**
     * Set the Bats used
     *
     * @param theBats - Players Bat
     */
    public void setBats(GameObject[] theBats) {
        bats = theBats;
    }

    /**
     * Cause update of view of game
     */
    public void modelChanged() {
        setChanged();
        notifyObservers();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int newPlayerNumber) {
        playerNumber = newPlayerNumber;
    }
}
