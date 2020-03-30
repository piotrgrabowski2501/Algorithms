import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ColouringGraph {
    private static boolean previouscolours(int position, int actualColour, int[][] A, int[] colours) {
        for(int j = 1; j < position; j++) {
            if(A[position][j] == 1) {
                if(colours[j] == actualColour)
                    return false;
            }
        }
        return true;
    }

    private static boolean colourGraph(int[][] A, int position, int numberOfcoloururs, int[] colours, int numberOfVertices) {
        if(position == numberOfVertices+1)
            return true;
        else {
            for(int actualColour = 1; actualColour <= numberOfcoloururs; actualColour++) {
                if(previouscolours(position, actualColour, A, colours)) {
                    colours[position] = actualColour;
                    if(colourGraph(A, position+1, numberOfcoloururs, colours, numberOfVertices))
                        return true;
                }
            }
        }
        return false;
    }

// true if is possible to colour graph with k colours and print
    private static boolean isColourable(int[][] A, int numberOfcolours, int numberOfVertices) {
        int[] colours = new int[numberOfVertices + 1];
        colours[1] = 1;
        if (colourGraph(A, 2, numberOfcolours, colours,numberOfVertices)) {
            print(numberOfVertices, numberOfcolours, colours);
            return true;
        }
        return false;
    }

    private static void greedyAlgorithm(int[][] A, int numberOfVertices) {
        int[] arrayWithColours = new int[numberOfVertices+1];
        int[] tempArray = new int[numberOfVertices+1];
        for( int i = 1 ; i <= numberOfVertices; i++) {
            arrayWithColours[i] = 0;
            tempArray[i] = 0;
        }

        for( int i = 1 ; i <= numberOfVertices; i++) {
            for( int j = 1 ; j <= numberOfVertices; j++)
                tempArray[j] = 0;
            for( int j = 1 ; j <= numberOfVertices; j++) {
                if(i != j) {
                    if(A[i][j] == 1)
                        tempArray[arrayWithColours[j]] = 1;
                }
            }
            for( int j = 1 ; j <= numberOfVertices; j++) {
                if(tempArray[j] == 0) {
                    arrayWithColours[i] = j;
                    break;
                }
            }
        }
        System.out.println();
        System.out.println("Greedy Algorithm:");

        int colours = 0;
        for( int i = 1 ; i <= numberOfVertices; i++) {
            if(arrayWithColours[i] > colours)
                colours = arrayWithColours[i];
        }
        print(numberOfVertices, colours, arrayWithColours);
    }

    private static void print(int numberOfVertices, int numberOfcolours, int[] arr) {
        System.out.println("It is possible to organise all exams in minimum " + numberOfcolours + " days:");
        for (int i = 1; i <= numberOfcolours; i++){
            System.out.print("Day " + i + " : ");
            for (int j = 1; j <= numberOfVertices; j++) {
                if(arr[j] == i)
                    System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner nameOfFile = new Scanner(System.in);

        String name = nameOfFile.next();

        Scanner scan = new Scanner(new File(name));

        int numberOfVertices = scan.nextInt();
        System.out.println("Number of Exams: " + numberOfVertices);
        System.out.println();
        Vector array = new Vector();
        int temp;

        while(scan.hasNextLine()) {
            try {
                temp = scan.nextInt();
                array.add(temp);
            }catch(Exception e){}
        }

        int[][] A = new int[numberOfVertices + 1][numberOfVertices + 1];
        for(int i = 1; i <= numberOfVertices; i++) {
            for (int j = 1; j <= numberOfVertices; j++) {
                if(i != j)
                    A[i][j] = 1;
            }
        }

        for(int i = 0; i < array.size(); i+=2) {
            A[(int)array.get(i)][(int)array.get(i+1)] = 0;
            A[(int)array.get(i+1)][(int)array.get(i)] = 0;
        }
        System.out.println("Optimal algorithm:");
        for (int colour = 1; colour <= numberOfVertices; ++colour) {
            if (isColourable(A, colour, numberOfVertices))
                break;
            else
                System.out.println("It is not possible to organise exams in " + colour + " day(-s)");
        }

        greedyAlgorithm(A, numberOfVertices);
    }
}
