package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        if (state >= period) {
            state = 0;
        }
        int weirdState = state & (state >> 3) & (state >> 8) % period;;
        double res = normalize(weirdState);
        state += 1;
        return res;
    }

    private double normalize(int state) {
        double k = (double)2 / period;
        double y = state * k - 1;
        return y;
    }
}
