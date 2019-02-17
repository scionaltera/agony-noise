package com.agonyengine.noise;

public class FbmParameters {
    private double baseFrequency;
    private double baseAmplitude;
    private double lacunarity;
    private double persistence;
    private int octaves;

    public FbmParameters(double baseFrequency, double baseAmplitude, double lacunarity, double persistence, int octaves) {
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
