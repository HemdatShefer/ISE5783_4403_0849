package renderer;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("testImage", 800, 500);

        // Write some pixels to the image
        for (int y = 0; y < imageWriter.getNy(); y++) {
            for (int x = 0; x < imageWriter.getNx(); x++) {
                if (x % 40 == 0 || y % 25 == 0) { // Creating a grid pattern
                    imageWriter.writePixel(x, y, new primitives.Color(255, 0, 0)); // Red color for grid lines
                } else {
                    imageWriter.writePixel(x, y, new primitives.Color(0, 0, 255)); // Blue color for background
                }
            }
        }

        // Write the image to file
        imageWriter.writeToImage();
    }
    /**
    @Test
    void writePixel() {
        ImageWriter imageWriter = new ImageWriter("testImage", 800, 500);

        // Write a specific pixel
        int xIndex = 400;
        int yIndex = 250;
        primitives.Color expectedColor = new primitives.Color(255, 255, 0); // Yellow color
        imageWriter.writePixel(xIndex, yIndex, expectedColor);

        // Check if the pixel color is as expected
          primitives.Color actualColor = new primitives.Color(imageWriter.getImage().getRGB(xIndex, yIndex));
        assertEquals(expectedColor, actualColor);
    }
    **/
}
