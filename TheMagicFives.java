//Piotr Grabowski - 5
import java.util.Scanner;

public class Source {

    private static void InsertionSort(int[] arrayOfElements, int left, int n){ 
        for (int i = left + 1; i < left + n; i++) {
            int tmp = arrayOfElements[i];
            int j = i - 1;

            while (j >= left && arrayOfElements[j] > tmp) {
                arrayOfElements[j + 1] = arrayOfElements[j];
                j = j - 1;
            }

            arrayOfElements[j + 1] = tmp;
        }
    }

    private static int selection(int[] arrayOfElements, int n, int left){
        int numberOfSubsets = n / 5;
        int counter = 0;
        for(int i = 0; i < numberOfSubsets; i++)
            InsertionSort(arrayOfElements, left+5*i, 5);  
        if(numberOfSubsets * 5 < n) 
            InsertionSort(arrayOfElements, left + numberOfSubsets*5, n - (5 * numberOfSubsets));
        for(int i = left; i < numberOfSubsets; i++) {  
            swap(arrayOfElements, counter, 5 * counter + 2 + left);
            counter++;
        }
        if(numberOfSubsets * 5 < n) {  
            swap(arrayOfElements, left + numberOfSubsets, (left + 5 * numberOfSubsets) + ((n - 1 - 5 * numberOfSubsets) / 2));
            numberOfSubsets++;
        }
        if(numberOfSubsets < 50)  
            InsertionSort(arrayOfElements, left, numberOfSubsets);
        else
            return selection(arrayOfElements, numberOfSubsets, left);  
        return arrayOfElements[numberOfSubsets / 2 + left]; 
    }

    private static int divide(int[] arrayOfElements, int left, int right, int median){   
        while(true) {
            while(arrayOfElements[left]<median)
                left++;

            while(arrayOfElements[right]>median)
                right--;

            if(left < right)
            {
                if(arrayOfElements[left] == arrayOfElements[right]) 
                    right--;
                swap(arrayOfElements, left, right); 
            }
            else
                break;
        }
        return left;   
    }

    private static void swap(int[] arrayOfElements, int i, int j){
        int tmp = arrayOfElements[i];
        arrayOfElements[i] = arrayOfElements[j];
        arrayOfElements[j] = tmp;
    }

    public static void main(String[] args)
    {
        Scanner number = new Scanner(System.in);

        int numberOfSets = number.nextInt();

        for (int sets = 0; sets < numberOfSets; sets++) {
            int numberOfElements = number.nextInt();

            int[] arrayOfElements = new int[numberOfElements];

            for (int elements = 0; elements < numberOfElements; elements++)
                arrayOfElements[elements] = number.nextInt();

            int nubmberOfQueries = number.nextInt();

            int median;

            for(int query = 0; query < nubmberOfQueries; query++){
                int searchElement = number.nextInt();
                if(searchElement < 1 || searchElement > numberOfElements)
                    System.out.println(searchElement + " absence");
                else if(numberOfElements < 50){
                    InsertionSort(arrayOfElements, 0, numberOfElements);
                    System.out.println(searchElement + " " + arrayOfElements[searchElement - 1]);
                }
                else {
                    int left = 0, right = numberOfElements - 1;
                    while(true){
                        median = selection(arrayOfElements, right - left + 1, left);
                        int pivot = divide(arrayOfElements, left, right, median);
                        if( searchElement - 1 < pivot )  
                            right = pivot - 1;
                        else if( searchElement - 1 > pivot )
                            left = pivot + 1;
                        else {  //zostal znaleziony k-ty element
                            median = arrayOfElements[searchElement - 1];
                            break;
                        }
                    }
                    System.out.println(searchElement + " " + median);
                }
            }
        }
    }
}

/*
test.in
3
10
1 7 3 8 4 9 0 2 8 55
3
2 0 8
53
76 97 23 61 50 93 10 68 58 32 22 76 95 93 13 79 98 58 84 47 84 89 6 9 34 29 59 54 38 92 93 50 74 2 89 65 3 69 20 61 85 79 28 56 83 34 97 8 61 22 53 58 27
8
-2 4 6 8 2 0 42 50
5
1 1 1 1 0
4
0 1 2 3
1
49
34 38 43 18 32 43 42 66 6 31 37 27 1 78 1 38 74 62 61 87 39 45 3 96 10 42 57 33 54 42 31 45 40 91 31 43 95 57 94 70 52 62 53 95 3 9 2 71 83
10
9 48 49 50 -4 5 7 1 0 100

test.out
2 1
0 absence
8 8
-2 absence
4 8
6 10
8 20
2 3
0 absence
42 84
50 95
0 absence
1 0
2 1
3 1
9 18
48 95
49 96
50 absence
-4 absence
5 3
7 9
1 1
0 absence
100 absence
 */
