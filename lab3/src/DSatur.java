import java.util.*;

public class DSatur {
    private int V;
    private LinkedList<Integer> adj[];

    public DSatur(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void dsaturColoring() {
        int result[] = new int[V];
        Arrays.fill(result, -1);

        int[] degree = new int[V];
        for (int i = 0; i < V; i++) {
            degree[i] = adj[i].size();
        }

        boolean[] usedColors = new boolean[V];
        Arrays.fill(usedColors, false);

        int maxDegreeVertex = 0;
        for (int i = 1; i < V; i++) {
            if (degree[i] > degree[maxDegreeVertex]) {
                maxDegreeVertex = i;
            }
        }

        result[maxDegreeVertex] = 0;
        usedColors[0] = true;

        for (int i = 1; i < V; i++) {
            int vertex = -1;
            int maxSaturationDegree = -1;
            for (int j = 0; j < V; j++) {
                if (result[j] == -1) {
                    int saturationDegree = 0;
                    for (int k : adj[j]) {
                        if (result[k] != -1) {
                            saturationDegree++;
                        }
                    }
                    if (saturationDegree > maxSaturationDegree) {
                        maxSaturationDegree = saturationDegree;
                        vertex = j;
                    }
                }
            }

            boolean[] availableColors = new boolean[V];
            Arrays.fill(availableColors, true);

            for (int k : adj[vertex]) {
                if (result[k] != -1) {
                    availableColors[result[k]] = false;
                }
            }

            int cr;
            for (cr = 0; cr < V; cr++) {
                if (availableColors[cr]) {
                    break;
                }
            }

            result[vertex] = cr;
        }

        for (int u = 0; u < V; u++) {
            System.out.println("Vertex " + u + " --->  Color " + result[u]);
        }
    }

    public static void main(String args[]) {
        DSatur g1 = new DSatur(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        System.out.println("Coloring of graph 1 using DSatur algorithm:");
        g1.dsaturColoring();
    }
}
