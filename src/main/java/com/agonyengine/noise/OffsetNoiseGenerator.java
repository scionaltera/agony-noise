package com.agonyengine.noise;

public interface OffsetNoiseGenerator extends NoiseGenerator {
    NoiseMap generate(String seedPrefix, int width, int height, int offsetX, int offsetY);
}
