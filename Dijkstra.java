import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
    int vertex;
    int distance;
    
    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
    
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

public class Dijkstra {
    public static int[] findShortestPaths(List<List<Edge>> graph, int source) {
        int n = graph.size();
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];
        
        // Initialize distances with infinity
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        
        // Create priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            
            // Skip if already processed
            if (visited[u]) continue;
            visited[u] = true;
            
            // Process all adjacent vertices
            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                
                // Relaxation step
                if (!visited[v] && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    pq.add(new Node(v, distances[v]));
                }
            }
        }
        
        return distances;
    }
    
    static class Edge {
        int to;
        int weight;
        
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    /**
     * Process a single input file and generate output
     */
    public static void processFile(String inputFile, String outputFile) {
        try {
            System.out.println("Processing: " + inputFile + " -> " + outputFile);
            
            Scanner scanner = new Scanner(new File(inputFile));
            int V = scanner.nextInt();
            int E = scanner.nextInt();
            
            // Create adjacency list
            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                graph.add(new ArrayList<>());
            }
            
            // Read edges
            for (int i = 0; i < E; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int weight = scanner.nextInt();
                graph.get(from).add(new Edge(to, weight));
            }
            scanner.close();
            
            // Run Dijkstra's algorithm from source 0
            int[] shortestDistances = findShortestPaths(graph, 0);
            
            // Write output
            PrintWriter writer = new PrintWriter(new File(outputFile));
            writer.print("Vertex");
            for (int i = 0; i < V; i++) {
                writer.print("\t" + i);
            }
            writer.println();
            
            writer.print("Distance");
            for (int i = 0; i < V; i++) {
                if (shortestDistances[i] == Integer.MAX_VALUE) {
                    writer.print("\tinfinity");
                } else {
                    writer.print("\t" + shortestDistances[i]);
                }
            }
            writer.println();
            writer.close();
            
            System.out.println("Successfully processed " + inputFile);
            
        } catch (Exception e) {
            System.err.println("Error processing " + inputFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            // Case 1: Specific input and output files provided as command line args
            if (args.length >= 2) {
                String inputFile = args[0];
                String outputFile = args[1];
                processFile(inputFile, outputFile);
                return;
            }
            
            // Case 2: Process all input files (input1.txt through input4.txt)
            for (int i = 1; i <= 4; i++) {
                String inputFile = "input" + i + ".txt";
                String outputFile = "output" + i + ".txt";
                
                // Check if input file exists before processing
                File file = new File(inputFile);
                if (file.exists()) {
                    processFile(inputFile, outputFile);
                } else {
                    System.out.println("File " + inputFile + " not found. Skipping.");
                }
            }
            
            System.out.println("All processing complete.");
            
        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}