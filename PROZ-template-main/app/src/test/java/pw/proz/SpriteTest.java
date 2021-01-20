package pw.proz;


import org.junit.Test;
import static org.junit.Assert.*;

public class SpriteTest {
    @Test
    public void testSprite() {
        Sprite classUnderTest = new Sprite();
        assertNull("Sprite shouldn't have an image yet", classUnderTest.getImage());
        assertNotNull("Sprite doesn't have an image", classUnderTest.getHeight());
        assertNotNull("Sprite doesn't have an image", classUnderTest.getWidth());
    }
}
