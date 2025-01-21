/**
 * Provides functionality to find the fastest route between two points in a graph based on distance.
 * This class uses Dijkstra's algorithm to determine the shortest path through a network represented by a graph of roads.
 */

import java.util.*;

class FastestDirection {
    private final Graph graph;
    private final Map<Integer, Road> roadMap;

    /**
     * Constructs a FastestDirection object with a graph and a map of roads.
     *
     * @param graph The graph representing the network of roads.
     * @param roadMap A map linking road IDs to their respective Road objects for quick access.
     */
    FastestDirection(Graph graph, Map<Integer, Road> roadMap) {
        this.graph = graph;
        this.roadMap = roadMap;
    }

    /**
     * Finds and returns the fastest route between the start and finish points using the shortest path algorithm.
     *
     * @param start The starting point of the route.
     * @param finish The ending point of the route.
     * @return A list of Roads representing the shortest route, or an empty list if no route is found.
     */
    List<Road> findFastestRoute(String start, String finish) {
        Map<String, Integer> shortestPaths = new HashMap<>();
        Map<String, Road> cameFrom = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Road> routeQueue = new PriorityQueue<>(Comparator.comparingInt((Road r) -> r.distance).thenComparingInt(r -> r.id));

        shortestPaths.put(start, 0);
        routeQueue.add(new Road(start, start, 0, -1));

        while (!routeQueue.isEmpty()) {
            Road current = routeQueue.poll();
            String currentLocation = current.exitPoint;

            if (visited.contains(currentLocation)) {
                continue;
            }

            visited.add(currentLocation);

            if (currentLocation.equals(finish)) {
                return buildPath(cameFrom, start, finish);
            }

            for (Road neighbor : graph.getNeighbors(currentLocation)) {
                String neighborPoint = neighbor.exitPoint;
                int newDistance = shortestPaths.get(currentLocation) + neighbor.distance;

                if (newDistance < shortestPaths.getOrDefault(neighborPoint, Integer.MAX_VALUE)) {
                    shortestPaths.put(neighborPoint, newDistance);
                    routeQueue.add(new Road(currentLocation, neighborPoint, newDistance, neighbor.id));
                    cameFrom.put(neighborPoint, neighbor);
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Reconstructs the path from start to finish using the map of predecessors.
     *
     * @param cameFrom Map tracking each node's predecessor in the path.
     * @param start The starting point of the path.
     * @param finish The end point of the path.
     * @return A list of Roads in order from start to finish.
     */
    private List<Road> buildPath(Map<String, Road> cameFrom, String start, String finish) {
        List<Road> path = new ArrayList<>();
        String currentPoint = finish;

        while (!currentPoint.equals(start)) {
            Road road = cameFrom.get(currentPoint);
            path.add(roadMap.get(road.id));
            currentPoint = road.entryPoint.equals(currentPoint) ? road.exitPoint : road.entryPoint;
        }

        Collections.reverse(path);
        return path;
    }
}