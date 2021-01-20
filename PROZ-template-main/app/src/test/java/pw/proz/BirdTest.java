package pw.proz;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class BirdTest {
    @Test
    public void testBird() {
        Bird classUnderTest = new Bird();
        assertNotNull("Bird should have a sprite", classUnderTest.getSprite());
        assertFalse("Bird is out of bounds!", classUnderTest.outOfBounds());
    }
}