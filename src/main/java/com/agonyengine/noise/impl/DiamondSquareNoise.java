package com.agonyengine.noise.impl;

import com.agonyengine.noise.NoiseGenerator;
import com.agonyengine.noise.NoiseMap;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class DiamondSquareNoise implements NoiseGenerator {
    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        if (width != height) {
            throw new IllegalArgumentException("diamond square maps must be square");
        }

        if (width <= 0 || ((width & (width - 1)) == 0)) {
            throw new IllegalArgumentException("width and height must be some value of n^2+1");
        }

        final SecureRandom random = new SecureRandom(seedPrefix.getBytes(StandardCharsets.UTF_8));
        final double[][] map = new double[width][height];

        double randomness = 0.5;
        int span = map.length - 1;
        double startValue = random.nextDouble(); // 0 -> 1.0

        // initialize the corners
        map[0][0] = startValue;
        map[0][map[0].length - 1] = startValue;
        map[map.length - 1][0] = startValue;
        map[map.length - 1][map[0].length - 1] = startValue;

        while (span > 1) {
            int halfSpan = span / 2;

            // diamond
            for (int x = 0; x < map.length - 1; x += span) {
                for (int y = 0; y < map[0].length - 1; y += span) {
                    double sum = map[x][y] +
                            map[x + span][y] +
                            map[x][y + span] +
                            map[x + span][y + span];
                    double avg = sum / 4;
                    avg += (random.nextDouble() * randomness) - (randomness / 2);

                    map[x + halfSpan][y + halfSpan] = avg;
                }
            }

            // square
            for (int x = 0; x < map.length - 1; x += halfSpan) {
                for (int y = (x + halfSpan) % span; y < map[0].length - 1; y += span) {
                    double sum = map[(x - halfSpan + map.length - 1) % (map.length - 1)][y] +
                            map[(x + halfSpan) % (map.length - 1)][y] +
                            map[x][(y + halfSpan) % (map.length - 1)] +
                            map[x][(y - halfSpan + map.length - 1) % (map.length - 1)];
                    double avg = sum / 4;
                    avg += (random.nextDouble() * randomness) - (randomness / 2);

                    map[x][y] = avg;

                    if (x == 0) {
                        map[map.length - 1][y] = avg;
                    }

                    if (y == 0) {
                        map[x][map.length - 1] = avg;
                    }
                }
            }

            randomness = Math.max(randomness / 2, 1);
            span /= 2;
        }

        return new NoiseMap(map);
    }
}
