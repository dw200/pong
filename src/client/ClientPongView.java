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
import java.awt.geom.Ellipse2D;
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
        /* Size of window */
        setSize(windowWidth, windowHeight);
        /* Called when key press */
        addKeyListener(new Transaction());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Called from the model when its state is changed
     * @param aPongModel Model of the game
     * @param arg        Argument passed not used
     */
    public void update(Observable aPongModel, Object arg) {
        ClientPongModel model = (ClientPongModel) aPongModel;
        ball = model.getBall();
        bats = model.getBats();
        DEBUG.trace("ClientPongView.update");
        /* Re draw game */
        repaint();
    }

    /* Called by repaint */
    public void update(Graphics graphics) {
        /* Draw Picture */
        drawPicture((Graphics2D) graphics);
    }

    /* When 'Window' is first */
    public void paint(Graphics graphics) {
        /* shown or damaged, Draw Picture */
        drawPicture((Graphics2D) graphics);
    }

    private Dimension alternateDimension; // Alternate Dimension
    private BufferedImage alternateImage; // Alternate Image
    private Graphics2D alternateGraphics; // Alternate Graphics

    /**
     * The code that actually displays the game graphically
     *
     * @param graphics2D Graphics context to use
     */
    /* Double buffer */
    public void drawPicture(Graphics2D graphics2D) {
        /* Allow re-size. Get size of current image. */
        Dimension dimension = getSize();
        /* New size */
        if ((alternateGraphics == null) ||
                (dimension.width != alternateDimension.width) ||
                (dimension.height != alternateDimension.height)) {
            alternateDimension = dimension;
            alternateImage = (BufferedImage) createImage(dimension
                    .width, dimension.height);
            alternateGraphics = alternateImage.createGraphics();
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToIdentity();
            affineTransform.scale(((double) dimension.width) /
                    windowWidth, ((double) dimension.height) /
                    windowHeight);
            alternateGraphics.transform(affineTransform);
        }

        /* Draw Actual Picture */
        drawActualPicture(alternateGraphics);
        /* Display on screen */
        graphics2D.drawImage(alternateImage, 0, 0, this);
    }


    /**
     * Code called to draw the current state of the game
     * Uses draw:       Draw a shape
     * fill:       Fill the shape
     * setPaint:   Colour used
     * drawString: Write string on display
     *
     * @param graphics2D Graphics context to use
     */
    public void drawActualPicture(Graphics2D graphics2D) {
        /* Set up the window. */
        /* White background filling the screen */
        graphics2D.setPaint(Color.white);
        graphics2D.fill(new Rectangle2D.Double(0, 0, windowWidth,
                windowHeight));
        /* Change colour to grey to paint the border. */
        graphics2D.setPaint(Color.darkGray);
        /* Draw the border */
        graphics2D.draw(new Rectangle2D.Double(borderOffset,
                menuOffset, windowWidth - borderOffset * 2,
                windowHeight - menuOffset - borderOffset));
        /* Display state of game, Race condition */
        if (ball == null) return;
        /* Setting the font to Helvetica Neue and the colour Grey */
        Font font = new Font("Helvetica Neue", Font.PLAIN, 14);
        graphics2D.setFont(font);
        FontMetrics fontMetrics = getFontMetrics(font);
        /* Output string ready to take information from stringData */
        String stringOutput = "Pong - Ball [%3.0f, %3.0f] Bat [%3.0f," +
                " %3.0f]" +
                " Bat [%3.0f, %3.0f]";
        String stringData = String.format(stringOutput,
                ball.getGameObjectPositionX(),
                ball.getGameObjectPositionY(),
                bats[0].getGameObjectPositionX(),
                bats[0].getGameObjectPositionY(),
                bats[1].getGameObjectPositionX(),
                bats[1].getGameObjectPositionY());
        /* Paint the string of text onto the screen. */
        graphics2D.drawString(stringData, windowWidth / 2 -
                fontMetrics.stringWidth(stringData) / 2,
                (int) menuOffset * 2);
        /* The ball at the current x, y position (windowWidth,
        windowHeight) */
        graphics2D.fill(new Ellipse2D.Double(ball
                .getGameObjectPositionX(),
                ball.getGameObjectPositionY(),
                ballSize, ballSize));

        for (int i = 0; i < 2; i++)
            graphics2D.fill(new Rectangle2D.Double(bats[i]
                    .getGameObjectPositionX(),
                    bats[i].getGameObjectPositionY(),
                    batWidth, batHeight));
    }
    /**
     * Need to be told where the controller is
     */
    public void setPongController(ClientPongController
                                          aPongController) {
        pongController = aPongController;
    }

    /**
     * Methods Called on a key press
     * calls the controller to process key
     */
    /* When character typed */
    class Transaction implements KeyListener {
        /* Obey this method */
        public void keyPressed(KeyEvent e) {
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