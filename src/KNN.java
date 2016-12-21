import java.util.*;

/**
 * Created by aniketsaoji on 12/15/16.
 */
public class KNN {

    ArrayList<Shape> database;
    int numberOfNeighbors;
    Shape shapeToCompareTo;

    public KNN(ArrayList<Shape> shapes, Shape shapeToCompareTo, int neighbors) {
        this.database = shapes;
        this.numberOfNeighbors = neighbors;
        this.shapeToCompareTo = shapeToCompareTo;
    }

    public double distance(double[] point1, double[] point2) {
        double[] dPoint = point1;
        double distance = 0;
        int count = 0;
        for (double axisPoint : point1) {
            dPoint[count] = axisPoint - point2[count];
            dPoint[count] = dPoint[count] * dPoint[count];
            count++;
        }

        for (double dAxis : dPoint) {
            distance = dAxis + distance;
        }
        return Math.sqrt(distance);
    }

    public Collection<Shape> returnNeighbors() {
        TreeMap<Double, Shape> neighbors = new TreeMap<>();
        for (Shape shape : database) {
            double distance = distance(shapeToCompareTo.getFeatures(numberOfNeighbors), shape.getFeatures(numberOfNeighbors));
            if (neighbors.size() < numberOfNeighbors)
                neighbors.put(distance, shape);
            else {
                if (distance < neighbors.lastKey()) {
                    neighbors.remove(neighbors.lastKey());
                    neighbors.put(distance, shape);
                }
            }
        }
        return neighbors.values();
    }
}
