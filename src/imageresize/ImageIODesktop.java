package imageresize;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ImageIODesktop {
    public static void saveBitmapToPNG(BufferedImage bufferedImage, String filePath) {
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.mkdirs();
            }
            javax.imageio.ImageIO.write(bufferedImage, "PNG", file);
        }
        catch(Exception e) {
            System.out.println("Erro ao salvar a imagem!");
            System.out.println(e.getMessage());
        }
    }

    public static void saveGreyPixels(int[] pixels, int width, int height, String filePath) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = bufferedImage.getRaster();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int x_y = x + y*width;
                
                raster.setSample(x, y, 0, pixels[x_y]);
                raster.setSample(x, y, 1, pixels[x_y]);
                raster.setSample(x, y, 2, pixels[x_y]);
            }
        }

        saveBitmapToPNG(bufferedImage, filePath);
    }
    
    public static void saveColoredPixels(int[] pixels, int width, int height, String filePath) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = bufferedImage.getRaster();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int x_y = (x + y*width)*3;
                
                raster.setSample(x, y, 0, pixels[x_y]);
                raster.setSample(x, y, 1, pixels[x_y+1]);
                raster.setSample(x, y, 2, pixels[x_y+2]);
            }
        }

        saveBitmapToPNG(bufferedImage, filePath);
    }

    public static void saveColoredPixels(int[] pixels, int x0, int y0, int x1, int y1, int width, String dir, String filePath) {
        BufferedImage bufferedImage = new BufferedImage(x1-x0+1, y1-y0+1, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = bufferedImage.getRaster();

        for(int y = y0; y <= y1; y++) {
            for(int x = x0; x <= x1; x++) {
                int x_y = x + y*width;
                
                raster.setSample(x, y, 0, pixels[x_y*3]);
                raster.setSample(x, y, 0, pixels[x_y*3+1]);
                raster.setSample(x, y, 0, pixels[x_y*3+2]);
            }
        }

        saveBitmapToPNG(bufferedImage, filePath);
    }

    public static File openFile(final String extension, File dir) {
        FileFilter filter = new FileFilter() {
            @Override
            public String getDescription() {
                return extension;
            }

            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(extension);
            }
        };

        JFileChooser jc = new JFileChooser(dir);
        jc.setFileFilter(filter);
        jc.setDialogType(JFileChooser.OPEN_DIALOG);

        int opt = jc.showDialog(null, "Abrir");

        if(opt != JFileChooser.APPROVE_OPTION)
            return null;

        return jc.getSelectedFile();
    }
}
