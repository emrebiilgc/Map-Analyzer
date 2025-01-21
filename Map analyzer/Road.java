/**
 * Represents a road segment in a graph with an identifier, start and end points, and distance.
 * Used to model connections between locations in a network.
 */
class Road {
    String entryPoint;
    String exitPoint;
    int distance;
    int id;

    Road(String entryPoint, String exitPoint, int distance, int id) {
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
        this.distance = distance;
        this.id = id;
    }

    @Override
    public String toString() {
        return entryPoint + "\t" + exitPoint + "\t" + distance + "\t" + id;
    }
}