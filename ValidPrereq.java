package prereqchecker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	// WRITE YOUR CODE HERE
        StdIn.setFile(args[0]); //set input file
        int numVertices = Integer.parseInt(StdIn.readLine()); //number of courses
        HashMap<String, ArrayList<String>> adjList = new HashMap<>(); //create Hashmap

        //populate keys of course IDs
        for(int i = 0; i < numVertices; i++){
            String courseID = StdIn.readLine();
            adjList.put(courseID, new ArrayList<>());
        }

        int b = Integer.parseInt(StdIn.readLine()); //number of prereqs

        //add prereqs as values
        for(int j = 0; j < b; j++){
            String course = StdIn.readString();
            String prereq = StdIn.readString();
            adjList.get(course).add(prereq);
        }

        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        
        String course1 = StdIn.readLine();
        String prereq = StdIn.readLine();
        ArrayList<String> visited = new ArrayList<>();
        visited.add(prereq);
        ArrayList<String> recursion = new ArrayList<>();
        
        //if the array list of all prereqs contains given prereq
        if(isCyclical(adjList, course1, visited, recursion)) {
            //yes, it can be a prereq!
            StdOut.print("YES");
        } else {
            StdOut.print("NO");
        }

    }

    public static boolean dfs(HashMap<String, ArrayList<String>> adjList, String course1, ArrayList<String> visited, ArrayList<String> recursion) {
        //add intitial course to visited array list
        visited.add(course1);
        recursion.add(course1);
        //add prereqs for course 1 and prereqs for those prereqs, etc. to visited
        
        for(String neighbor : adjList.get(course1)) {
            if(!visited.contains(neighbor)) {
                return true;
            } else if(recursion.contains(neighbor)) {
                return true;
            }
        }
        recursion.remove(course1);
        return false;
    }

    public static boolean isCyclical(HashMap<String, ArrayList<String>> adjList, String course1, ArrayList<String> visited, ArrayList<String> recursion) {
        for(String vertex : adjList.keySet()) {
            if(!visited.contains(vertex) && dfs(adjList, course1, visited, recursion)) {
                return true;
            }
        }

        return false;
    }
}