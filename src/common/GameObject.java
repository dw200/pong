package common;
import java.io.*;
/*
 * An Object in the game, represented as a rectangle or ellipse
 * Holds details of shape, plus possible direction of travel
 */
public class GameObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Collision {HIT, NO_HIT};
    // All the variables below are vital to the state of the object
    /* Top left corner X */
    private double gameObjectPositionX = 0.0;
    /* Top left corner Y */
    private double gameObjectPositionY = 0.0;
    /* Width of object */
    private double gameObjectWidth = 0.0;
    /* Height of object */
    private double gameObjectHeight = 0.0;
    /* Direction X (1 or -1) */
    private double directionX = 1;
    /* Direction Y (1 or -1) */
    private double directionY = 1;
    /**
     * Create an instance of a Game object
     * @param gameObjectPositionX Top left hand corner x
     * @param gameObjectPositionY Top left hand corner y
     * @param gameObjectWidth  Width of object
     * @param gameObjectHeight Height of Object
     */
    public GameObject(double gameObjectPositionX,
                      double gameObjectPositionY,
                      double gameObjectWidth, double gameObjectHeight) {
        this.gameObjectPositionX = gameObjectPositionX;
        this.gameObjectPositionY = gameObjectPositionY;
        this.gameObjectWidth = gameObjectWidth;
        this.gameObjectHeight = gameObjectHeight;
    }
    public double getGameObjectPositionX() {
        return gameObjectPositionX;
    }
    public double getGameObjectPositionY() {
        return gameObjectPositionY;
    }
    public double getGameObjectWidth() {
        return gameObjectWidth;
    }
    public double getGameObjectHeight() {
        return gameObjectHeight;
    }
    public void setGameObjectPositionX(double gameObjectPositionX) {
        this.gameObjectPositionX = gameObjectPositionX;
    }
    public void setGameObjectPositionY(double gameObjectPositionY) {
        this.gameObjectPositionY = gameObjectPositionY;
    }
    /**
     * Move object by X units
     * The actual direction moved is flipped by changeDirectionX()
     * @param unitsToMove Units to move game object by X direction
     */
    public void moveX(double unitsToMove) {
        gameObjectPositionX += unitsToMove * directionX;
    }
    /**
     * Move object by Y units
     * The actual direction moved is flipped by changeDirectionY()
     * @param unitsToMove Units to move game object by Y direction
     */
    public void moveY(double unitsToMove) {
        gameObjectPositionY += unitsToMove * directionY;
    }
    /**
     * Change direction of future moves in the X direction
     */
    public void changeDirectionX() {
        directionX = -directionX;
    }
    /**
     * Change direction of future moves in the Y direction
     */
    public void changeDirectionY() {
        directionY = -directionY;
    }
    /**
     * Detect a collision between two GameObjects
     * Would be good to know where the object is hit
     * @param with Check if object (with) collides with us
     */
    public Collision collision(GameObject with) {
        if (gameObjectPositionX >= with.getGameObjectPositionX() +
                with.getGameObjectWidth() ||
                gameObjectPositionX + gameObjectWidth <= with
                        .getGameObjectPositionX() ||
                gameObjectPositionY >= with.getGameObjectPositionY()
                        + with.getGameObjectHeight() ||
                gameObjectPositionY + gameObjectHeight <= with
                        .getGameObjectPositionY())
            return Collision.NO_HIT;
        else {
            return Collision.HIT;
        }
    }
}