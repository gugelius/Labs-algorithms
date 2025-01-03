import java.util.LinkedList;

class Graph {
    private int V;
    private LinkedList<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    public LinkedList<Integer>[] getAdjacencyList() {
        return adj;
    }

    public int getVertexCount() {
        return V;
    }

    // Метод для подсчета количества ребер
    public int countEdges() {
        int count = 0;
        for (int i = 0; i < V; i++) {
            count += adj[i].size();
        }
        return count / 2;
    }
}