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
     * @param aPongModel Model of game on client
     * @param aPongView  View of game on client
     */
    public ClientPongController(ClientPongModel aPongModel, ClientPongView aPongView) {
        model = aPongModel;
        view = aPongView;
        /* View talks to controller */
        view.setPongController(this);
    }
    /**
     * Decide what to do for each key pressed
     * @param keyCode The keycode of the key pressed
     */
    public void userKeyInteraction(int keyCode) {
        /* If player isn't assigned a number, then they cannot play */
        if (model.getPlayerNumber() <= -1) {
            return;
        }
        // Key typed includes specials, -ve
        // Char is ASCII value
        synchronized (model) {
            GameObject bat = model.getBats()[model.getPlayerNumber()];
            /* Which key is pressed */
            switch (keyCode) {
                /* Left Arrow */
                case -KeyEvent.VK_LEFT:
                    bat.setGameObjectPositionX(bat.getGameObjectPositionX() - Global.batMove);
                    break;
                /* Right Arrow */
                case -KeyEvent.VK_RIGHT:
                    bat.setGameObjectPositionX(bat.getGameObjectPositionX() + Global.batMove);
                    break;
                /* Up Arrow */
                case -KeyEvent.VK_UP:
                    bat.setGameObjectPositionY(bat.getGameObjectPositionY() - Global.batMove);
                    break;
                /* Down Arrow */
                case -KeyEvent.VK_DOWN:
                    bat.setGameObjectPositionY(bat.getGameObjectPositionY() + Global.batMove);
                    break;
            }
            model.modelChanged();
        }
    }
}