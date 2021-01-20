package pw.proz;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private Image image;
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private double width;
    private double height;

    public Sprite() {
        posX = 0.0;
        posY = 0.0;
        velX = 0.0;
        velY = 0.0;
    }

    public void setImage(Image copied) {
        image = copied;
        width = copied.getWidth();
        height = copied.getHeight();
    }
    public void setPosition(double x, double y) {
        posX = x;
        posY = y;
    }
    public void setVelocity(double vX, double vY) {
        velX = vX;
        velY = vY;
    }

    public Image getImage() { return image; }
    public double getPositionX() { return posX; }
    public double getPositionY() { return posY; }
    public double getVelocityX() { return velX; }
    public double getVelocityY() { return velY; }
    public double getHeight() { return height; }
    public double getWidth() { return width; }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }

    public Rectangle2D area() {
        return new Rectangle2D(posX, posY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.area().intersects(this.area());
    }

    public void updatePosition(double time) {
        posX += velX * time;
        posY += velY * time;
    }
}
