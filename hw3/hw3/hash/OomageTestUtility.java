package hw3.hash;

import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        Map<Integer, Integer> bucket2Num = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (bucket2Num.containsKey(bucketNum)) {
                int num = bucket2Num.get(bucketNum);
                bucket2Num.put(bucketNum, num + 1);
            } else {
                bucket2Num.put(bucketNum, 1);
            }
        }
        int N = oomages.size();
        for (Integer key : bucket2Num.keySet()) {
            int numOmage = bucket2Num.get(key);
            if ( numOmage> N/50 && numOmage < N/2.5) {
                return true;
            }
        }
        return false;
    }
}
