package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import javax.naming.directory.SearchResult;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class Solver {
    private class SearchNode {
        private WorldState state;
        private int moveNum;
        private SearchNode prevNode;
        private Integer priority;
        private SearchNode(WorldState state, SearchNode prevNode) {
            this.state = state;
            this.moveNum = prevNode == null ? 0 : prevNode.moveNum + 1; // compute the number of moveNum

            if (edCaches.containsKey(this.state)) {
                int ed = edCaches.get(this.state);
                this.priority = ed + this.moveNum;
            } else {
                int ed = this.state.estimatedDistanceToGoal();
                edCaches.put(this.state, ed);
                this.priority = ed + this.moveNum;
            }
            this.prevNode = prevNode;
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode left, SearchNode right) {
            return left.priority.compareTo(right.priority);
        }
    }



    private Map<WorldState, Integer> edCaches = new HashMap<>(); // Caches estimated distance
    private int searchedCnt = 0;        // Keep track of the number of `SearchNode`s are enqueued
    private Stack<WorldState> path = new Stack<>(); // Keep track of the searched path
    /**Constructor which solves the puzzle, computing
     *   everything necessary for moves() and solution() to
     *   not have to solve the problem again. Solves the
     *   puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode currSearchNode = new SearchNode(initial, null);

        while (!currSearchNode.state.isGoal()) {
            for (WorldState neighbor : currSearchNode.state.neighbors()) {
                if (currSearchNode.prevNode == null || !neighbor.equals(currSearchNode.prevNode.state)){
                    SearchNode nextNode = new SearchNode(neighbor, currSearchNode);
                    pq.insert(nextNode);
                    searchedCnt += 1;
                }
            }
            currSearchNode = pq.delMin();
        }

        for (SearchNode node = currSearchNode; node != null; node = node.prevNode) {
            path.push(node.state);
        }
    }

    /**Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return path.size() - 1;
    }

    /**Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */

    public Iterable<WorldState> solution() {
        return path;
    }


//    int searchedCount() {
//        return searchedCnt;
//
//    }
}
