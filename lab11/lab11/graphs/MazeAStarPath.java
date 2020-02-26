package lab11.graphs;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Integer> fringe;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
        fringe = new MinPQ<>(new aStarComparator());
        fringe.insert(s);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        //Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
        return Math.abs(t - v);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class aStarComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer left, Integer right) {
            left = distTo[left] + h(left);
            right = distTo[right] + h(right);
            return left.compareTo(right);
        }
    }


    /** Performs an A star search from vertex s. */
    private void aStar() {
        while (!fringe.isEmpty()) {
            if (targetFound) {
                return;
            }
            int v = fringe.delMin();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    announce();
                    if (w == t) {
                        targetFound = true;
                    } else {
                        fringe.insert(w);
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        aStar();
    }

}

