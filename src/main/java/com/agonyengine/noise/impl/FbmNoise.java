package com.agonyengine.noise.impl;

import com.agonyengine.noise.OffsetNoiseGenerator;
import com.agonyengine.noise.NoiseMap;
import com.agonyengine.noise.thirdparty.FastNoiseLite;

public class FbmNoise implements OffsetNoiseGenerator {
    private final FbmParameters parameters;
    private final FastNoiseLite noiseGenerator = new FastNoiseLite();

    public FbmNoise(FbmParameters parameters) {
        this.parameters = parameters;
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        return generate(seedPrefix, width, height, 0, 0);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height, final int offsetX, final int offsetY) {
        double[][] result = new double[width][height];
        double frequency = parameters.getBaseFrequency();
        double amplitude = parameters.getBaseAmplitude();

        for (int octave = 0; octave < parameters.getOctaves(); octave++) {
            noiseGenerator.SetSeed((seedPrefix + " Octave " + octave).hashCode());

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    result[x][y] += amplitude * noiseGenerator.GetNoise((x + offsetX) * frequency, (y + offsetY) * frequency);
                }
            }

            frequency *= parameters.getLacunarity();
            amplitude *= parameters.getPersistence();
        }

        NoiseMap noiseMap = new NoiseMap(result);

        noiseMap.adjustScale(0.0, 1.0);

        return noiseMap;
    }
}
