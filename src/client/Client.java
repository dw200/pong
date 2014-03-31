package client;
import common.*;
import java.io.IOException;
import java.net.Socket;
/**
 * Start the client that will display the game for a player
 */
class Client {
    public static void main(String args[]) {
        (new Client()).start();
    }
    private Player currentPlayer = null;

    /**
     * Start the Client
     */
    public void start() {
        DEBUG.set(true);
        DEBUG.trace("Pong Client");
        DEBUG.set(false);
        /* pass -1 (player number) as no player yet */
        ClientPongModel model = new ClientPongModel(-1);
        ClientPongView view = new ClientPongView();
        ClientPongController cont = new ClientPongController(model,
                view);
        makeContactWithServer(model, cont);
        /* Add observer to the model */
        model.addObserver(view);
        /* Display Screen */
        view.setVisible(true);
    }

    /**
     * Make contact with the Server who controls the game
     * Players will need to know about the model
     * @param model Of the game
     * @param cont  Controller (MVC) of the Game
     */
    public void makeContactWithServer(ClientPongModel model,
                                      ClientPongController cont) {
        // Also starts the Player task that get the current state
        //  of the game from the server
        Socket serverSocket = null;

        try {
            serverSocket = new Socket(Global.host, Global.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (serverSocket != null);
        currentPlayer = new Player(model, serverSocket);
        currentPlayer.start();

    }
}
