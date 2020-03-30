//Piotr Grabowski
import java.util.Scanner;

public class Source {

    public static void main(String[] args) {
        Scanner number = new Scanner(System.in);

        int numberOfSets = number.nextInt();

        for (int sets = 0; sets < numberOfSets; sets++) {
            int DifferentElements = number.nextInt(); 
            int AllElements = number.nextInt(); 
            Heap heap = new Heap(DifferentElements, AllElements);
            int x;

            boolean theEnd = false;
            while(!theEnd){
                String option = number.next();
                switch(option){
                    case "i":
                        int k = number.nextInt();
                        for(int i = 0; i < k; i++) {
                            x = number.nextInt();
                            SingleHeap singleHeap = new SingleHeap(x, 1);
                            heap.insert(singleHeap);
                        }
                        break;
                    case "g":
                        int m = number.nextInt();
                        heap.removeMax(m);
                        break;
                    case "s":
                        heap.heapSort();
                        theEnd = true;
                        break;
                }
            }
        }
    }

    private static class SingleHeap{
        private int value;
        private int counter;
        private SingleHeap(int value, int counter){
            this.value = value;
            this.counter = counter;
        }
    }

    private static class Heap {

        private SingleHeap[] arr;
        private int maxSizeWithoutDuplicates;
        private int maxSize;  //bez duplikatow
        private int actualSizeWithoutDup = 0;
        private int actualSize;

        private Heap(int min, int max) {
            this.maxSize = min;
            this.maxSizeWithoutDuplicates = max;
            arr = new SingleHeap[maxSize];  
            actualSize = 0;
        }

        private void upheap(int t) {
            int i = (t-1)/2;  // indeks przodka elementu arr[t]
            SingleHeap tmp = arr[t];
            while ((t > 0 && arr[i].counter < tmp.counter) ||
                    (t > 0 && arr[i].counter == tmp.counter && arr[i].value < tmp.value)) {  
                arr[t] = arr[i];
                t = i ;       
                i = (i-1)/2;  
            }
            arr[t] = tmp;
        }

        private void downheap(int t) {
            int j;
            SingleHeap tmp = arr[t];
            while(t < actualSize / 2) {
                j = 2*t + 1;   
                if ((j < actualSize - 1 && arr[j].counter == arr[j + 1].counter && arr[j].value < arr[j + 1].value)
                         || (j < actualSize - 1 && arr[j].counter < arr[j + 1].counter ))  j++;        
                if ((tmp.counter > arr[j].counter) || (tmp.counter == arr[j].counter && tmp.value > arr[j].value))
                    break ; 
                arr[t] = arr[j] ;                 
                t = j ;
            }  
            arr[t] = tmp;
        }

        private void insert(SingleHeap v) {
            if(actualSizeWithoutDup < maxSizeWithoutDuplicates) {  
                boolean isHere = false;
                for(int i = 0; i < actualSize; i++){
                    if(arr[i].value == v.value){  
                        arr[i].counter++;    
                        actualSizeWithoutDup++;
                        upheap(i);    
                        isHere = true;
                        break;
                    }
                }

                if(actualSize < maxSize && !isHere) {  
                    actualSize++;
                    actualSizeWithoutDup++;  
                    arr[actualSize - 1] = v;  
                    upheap(actualSize - 1);
                }
            }
        }

        private void removeMax(int k) {
            if(arr[0] != null && actualSize != 0) {  
                int valueMax = arr[0].value;   
                int tmp = Math.min(k, arr[0].counter);    
                arr[0].counter -= tmp;      
                actualSizeWithoutDup -= tmp;   
                if(arr[0].counter == 0) {       
                    actualSize--;
                    arr[0] = arr[actualSize];
                    downheap(0);
                }
                else
                    downheap(0);          
                System.out.println(valueMax + " " + tmp);
            }
            else
                System.out.println(0 + " " + 0);
        }

        private void heapSort() {
            if (arr[0] != null && actualSize != 0) {
                int tmpActualSize = actualSize;
                for(int i = (actualSize - 2) / 2; i >= 0; i--)
                    downheap(i); 
                while (actualSize > 0) {   
                    swap(arr, 0, actualSize - 1);
                    actualSize--;
                    downheap(0);
                }
                for(int i = tmpActualSize - 1; i >= 0; i--)  
                    System.out.print(arr[i].value + " " + arr[i].counter + " ");
                System.out.println();
            }
            else
                System.out.println(0 + " " + 0);
        }

        private static void swap(SingleHeap[] array, int i, int j){
            SingleHeap tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }
}

/*
test.in
3
2 3
i 3 1 1 1
g 3
s
1 10
i 6 1 2 3 4 5 6
g 5
g 3
i 2 2 3
s
5 10
i 6 2 5 7 2 2 9
g 1
i 4 1 2 3 4
g 5
i 2 1 1
s

test.out
1 3
0 0
1 1
0 0
2 1
2 1
2 3
1 3 9 1 7 1 5 1
 */
