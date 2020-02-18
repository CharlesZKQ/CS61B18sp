package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private PercolationFactory pf;
    private Percolation SystemNByN;
    private double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0) {
            throw new IllegalArgumentException(
                    "N should be greater than 0 but given N = " + N + "."
            );
        }
        if (T < 0) {
            throw new IllegalArgumentException(
                    "T should be greater than 0 but given T = " + T + "."
            );
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        thresholds = new double[T];
        SystemNByN = pf.make(N);
        calculate();
    }

    private void calculate() {
        for (int i = 0; i < T; i++) {
            while (!SystemNByN.percolates()) {
                int randomRow = StdRandom.uniform(N);
                int randomCol = StdRandom.uniform(N);
                SystemNByN.open(randomRow, randomCol);
            }
            thresholds[i] = SystemNByN.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
        }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double u = mean();
        double theta = stddev();
        return u - (1.96 * theta) / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double u = mean();
        double theta = stddev();
        return u + (1.96 * theta) / Math.sqrt(T);
    }
}
