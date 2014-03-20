package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import common.*;

/**
 * Start the game server
 * The call to makeActiveObject() in the model
 * starts the play of the game
 */
class Server {

    private NetObjectWriter player1NetObjectWriter, player2NetObjectWriter;
    private Player player1, player2;


    /**
     * @param args
     */
    public static void main(String args[]) {
        (new Server()).start();
    }

    /**
     * Start the server
     */
    public void start() {
        DEBUG.set(true);
        DEBUG.trace("Pong Server");
        DEBUG.set(false);               // Otherwise lots of debug info

        ServerSocket s = null;
        try {
            s = new ServerSocket(Global.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (s != null);

        ArrayList<Game> games = new ArrayList<Game>();

        while (true) {
            Game game = new Game(s);
            game.start();
            games.add(game);
        }
    }


}


