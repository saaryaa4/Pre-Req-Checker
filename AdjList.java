package prereqchecker;

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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
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
            String prereq = StdIn.readLine();
            adjList.get(course).add(prereq);
        }

        StdOut.setFile(args[1]); //set output file


        //traverse HashMap
        for(String i : adjList.keySet()) {
            StdOut.print(i);
            ArrayList<String> arr = adjList.get(i);
            for(int j = 0; j < arr.size(); j++){
                StdOut.print(arr.get(j) + " ");
            }
            StdOut.println();
        }
    }
}
