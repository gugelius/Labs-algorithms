import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = generateExponentialArray(1000);
        System.out.println("Массив: " + Arrays.toString(arr));

        int index = FibonacciSearch.fibMonaccianSearch(arr, arr[500], arr.length);
        if (index >= 0)
            System.out.println("Элемент найден на позиции " + index);
        else
            System.out.println("Элемент не найден");
        //todo
        int[] arr2 = generateExponentialArray(1000);
        System.out.println("Массив: " + Arrays.toString(arr2));

        int index2 = HybridSearch.hybridSearch(arr2, arr2[500]);
        if (index2 >= 0)
            System.out.println("Элемент найден на позиции " + index2);
        else
            System.out.println("Элемент не найден");
    }

    private static int[] generateExponentialArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.pow(2.7, i % 50) + (Math.random() * 10));
        }
        Arrays.sort(arr);
        return arr;
    }
}