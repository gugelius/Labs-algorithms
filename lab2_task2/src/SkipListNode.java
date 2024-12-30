class SkipListNode<T> {
    T value;
    SkipListNode<T>[] forward;

    public SkipListNode(int level, T value) {
        forward = new SkipListNode[level + 1];
        this.value = value;
    }
}