package com.agonyengine.noise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {
    private static final int HEIGHT = 1024;
    private static final int WIDTH = 1024;

    private AdjustedNoise adjustedNoise = new AdjustedNoise();
    private MaxNoise maxNoise = new MaxNoise();

    public static void main(String... args) throws IOException {
        ImageGenerator generator = new ImageGenerator();

        if (args.length == 0 || "raw".equals(args[0])) {
            generator.generateRaw();
        } else if ("fbm".equals(args[0])) {
            generator.generateFractalBrownianMotion();
        }
    }

    /**
     * Generate an image using raw noise. It should just look like black and white TV static.
     *
     * @throws IOException If draw() throws an IOException.
     */
    private void generateRaw() throws IOException {
        double[][] noises = new double[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                noises[x][y] = adjustedNoise.eval(x, y);
            }
        }

        draw(noises);
    }

    /**
     * Generate an image using Fractal Brownian Motion. It should look like clouds or terrain.
     *
     * @throws IOException If draw() throws an IOException.
     */
    private void generateFractalBrownianMotion() throws IOException {
        final double maxValue = fbmNoise(0, 0, maxNoise); // the max value of the FBM noise

        double[][] noises = new double[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                noises[x][y] = rescale(fbmNoise(x - WIDTH, y - HEIGHT), 0, maxValue, 0, 1.0);
            }
        }

        draw(noises);
    }

    /**
     * Draw a PNG from a two dimensional array of numbers.
     *
     * @param noises The numbers to use for each pixel. They should be in the range of 0 to 1.0.
     * @throws IOException If anything goes wrong while writing the image to disk.
     */
    private void draw(double[][] noises) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int value = (int)(noises[x][y] * 0xFF);
                int rgb = 0xFF000000;

                if (value < 110) {
                    rgb = rgb | (value); // blue
                } else if (value < 198) {
                    rgb = rgb | (value << 8); // green
                } else {
                    rgb = rgb | (value); // blue
                    rgb = rgb | (value << 8); // green
                    rgb = rgb | (value << 16); // red
                }

                image.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(image, "PNG", new File("map" + System.currentTimeMillis() + ".PNG"));
    }

    /**
     * Sample FBM noise for one location using the AdjustedNoise instance.
     *
     * @param x The position on the X axis to sample.
     * @param y The position on the Y axis to sample.
     * @return The raw noise value. It is not normalized or scaled.
     */
    private double fbmNoise(double x, double y) {
        return fbmNoise(x, y, adjustedNoise);
    }

    /**
     * Sample FBM noise for one location.
     *
     * @param x The position on the X axis to sample.
     * @param y The position on the Y axis to sample.
     * @param noise The noise generator to use.
     * @return The raw noise value. It is not normalized or scaled.
     */
    private double fbmNoise(double x, double y, NoiseGenerator noise) {
        final double baseFrequency = 0.008; // starting frequency, smaller values zoom in
        final double baseAmplitude = 1.0; // starting amplitude
        final double lacunarity = 2.1042; // how the frequency changes in each octave (usually > 1)
        final double persistence = 0.5; // how the amplitude changes in each octave (usually < 1)
        final int octaves = 6; // the number of octaves to compute

        double total = 0.0;
        double frequency = baseFrequency;
        double amplitude = baseAmplitude;

        for (int i = 0; i < octaves; i++) {
            total += amplitude * noise.eval(x * frequency, y * frequency);

            frequency *= lacunarity;
            amplitude *= persistence;
        }

        return total;
    }

    /**
     * Re-scale a value from one range to another range.
     *
     * @param value The value to be scaled.
     * @param min The minimum of the original range.
     * @param max The maximum of the original range.
     * @param scaledMin The minimum of the new range.
     * @param scaledMax The maximum of the new range.
     * @return The adjusted value, within the new range.
     */
    private double rescale(final double value, final double min, final double max, final double scaledMin, final double scaledMax) {
        return ((value - min) / (max - min)) * (scaledMax - scaledMin) + scaledMin;
    }
}
