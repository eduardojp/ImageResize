package imageresize;

import java.awt.image.BufferedImage;

/**
 *
 * @author eduardo
 */
public class Scale {
    public static int[] getScaledGreyPixels(BufferedImage bufferedImage, int largestScaledSide) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixels = bufferedImage.getRaster().getPixels(0, 0, width, height, (int[]) null);
        int newWidth = width, newHeight = height;
        
        if(width > largestScaledSide || height > largestScaledSide) {
            if(width > height) {
                newWidth = largestScaledSide;
                newHeight = (int) (largestScaledSide * ((double) height/ (double) width) + 0.5);
            }
            else {
                newWidth = (int) (largestScaledSide * ((double) width/ (double) height) + 0.5);
                newHeight = largestScaledSide;
            }
        }
        
        System.out.println("Mini size: " + newWidth + " x " + newHeight);

        int[] scaledGreyPixels = new int[newWidth * newHeight];
        double stepX = (double) width / (double) newWidth;
        double stepY = (double) height / (double) newHeight;

        for(int x = 0; x < newWidth; x++) {
            for(int y = 0; y < newHeight; y++) {
                int x_ = (int) (x*stepX + stepX/2 + 0.5);
                int y_ = (int) (y*stepY + stepY/2 + 0.5);
                int x_y = x_ + y_*width;
                
                int r = pixels[x_y*3];
                int g = pixels[x_y*3+1];
                int b = pixels[x_y*3+2];
                int lum = (int) (0.299*r + 0.587*g + 0.114*b);

                scaledGreyPixels[x + y*newWidth] = (int) (lum + 0.5);
            }
        }

        return scaledGreyPixels;
    }
}