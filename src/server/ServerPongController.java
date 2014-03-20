package server;


/**
 * Pong controller
 * Not used currently.
 * But there could be a management console for the server.
 */
public class ServerPongController {
    private ServerPongModel model;
    private ServerPongView view;

    public ServerPongController(ServerPongModel aPongModel, ServerPongView aPongView) {
        model = aPongModel;
        view = aPongView;
    }

}

