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
    private NetObjectWriter player1NetObjectWriter,
            player2NetObjectWriter;
    private Player player1, player2;
    /* ArrayList to hold the game objects */
    ArrayList<Game> games = new ArrayList<Game>();
    /**
     * @param args
     * When this method is run, call the start() method and create a
     * new Server
     */
    public static void main(String args[]) {
        (new Server()).start();
    }
    /**
     * Start the server
     */
    public void start() {
        /*
        * We could remove the comment block surrounding the DEBUG lines
        * below so to disable output to the console when the server
        * starts. However it is good to keep it in to give feedback
        * to the user who may be unsure if anything is happening as
        * it will just wait until two Clients are run */
        DEBUG.set(true);
        DEBUG.trace("Staring Pong Server...");
        /* Here we stop the debug mode otherwise we will get the
        * message 'ServerPongModel.modelChanged' printed to the console
        * every 20 milliseconds */
        DEBUG.set(false);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Global.port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
         /* Will break if serverSocket doesn't exist */
        assert (serverSocket != null);
        while (true) {
            Game game = new Game(serverSocket);
            game.start();
            /* Add game to games array */
            games.add(game);
        }
    }
}