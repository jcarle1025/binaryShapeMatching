import java.io.*;

public class StringProcessor {

    //BufferedReader iterates through a file and converts lines to rows
    //of an integer array. This creates a workable object for us to
    //manipulate and compare
    public int[][] processFile(File f) throws Exception {
        int[][] image = null;
        int row = 0;
        int size = 0;

        String line;
        BufferedReader buffer = new BufferedReader(new FileReader(f));

        while ((line = buffer.readLine()) != null) {
            String[] vals = line.split("\\s+");
            if (image == null) {
                size = vals.length;
                image = new int[size][size];
            }

            for (int col = 0; col < size; col++)
                image[row][col] = Integer.parseInt(vals[col]);

            row++;
        }

        return image;
    }
}
