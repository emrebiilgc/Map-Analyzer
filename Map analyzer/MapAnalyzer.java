/**
 * The Main class serves as the entry point for the application that calculates routes using a graph of roads.
 * It reads input data from a file, sets up the graph structure, and executes different route finding processes,
 * including finding the fastest route and constructing an "Apocalypse Map" with minimal connectivity.
 */

import java.util.*;

public class MapAnalyzer {

    /**
     * The main method reads input data, initializes graph structures, and executes routing processes.
     * After processing routes, it writes the output to a specified file.
     *
     * @param args The command line arguments where args[0] is the input file path and args[1] is the output file path.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        String[] lines = FileInput.readFile(args[0], true, true);

        String start = lines[0].split("\t")[0];
        String finish = lines[0].split("\t")[1];

        Map<Integer, Road> roadMap = new HashMap<>();
        Graph graph = new Graph();

        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split("\t");
            String entryPoint = parts[0];
            String exitPoint = parts[1];
            int distance = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[3]);

            Road road = new Road(entryPoint, exitPoint, distance, id);
            roadMap.put(id, road);
            graph.addRoad(road);
        }

        Process process = new Process(graph, roadMap, start, finish);
        process.findFastestRoute();
        process.findApocalypseMap();
        process.FastestDirectionOnApocalypseMap();

        String output = process.generateOutput();
        FileOutput.writeToFile(args[1], output, false, false);
    }
}
