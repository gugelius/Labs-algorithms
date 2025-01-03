public class Main {
    public static void main(String[] args) {
        int vertexCount = 16;
        double density = 0.5;

        // Создаем графы
        Graph randomGraph = new Graph(vertexCount);
        Graph bipartiteGraph = new Graph(vertexCount);
        Graph planarGraph = new Graph(vertexCount);

        GraphGenerator randomGraphGenerator = new GraphGenerator(randomGraph);
        GraphGenerator bipartiteGraphGenerator = new GraphGenerator(bipartiteGraph);
        GraphGenerator planarGraphGenerator = new GraphGenerator(planarGraph);

        // Генерируем графы с заданной плотностью
        randomGraph = randomGraphGenerator.generateRandomGraph(density);
        bipartiteGraph = bipartiteGraphGenerator.generateBipartiteGraph(density);
        planarGraph = planarGraphGenerator.generatePlanarGraph(density);

        // Выводим графы
        System.out.println("Random Graph:");
        printGraph(randomGraph);

        System.out.println("Bipartite Graph:");
        printGraph(bipartiteGraph);

        System.out.println("Planar Graph:");
        printGraph(planarGraph);
    }

    public static void printGraph(Graph graph) {
        for (int v = 0; v < graph.getVertexCount(); v++) {
            System.out.print(v + ": ");
            for (int w : graph.getAdjacencyList()[v]) {
                System.out.print(w + " ");
            }
            System.out.println();
        }
    }
}