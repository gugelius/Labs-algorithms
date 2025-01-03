import java.util.*;

public class WelshPowell {
    private int V;
    private LinkedList<Integer> adj[];

    public WelshPowell(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void welshPowellColoring() {
        int result[] = new int[V];
        Arrays.fill(result, -1);

        Integer[] vertices = new Integer[V];
        for (int i = 0; i < V; i++) {
            vertices[i] = i;
        }

        Arrays.sort(vertices, (a, b) -> adj[b].size() - adj[a].size());

        result[vertices[0]] = 0;

        for (int i = 1; i < V; i++) {
            boolean[] available = new boolean[V];
            Arrays.fill(available, true);

            int u = vertices[i];
            for (int j : adj[u]) {
                if (result[j] != -1) {
                    available[result[j]] = false;
                }
            }

            int cr;
            for (cr = 0; cr < V; cr++) {
                if (available[cr]) {
                    break;
                }
            }

            result[u] = cr;
        }

        for (int u = 0; u < V; u++) {
            System.out.println("Vertex " + u + " --->  Color " + result[u]);
        }
    }

    public static void main(String args[]) {
        WelshPowell g1 = new WelshPowell(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        System.out.println("Coloring of graph 1 using Welsh-Powell algorithm:");
        g1.welshPowellColoring();
    }
}
