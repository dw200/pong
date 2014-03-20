package client;

import java.util.Observable;
import common.GameObject;
import static common.Global.*;

/**
 * Model of the game of pong (Client)
 */
public class ClientPongModel extends Observable
{
  private GameObject ball   = new GameObject( width /2, height /2, ball_size, ball_size);
  private GameObject bats[] = new GameObject[2];


  public ClientPongModel()
  {
    bats[0] = new GameObject(  60, height /2, BAT_WIDTH, BAT_HEIGHT);
    bats[1] = new GameObject(width -60, height /2, BAT_WIDTH, BAT_HEIGHT);
  }

  /**
   * Return the Game object representing the ball
   * @return the ball
   */
  public GameObject getBall()
  {
    return ball;
  }

  /**
   * Set a new Ball object
   * @param aBall - Ball to be set
   */
  public void setBall( GameObject aBall )
  {
    ball = aBall;
  }

  /**
   * Return the Game object representing the Bats for player
   * @return Array of two bats
   */
  public GameObject[] getBats()
  {
    return bats;
  }

  /**
   * Set the Bats used
   * @param theBats - Players Bat
   */
  public void setBats( GameObject[] theBats )
  {
    bats = theBats;
  }

  /**
   * Cause update of view of game
   */
  public void modelChanged()
  {
    setChanged(); notifyObservers();
  }

}
