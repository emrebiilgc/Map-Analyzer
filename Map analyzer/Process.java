/**
 * Manages the processing of route finding in a graph, incorporating scenarios like the fastest route and an apocalypse map.
 * This class coordinates the utilization of different map scenarios (standard and minimal connectivity) and calculates the optimal routes.
 */

import java.util.*;

public class Process {
    private final Graph graph;
    private final Map<Integer, Road> roadMap;
    private final String start;
    private final String finish;
    private List<Road> fastestRoute;
    private List<Road> ApocalypseRoutes;
    private List<Road> newFastestRoute;

    /**
     * Initializes the Process with a graph, road map, and start and end points.
     *
     * @param graph The graph representing the road network.
     * @param roadMap A map linking road IDs to Road objects.
     * @param start The starting point for the route calculation.
     * @param finish The endpoint for the route calculation.
     */
    public Process(Graph graph, Map<Integer, Road> roadMap, String start, String finish) {
        this.graph = graph;
        this.roadMap = roadMap;
        this.start = start;
        this.finish = finish;
    }

    /**
     * Finds the fastest route from the start to the finish point using the original graph.
     */
    public void findFastestRoute() {
        FastestDirection fastestDirection = new FastestDirection(graph, roadMap);
        fastestRoute = fastestDirection.findFastestRoute(start, finish);
    }

    /**
     * Generates a route map with minimal connections, simulating an apocalyptic scenario.
     */
    public void findApocalypseMap() {
        ApocalypseMap barelyConnectedMap = new ApocalypseMap(graph, roadMap);
        ApocalypseRoutes = barelyConnectedMap.findApocalypseMap();
        // Sort the apocalypse map by distance and then by ID
        ApocalypseRoutes.sort(Comparator.comparingInt((Road r) -> r.distance).thenComparingInt(r -> r.id));
    }

    /**
     * Determines the fastest route in the apocalyptic map scenario from the start to the finish point.
     */
    public void FastestDirectionOnApocalypseMap() {
        Graph newGraph = new Graph();
        for (Road road : ApocalypseRoutes) {
            newGraph.addRoad(road);
        }
        FastestDirection newFastestDirection = new FastestDirection(newGraph, roadMap);
        newFastestRoute = newFastestDirection.findFastestRoute(start, finish);
    }

    /**
     * Compiles results of various route calculations into a formatted string output.
     *
     * @return A string representation of all routes and comparative analysis.
     */
    public String generateOutput() {
        StringBuilder output = new StringBuilder();

        int originalFastestRouteLength = 0;
        for (Road road : fastestRoute) {
            originalFastestRouteLength += road.distance;
        }

        output.append("Fastest Route from ").append(start).append(" to ").append(finish)
                .append(" (").append(originalFastestRouteLength).append(" KM):\n");
        for (Road road : fastestRoute) {
            output.append(roadMap.get(road.id)).append("\n");
        }

        output.append("Roads of Barely Connected Map is:\n");
        int barelyConnectedMapLength = 0;
        for (Road road : ApocalypseRoutes) {
            barelyConnectedMapLength += road.distance;
            output.append(roadMap.get(road.id)).append("\n");
        }

        int newFastestRouteLength = 0;
        for (Road road : newFastestRoute) {
            newFastestRouteLength += road.distance;
        }

        output.append("Fastest Route from ").append(start).append(" to ").append(finish)
                .append(" on Barely Connected Map (").append(newFastestRouteLength).append(" KM):\n");
        for (Road road : newFastestRoute) {
            output.append(roadMap.get(road.id)).append("\n");
        }

        int originalMapTotalLength = 0;
        for (Road road : roadMap.values()) {
            originalMapTotalLength += road.distance;
        }

        // Analysis
        double constructionMaterialRatio = (double) barelyConnectedMapLength / originalMapTotalLength;
        double fastestRouteRatio = (double) newFastestRouteLength / originalFastestRouteLength;

        output.append("Analysis:\n");
        output.append(String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n", constructionMaterialRatio));
        output.append(String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", fastestRouteRatio));

        return output.toString();
    }
}
