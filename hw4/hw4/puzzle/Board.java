package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;
    private int size;
    private static final int BLANK = 0;
    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new IndexOutOfBoundsException("Invalid index given: i == " + i + " j == " + j);
        }
        return tiles[i][j];
    }
    public int size() {
        return size;
    }

    /**
     * Returns the neighbors of the current board
     * @author SPOILERZ (Cited from http://joshh.ug/neighbors.html)
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming(){
        int expectedVal = 1;
        int totalWrong = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (expectedVal == size * size) { // Break when hit the BLANK tile\
                    break;
                }
                int actualNum = tileAt(i, j);
                if (actualNum != expectedVal) {
                    totalWrong += 1;
                }
                expectedVal += 1;
            }
        }
        return totalWrong;
    }
    public int manhattan(){
        int totalDis = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int numGet = tileAt(row, col);
                if (numGet == BLANK) {
                    continue;
                }
                int realRow = (numGet - 1) / size;
                int realCol = (numGet - 1) % size;
                totalDis = totalDis + Math.abs(row - realRow) + Math.abs(col - realCol);
            }
        }
        return totalDis;
    }
    public int estimatedDistanceToGoal(){
        return manhattan();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (this.getClass() != y.getClass() || y == null) {
            return false;
        }
        Board boardY = (Board) y;
        if (boardY.size != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tileAt(i, j) != boardY.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
