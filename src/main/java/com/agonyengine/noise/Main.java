package com.agonyengine.noise;

import com.agonyengine.noise.image.ImageFactory;
import com.agonyengine.noise.impl.FbmNoise;
import com.agonyengine.noise.impl.FbmParameters;

import java.io.IOException;

public class Main {
    public static void main(String ... args) throws IOException {
        FbmParameters parameters = new FbmParameters(
                1.0,
                1.0,
                2.0,
                0.5,
                3
        );
        OffsetNoiseGenerator noise = new FbmNoise(parameters);
//        NoiseGenerator noise = new DiamondSquareNoise();
//        NoiseGenerator noise = new OpenSimplex2Noise();
//        NoiseMap map = noise.generate("World Map", 257, 257);
        NoiseMap elevation = noise.generate("Elevation Map", 257, 257);
        NoiseMap moisture = noise.generate("Moisture Map", 257, 257);

        // Generate a second map immediately to the right (east?) of the first one.
        NoiseMap elevationRight = noise.generate("Elevation Map", 257, 257, 257, 0);
        NoiseMap moistureRight = noise.generate("Moisture Map", 257, 257, 257, 0);

//        ImageFactory.drawMono(map);
//        ImageFactory.drawQuarters(map);
        ImageFactory.drawBiomes(elevation, moisture);
        ImageFactory.drawBiomes(elevationRight, moistureRight);
    }
}
