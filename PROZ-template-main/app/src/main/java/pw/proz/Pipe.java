package pw.proz;

import javafx.scene.image.Image;

public class Pipe {
    private final Sprite[] pipeSprite = new Sprite[2];
    private boolean halfPassed;

    public Pipe() {
        double posX = GameWindow.getWINDOW_WIDTH();
        // min height of single pipe = 30, space between lower and upper pipe = 320
        double heightLower = Math.random() * (GameWindow.getWINDOW_HEIGHT() - 380) + 30;
        double heightUpper = GameWindow.getWINDOW_HEIGHT() - 200 - heightLower;
        double posYUpper = 0;
        double posYLower = heightUpper + 200;

        Image lower = new Image("/images/single_pipe_1.png", 100, heightLower, false, false);
        Image upper = new Image("/images/single_pipe_2.png", 100, heightUpper, false, false);

        pipeSprite[0] = new Sprite();
        pipeSprite[0].setImage(lower);
        pipeSprite[0].setPosition(posX, posYLower);

        pipeSprite[1] = new Sprite();
        pipeSprite[1].setImage(upper);
        pipeSprite[1].setPosition(posX, posYUpper);

        halfPassed = false;
    }

    public void setVelocity(double vX, double vY) {
        pipeSprite[0].setVelocity(vX, vY);
        pipeSprite[1].setVelocity(vX, vY);
    }
    public void setHalfPassed(boolean value) { halfPassed = value; }
    public Sprite getSprite(int i) { return pipeSprite[i]; }
    public boolean isHalfPassed() { return halfPassed; }
}