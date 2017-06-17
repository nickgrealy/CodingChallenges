package org.nickgrealy.performance;

/**
 * Quick and dirty, not thread safe, static implementation of a counter and timer.
 */
public class QuickPerf {

    private static long start;
    private static long iterations = 0;

    public static void start() {
        start = System.nanoTime();
    }

    public static void end() {
        double durationMillis = (System.nanoTime() - start) / 1000000.0;
        System.out.printf("DurationMillis: %s Iterations: %s %n", durationMillis, iterations);
    }

    public static void iterate() {
        iterations++;
    }
}
