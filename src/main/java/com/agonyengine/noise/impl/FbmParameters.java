package com.agonyengine.noise.impl;

public class FbmParameters {
    private final double baseFrequency;
    private final double baseAmplitude;
    private final double lacunarity;
    private final double persistence;
    private final int octaves;

    public FbmParameters(final double baseFrequency, final double baseAmplitude, final double lacunarity, final double persistence, final int octaves) {
        this.baseFrequency = baseFrequency; // starting frequency, smaller values zoom in
        this.baseAmplitude = baseAmplitude; // starting amplitude
        this.lacunarity = lacunarity; // how the frequency changes in each octave (usually > 1)
        this.persistence = persistence; // how the amplitude changes in each octave (usually < 1)
        this.octaves = octaves; // the number of octaves to compute
    }

    public double getBaseFrequency() {
        return baseFrequency;
    }

    public double getBaseAmplitude() {
        return baseAmplitude;
    }

    public double getLacunarity() {
        return lacunarity;
    }

    public double getPersistence() {
        return persistence;
    }

    public int getOctaves() {
        return octaves;
    }
}
