package prereqchecker;
import java.util.HashMap;
import java.util.ArrayList;

class Graph {
    private HashMap<String, ArrayList<String>> adjList;

    public Graph() {
        this.adjList = new HashMap<>();
    }

    public void addEdge(String source, String destination) {
        adjList.put(source, new ArrayList<>()).add(destination);
    }

    private boolean isCyclicalUntil(String vertex, ArrayList<String> visited, ArrayList<String> recursion) {
        visited.add(vertex);
        recursion.add(vertex);

        for(String neighbor : adjList.get(vertex)) {
            if(!visited.contains(neighbor)) {
                if(isCyclicalUntil(neighbor, visited, recursion)) {
                    return true;
                }
            } else if (recursion.contains(neighbor)) {
                return true;
            }
        }

        recursion.remove(vertex);
        return false;
    }

    public boolean isCyclical() {
        ArrayList<String> visited = new ArrayList<>();
        ArrayList<String> recursion = new ArrayList<>();

        for(String vertex : adjList.keySet()) {
            if(!visited.contains(vertex) && isCyclicalUntil(vertex, visited, recursion)) {
                return true;
            }
        }

        return false;
    }
}