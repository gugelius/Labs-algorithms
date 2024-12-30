import java.util.ArrayList;

class BTree {
    private static final int T = 3; // Минимальная степень B-дерева

    class BTreeNode {
        int[] keys = new int[2 * T - 1];
        BTreeNode[] children = new BTreeNode[2 * T];
        int n; // Количество ключей
        boolean leaf;

        BTreeNode(boolean leaf) {
            this.leaf = leaf;
        }
    }

    private BTreeNode root;

    public BTree() {
        root = new BTreeNode(true);
    }

    public void insert(int k) {
        BTreeNode r = root;
        if (r.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode(false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0);
            insertNonFull(s, k);
        } else {
            insertNonFull(r, k);
        }
    }

    private void insertNonFull(BTreeNode x, int k) {
        int i = x.n - 1;
        if (x.leaf) {
            while (i >= 0 && k < x.keys[i]) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = k;
            x.n++;
        } else {
            while (i >= 0 && k < x.keys[i]) {
                i--;
            }
            i++;
            if (x.children[i].n == 2 * T - 1) {
                splitChild(x, i);
                if (k > x.keys[i]) {
                    i++;
                }
            }
            insertNonFull(x.children[i], k);
        }
    }

    private void splitChild(BTreeNode x, int i) {
        BTreeNode y = x.children[i];
        BTreeNode z = new BTreeNode(y.leaf);
        x.children[i + 1] = z;
        for (int j = 0; j < T - 1; j++) {
            z.keys[j] = y.keys[j + T];
        }
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.children[j] = y.children[j + T];
            }
        }
        y.n = T - 1;
        for (int j = x.n; j >= i + 1; j--) {
            x.children[j + 1] = x.children[j];
        }
        x.children[i + 1] = z;
        for (int j = x.n - 1; j >= i; j--) {
            x.keys[j + 1] = x.keys[j];
        }
        x.keys[i] = y.keys[T - 1];
        x.n++;
    }

    public void delete(int k) {
        delete(root, k);
    }

    private void delete(BTreeNode x, int k) {
        int idx = findKey(x, k);

        if (idx < x.n && x.keys[idx] == k) {
            if (x.leaf) {
                removeFromLeaf(x, idx);
            } else {
                removeFromNonLeaf(x, idx);
            }
        } else {
            if (x.leaf) {
                return;
            }

            boolean flag = (idx == x.n);

            if (x.children[idx].n < T) {
                fill(x, idx);
            }

            if (flag && idx > x.n) {
                delete(x.children[idx - 1], k);
            } else {
                delete(x.children[idx], k);
            }
        }
    }

    private void removeFromLeaf(BTreeNode x, int idx) {
        for (int i = idx + 1; i < x.n; i++) {
            x.keys[i - 1] = x.keys[i];
        }
        x.n--;
    }

    private void removeFromNonLeaf(BTreeNode x, int idx) {
        int k = x.keys[idx];

        if (x.children[idx].n >= T) {
            int pred = getPred(x, idx);
            x.keys[idx] = pred;
            delete(x.children[idx], pred);
        } else if (x.children[idx + 1].n >= T) {
            int succ = getSucc(x, idx);
            x.keys[idx] = succ;
            delete(x.children[idx + 1], succ);
        } else {
            merge(x, idx);
            delete(x.children[idx], k);
        }
    }

    private int getPred(BTreeNode x, int idx) {
        BTreeNode cur = x.children[idx];
        while (!cur.leaf) {
            cur = cur.children[cur.n];
        }
        return cur.keys[cur.n - 1];
    }

    private int getSucc(BTreeNode x, int idx) {
        BTreeNode cur = x.children[idx + 1];
        while (!cur.leaf) {
            cur = cur.children[0];
        }
        return cur.keys[0];
    }

    private void fill(BTreeNode x, int idx) {
        if (idx != 0 && x.children[idx - 1].n >= T) {
            borrowFromPrev(x, idx);
        } else if (idx != x.n && x.children[idx + 1].n >= T) {
            borrowFromNext(x, idx);
        } else {
            if (idx != x.n) {
                merge(x, idx);
            } else {
                merge(x, idx - 1);
            }
        }
    }

    private void borrowFromPrev(BTreeNode x, int idx) {
        BTreeNode child = x.children[idx];
        BTreeNode sibling = x.children[idx - 1];

        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = x.keys[idx - 1];

        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        x.keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    private void borrowFromNext(BTreeNode x, int idx) {
        BTreeNode child = x.children[idx];
        BTreeNode sibling = x.children[idx + 1];

        child.keys[child.n] = x.keys[idx];

        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        x.keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n += 1;
        sibling.n -= 1;
    }

    private void merge(BTreeNode x, int idx) {
        BTreeNode child = x.children[idx];
        BTreeNode sibling = x.children[idx + 1];

        child.keys[T - 1] = x.keys[idx];

        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + T] = sibling.keys[i];
        }

        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + T] = sibling.children[i];
            }
        }

        for (int i = idx + 1; i < x.n; i++) {
            x.keys[i - 1] = x.keys[i];
        }

        for (int i = idx + 2; i <= x.n; i++) {
            x.children[i - 1] = x.children[i];
        }

        child.n += sibling.n + 1;
        x.n--;
    }

    private int findKey(BTreeNode x, int k) {
        int idx = 0;
        while (idx < x.n && x.keys[idx] < k) {
            idx++;
        }
        return idx;
    }
}
