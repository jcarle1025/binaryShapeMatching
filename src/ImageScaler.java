/**
 * Created by aniketsaoji on 12/14/16.
 */
import java.io.File;
import java.util.Scanner;

/**
 * Created by aniketsaoji on 11/29/16.
 */
public class ImageScaler {

    public final int LENGTH_TO_SCALE_TO = 36;
    public final int WIDTH_TO_SCALE_TO = 36;


    public int[][] getScaledImage(int[][] original) throws java.io.FileNotFoundException{
        int w = original[0].length;
        int l = original.length;
        int [][] output = new int[LENGTH_TO_SCALE_TO][WIDTH_TO_SCALE_TO];
        double xscale = (WIDTH_TO_SCALE_TO+0.0) / w;
        double yscale = (LENGTH_TO_SCALE_TO+0.0) / l;
        double threshold = 0.5 / (xscale * yscale);
        double yend = 0.0;
        for (int f = 0; f < LENGTH_TO_SCALE_TO; f++) // y on output
        {
            double ystart = yend;
            yend = (f + 1) / yscale;
            if (yend >= l)
                yend = l - 0.000001;
            double xend = 0.0;
            for (int g = 0; g < WIDTH_TO_SCALE_TO; g++) // x on output
            {
                double xstart = xend;
                xend = (g + 1) / xscale;
                if (xend >= w)
                    xend = w - 0.000001;
                double sum = 0.0;
                for (int y = (int)ystart; y <= (int)yend; ++y)
                {
                    double yportion = 1.0;
                    if (y == (int)ystart)
                        yportion -= ystart - y;
                    if (y == (int)yend)
                        yportion -= y+1 - yend;
                    for (int x = (int)xstart; x <= (int)xend; ++x) {
                        double xportion = 1.0;
                        if (x == (int)xstart)
                            xportion -= xstart - x;
                        if (x == (int)xend)
                            xportion -= x+1 - xend;
                        sum += original[y][x] * yportion * xportion;
                    }
                }
                output[f][g] = (sum > threshold) ? 1 : 0;
            }
        }
        return output;
    }
}
