package server;
import java.io.IOException;
import java.net.*;

import common.*;

/**
 * Start the game server
 *  The call to makeActiveObject() in the model
 *   starts the play of the game
 */
class Server
{
  private NetObjectWriter player1, player2;

  public static void main( String args[] )
  {
   ( new Server() ).start();
  }

  /**
   * Start the server
   */
  public void start()
  {
    DEBUG.set( true );
    DEBUG.trace("Pong Server");
    DEBUG.set( false );               // Otherwise lots of debug info
    ServerPongModel model = new ServerPongModel();

    makeContactWithClients( model );

    ServerPongView view  = new ServerPongView(player1, player2);
                        new ServerPongController( model, view );

    model.addObserver( view );       // Add observer to the model
    model.makeActiveObject();        // Start play
  }

  /**
   * Make contact with the clients who wish to play
   * Players will need to know about the model
   * @param model  Of the game
   */
  public void makeContactWithClients( ServerPongModel model )
  {
      try {
          ServerSocket serverSocket = new ServerSocket(Global.PORT);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}


