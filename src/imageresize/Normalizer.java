package imageresize;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author eduardo
 */
public class Normalizer {
    /*public static void run(BufferedImage image) {
        int[] pixels = image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), (int[]) null);
        
        BufferedImage.
        
        switch(image.getType()) {
            
                
        }
    }*/
    
    public static BufferedImage run2(String filePath) throws IOException {
        BufferedImage in = ImageIO.read(new File(filePath));
        BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(in, 0, 0, in.getWidth(), in.getHeight(), null);
        g.dispose();
        
        return newImage;
    }
    
    public static BufferedImage run(String filePath) throws IOException {
        BufferedImage image = null;
        
        try ( //Create input stream
            ImageInputStream input = ImageIO.createImageInputStream(filePath) // Close stream in finally block to avoid resource leaks
        ) {
            // Get the reader
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

            if(!readers.hasNext()) {
                throw new IllegalArgumentException("No reader for: " + filePath); // Or simply return null
            }

            ImageReader reader = readers.next();

            try {
                // Set input
                reader.setInput(input);

                // Configure the param to use the destination type you want
                ImageReadParam param = reader.getDefaultReadParam();
                param.setDestinationType(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB));

                // Finally read the image, using settings from param
                image = reader.read(0, param);
            }
            finally {
                // Dispose reader in finally block to avoid memory leaks
                reader.dispose();
            }
        }
        
        return image;
    }
}
