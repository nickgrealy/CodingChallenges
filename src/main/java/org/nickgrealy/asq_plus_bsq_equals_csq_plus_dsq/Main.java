
package org.nickgrealy.asq_plus_bsq_equals_csq_plus_dsq;

import org.nickgrealy.performance.QuickPerf;

/**
 * Print out all integers less than 10,000, where:
 * 1) a²+b²=c²+d²
 * 2) a,b,c and d are all integers less than 10,000
 * 3) a,b,d and d are not equal
 */
public class Main {

    static final int[] RESULT_RANGE = { 0, 10000 };
    static final int ABCD_LOWER_BOUNDS = 0;
    static final int ABCD_UPPER_BOUNDS = 10000;

    public static void main(final String[] args) {
        int[] abcdRange = abcdRange(RESULT_RANGE);
        QuickPerf.start();
        run(abcdRange[0], abcdRange[1]);
        QuickPerf.end();
    }

    /**
     * Iterate over a and b, so we can find c and d...
     */
    public static void run(final int lower, final int upper) {
        for (int a = lower; a <= upper; a++) {
            // a and b should not overlap... (it's redundant)
            for (int b = a + 1; b <= upper; b++) {
                findCandD(a, b, lower, upper);
            }
        }
    }

    /**
     * Work backwards to find c and d...
     */
    public static void findCandD(final int a, final int b, final int lower, final int upper) {
        int candidateResult = (int) (Math.pow(a, 2) + Math.pow(b, 2));
        // eliminate results greater than the upper limit...
        if (candidateResult <= RESULT_RANGE[1]) {
            // iterate over abcd range (excluding a and b)... see if we can find an integer match for c and d.
            for (int c = lower; c <= upper && c != a && c != b; c++) {
                QuickPerf.iterate();
                // work backwards, to find d...
                double doubleD = Math.sqrt(candidateResult - Math.pow(c, 2));
                int d = (int) doubleD;
                // if d is an integer, and doesn't equal a,b or c, then we have a winner!
                if (d == doubleD && d != a && d != b && d != c) {
                    // we have a winner!
                    System.out.printf("%s²+%s² = %s²+%s² = %s%n", a, b, c, d, candidateResult);
                }
            }
        }
    }

    /**
     * ABCD values must be within supplied bounds, and logically cannot be:
     * 1) lower than the square-root of the lower limit
     * 2) greater than the square-root of the upper limit
     */
    public static int[] abcdRange(final int[] range) {
        return new int[] { Math.max((int) Math.sqrt(range[0]), ABCD_LOWER_BOUNDS),
                Math.min((int) Math.sqrt(range[1]), ABCD_UPPER_BOUNDS) };
    }
}
