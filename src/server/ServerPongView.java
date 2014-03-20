package server;

import common.*;

import java.util.Observable;
import java.util.Observer;


/**
 * Displays a graphical view of the game of pong
 */
class ServerPongView implements Observer
{
  private ServerPongController pongController;
  private GameObject   ball;
  private GameObject[] bats;
  private NetObjectWriter left, right;

  public ServerPongView(NetObjectWriter c1, NetObjectWriter c2)
  {
    left = c1; right = c2;
  }

  /**
   * Called from the model when its state is changed
   * @param aPongModel Model of game
   * @param arg Arguments - not used
   */
  public void update( Observable aPongModel, Object arg )
  {
    ServerPongModel model = (ServerPongModel) aPongModel;
    ball  = model.getBall();
    bats  = model.getBats();
    // Now need to send position of game objects to the client
    //  as the model on the server has changed
  }


}
