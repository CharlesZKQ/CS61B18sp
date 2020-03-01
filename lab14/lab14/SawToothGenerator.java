package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        if (state >= period) {
            state = 0;
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
