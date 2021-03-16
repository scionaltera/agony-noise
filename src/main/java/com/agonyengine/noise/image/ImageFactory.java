package com.agonyengine.noise.image;

import com.agonyengine.noise.NoiseMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFactory {
    public static void drawMono(NoiseMap noiseMap) throws IOException {
        BufferedImage image = new BufferedImage(noiseMap.getWidth(), noiseMap.getHeight(), BufferedImage.TYPE_INT_ARGB);

        noiseMap.adjustScale(0.0, 1.0);

        for (int x = 0; x < noiseMap.getWidth(); x++) {
            for (int y = 0; y < noiseMap.getHeight(); y++) {
                int value = (int)(noiseMap.get(x, y) * 0xFF);
                int rgb = 0xFF000000;

                rgb = rgb | (value); // blue
                rgb = rgb | (value << 8); // green
                rgb = rgb | (value << 16); // red

                image.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(image, "PNG", new File("map" + System.currentTimeMillis() + ".PNG"));
    }

    public static void drawQuarters(NoiseMap noiseMap) throws IOException {
        BufferedImage image = new BufferedImage(noiseMap.getWidth(), noiseMap.getHeight(), BufferedImage.TYPE_INT_ARGB);

        noiseMap.adjustScale(0.0, 1.0);

        for (int x = 0; x < noiseMap.getWidth(); x++) {
            for (int y = 0; y < noiseMap.getHeight(); y++) {
                int height = (int)(noiseMap.get(x, y) * 0xFF);
                int rgb = 0xFF000000;

                if (height < 0) {
                    rgb = rgb | (0xFF << 16); // red
                } else if (height < 64) {
                    int color = height * 4;

                    rgb = rgb | (color); // blue
                } else if (height < 128) {
                    int color = height * 2;

                    rgb = rgb | (color << 8); // green
                } else if (height < 192) {
                    int color = (int)Math.ceil(height * 1.25);

                    rgb = rgb | (color << 8); // green
                    rgb = rgb | (color << 16); // red
                } else {
                    rgb = rgb | (height); // blue
                    rgb = rgb | (height << 8); // green
                    rgb = rgb | (height << 16); // red
                }

                image.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(image, "PNG", new File("map" + System.currentTimeMillis() + ".PNG"));
    }

    private static final int[][] biomeColors = new int[][] {
            { // elevation 1, moisture 1 - 6
                    0xffe9ddc7, // subtropical desert
                    0xffc4d4aa, // grassland
                    0xffa9cca4, // topical seasonal forest
                    0xffa9cca4, // topical seasonal forest
                    0xff9cbba9, // tropical rainforest
                    0xff9cbba9  // tropical rainforest
            },
            { // elevation 2
                    0xffe4e8ca, // temperate desert
                    0xffc4d4aa, // grassland
                    0xffc4d4aa, // grassland
                    0xffb4c9a9, // temperate deciduous forest
                    0xffb4c9a9, // temperate deciduous forest
                    0xffa4c4a8  // temperate rainforest
            },
            { // elevation 3
                    0xffe4e8ca, // temperate desert
                    0xffe4e8ca, // temperate desert
                    0xffc4ccbb, // shrubland
                    0xffc4ccbb, // shrubland
                    0xffccd4bb, // taiga
                    0xffccd4bb, // taiga
            },
            { // elevation 4
                    0xff999999, // scorched
                    0xffbbbbbb, // bare
                    0xffddddbb, // tundra
                    0xfff8f8f8, // snow
                    0xfff8f8f8, // snow
                    0xfff8f8f8, // snow
            }
    };

    public static void drawBiomes(NoiseMap elevation, NoiseMap moisture) throws IOException {
        if (elevation.getWidth() != moisture.getWidth() || elevation.getHeight() != moisture.getHeight()) {
            throw new IllegalArgumentException("maps must be the same size");
        }

        BufferedImage image = new BufferedImage(elevation.getWidth(), elevation.getHeight(), BufferedImage.TYPE_INT_ARGB);

        elevation.adjustScale(0.0, 3.0);
        moisture.adjustScale(0.0, 5.0);

        for (int x = 0; x < elevation.getWidth(); x++) {
            for (int y = 0; y < elevation.getHeight(); y++) {
                int e = (int)Math.ceil(elevation.get(x, y));
                int m = (int)Math.ceil(moisture.get(x, y));
                int rgb = biomeColors[e][m];

                image.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(image, "PNG", new File("map" + System.currentTimeMillis() + ".PNG"));
    }
}
