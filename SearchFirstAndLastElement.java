//Piotr Grabowski - 5
import java.util.Scanner;

public class SearchFirstAndLastElement {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfSets = scan.nextInt(); 
        int n, m; //dimensions data
        int seekingElement;

        for (int i = 0; i < numberOfSets; i++) {
            n = scan.nextInt();
            m = scan.nextInt();
            int[][] arrayOfData = new int[n][m];
            int j = -1; //wczytuje arrlice wierszami
            while (++j < n) {
                int k = -1;
                while (++k < m) arrayOfData[j][k] = scan.nextInt();
            }

            seekingElement = scan.nextInt(); 
            RecursionFirstElement(arrayOfData, n, 0, m, seekingElement);
            RecursionLastElement(arrayOfData,n - 1, n, m, seekingElement);
            IterationFirstElement(arrayOfData, n, m, seekingElement);
            IterationLastElement(arrayOfData, n, m, seekingElement);
            System.out.println("---");
        }

    }

    private static void IterationFirstElement(int[][] arr, int n, int m, int seekingNumber) {
        int tmpIndexRow = 0;
        int indexOfRow;
        int indexColumn;
        for (int i = 0; i < n; i++) {

            int right = m - 1; 
            int left = 0;
            int medium;

            while (left <= right) {
                medium = (left + right) / 2;
                if (arr[tmpIndexRow][medium] == seekingNumber) { 
                    if (medium == 0) {
                        indexOfRow = tmpIndexRow;
                        indexColumn = medium;
                        System.out.println("IterationFirstElement: " + seekingNumber + " w " + "(" + indexOfRow + "," +
                                indexColumn + ")");
                        return; 
                    }
                    else {
                        if (arr[tmpIndexRow][medium - 1] != seekingNumber) { 
                            indexOfRow = tmpIndexRow;
                            indexColumn = medium;
                            System.out.println("IterationFirstElement: " + seekingNumber + " w " + "(" + indexOfRow + "," +
                                    indexColumn + ")");
                            return;
                        } else
                            right = medium - 1;  //zatem to nie pierwsza
                    }
                }
                else if (arr[tmpIndexRow][medium] > seekingNumber)
                    right = medium - 1;
                else if (arr[tmpIndexRow][medium] < seekingNumber)
                    left = medium + 1;
            }

            tmpIndexRow++; //przesuwamy sie o wiersz w dol
            if (tmpIndexRow == n) {
                System.out.println("IterationFirstElement: nie ma " + seekingNumber);
                return;
            }
        }
    }

    private static void IterationLastElement(int[][] arr, int n, int m, int seekingNumber) {
        int indexRow = n - 1; 
        int medium = -1, left, right;
        while (indexRow >= 0) { e
            left = 0;
            right = m - 1;
            while (left <= right) {
                medium = (left + right) / 2;

                if (arr[indexRow][medium] == seekingNumber) {
                    if (medium == m - 1) 
                        break;
                    else {
                        if (arr[indexRow][medium + 1] == seekingNumber) 
                            left = medium + 1;
                        else
                            break;    
                    }
                }
                else if (arr[indexRow][medium] > seekingNumber)
                    right = medium - 1;
                else
                    left = medium + 1;
            }
            if (left > right)    
                indexRow--;
            else      
                break;
        }
        if (indexRow == -1) 
            System.out.println("IterationLastElement: nie ma " + seekingNumber);
        else
            System.out.println("IterationLastElement: " + seekingNumber + " w (" + indexRow + "," + medium + ")");
    }

    private static void RecursionFirstElement(int[][] arr, int n, int currentLine, int m, int seekingNumber) {
        int w = currentLine;
        int column = seekingFirstElementRec(arr, 0, m - 1, seekingNumber, w);  
        if (column == -1 && w < n - 1) {
        
            w++;
            RecursionFirstElement(arr, n, w, m, seekingNumber);
        }
        else if (column == -1 && w == n - 1)
            System.out.println("RecursionFirstElement: nie ma " + seekingNumber);
        else
            System.out.println("RecursionFirstElement: " + seekingNumber + " w (" + currentLine + ","
                    + column + ")");
    }

    private static int seekingFirstElementRec(int[][] arr, int left1, int right1, int seekingNumber, int currentLine1) {
        int medium;
        medium = (left1 + right1) / 2;

        if (left1 > right1) 
            return -1;
        else {
            if (arr[currentLine1][medium] == seekingNumber) {
            
                if (medium == 0) 
                    return medium;
                else {
                    if (arr[currentLine1][medium - 1] == seekingNumber) 
                        return seekingFirstElementRec(arr, left1, medium - 1, seekingNumber, currentLine1);
                    else 
                        return medium;
                }
            }
            else if (arr[currentLine1][medium] > seekingNumber) 
                return seekingFirstElementRec(arr, left1, medium - 1, seekingNumber, currentLine1);
            else if (arr[currentLine1][medium] < seekingNumber) 
                return seekingFirstElementRec(arr, medium + 1, right1, seekingNumber, currentLine1);
        }
        return -1;  
    }

    private static void RecursionLastElement(int[][] arr, int currentLine, int n, int m, int seekingNumber) {
        int w = currentLine;
        int column = seekingLastElementRec(arr, 0, m - 1, seekingNumber, w, m);  //rekurencyjny binary search
        if (column == -1 && w > 0) {  
            w--;
            RecursionLastElement(arr, w, n, m, seekingNumber);
        }
        else if (column == -1 && w == 0) 
            System.out.println("RecursionLastElement: nie ma " + seekingNumber);
        else
            System.out.println("RecursionLastElement: " + seekingNumber + " w (" + currentLine + ","
                    + column + ")");
    }

    private static int seekingLastElementRec(int[][] arr, int left1, int right1, int seekingNumber, int currentLine1, int numberInColumn) {
        int medium;
        medium = (left1 + right1) / 2;

        if(left1 > right1)
            return -1; //warunek stopu
        else {
            if (arr[currentLine1][medium] == seekingNumber) {
                if (medium == numberInColumn - 1) 
                    return medium;
                else {
                    if (arr[currentLine1][medium + 1] == seekingNumber)  
                        return seekingLastElementRec(arr, medium + 1, right1, seekingNumber, currentLine1, numberInColumn);
                    else
                        return medium;
                }
            }
            else if (arr[currentLine1][medium] > seekingNumber)
                return seekingLastElementRec(arr, left1, medium - 1, seekingNumber, currentLine1, numberInColumn);
            else if (arr[currentLine1][medium] < seekingNumber)
                return seekingLastElementRec(arr, medium + 1, right1, seekingNumber, currentLine1, numberInColumn);
        }
        return -1; 
    }
}

/*
test.in
4
1 6
10 10 20 20 20 30
20
3 4
10 10 10 10
10 20 20 30
20 20 20 40
40
2 4
10 20 20 30
20 30 30 40
10
2 4
10 20 20 30
10 20 30 40
10

test.out
RecursionFirstElement: 20 w (0,2)
RecursionLastElement: 20 w (0,4)
IterationFirstElement: 20 w (0,2)
IterationLastElement: 20 w (0,4)
---
RecursionFirstElement: 40 w (2,3)
RecursionLastElement: 40 w (2,3)
IterationFirstElement: 40 w (2,3)
IterationLastElement: 40 w (2,3)
---
RecursionFirstElement: 10 w (0,0)
RecursionLastElement: 10 w (0,0)
IterationFirstElement: 10 w (0,0)
IterationLastElement: 10 w (0,0)
---
RecursionFirstElement: 10 w (0,0)
RecursionLastElement: 10 w (1,0)
IterationFirstElement: 10 w (0,0)
IterationLastElement: 10 w (1,0)
---
 */
