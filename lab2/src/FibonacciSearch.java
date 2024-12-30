import java.util.Arrays;

public class FibonacciSearch {
    public static int fibMonaccianSearch(int arr[], int x, int n) {
        int fibMMm2 = 0;   // (m-2)'th Fibonacci No.
        int fibMMm1 = 1;   // (m-1)'th Fibonacci No.
        int fibM = fibMMm2 + fibMMm1;  // m'th Fibonacci

        // fibM is going to store the smallest Fibonacci
        // Number greater than or equal to n
        while (fibM < n) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM  = fibMMm2 + fibMMm1;
        }

        int offset = -1;

        // while there are elements to be inspected.
        // Note that we compare arr[fibMMm2] with x.
        // When fibM becomes 1, fibMMm2 becomes 0
        while (fibM > 1) {
            int i = Math.min(offset+fibMMm2, n-1);

            if (arr[i] < x) {
                fibM  = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            }

            else if (arr[i] > x) {
                fibM  = fibMMm2;
                fibMMm1 = fibMMm1 - fibMMm2;
                fibMMm2 = fibM - fibMMm1;
            }

            else return i;
        }

        if(fibMMm1 == 1 && arr[offset+1] == x) return offset+1;

        return -1;
    }
}
