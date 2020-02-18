package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {
    private int N;
    private int openNum;
    private WeightedQuickUnionUF connectedGrid;
    private boolean[] openGridIn1D;
    private boolean percolate;

    private Set<Integer> topOpenIndex = new HashSet<>();
    private Set<Integer> bottomOpenIndex = new HashSet<>();


    /**
     * Creates N-by-N grid, with all sites initially blocked
     * @throws IllegalArgumentException if {@code N <= 0}
     * with run time N * N
     * @param  N the number of rows(cols), in hw2 col = row
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N should be greater than 0 but given N = " + N + "."
            );
        }
        this.N = N;
        connectedGrid = new WeightedQuickUnionUF(N * N);
        openGridIn1D = new boolean[N * N];
        for (boolean itemState : openGridIn1D) {
            itemState = false;
        }
        openNum = 0;
        percolate = false;
    }

    /**
     * transform 2D coordination to 1D index, which is easier to compute afterwards.
     * @param  row the number of rows
     * @param  col the number of cols, these two parameters are the same in hw2, which is N!
     */
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    /**
     * To check if the index of row or col is valid.
     * @param  row the number of rows
     * @param  col the number of cols, these two parameters are the same in hw2, which is N!
     */
    private boolean isValidCoordinate(int row, int col) {
//        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
//            return false;
//        } else {
//            return true;
//        }
        return (row < 0 || row > N - 1 || col < 0 || col > N - 1);
    }

    /**
     * open the site (row, col) if it is not open already
     * @throws IndexOutOfBoundsException given arguments outside of constructed range
     * @param  row the number of rows
     * @param  col the number of cols, these two parameters are the same, which is N!
     */
    public void open(int row, int col) {
        if (!isValidCoordinate(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given: row = " + row + " col = " + col + "."
            );
        }
        int index1D = xyTo1D(row, col);
        if (openGridIn1D[index1D]) {
            return;
        }
        openGridIn1D[index1D] = true;
        int parentID = connectedGrid.find(index1D);
        if (index1D < N) {
            topOpenIndex.add(parentID);
        }
        if (N * N - index1D <= N) {
            bottomOpenIndex.add(parentID);
        }
        //update the connectivity between the new open point and adjacent points
        updateConnect(row, col, row + 1, col);
        updateConnect(row, col, row - 1, col);
        updateConnect(row, col, row, col + 1);
        updateConnect(row, col, row, col - 1);
        openNum += 1;

        int updatedID = connectedGrid.find(index1D);
        if (topOpenIndex.contains(updatedID) && bottomOpenIndex.contains(updatedID)) {
            percolate = true;
        }
    }

    private void updateConnect(int row, int col, int neighborRow, int neighborCol) {
        int index = xyTo1D(row, col);
        int oriID = connectedGrid.find(index);
        if (isValidCoordinate(neighborRow, neighborCol)
                && isOpen(neighborRow, neighborCol)) {
            int neighborIndex = xyTo1D(neighborRow, neighborCol);
            int neighborID = connectedGrid.find(neighborIndex);
            connectedGrid.union(index, neighborIndex);
            int updatedID = connectedGrid.find(index);

            //update the index of the Top layer so that we can avoid iteration
            if (topOpenIndex.contains(oriID)) {
                topOpenIndex.remove(oriID);
                topOpenIndex.add(updatedID);
            } else if (topOpenIndex.contains(neighborID)) {
                topOpenIndex.remove(neighborID);
                topOpenIndex.add(updatedID);
            }

            //update the index of Bottom layer
            if (bottomOpenIndex.contains(oriID)) {
                bottomOpenIndex.remove(oriID);
                bottomOpenIndex.add(updatedID);
            } else if (bottomOpenIndex.contains(neighborID)) {
                bottomOpenIndex.remove(neighborID);
                bottomOpenIndex.add(updatedID);
            }
        }

    }

    /**
     * Check if the site (row, col) is open
     * @throws IndexOutOfBoundsException given arguments outside of constructed range
     * @param  row the number of rows
     * @param  col the number of cols, these two parameters are the same, which is N!
     */
    public boolean isOpen(int row, int col) {
        if (!isValidCoordinate(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given: row = " + row + " col = " + col + "."
            );
        }
        return openGridIn1D[xyTo1D(row, col)];
    }

    /**
     * Check if the site (row, col) is full, if the site is connected to the top layer.
     * @throws IndexOutOfBoundsException given arguments outside of constructed range
     * @param  row the number of rows
     * @param  col the number of cols, these two parameters are the same, which is N!
     */
    public boolean isFull(int row, int col) {
        if (!isValidCoordinate(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given: row = " + row + " col = " + col + "."
            );
        }
        int index1D = xyTo1D(row, col);
        return topOpenIndex.contains(connectedGrid.find(index1D));
    }

    /** return the number of the open sites, use openNum to record.
     * @return openNum
     */
    public int numberOfOpenSites() {
        return openNum;
    }

    /** return true if the system percolates,
     * that is at least one site at bottom layer is connected to the top layer.
     * use instance member to indicate the state of percolation.
     */
    public boolean percolates() {
        return percolate;
    }

    public static void main(String[] args) {

    }
}
