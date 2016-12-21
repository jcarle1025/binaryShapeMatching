/**
 * Created by jcarle1025 on 12/15/16.
 */
public class SegmentAverager {

    double[] features;
    int[][] image;
    int len;
    int factor;

    //constructor so any image can be divided into any given number of sub-squares
    public SegmentAverager(int [][] image, int factor){
        this.image = image;
        this.len = image.length;
        this.features = new double[factor*factor];
        for(int i = 0; i< features.length; i++)
            features[i] = 2;
        this.factor = factor;
    }

    //iterates through all segments and calls getAverages to populate the features array
    public void populate(){
        int i=0;
        int j=0;
        int increment = (len /factor);
        int count = 0;

        for(int index = 0; index< features.length; index++){
            getAverages(i, i+increment, j, j+increment);
            count ++;

            if(count == factor){
                i+= increment;
                j=0;
                count = 0;
            }

            else
                j+= increment;

        }
    }

    //calculates the concentration of 1's in the specified boundaries (sub-squares)
    public void getAverages(int i, int threshI, int j, int threshJ){
        double total = 0;
        double avg;
        int bStart=j;
        int area = (threshI-i)*(threshJ-j);

        while(i < threshI){
            while(j < threshJ){
                total += this.image[i][j];
                j++;
            }
            j=bStart;
            i++;
        }
        avg = total/area;
        features[nextOpenIndex()] = avg;
    }

    //finds the element of the features array to place the average in
    public int nextOpenIndex(){
        for(int i = 0; i< features.length; i++){
            if(features[i] > 1)
                return i;
        }
        return features.length;
    }
}