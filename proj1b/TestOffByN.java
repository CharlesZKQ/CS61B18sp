import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy5 = new OffByN(5);


    @Test
    public void TestEqualChars(){
        assertTrue(offBy5.equalChars('a', 'f'));  // True
        assertTrue(offBy5.equalChars('f', 'a')); // True
        assertFalse(offBy5.equalChars('f', 'h')); // False

    }
}
