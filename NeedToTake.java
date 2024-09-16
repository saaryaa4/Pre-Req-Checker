package prereqchecker;

import java.util.*;

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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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

        //number of courses taken
        String target = StdIn.readLine();
        int numCourses = Integer.parseInt(StdIn.readLine());
        String[] ids = new String[numCourses]; //array for courses taken

        ArrayList<String> visitedtarget = new ArrayList<>(); //array list of prereqs of target course
        dfs(adjList, target, visitedtarget);
        visitedtarget.remove(target);

        ArrayList<String> visited = new ArrayList<>(); //arraylist of courses already taken
        //add courses to ids array and complete dfs for all of them
        for(int i = 0; i < numCourses; i++) {
            ids[i] = StdIn.readLine();
            dfs(adjList, ids[i], visited);
        }

        for(int j = 0; j < visitedtarget.size(); j++) {
            if(!visited.contains(visitedtarget.get(j))) {
                StdOut.println(visitedtarget.get(j));
            }
        }
    }

    public static void dfs(HashMap<String, ArrayList<String>> adjList, String course1, ArrayList<String> visited) {
        //add intitial course to visited array list
        visited.add(course1);

        //add prereqs for course 1 and prereqs for those prereqs, etc. to visited
        ArrayList<String> prereqs = adjList.get(course1);
        
        for(int i = 0; i < prereqs.size(); i++) {
            String course = prereqs.get(i);
            if(!visited.contains(course)) {
                dfs(adjList, course, visited);
            }
        }
    }
}
