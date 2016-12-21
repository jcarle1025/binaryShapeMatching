import java.util.*;

public class ConnectedComponents {
    ArrayList<Component> arr = new ArrayList<Component>();
    int[] dx = {+1, 0, -1, 0}; //the four directions that we are going to go 
    int[] dy = {0, +1, 0, -1};
    int row = 0;
    int col = 0;
    int[][] array;
    int[][] label; //we keep track of overlay label of the array

    public ConnectedComponents(int[][] arr) {
        this.row = arr.length;
        this.col = arr[0].length;
        array = arr;
        label = new int[row][col];
    }

    public void neighborLook(int x, int y, int cur, Component cp) {
        if (x < 0 || x == row) return; //would produce index of out bounds error
        if (y < 0 || y == col) return; 
        if (label[x][y] != 0 || array[x][y] != 1) return; //not a 1 in the main array or it has already been processed

        label[x][y] = cur; //if not, set the value to the current labeling value (i.e. 3)

        if (cp.getXMax() == -100) { //check if the value would be a new max/min for our component
            cp.setXMax(x);
        } else {
            if (cp.getXMax() < x) {
                cp.setXMax(x);
            }
        }

        if (cp.getXMin() == -100) {
            cp.setXMin(x);
        } else {
            if (cp.getXMin() > x) {
                cp.setXMin(x);
            }
        }

        if (cp.getYMax() == -100) {
            cp.setYMax(y);
        } else {
            if (cp.getYMax() < y) {
                cp.setYMax(y);
            }
        }

        if (cp.getYMin() == -100) {
            cp.setYMin(y);
        } else {
            if (cp.getYMin() > y) {
                cp.setYMin(y);
            }
        }


        int i = 0;

        for (i = 0; i < 4; ++i) {
            neighborLook(x + dx[i], y + dy[i], cur, cp); //call DFS on the neighbors
        }
    }

    public void find_components() {
        int component = 0;
        for (int i = 0; i < row; ++i){
            for (int j = 0; j < col; ++j){
            	if (label[i][j] == 0 && array[i][j] == 1) { //has not been found as a connected component 
                    int newComp = ++component; //iterate for new labelling value

                    Component cp = new Component(newComp); //create a Component for this labeled region
                    arr.add(cp); //add it to our array

                    neighborLook(i, j, newComp, cp); //start DFS of neighbors
                }
            }
        }
        processArr(arr);
    }

    public void processArr(ArrayList<Component> arr) {
        for (int i = 0; i < arr.size(); i++) {
            arr.get(i).setSubArray(array); //once we are done, we need to set the array value for the component
        }
    }

    public Component getLargest() { //most likely, we will only care about the main component. The rest will probably be noise
        int max = 0;
        int area = -100000; //set the area initially as a very negative, so we get max at least once;
        if (arr.size() == 0) { 
            return null;
        } else {
            for (int i = 0; i < arr.size(); i++) {
                int x = arr.get(i).getXMax() - arr.get(i).getXMin();
                int y = arr.get(i).getYMax() - arr.get(i).getYMin();
                if (x * y > area) {
                    area = x * y; // if area is greater then previous, replace value
                    max = i;
                }
            }
            return arr.get(max);
        }
    }
}