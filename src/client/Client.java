package client;

import common.*;

/**
 * Start the client that will display the game for a player
 */
class Client {
    public static void main(String args[]) {
        (new Client()).start();
    }

    /**
     * Start the Client
     */
    public void start() {
        DEBUG.set(true);
        DEBUG.trace("Pong Client");
        DEBUG.set(false);
        ClientPongModel model = new ClientPongModel();
        ClientPongView view = new ClientPongView();
        ClientPongController cont = new ClientPongController(model, view);

        makeContactWithServer(model, cont);

        model.addObserver(view);       // Add observer to the model
        view.setVisible(true);           // Display Screen
    }

    /**
     * Make contact with the Server who controls the game
     * Players will need to know about the model
     *
     * @param model Of the game
     * @param cont  Controller (MVC) of the Game
     */
    public void makeContactWithServer(ClientPongModel model,
                                      ClientPongController cont) {
        // Also starts the Player task that get the current state
        //  of the game from the server
    }
}
