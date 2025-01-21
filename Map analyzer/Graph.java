/**
 * Represents a directional graph where each point can have multiple entry and exit roads.
 * This class is essential for creating and managing a network of roads, facilitating operations like
 * adding roads and fetching neighbors of a point.
 */

import java.util.*;

class Graph {
    private final Map<String, List<Road>> neighborMap = new HashMap<>();


    /**
     * Adds a road to the graph. The road is added in both directions, from entryPoint to exitPoint and vice versa,
     * to ensure bidirectional connectivity.
     *
     * @param road The road to be added to the graph.
     */
    public void addRoad(Road road) {
        neighborMap.computeIfAbsent(road.entryPoint, k -> new ArrayList<>()).add(road);
        neighborMap.computeIfAbsent(road.exitPoint, k -> new ArrayList<>()).add(new Road(road.exitPoint, road.entryPoint, road.distance, road.id));
    }

    List<Road> getNeighbors(String point) {
        return neighborMap.getOrDefault(point, new ArrayList<>());
    }
    Set<String> getAllPoints() {
        return neighborMap.keySet();
    }
}
