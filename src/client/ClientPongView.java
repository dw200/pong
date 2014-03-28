package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import java.util.Observable;
import java.util.Observer;

import common.DEBUG;
import common.GameObject;

import static common.Global.*;

/**
 * Displays a graphical view of the game of pong
 */
class ClientPongView extends JFrame implements Observer {
    private static final long serialVersionUID = 1L;
    private ClientPongController pongController;
    GameObject ball;
    GameObject[] bats;

    public ClientPongView() {
        setSize(windowWidth, windowHeight);                        // Size of window
        addKeyListener(new Transaction());    // Called when key press
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Called from the model when its state is changed
     *
     * @param aPongModel Model of the game
     * @param arg        Argument passed not used
     */
    public void update(Observable aPongModel, Object arg) {
        ClientPongModel model = (ClientPongModel) aPongModel;
        ball = model.getBall();
        bats = model.getBats();
        DEBUG.trace("ClientPongView.update");
        repaint();                              // Re draw game
    }

    public void update(Graphics g)          // Called by repaint
    {
        drawPicture((Graphics2D) g);          // Draw Picture
    }

    public void paint(Graphics g)           // When 'Window' is first
    {                                         //  shown or damaged
        drawPicture((Graphics2D) g);          // Draw Picture
    }

    private Dimension theAD;              // Alternate Dimension
    private BufferedImage theAI;              // Alternate Image
    private Graphics2D theAG;              // Alternate Graphics

    /**
     * The code that actually displays the game graphically
     *
     * @param g Graphics context to use
     */
    public void drawPicture(Graphics2D g)   // Double buffer
    {                                         //  allow re-size
        Dimension d = getSize();             // Size of curr. image

        if ((theAG == null) ||
                (d.width != theAD.width) ||
                (d.height != theAD.height)) {                                       // New size
            theAD = d;
            theAI = (BufferedImage) createImage(d.width, d.height);
            theAG = theAI.createGraphics();
            AffineTransform at = new AffineTransform();
            at.setToIdentity();
            at.scale(((double) d.width) / windowWidth, ((double) d.height) / windowHeight);
            theAG.transform(at);
        }

        drawActualPicture(theAG);             // Draw Actual Picture
        g.drawImage(theAI, 0, 0, this);       //  Display on screen
    }


    /**
     * Code called to draw the current state of the game
     * Uses draw:       Draw a shape
     * fill:       Fill the shape
     * setPaint:   Colour used
     * drawString: Write string on display
     *
     * @param g Graphics context to use
     */
    public void drawActualPicture(Graphics2D g) {
        // White background

        g.setPaint(Color.white);
        g.fill(new Rectangle2D.Double(0, 0, windowWidth, windowHeight));

        Font font = new Font("Monospaced", Font.PLAIN, 14);
        g.setFont(font);

        // Blue playing border

        g.setPaint(Color.blue);              // Paint Colour
        g.draw(new Rectangle2D.Double(borderOffset, menuOffset, windowWidth - borderOffset * 2, windowHeight - menuOffset - borderOffset));

        // Display state of game
        if (ball == null) return;  // Race condition
        g.setPaint(Color.blue);
        FontMetrics fm = getFontMetrics(font);
        String fmt = "Pong - Ball [%3.0f, %3.0f] Bat [%3.0f, %3.0f]" +
                " Bat [%3.0f, %3.0f]";
        String text = String.format(fmt, ball.getGameObjectPositionX(), ball.getGameObjectPositionY(),
                bats[0].getGameObjectPositionX(), bats[0].getGameObjectPositionY(),
                bats[1].getGameObjectPositionX(), bats[1].getGameObjectPositionY());
        g.drawString(text, windowWidth / 2 - fm.stringWidth(text) / 2, (int) menuOffset * 2);

        // The ball at the current x, y position (windowWidth, windowHeight)

        g.setPaint(Color.red);
        g.fill(new Rectangle2D.Double(ball.getGameObjectPositionX(), ball.getGameObjectPositionY(),
                ballSize, ballSize));

        g.setPaint(Color.blue);
        for (int i = 0; i < 2; i++)
            g.fill(new Rectangle2D.Double(bats[i].getGameObjectPositionX(), bats[i].getGameObjectPositionY(),
                    batWidth, batHeight));
    }

    /**
     * Need to be told where the controller is
     */
    public void setPongController(ClientPongController aPongController) {
        pongController = aPongController;
    }

    /**
     * Methods Called on a key press
     * calls the controller to process key
     */
    class Transaction implements KeyListener  // When character typed
    {
        public void keyPressed(KeyEvent e)      // Obey this method
        {
            // Make -ve so not confused with normal characters
            pongController.userKeyInteraction(-e.getKeyCode());
        }

        public void keyReleased(KeyEvent e) {
            // Called on key release including specials
        }

        public void keyTyped(KeyEvent e) {
            // Normal key typed
            pongController.userKeyInteraction(e.getKeyChar());
        }
    }
}
