package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
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
        int numCourses = Integer.parseInt(StdIn.readLine());
        String[] ids = new String[numCourses]; //array for courses taken

        ArrayList<String> visited = new ArrayList<>();

        //add courses to ids array and complete dfs for all of them
        for(int i = 0; i < numCourses; i++) {
            ids[i] = StdIn.readLine();
            dfs(adjList, ids[i], visited);
        }

        //all courses available to take
        Object[] keys = adjList.keySet().toArray();

        for(int k = 0; k < adjList.keySet().size(); k++) {
            String course = (String)(keys[k]);
            if(!visited.contains(course)) {
                ArrayList<String> arr = adjList.get(course);
                boolean check = true;
                for(int l = 0; l < arr.size(); l++) {
                    if(!visited.contains(arr.get(l))) {
                        check = false;
                    }
                }
                if(check == true) {
                    StdOut.println(course);
                }
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
