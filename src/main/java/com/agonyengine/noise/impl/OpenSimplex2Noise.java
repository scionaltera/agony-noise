package com.agonyengine.noise.impl;

import com.agonyengine.noise.OffsetNoiseGenerator;
import com.agonyengine.noise.NoiseMap;
import com.agonyengine.noise.thirdparty.FastNoiseLite;

public class OpenSimplex2Noise implements OffsetNoiseGenerator {
    private final FastNoiseLite noiseGenerator = new FastNoiseLite();

    public OpenSimplex2Noise() {
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        return generate(seedPrefix, width, height, 0, 0);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height, final int offsetX, final int offsetY) {
        final double[][] map = new double[width][height];

        noiseGenerator.SetSeed(seedPrefix.hashCode());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = noiseGenerator.GetNoise((x + offsetX), (y + offsetY));
            }
        }

        return new NoiseMap(map);
    }
}
