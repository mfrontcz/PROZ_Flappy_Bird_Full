package pw.proz;


import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class BonusTest {
    @Test public void testBonus() {
        Bonus classUnderTest = new Bonus(1);
        assertNotNull("Bonus should have a sprite", classUnderTest.getSprite());
        assertNotNull("Bonus should have a type", classUnderTest.getBonusType());
    }
}
