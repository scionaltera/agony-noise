package com.agonyengine.noise;

import java.util.HashMap;
import java.util.Map;

public class NoiseMap {
    private final double[][] map;
    private final double[] minMax;

    public NoiseMap(final double[][] map) {
        this.map = map;
        this.minMax = minMax();
    }

    public double get(int x, int y) {
        return map[x][y];
    }

    /**
     * Prints a grid with the map's raw values to the console.
     */
    public void showValueGrid() {
        for (int y = 0; y < map.length; y++) {
            System.out.printf("%3d  ", y);

            for (int x = 0; x < map[0].length; x++) {
                System.out.printf("%.2f ", map[x][y]);
            }

            System.out.print("\n");
        }

        System.out.print("\n  ");

        for (int x = 0; x < map.length; x++) {
            System.out.printf("%4d ", x);
        }

        System.out.print("\n");
    }

    /**
     * Print the distribution of values in the map, on the console.
     */
    public void showValueDistribution(double[][] noises) {
        Map<String, Integer> distribution = new HashMap<>();

        for (double[] noise : noises) {
            for (int y = 0; y < noises[0].length; y++) {
                distribution.compute(String.format("%.2f", noise[y]), (k, v) -> v == null ? 0 : v + 1);
            }
        }

        distribution
                .keySet()
                .stream()
                .sorted()
                .forEach(key -> System.out.printf("%s: %d\n", key, distribution.get(key)));
    }

    /**
     * Adjust the scale of a noise map from one range to another. This method adjusts
     * the map in place and <b>does not</b> allocate a new map.
     *
     * @param min The minimum range for the adjusted map.
     * @param max The maximum range for the adjusted map.
     */
    public void adjustScale(double min, double max) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y] = rescale(map[x][y], minMax[0], minMax[1], min, max);
            }
        }

        double[] newMinMax = minMax();
        System.arraycopy(newMinMax, 0, minMax, 0, 2);
    }

    /**
     * Return the width of the map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return map.length;
    }

    /**
     * Return the height of the map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return map[0].length;
    }

    /**
     * Get this map's minimum value.
     *
     * @return The minimum value contained in this map.
     */
    public double getMin() {
        return minMax[0];
    }

    /**
     * Get this map's maximum value.
     *
     * @return The maximum value contained in this map.
     */
    public double getMax() {
        return minMax[1];
    }

    // find the min and max for the map
    private double[] minMax() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        //noinspection ForLoopReplaceableByForEach
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                min = Math.min(min, map[x][y]);
                max = Math.max(max, map[x][y]);
            }
        }

        return new double[] { min, max };
    }

    // rescale a value from one range to another
    private double rescale(final double value, final double min, final double max, final double scaledMin, final double scaledMax) {
        return ((value - min) / (max - min)) * (scaledMax - scaledMin) + scaledMin;
    }
}
