import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] testData = generateTestData(1000);
        System.out.println("Массив для тестирования: " + Arrays.toString(testData));

        // Тестирование SkipList
        SkipList<Integer> skipList = new SkipList<>();
        int skipListInsertIterations = testInsertSkipList(skipList, testData);
        int skipListDeleteIterations = testDeleteSkipList(skipList, testData);
        System.out.println("SkipList - Вставка: " + skipListInsertIterations + " итераций, Удаление: " + skipListDeleteIterations + " итераций");

        // Тестирование SplayTree
        SplayTree<Integer> splayTree = new SplayTree<>();
        int splayTreeInsertIterations = testInsertSplayTree(splayTree, testData);
        int splayTreeDeleteIterations = testDeleteSplayTree(splayTree, testData);
        System.out.println("SplayTree - Вставка: " + splayTreeInsertIterations + " итераций, Удаление: " + splayTreeDeleteIterations + " итераций");

        // Тестирование BTree
        BTree bTree = new BTree();
        int bTreeInsertIterations = testInsertBTree(bTree, testData);
        int bTreeDeleteIterations = testDeleteBTree(bTree, testData);
        System.out.println("B-Tree - Вставка: " + bTreeInsertIterations + " итераций, Удаление: " + bTreeDeleteIterations + " итераций");
    }

    private static int[] generateTestData(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.pow(2.7, i % 50) + (Math.random() * 10));
        }
        Arrays.sort(arr);
        return arr;
    }

    private static int testInsertSkipList(SkipList<Integer> skipList, int[] data) {
        int iterations = 0;
        for (int value : data) {
            skipList.insert(value);
            iterations++;
        }
        return iterations;
    }

    private static int testInsertSplayTree(SplayTree<Integer> splayTree, int[] data) {
        int iterations = 0;
        for (int value : data) {
            splayTree.insert(value);
            iterations++;
        }
        return iterations;
    }

    private static int testInsertBTree(BTree bTree, int[] data) {
        int iterations = 0;
        for (int value : data) {
            bTree.insert(value);
            iterations++;
        }
        return iterations;
    }

    private static int testDeleteSkipList(SkipList<Integer> skipList, int[] data) {
        int iterations = 0;
        for (int value : data) {
            skipList.delete(value);
            iterations++;
        }
        return iterations;
    }

    private static int testDeleteSplayTree(SplayTree<Integer> splayTree, int[] data) {
        int iterations = 0;
        for (int value : data) {
            splayTree.delete(value);
            iterations++;
        }
        return iterations;
    }

    private static int testDeleteBTree(BTree bTree, int[] data) {
        int iterations = 0;
        for (int value : data) {
            bTree.delete(value);
            iterations++;
        }
        return iterations;
    }
}
