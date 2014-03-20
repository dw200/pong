package server;

import common.Global;
import common.NetObjectWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Mike on 20/03/2014.
 */
public class Game extends Thread {

    private NetObjectWriter player1NetObjectWriter;
    private NetObjectWriter player2NetObjectWriter;
    private Player player1;
    private Player player2;
    ServerPongModel model;
    private ServerSocket serverSocket;

    public Game(ServerSocket socket) {
        this.serverSocket = socket;

        model = new ServerPongModel();

        // blocks until two clients are available
        makeContactWithClients(model);
    }

    public void run() {

        ServerPongView view = new ServerPongView(player1NetObjectWriter, player2NetObjectWriter);


        model.addObserver(view);       // Add observer to the model
        model.makeActiveObject();      // Start play
    }

    /**
     * Make contact with the clients who wish to play
     * Players will need to know about the model
     *
     * @param model Of the game
     */
    public void makeContactWithClients(ServerPongModel model) {
        Socket player1Socket = null;
        Socket player2Socket = null;

        try {

            player1Socket = serverSocket.accept();
            player2Socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


        try {
            player1 = new Player(0, model, player1Socket);
            player2 = new Player(1, model, player2Socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (player1 != null);
        assert (player2 != null);

        player1.start();
        player2.start();
    }
}
