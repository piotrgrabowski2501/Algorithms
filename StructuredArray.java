//Piotr Grabowski - 5
import java.util.Scanner;

public class Source {
    public static void main(String args[]) {

        Scanner number = new Scanner(System.in);

        int numberOfSets = number.nextInt();

        for (int sets = 0; sets < numberOfSets; sets++) {
            int numberOfElements = number.nextInt();

            int[] arrayOfElements = new int[numberOfElements];

            for(int elements = 0; elements < numberOfElements; elements++)
                arrayOfElements[elements] = number.nextInt();

            int numberOfQueries = number.nextInt();

            for(int query = 0; query < numberOfQueries; query++) {
                int searchNumber1 = number.nextInt();
                int firstSeekingElement = BinarySearch(searchNumber1, arrayOfElements, numberOfElements); 
                int firstAfterSequenceSeekingElements = BinarySearch(searchNumber1 + 1,
                        arrayOfElements, numberOfElements); 
                System.out.print("(" + firstSeekingElement + "," + (firstAfterSequenceSeekingElements - firstSeekingElement) + ")");
                if(query != numberOfQueries - 1) System.out.print(" ");
            }
            System.out.println();
            System.out.print(arrayOfElements[0] + " "); 

            int counter = 1;
            for(int i = 1; i < numberOfElements; i++) {
                if(arrayOfElements[i] != arrayOfElements[i - 1] && counter < 200) {
                    if(counter == 50 || counter == 100 || counter == 150) 
                        System.out.println();
                    counter++;
                    System.out.print(arrayOfElements[i] + " ");
                }
            }
            System.out.println();
        }
    }

    private static int BinarySearch(int searchNumber, int[] arrayOfElements, int numberOfElements) {
        int right = numberOfElements -1; //indeksy w arrayOfElementslicy sa od 0
        int left = 0;
        int medium;

        while (left <= right) {
            medium =(left + right) / 2;

            if(arrayOfElements[medium] == searchNumber) {
                if(medium == 0)
                    return medium;       
                else {
                    if(arrayOfElements[medium - 1] != searchNumber) 
                        return medium;                   
                    else
                        right = medium - 1;    
                }
            }
            else if(arrayOfElements[medium] > searchNumber) {
                right = medium - 1;
            }
            else if(arrayOfElements[medium] < searchNumber) {
                left = medium + 1;
            }
        }
        return left;
    }
}

/*
test.in
6
19
-9684 -8676 -5175 -4481 -3203 -3054 -1317 -1317 562 1208 1208 1208 1208 1214 1784 4063 4063 4063 4461
28
80467 1208 1208 20299 1784 4063 -2306 4063 4461 -118744 -8676 -5175 -148289 -3203 -3054 -27196 -1317 562 163839 1208 1208 -7546 1214 1784 -158827 4063 4063 -56163
18
-9790 -9790 -8806 -5737 -5737 -5737 -173 -173 1740 2082 2707 5787 6891 7431 7455 8480 8802 8841
1
54193
10
-7566 -7566 -2012 -148 824 1946 2396 2396 6606 6606
23
-4294 -7566 -2012 5804 824 1946 -945 2396 6606 67007 -7566 -7566 -66377 -148 824 79513 2396 2396 21629 6606 -7566 59369 -2012
15
-9731 -9731 -9291 -4263 -4263 -2170 -736 3744 3744 7670 8019 8506 8506 9040 9374
14
-43753 8506 8506 -28492 9374 -9731 -3743 -9291 -4263 46988 -2170 -736 137511 3744
16
-9949 -8733 -8678 -7995 -4545 -2695 -2695 -1692 -1692 -1068 515 993 1763 1763 4987 4987
5
146000 993 1763 -97287 4987
10
0 0 0 0 0 0 0 0 0 1
4
0 -1 1 2

test.out
 (19,0) (9,4) (9,4) (19,0) (14,1) (15,3) (6,0) (15,3) (18,1) (0,0) (1,1) (2,1) (0,0) (4,1) (5,1) (0,0) (6,2) (8,1) (19,0) (9,4) (9,4) (2,0) (13,1) (14,1) (0,0) (15,3) (15,3) (0,0)
-9684 -8676 -5175 -4481 -3203 -3054 -1317 562 1208 1214 1784 4063 4461
(18,0)
-9790 -8806 -5737 -173 1740 2082 2707 5787 6891 7431 7455 8480 8802 8841
(2,0) (0,2) (2,1) (8,0) (4,1) (5,1) (3,0) (6,2) (8,2) (10,0) (0,2) (0,2) (0,0) (3,1) (4,1) (10,0) (6,2) (6,2) (10,0) (8,2) (0,2) (10,0) (2,1)
-7566 -2012 -148 824 1946 2396 6606
(0,0) (11,2) (11,2) (0,0) (14,1) (0,2) (5,0) (2,1) (3,2) (15,0) (5,1) (6,1) (15,0) (7,2)
-9731 -9291 -4263 -2170 -736 3744 7670 8019 8506 9040 9374
(16,0) (11,1) (12,2) (0,0) (14,2)
-9949 -8733 -8678 -7995 -4545 -2695 -1692 -1068 515 993 1763 4987
(0,9) (0,0) (9,1) (10,0)
0 1
 */
