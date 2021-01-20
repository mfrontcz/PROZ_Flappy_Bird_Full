package pw.proz;

import javafx.scene.image.Image;

public class Bird {
    private int imageIndex;
    private final Sprite birdSprite;
    // height and width = 50
    private final Image[] birdImages = {
            new Image("/images/bird-sprite-1.png", 50, 50, false, false),
            new Image("/images/bird-sprite-2.png", 50, 50, false, false),
            new Image("/images/bird-sprite-3.png", 50, 50, false, false),
            new Image("/images/bird-sprite-4.png", 50, 50, false, false),
    };

    public Bird() {
        double posX = GameWindow.getWINDOW_WIDTH()/3.0;
        double posY = GameWindow.getWINDOW_HEIGHT()/2.0;

        birdSprite = new Sprite();
        birdSprite.setImage(birdImages[0]);
        birdSprite.setPosition(posX, posY);

        imageIndex = 0;
    }

    public boolean outOfBounds() {
        if( this.getSprite().getPositionY() <= 0 || this.getSprite().getPositionY() >= GameWindow.getWINDOW_HEIGHT() - this.getSprite().getHeight() )
            return true;
        else
            return false;
    }

    public void changeImage() {
        imageIndex++;
        if (imageIndex == 4) imageIndex = 0;
        this.getSprite().setImage(birdImages[imageIndex]);
    }

    public Sprite getSprite() { return birdSprite; }
}
