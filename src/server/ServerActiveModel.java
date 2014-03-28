package server;
import static common.Global.*;
import common.*;
/**
 * A class used by the server model to give it an active part
 * which continuously moves the ball and decides what to
 * do on a collision
 */
class ServerActiveModel implements Runnable {
    ServerPongModel pongModel;
    public ServerActiveModel(ServerPongModel aPongModel) {
        pongModel = aPongModel;
    }
    /**
     * Code to position the ball after time interval
     * runs as a separate thread
     */
    public void run() {
        final double unitsToMove = 1; // Units to move
        try {
            while (true) {
                pongModel.processUpdates();
                GameObject ball = pongModel.getBall();
                GameObject bats[] = pongModel.getBats();
                double ballPositionX = ball.getGameObjectPositionX();
                double ballPositionY = ball.getGameObjectPositionY();
                // Deal with possible edge of board hit
                if (ballPositionX >= windowWidth - borderOffset -
                        ballSize) ball.changeDirectionX();
                if (ballPositionX <= 0 + borderOffset) ball
                        .changeDirectionX();
                if (ballPositionY >= windowHeight - borderOffset -
                        ballSize) ball.changeDirectionY();
                if (ballPositionY <= 0 + menuOffset) ball
                        .changeDirectionY();
                ball.moveX(unitsToMove);
                ball.moveY(unitsToMove);
                // As only a hit on the bat is detected it is assumed
                // to be on the front or back of the bat
                // A hit on the top or bottom has an interesting affect
                if (bats[0].collision(ball) == GameObject.Collision.HIT ||
                        bats[1].collision(ball) == GameObject.Collision.HIT) {
                    ball.changeDirectionX();
                }
                pongModel.modelChanged(); // Model changed refresh
                // screen
                Thread.sleep(20); // About 50 Hz
            }
        } catch (Exception e) {
        };
    }
}