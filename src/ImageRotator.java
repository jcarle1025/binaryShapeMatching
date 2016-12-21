import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by aniketsaoji on 12/7/16.
 */
public class ImageRotator {
    public int WIDTH;
    public int LENGTH;
    public int MIDDLE;
    public int NUMBER_OF_DIAGONALS;


    public ImageRotator(int WIDTH, int LENGTH, int NUMBER_OF_DIAGONALS) {
        this.WIDTH = WIDTH;
        this.LENGTH = LENGTH;
        this.MIDDLE = WIDTH/2;
        this.NUMBER_OF_DIAGONALS = NUMBER_OF_DIAGONALS;
    }

    public int toCartesianX(int x) {
        return x - MIDDLE;
    }
    public int toCartesianY(int y) {
        return MIDDLE - y;
    }

    public int toX(int cartesianX) {
        return cartesianX + MIDDLE;
    }

    public int toY(int cartesianY) {
//        if (cartesianY >= 0)
//            return cartesianY + MIDDLE;
//        else
//            return cartesianY + MIDDLE;
        return MIDDLE - cartesianY;
    }


    public ArrayList<String> createDiagonals(int [][] array) {
        ArrayList<String> diagonals = new ArrayList<>();
        int listCount = 0;

        for (int theta = 0; theta < 360; theta = theta + 360/NUMBER_OF_DIAGONALS) {
            diagonals.add("");
            for (int i = -MIDDLE; i < MIDDLE; i++) {
                int coordinatesX = (int) Math.round(Math.cos(Math.toRadians(theta))*i);
                int coordinatesY = (int) Math.round(Math.sin(Math.toRadians(theta))*i);
//                System.out.println(coordinatesX + " " + coordinatesY + " INTO " + toX(coordinatesX) + " " + toY(coordinatesY));
                if (array[toY(coordinatesY)][toX(coordinatesX)] == 1) {
                    diagonals.set(listCount, diagonals.get(listCount) + "1");
                }
                else {
                    diagonals.set(listCount, diagonals.get(listCount) + "0");
                }
            }
            listCount++;

        }
        return diagonals;
    }

    public int getStreak(String s){
        int maxStreak = 0;
        int streak = 0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '1'){
                streak ++;
                if(streak > maxStreak)
                    maxStreak = streak;
            }
            else
                streak =0;
        }
        return maxStreak;
    }

    public int getLongest(ArrayList<String> strings){
        String maxString = new String();
        int maxStreak = 0;
        for(String s : strings){
            int temp = getStreak(s);
            if(temp > maxStreak){
                maxStreak = temp;
                maxString = s;
            }
        }
        return strings.indexOf(maxString);
    }

    public void printDiagonals(ArrayList<String> diagonals) {
        int count = 0;
        int currentCount = 0;
        for (String line : diagonals) {
            System.out.println(line);
        }
    }

    public int[][] rotateImage(double angle, int [][] twoDArray) {
//        angle = 360 - angle;
        int[][] rotatedImage = new int[WIDTH][LENGTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                int newX = toX((int) Math.round(toCartesianX(j)*Math.cos(Math.toRadians(angle)) - toCartesianY(i)*Math.sin(Math.toRadians(angle))));
                int newY = toY((int) Math.round(toCartesianX(j)*Math.sin(Math.toRadians(angle)) + toCartesianY(i)*Math.cos(Math.toRadians(angle))));
//                System.out.println(newX + " " + newY + " from " + j + " " + i);
                if ((newX > 0) && (newY > 0) && (newX < WIDTH) && (newY < LENGTH))
                    rotatedImage[newY][newX] = twoDArray[i][j];
            }
        }
        return rotatedImage;
    }
    public int[][] rotate(int [][] image) {
        ArrayList<String> myList = createDiagonals(image);
        double angle = getLongest(myList) * 10;
//        System.out.println(angle);
        return rotateImage(angle, image);
    }
}