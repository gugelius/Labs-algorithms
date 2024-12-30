public class HybridSearch {

    public static int hybridSearch(int[] arr, int x) {
        int lo = 0;
        int hi = arr.length - 1;

        while ((hi - lo) > (arr.length / 2)) {
            if (arr[hi] == arr[lo]) {  // Избежание деления на ноль
                if (arr[lo] == x) {
                    return lo;
                }
                return -1;  // Если все элементы между lo и hi равны и не равны x
            }

            int pos = lo + (((hi - lo) / (arr[hi] - arr[lo])) * (x - arr[lo]));

            if (arr[pos] == x) {
                return pos;
            }

            if (arr[pos] < x) {
                lo = pos + 1;
            } else {
                hi = pos - 1;
            }
        }

        return binarySearch(arr, lo, hi, x);
    }

    private static int binarySearch(int[] arr, int lo, int hi, int x) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (arr[mid] == x) {
                return mid;
            }

            if (arr[mid] < x) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }
}
