/**
 * Generates a minimal connectivity map, or "Apocalypse Map," from a graph.
 * This class is designed to ensure minimal necessary connectivity among nodes,
 * simulating reduced infrastructure scenarios.
 */

import java.util.*;

class ApocalypseMap {
    private final Graph graph;
    private final Map<Integer, Road> roadMap;

    /**
     * Constructs an ApocalypseMap object.
     *
     * @param graph The graph from which to create the minimal map.
     * @param roadMap A reference map of road IDs to Road objects.
     */
    ApocalypseMap(Graph graph, Map<Integer, Road> roadMap) {
        this.graph = graph;
        this.roadMap = roadMap;
    }

    /**
     * Finds and returns the minimal set of roads needed to keep all nodes connected.
     *
     * @return A list of Roads representing the minimally connected network.
     */
    List<Road> findApocalypseMap() {
        PriorityQueue<Road> explorationQueue = new PriorityQueue<>(Comparator.comparingInt((Road r) -> r.distance).thenComparingInt(r -> r.id));
        Set<String> visited = new HashSet<>();
        List<Road> result = new ArrayList<>();

        String start = graph.getAllPoints().stream().min(String::compareTo).get();
        visited.add(start);
        explorationQueue.addAll(graph.getNeighbors(start));

        while (!explorationQueue.isEmpty() && result.size() < graph.getAllPoints().size() - 1) {
            Road road = explorationQueue.poll();
            if (visited.contains(road.entryPoint) && visited.contains(road.exitPoint)) {
                continue; // Avoid creating cycles
            }

            result.add(road);
            String newPoint = visited.contains(road.entryPoint) ? road.exitPoint : road.entryPoint;
            visited.add(newPoint);

            for (Road neighbor : graph.getNeighbors(newPoint)) {
                if (!visited.contains(neighbor.exitPoint)) {
                    explorationQueue.add(neighbor);
                }
            }
        }

        return result;
    }
}