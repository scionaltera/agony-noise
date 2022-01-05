package com.agonyengine.noise.impl;

import com.agonyengine.noise.NoiseGenerator;
import com.agonyengine.noise.NoiseMap;
import com.agonyengine.noise.thirdparty.FastNoiseLite;

public class OpenSimplex2Noise implements NoiseGenerator {
    private final FastNoiseLite noiseGenerator = new FastNoiseLite();

    public OpenSimplex2Noise() {
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        final double[][] map = new double[width][height];

        noiseGenerator.SetSeed(seedPrefix.hashCode());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = noiseGenerator.GetNoise(x, y);
            }
        }

        return new NoiseMap(map);
    }
}
