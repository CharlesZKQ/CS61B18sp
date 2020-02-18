package hw2;
import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {
    Percolation percolation = new Percolation(5);

    @Test
    public void testIsOpen() {
        percolation.open(3,4);
        assertTrue(percolation.isOpen(3,4));
        assertFalse(percolation.isOpen(1,1));
        percolation.open(1,1);
        assertTrue(percolation.isOpen(1,1));
    }

    @Test
    public void testIsFull(){
        percolation.open(0, 1);
        assertTrue(percolation.isFull(0, 1));
        percolation.open(1,1);
        assertTrue(percolation.isFull(1,1));
        assertTrue(percolation.isFull(0, 1));
        percolation.open(2,2);
        assertFalse(percolation.isFull(2,2));
    }

    @Test
    public void testNumberOfOpenSites() {
        assertEquals(0, percolation.numberOfOpenSites());
        percolation.open(1, 0);
        assertEquals(1, percolation.numberOfOpenSites());
        percolation.open(0, 0);
        assertEquals(2, percolation.numberOfOpenSites());
    }

    @Test
    public void testPercolates() {
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        assertFalse(percolation.percolates());
        percolation.open(4, 1);
        assertTrue(percolation.percolates());
    }
}
