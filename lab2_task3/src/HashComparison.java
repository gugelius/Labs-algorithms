import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

public class HashComparison {

    private static final int N = 100000; // Количество ключей для тестирования

    public static void main(String[] args) {
        Random random = new Random();
        int[] keys = new int[N];
        for (int i = 0; i < N; i++) {
            keys[i] = random.nextInt();
        }

        compareHashes(keys);
    }

    public static void compareHashes(int[] keys) {
        int murMurCollisions = countCollisions(keys, HashComparison::murMurHash);
        int knuthCollisions = countCollisions(keys, HashComparison::knuthMultiplicativeHash);
        int jenkinsCollisions = countCollisions(keys, HashComparison::jenkinsHash);

        System.out.println("MurMurHash Collisions: " + murMurCollisions);
        System.out.println("Knuth Multiplicative Hash Collisions: " + knuthCollisions);
        System.out.println("Jenkins Hash Collisions: " + jenkinsCollisions);

        double murMurAvalanche = calculateAvalancheEffect(keys, HashComparison::murMurHash, "MurMurHash");
        double knuthAvalanche = calculateAvalancheEffect(keys, HashComparison::knuthMultiplicativeHash, "Knuth Multiplicative Hash");
        double jenkinsAvalanche = calculateAvalancheEffect(keys, HashComparison::jenkinsHash, "Jenkins Hash");

        visualizeResults(murMurCollisions, knuthCollisions, jenkinsCollisions, murMurAvalanche, knuthAvalanche, jenkinsAvalanche);
    }

    private static int murMurHash(int key) {
        int c1 = 0xcc9e2d51;
        int c2 = 0x1b873593;
        int r1 = 15;
        int r2 = 13;
        int m = 5;
        int n = 0xe6546b64;

        int hash = key;
        hash *= c1;
        hash = (hash << r1) | (hash >>> (32 - r1));
        hash *= c2;

        hash ^= hash >>> 16;
        hash *= 0x85ebca6b;
        hash ^= hash >>> 13;
        hash *= 0xc2b2ae35;
        hash ^= hash >>> 16;

        return hash;
    }

    private static int knuthMultiplicativeHash(int key) {
        return key * 2654435769;
    }

    private static int jenkinsHash(int key) {
        int hash = key;
        hash += (hash << 12);
        hash ^= (hash >> 22);
        hash += (hash << 4);
        hash ^= (hash >> 9);
        hash += (hash << 10);
        hash ^= (hash >> 2);
        hash += (hash << 7);
        hash ^= (hash >> 12);
        return hash;
    }

    private static int countCollisions(int[] keys, HashFunction hashFunction) {
        int collisions = 0;
        int[] hashTable = new int[N];
        for (int key : keys) {
            int hash = hashFunction.apply(key);
            if (hashTable[Math.abs(hash) % N] != 0) {
                collisions++;
            }
            hashTable[Math.abs(hash) % N] = hash;
        }
        return collisions;
    }

    private static double calculateAvalancheEffect(int[] keys, HashFunction hashFunction, String hashName) {
        int bitChanges = 0;
        int bitCount = 32;

        for (int key : keys) {
            int originalHash = hashFunction.apply(key);
            int modifiedKey = key ^ (1 << (new Random().nextInt(bitCount)));
            int modifiedHash = hashFunction.apply(modifiedKey);

            bitChanges += Integer.bitCount(originalHash ^ modifiedHash);
        }

        double avalancheCoefficient = (double) bitChanges / (keys.length * bitCount);
        System.out.println(hashName + " Avalanche Coefficient: " + avalancheCoefficient);
        return avalancheCoefficient;
    }

    private static void visualizeResults(int murMurCollisions, int knuthCollisions, int jenkinsCollisions,
                                         double murMurAvalanche, double knuthAvalanche, double jenkinsAvalanche) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(murMurCollisions, "Collisions", "MurMurHash");
        dataset.addValue(knuthCollisions, "Collisions", "Knuth Multiplicative Hash");
        dataset.addValue(jenkinsCollisions, "Collisions", "Jenkins Hash");

        dataset.addValue(murMurAvalanche, "Avalanche Coefficient", "MurMurHash");
        dataset.addValue(knuthAvalanche, "Avalanche Coefficient", "Knuth Multiplicative Hash");
        dataset.addValue(jenkinsAvalanche, "Avalanche Coefficient", "Jenkins Hash");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Hash Function Comparison",
                "Hash Functions",
                "Values",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Hash Function Comparison");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @FunctionalInterface
    interface HashFunction {
        int apply(int key);
    }
}
