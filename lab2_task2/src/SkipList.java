import java.util.Random;

class SkipList<T extends Comparable<T>> {
    private final int MAX_LEVEL = 16;
    private final double P = 0.5;
    private SkipListNode<T> header;
    private int level;
    private Random random;

    public SkipList() {
        header = new SkipListNode<>(MAX_LEVEL, null);
        level = 0;
        random = new Random();
    }

    private int randomLevel() {
        int lvl = 0;
        while (random.nextDouble() < P && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    public void insert(T value) {
        SkipListNode<T>[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode<T> x = header;

        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].value.compareTo(value) < 0) {
                x = x.forward[i];
            }
            update[i] = x;
        }

        x = x.forward[0];

        if (x == null || !x.value.equals(value)) {
            int lvl = randomLevel();
            if (lvl > level) {
                for (int i = level + 1; i <= lvl; i++) {
                    update[i] = header;
                }
                level = lvl;
            }

            x = new SkipListNode<>(lvl, value);
            for (int i = 0; i <= lvl; i++) {
                x.forward[i] = update[i].forward[i];
                update[i].forward[i] = x;
            }
        }
    }

    public boolean delete(T value) {
        SkipListNode<T>[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode<T> x = header;

        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].value.compareTo(value) < 0) {
                x = x.forward[i];
            }
            update[i] = x;
        }

        x = x.forward[0];

        if (x != null && x.value.equals(value)) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != x) {
                    break;
                }
                update[i].forward[i] = x.forward[i];
            }

            while (level > 0 && header.forward[level] == null) {
                level--;
            }

            return true;
        }

        return false;
    }
}
