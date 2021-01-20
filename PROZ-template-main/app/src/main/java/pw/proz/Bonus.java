package pw.proz;

import javafx.scene.image.Image;

public class Bonus {
    private final int bonusType;
    private final Sprite bonusSprite;
    // height and width = 30
    private final static Image[] bonusImages = {
            new Image("/images/lemon.png", 30, 30, false, false),
            new Image("/images/cherry.png", 30, 30, false, false)
    };


    public Bonus(int index) {
        double posX = GameWindow.getWINDOW_WIDTH();
        double posY = Math.random() * (GameWindow.getWINDOW_HEIGHT() - 30);

        bonusSprite = new Sprite();
        bonusSprite.setImage(bonusImages[index]);
        bonusSprite.setPosition(posX, posY);

        bonusType = index;
    }

    public int getBonusType() { return bonusType; }
    public Sprite getSprite() { return bonusSprite; }
}