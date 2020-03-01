package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }


    public double next() {
        if (state >= period) {
            state = 0;
            period = (int)(period * factor);
        }
        double res = normalize(state);
        state += 1;
        return res;
    }

    private double normalize(int state) {
        double k = (double)2 / period;
        double y = state * k - 1;
        return y;
    }
}
