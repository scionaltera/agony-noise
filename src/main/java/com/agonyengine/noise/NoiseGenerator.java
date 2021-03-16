package com.agonyengine.noise;

public interface NoiseGenerator {
    NoiseMap generate(String seedPrefix, int width, int height);
}
