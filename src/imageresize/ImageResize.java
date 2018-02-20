package imageresize;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.*;

/**
 *
 * @author eduardo
 */
public class ImageResize {

    public static void main(String[] args) {
        String filePath = "1.JPG";
//        String filePath = "red.png";
//        int targetWidth = 600;
//        int targetHeight = 800;
        int targetWidth = 2277;
        int targetHeight = 2277;
        
        if(filePath.isEmpty()) {
            System.out.println("Specify a folder or a file path");
        }
        if(targetWidth <= 0 || targetHeight <= 0) {
            System.out.println("Dimensions should be positive numbers");
        }
        
        System.out.println("args.length: " + args.length);
        
        if(args.length > 0) {
            
        }
        
        BufferedImage image;
        
        try {
            image = Normalizer.run2(filePath); //ImageIO.read(new File(filePath));
            
            System.out.println("Original size: " + image.getWidth() + " x " + image.getHeight());
            
            int[] miniPixels = Scale.getScaledGreyPixels(image, 100);
            int miniWidth = 67;
            int miniHeight = 100;
                    
            for(int x = 0; x < miniWidth; x++) {
                for(int y = 0; y < miniHeight; y++) {
                    System.out.println(miniPixels[x + y*miniWidth]);
                }
            }
            
//            //Mini image
//            BufferedImage miniImage = (originalWidth > 100 || originalHeight > 100) ?
//                Scalr.resize(image, Method.SPEED, 100, OP_ANTIALIAS, OP_BRIGHTER) :
//                image;
//            int miniWidth = miniImage.getWidth();
//            int miniHeight = miniImage.getHeight();
            
            //Get mass center
            int[] massCenter = Sobel.getMassCenter(miniPixels, miniWidth, miniHeight);
            //int[] massCenter = Edges.getMassCenter(miniPixels, miniWidth, miniHeight);
            miniPixels[massCenter[0] + massCenter[1]*miniWidth] = 255;            
            
            massCenter[0] *= (double) image.getWidth()/ (double) miniWidth;
            massCenter[1] *= (double) image.getHeight()/ (double) miniHeight;
            
            ImageIODesktop.saveGreyPixels(miniPixels, miniWidth, miniHeight, "./border.png");
            
            System.out.println((double) image.getWidth() / (double) miniWidth);
            System.out.println((double) image.getHeight()/ (double) miniHeight);
            
            System.out.println("("+massCenter[0]+","+massCenter[1]+")");
            
            int[] originalPixels = image.getRaster().getPixels(massCenter[0]-targetWidth/2, massCenter[1]-targetHeight/2, targetWidth, targetHeight, (int[]) null);
            ImageIODesktop.saveColoredPixels(originalPixels, targetWidth, targetHeight, "./marked.png");
        }
        catch(IOException e) {
            System.out.println("Could not open " + filePath);
        }
    }
    
    public static BufferedImage createResizedImage(BufferedImage img) {
        img.getWidth();

        img = Scalr.resize(img, Method.SPEED, 125, OP_ANTIALIAS, OP_BRIGHTER);

        // Let's add a little border before we return result.
        //return pad(img, 4);
        return img;
    }
}