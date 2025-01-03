import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.*;

class GraphGenerator {
    private Graph graph;

    public GraphGenerator(Graph graph) {
        this.graph = graph;
    }

    public Graph generateRandomGraph(double density) {
        int V = graph.getVertexCount();
        int maxEdges = (V * (V - 1)) / 2;
        int targetEdges = (int) (density * maxEdges);

        Random rand = new Random();
        while (graph.countEdges() < targetEdges) {
            int v = rand.nextInt(V);
            int w = rand.nextInt(V);
            if (v != w && !graph.getAdjacencyList()[v].contains(w)) {
                graph.addEdge(v, w);
            }
        }

        return graph;
    }

    public Graph generateBipartiteGraph(double density) {
        int V = graph.getVertexCount();
        int maxEdges = (V / 2) * (V / 2);
        int targetEdges = (int) (density * maxEdges);

        Random rand = new Random();
        int half = V / 2;
        while (graph.countEdges() < targetEdges) {
            int v = rand.nextInt(half);
            int w = half + rand.nextInt(half);
            if (!graph.getAdjacencyList()[v].contains(w)) {
                graph.addEdge(v, w);
            }
        }

        return graph;
    }

    public Graph generatePlanarGraph(double density) {
        int V = graph.getVertexCount();
        int maxEdges = (V * (V - 1)) / 2;
        int targetEdges = (int) (density * maxEdges);

        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] coords = new Coordinate[V];
        Random rand = new Random();
        for (int i = 0; i < V; i++) {
            coords[i] = new Coordinate(rand.nextDouble(), rand.nextDouble());
        }

        DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
        builder.setSites(Arrays.asList(coords));
        List<org.locationtech.jts.geom.Geometry> triangles = builder.getTriangles(geomFactory);

        for (org.locationtech.jts.geom.Geometry triangle : triangles) {
            Coordinate[] vertices = triangle.getCoordinates();
            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 3; j++) {
                    int v = Arrays.asList(coords).indexOf(vertices[i]);
                    int w = Arrays.asList(coords).indexOf(vertices[j]);
                    if (!graph.getAdjacencyList()[v].contains(w)) {
                        graph.addEdge(v, w);
                    }
                }
            }
        }

        while (graph.countEdges() > targetEdges) {
            removeEdge(graph);
        }

        return graph;
    }

    private void removeEdge(Graph graph) {
        Random rand = new Random();
        int v = rand.nextInt(graph.getVertexCount());
        if (graph.getAdjacencyList()[v].size() > 0) {
            int w = graph.getAdjacencyList()[v].get(rand.nextInt(graph.getAdjacencyList()[v].size()));
            graph.getAdjacencyList()[v].remove((Integer) w);
            graph.getAdjacencyList()[w].remove((Integer) v);
        }
    }
}



