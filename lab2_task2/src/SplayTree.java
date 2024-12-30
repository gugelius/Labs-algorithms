class SplayTree<T extends Comparable<T>> {
    private class Node {
        T value;
        Node left, right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node root;

    private Node splay(Node root, T value) {
        if (root == null || root.value.equals(value)) {
            return root;
        }

        if (root.value.compareTo(value) > 0) {
            if (root.left == null) return root;

            if (root.left.value.compareTo(value) > 0) {
                root.left.left = splay(root.left.left, value);
                root = rotateRight(root);
            } else if (root.left.value.compareTo(value) < 0) {
                root.left.right = splay(root.left.right, value);
                if (root.left.right != null) root.left = rotateLeft(root.left);
            }

            return (root.left == null) ? root : rotateRight(root);
        } else {
            if (root.right == null) return root;

            if (root.right.value.compareTo(value) > 0) {
                root.right.left = splay(root.right.left, value);
                if (root.right.left != null) root.right = rotateRight(root.right);
            } else if (root.right.value.compareTo(value) < 0) {
                root.right.right = splay(root.right.right, value);
                root = rotateLeft(root);
            }

            return (root.right == null) ? root : rotateLeft(root);
        }
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        return newRoot;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        return newRoot;
    }

    public void insert(T value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        root = splay(root, value);

        if (root.value.equals(value)) return;

        Node newNode = new Node(value);
        if (root.value.compareTo(value) > 0) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode;
    }

    public boolean delete(T value) {
        if (root == null) return false;

        root = splay(root, value);

        if (!root.value.equals(value)) return false;

        if (root.left == null) {
            root = root.right;
        } else {
            Node rightSubTree = root.right;
            root = root.left;
            root = splay(root, value);
            root.right = rightSubTree;
        }
        return true;
    }
}
