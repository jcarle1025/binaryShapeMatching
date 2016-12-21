/**
 * Created by jcarle1025 on 12/11/16.
 */
public class Shape {
    public String fileOrigin;
    public int[][] image;
    public double[] features;

    //constructor
    public Shape(String fileOrigin, int[][] image){
        this.fileOrigin = fileOrigin;
        this.image = image;
    }

    //fills all values in feature array
    public double[] getFeatures(int n){
        SegmentAverager g = new SegmentAverager(image,n);
        g.populate();
        this.features = g.features;
        return features;
    }

    //allows for more efficient memory usage. shape.image gets reset
    //after bounding, scaling, and rotation are done in GlobalDriver.java
    public void setImage(int[][] image){
        this.image = image;
    }
}
