package com.agonyengine.noise.impl;

import com.agonyengine.noise.NoiseGenerator;
import com.agonyengine.noise.NoiseMap;
import com.agonyengine.noise.thirdparty.FastNoiseLite;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class OpenSimplex2Noise implements NoiseGenerator {
    private final FastNoiseLite noiseGenerator = new FastNoiseLite();

    public OpenSimplex2Noise() {
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        final SecureRandom random = new SecureRandom(seedPrefix.getBytes(StandardCharsets.UTF_8));
        final double[][] map = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = noiseGenerator.GetNoise(x, y);
            }
        }

        return new NoiseMap(map);
    }
}
