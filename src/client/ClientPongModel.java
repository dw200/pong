package client;
import java.util.Observable;
import common.GameObject;
import static common.Global.*;
/**
 * Model of the game of pong (Client)
 */
public class ClientPongModel extends Observable {
    /* Create the ball and place in the middle of the screen */
    private GameObject ball = new GameObject(windowWidth / 2,
            windowHeight / 2, ballSize, ballSize);
    /* Create two 'empty' bats */
    private GameObject bats[] = new GameObject[2];
    /* Set the player number to be -1 (not set) */
    private int playerNumber = -1;
    /* Create a new model and create a player */
    public ClientPongModel(int playerNumber) {
        this.playerNumber = playerNumber;
        bats[0] = new GameObject(60, windowHeight / 2, batWidth,
                batHeight);
        bats[1] = new GameObject(windowWidth - 60, windowHeight / 2,
                batWidth, batHeight);
    }
    /**
     * Return the Game object representing the ball
     * @return the ball
     */
    public GameObject getBall() {
        return ball;
    }
    /**
     * Return the Game object representing the Bats for player
     * @return Array of two bats
     */
    public GameObject[] getBats() {
        return bats;
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
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}