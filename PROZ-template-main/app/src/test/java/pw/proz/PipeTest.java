package pw.proz;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class PipeTest {
    @Test public void testPipe() {
        Pipe classUnderTest = new Pipe();
        assertNotNull("Pipe should have two sprites", classUnderTest.getSprite(0));
        assertNotNull("Pipe should have two sprites", classUnderTest.getSprite(1));
        assertFalse("Pipe should have an indicator if it's located on the left side of game screen", classUnderTest.isHalfPassed());
    }
}