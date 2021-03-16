package com.agonyengine.noise.impl;

import com.agonyengine.noise.NoiseGenerator;
import com.agonyengine.noise.NoiseMap;
import com.agonyengine.noise.thirdparty.FastNoiseLite;

public class FbmNoise implements NoiseGenerator {
    private final FbmParameters parameters;
    private final FastNoiseLite noiseGenerator = new FastNoiseLite();

    public FbmNoise(FbmParameters parameters) {
        this.parameters = parameters;
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public NoiseMap generate(final String seedPrefix, final int width, final int height) {
        double[][] result = new double[width][height];
        double frequency = parameters.getBaseFrequency();
        double amplitude = parameters.getBaseAmplitude();

        for (int octave = 0; octave < parameters.getOctaves(); octave++) {
            noiseGenerator.SetSeed((seedPrefix + " Octave " + octave).hashCode());

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    result[x][y] += amplitude * noiseGenerator.GetNoise(x * frequency, y * frequency);
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
