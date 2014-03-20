package server;

import java.io.IOException;
import java.net.*;

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
        ServerPongModel model = new ServerPongModel();

        makeContactWithClients(model);

        ServerPongView view = new ServerPongView(player1NetObjectWriter, player2NetObjectWriter);


        model.addObserver(view);       // Add observer to the model
        model.makeActiveObject();        // Start play
    }

    /**
     * Make contact with the clients who wish to play
     * Players will need to know about the model
     *
     * @param model Of the game
     */
    public void makeContactWithClients(ServerPongModel model) {
        ServerSocket serverSocket = null;
        Socket player1Socket = null;
        Socket player2Socket = null;

        try {
            serverSocket = new ServerSocket(Global.PORT);
            player1Socket = serverSocket.accept();
            player2Socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert (serverSocket != null);
        assert (player1Socket != null);
        assert (player2Socket != null);

        try {
            player1NetObjectWriter = new NetObjectWriter(player1Socket);
            player2NetObjectWriter = new NetObjectWriter(player2Socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (player1NetObjectWriter != null);
        assert (player2NetObjectWriter != null);


        player1 = new Player(0, model, player1Socket);
        player2 = new Player(1, model, player2Socket);

        player1.start();
        player2.start();
    }
}


