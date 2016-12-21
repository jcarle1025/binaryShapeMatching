import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by jcarle1025 on 12/7/16.
 */
public class GlobalDriver {
    String dbPath, queryPath, outputFilePath;
    String numMatches;

    public GlobalDriver(String dbPath, String queryPath, String outputFilePath, String numMatches){
        this.dbPath =  dbPath;
        this.queryPath = queryPath;
        this.outputFilePath = outputFilePath;
        this.numMatches = numMatches;
    }

    public ArrayList<Shape> getDatabase(){
        ArrayList<Shape> list = new ArrayList<>();
        File[] directory = new File(dbPath).listFiles();
        StringProcessor s = new StringProcessor();

        for(File f : directory) {
        	if(!f.getName().startsWith(".")){
        		try {
                    list.add(new Shape(f.getName(), s.processFile(f)));
                } catch(Exception e){
                    e.printStackTrace();
                }
        	}
        }
        return list;
    }
   
    public ArrayList<Shape> getQueries(){
        ArrayList<Shape> list = new ArrayList<>();
        File[] directory = new File(queryPath).listFiles();

        StringProcessor s = new StringProcessor();
        for(File f : directory) {
        	if(!f.getName().startsWith(".")){
	            try {
	                list.add(new Shape(f.getName(),s.processFile(f)));
	            } catch(Exception e){
	                e.printStackTrace();
	            }
        	}
        }
        return list;
    }

    public static void main(String[] args) throws Exception{
        long startTime = System.currentTimeMillis();
        String d = args[0];
        String q = args[1];
        String o = args[2];
        String n = args[3];

        GlobalDriver driver = new GlobalDriver(d,q,o,n);
        ArrayList<Shape> database = driver.getDatabase();
        ArrayList<Shape> queries = driver.getQueries();

        ConnectedComponents cc;
        ImageRotator ir;
        ImageScaler id = new ImageScaler();

        //bound, scale, and rotate all images, and update that query Shape with new image
        for(Shape s : queries){
            int[][] query = s.image;
            cc = new ConnectedComponents(query);
            cc.find_components();
            int[][] temp = id.getScaledImage(cc.getLargest().getSubArray());
            ir = new ImageRotator(temp.length-1, temp.length-1, 36);
            ir.rotate(temp);

            s.setImage(temp);
        }

        //bound, scale, and rotate all images, and update that database Shape with new image
        for(Shape db : database){
            int[][] data = db.image;
            cc = new ConnectedComponents(data);
            cc.find_components();
            int[][] temp = id.getScaledImage(cc.getLargest().getSubArray());
            ir = new ImageRotator(temp.length-1, temp.length-1, 36);
            ir.rotate(temp);

            db.setImage(temp);
        }


        KNN knn;
        PrintWriter writer = new PrintWriter(o, "UTF-8");

        //find the K nearest neighbors of each query image and write them to the output file
        for(Shape query : queries){
            knn = new KNN(database, query, Integer.parseInt(n));
            Collection<Shape> neighbors = knn.returnNeighbors();

            writer.print(query.fileOrigin+" ");
            for(Shape s : neighbors)
                writer.print(s.fileOrigin+" ");
            writer.println();
        }

        writer.close();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
//        System.out.println(totalTime);
    }
}