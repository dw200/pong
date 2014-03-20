package client;

import common.GameObject;
import common.Global;

import java.awt.event.KeyEvent;

/**
 * Pong controller, handles user interactions
 */
public class ClientPongController {
    private final ClientPongModel model;
    private ClientPongView view;

    /**
     * Constructor
     *
     * @param aPongModel Model of game on client
     * @param aPongView  View of game on client
     */
    public ClientPongController(ClientPongModel aPongModel, ClientPongView aPongView) {
        model = aPongModel;
        view = aPongView;
        view.setPongController(this);  // View talks to controller
    }

    /**
     * Decide what to do for each key pressed
     *
     * @param keyCode The keycode of the key pressed
     */
    public void userKeyInteraction(int keyCode) {

        if (model.getPlayerNumber() <= -1) {
            return;
        }

        // Key typed includes specials, -ve
        // Char is ASCII value

        synchronized (model) {
            GameObject bat = model.getBats()[model.getPlayerNumber()];

            switch (keyCode)              // Character is
            {
                case -KeyEvent.VK_LEFT:        // Left Arrow
                    bat.setX(bat.getX() - Global.batMove);
                    break;
                case -KeyEvent.VK_RIGHT:       // Right arrow
                    bat.setX(bat.getX() + Global.batMove);
                    break;
                case -KeyEvent.VK_UP:          // Up arrow
                    bat.setY(bat.getY() - Global.batMove);
                    break;
                case -KeyEvent.VK_DOWN:        // Down arrow
                    bat.setY(bat.getY() + Global.batMove);
                    break;
            }

            model.modelChanged();
        }
    }


}

