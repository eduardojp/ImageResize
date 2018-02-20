package imageresize;

public class Edges {
    private static int X = 0;
    private static int Y = 1;
    
    public static int[] getMassCenter(int[] pixels, int width, int height) {
        int conv;
        int x0, x1, y0, y1;
        int luminance_x0_y0, luminance_x0_y, luminance_x0_y1;
        int luminance_x_y0, luminance_x_y, luminance_x_y1;
        int luminance_x1_y0, luminance_x1_y, luminance_x1_y1;
        
        double weight;
        double totalWeight = 0;
        double[] meanCoord = new double[2];

        //Varredura da imagem para obtençãoo dos valores máximos e mínimos
        for(int x = 0; x < width; x++) {
            x0 = x-1 < 0 ? 0 : x-1;
            x1 = x+1 >= width ? width-1 : x+1;
            
            for(int y = 0; y < height; y++) {
                y0 = y-1 < 0 ? 0 : y-1;
                y1 = y+1 >= height ? height-1 : y+1;

                luminance_x_y = pixels[x + y*width];
                luminance_x0_y0 = pixels[x0 + y0*width];
                luminance_x0_y = pixels[x0 + y*width];
                luminance_x0_y1 = pixels[x0 + y1*width];
                luminance_x_y0 = pixels[x + y0*width];
                luminance_x_y1 = pixels[x + y1*width];
                luminance_x1_y0 = pixels[x1 + y0*width];
                luminance_x1_y = pixels[x1 + y*width];
                luminance_x1_y1 = pixels[x1 + y1*width];

                conv = luminance_x0_y0 + luminance_x0_y + luminance_x0_y1 +
                    luminance_x_y0 - 8*luminance_x_y + luminance_x_y1 +
                    luminance_x1_y0 + luminance_x1_y + luminance_x1_y1;
                
                weight = Math.abs(conv);
                totalWeight += weight;

                meanCoord[X] += x * weight;
                meanCoord[Y] += y * weight;
                
                System.out.println("y: "+y+" , "+weight);
                System.out.println("pixel: " + Integer.toHexString(pixels[x + y*width]));
                System.out.println((meanCoord[X]/totalWeight) + "," + (meanCoord[Y]/totalWeight));
            }
        }
        
        return new int[] {
            (int) (meanCoord[X] / totalWeight + 0.5),
            (int) (meanCoord[Y] / totalWeight + 0.5)
        };
    }
}