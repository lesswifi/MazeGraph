import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.*;

/*****************
 * This class reads in a maze (as created by Maze.java)
 * as a graph, then solves it, then prints out its solution
 *
 * To run it:
 * java MazeGraph [-w] mazefile.txt
 *
 * Use -w if you want to take weights into account
 * When input the startvertex and endvertex, include its weight
 *
 * Andy Exley
 *
 * 
 *
 * Modified by Megan Zhao & Fangge Deng
 */
public class MazeGraph {

  // take in user input including whether the graph is weighted, filename, starting vertex
  // and ending vertex
  public static void main(String[] args) {
    String fname = null;
    String startvertex = null;
    String endvertex = null;
    boolean weighted = false;
    if(args.length < 3 || (args[0].equals("-w") && args.length < 4)) {
      System.err.println("Usage:\njava MazeGraph [-w] mazefile.txt start end");
      System.exit(1);
    } else if (args[0].equals("-w")) {
      fname = args[1];
      startvertex = args[2];
      endvertex = args[3];
      weighted = true;
    } else {
      fname = args[0];
      startvertex = args[1];
      endvertex = args[2];
    }
    
    if(!weighted) {
      BasicGraphADT<String> gmaze = loadMaze(fname);
      List<Vertex<String>> path1 = solveMazeDepthFirst(gmaze, startvertex, endvertex);
      System.out.println("Solution using DFS:");
      for(int i = 0; path1 != null && i < path1.size(); i++) {
        System.out.println(path1.get(i).getLabel());
      }

      // reload maze in case the graph needs to be reset
      System.out.println();
      BasicGraphADT<String> gmaze2 = loadMaze(fname);
      List<Vertex<String>> path2 = solveMazeBreadthFirst(gmaze2, startvertex, endvertex);
      System.out.println("Solution using BFS:");
      for(int i = 0; path2 != null && i < path2.size(); i++) {
        System.out.println(path2.get(i).getLabel());
      }
    } else {
      WeightedGraphADT<String> gmaze = loadWeightedMaze(fname);
      List<Vertex<String>> path3 = solveMaze(gmaze, startvertex, endvertex);
      System.out.println("Solution with least weight:");
      for(int i = 0; i < path3.size(); i++) {
        System.out.println(path3.get(i));
      }
    }
  }

  /*********************
   * This method loads a maze from a given file with name fname
   *********************/
  public static BasicGraphADT<String> loadMaze(String fname) {

    BasicGraphADT<String> mymaze = new AdjListGraph<String>(); 
    List<String> list1 = new ArrayList<String>();
          
    Scanner s = null;
        try {
            s = new Scanner(new File(fname));
        } catch(FileNotFoundException e) {
            System.out.println("Unable to find word list file.");
            System.exit(0);
        }
      
      int height = Integer.parseInt(s.next());
      int width = Integer.parseInt(s.next());

      while(s.hasNext()){
          String word = s.next();

          if(word.charAt(2) != '0') {
              mymaze.addVertex(word);
              list1.add(word);
          }
      }
      
      List<String> list2 = list1;
      
      // add the surrounding nonzero vertexes of a vertex to the vertex's neighbor list
      for (int i = 0; i < list1.size(); i++){
          int value1 = (int)list1.get(i).charAt(0);
          int value2 = (int)list1.get(i).charAt(1);

          for (int j = 0; j < list2.size(); j++){
              int value3 = (int)list2.get(j).charAt(0);
              int value4 = (int)list2.get(j).charAt(1);

              if (list1.get(i).charAt(0) == list2.get(j).charAt(0)){
                  if (Math.abs(value4 - value2) == 1 || Math.abs(value4 - value2) == height)
                      mymaze.addEdge(list1.get(i), list2.get(j));
              }else{
                  int diff = (value3 - value1)*26;
                  int sValue = diff + value4 - value2;
                  
                  if (Math.abs(sValue) == 1 || Math.abs(sValue) == height)
                      mymaze.addEdge(list1.get(i), list2.get(j));
                  
              }
          }
      }
      
    System.out.println(mymaze.toString());
    return mymaze;
  }

  /*********************
   * This method loads a maze from a given file with name fname as
   * a weighted graph
   *********************/
  public static WeightedGraphADT<String> loadWeightedMaze(String fname) {
    //change this to initalize your graph
    // build your maze based on the given file
    WeightedGraphADT<String> mymaze = new WeightedGraph<String>(); 
    List<String> list1 = new ArrayList<String>();
      
          
    Scanner s = null;
        try {
            s = new Scanner(new File(fname));
        } catch(FileNotFoundException e) {
            System.out.println("Unable to find word list file.");
            System.exit(0);
        }
      
      int height = Integer.parseInt(s.next());
      int width = Integer.parseInt(s.next());

      while(s.hasNext()){
          String word = s.next();

          if(word.charAt(2) != '0') {
              mymaze.addVertex(word);
              int num = (int) word.charAt(2) - 48;
              double dnum = (double) num;
              mymaze.getVertex(word).setDistance(dnum);
              list1.add(word);
          }
      }
      
      List<String> list2 = list1;
      
      // add the surrounding nonzero vertexes of a vertex to the vertex's neighbor list with
      // its specific weight between them
      for (int i = 0; i < list1.size(); i++){
          int value1 = (int)list1.get(i).charAt(0);
          int value2 = (int)list1.get(i).charAt(1);

          for (int j = 0; j < list2.size(); j++){
              int value3 = (int)list2.get(j).charAt(0);
              int value4 = (int)list2.get(j).charAt(1);

              if (list1.get(i).charAt(0) == list2.get(j).charAt(0)){
                  if (Math.abs(value4 - value2) == 1 || Math.abs(value4 - value2) == height){
                      int distance1 = (int)list1.get(i).charAt(2) - 48;
                      int distance2 = (int)list2.get(j).charAt(2) - 48;
                      
                      double dis1 = (double) distance1 + distance2;
                      mymaze.addEdge(list1.get(i), list2.get(j), dis1);
                  }
                      
              }else{
                  int diff = (value3 - value1)*26;
                  int sValue = diff + value4 - value2;
                  
                  if (Math.abs(sValue) == 1 || Math.abs(sValue) == height){
                      int distance3 = (int)list1.get(i).charAt(2) - 48;
                      int distance4 = (int)list2.get(j).charAt(2) - 48;
                      
                      double dis2 = (double) distance3 + distance4;
                      mymaze.addEdge(list1.get(i), list2.get(j), dis2);
                  }
              }
          }
      }      
    System.out.println(mymaze.toString());
    return mymaze;
  }
    
  /******** 
   * This method should use a breadth-first traversal to find a path through the 
   * maze, then return that path.
   ******/
  public static List<Vertex<String>> solveMazeBreadthFirst(BasicGraphADT<String> maze, String startvert, String endvert) {
    
    Queue<Vertex<String>> queue = new LinkedList<Vertex<String>>();
    List<Vertex<String>> VL = new ArrayList<Vertex<String>>();

    queue.add(maze.getVertex(startvert));

    while (!queue.isEmpty()){
        Vertex<String> currentVert = queue.remove();
        VL.add(currentVert);
        
        // return the path to get to the currentVert if the currentVert equals to the endvert
        if (endvert.equals(currentVert.getLabel())){
            currentVert.getPath().add(currentVert);
            return currentVert.getPath();
        }else{
            for (int i = 0; i < currentVert.getNeighbors().size(); i++){
                Vertex<String> n = currentVert.getNeighbors().get(i);
      
      			// eliminate the vertexes that are already within the path
                if (!VL.contains(n) && !queue.contains(n)){                 
                    
                    List<Vertex<String>> path = new ArrayList<Vertex<String>>();
                    for (int j = 0; j < currentVert.getPath().size(); j++){
                        path.add(currentVert.getPath().get(j));
                    }
                    
                    path.add(currentVert);
                    n.setPath(path);
                    queue.add(n);
                }
            }
        } 
    }
  // returns a list of the graph path in a breadth-first traversal if cannot find the endvert
  return VL;
  }

  /******** 
   * This method should use a depth-first traversal to find a path through the 
   * maze, then return that path.
   ******/
  public static List<Vertex<String>> solveMazeDepthFirst(BasicGraphADT<String> maze, String startvert, String endvert) {
    // Use a depth-first search to find a path through the maze
      Stack<Vertex<String>> stack = new Stack<Vertex<String>>();
      List<Vertex<String>> VL1 = new ArrayList<Vertex<String>>();
     
      stack.push(maze.getVertex(startvert));
          
	while (!stack.empty()){
		Vertex<String> currentVert1 = stack.pop();     

			// eliminate the vertexes that are already within the path
            if (!VL1.contains(currentVert1)){ 
				VL1.add(currentVert1);
				
				// return the path to get to the currentVert if the currentVert equals to the endvert
                if (endvert.equals(currentVert1.getLabel())){
                	currentVert1.getPath().add(currentVert1);
                    return currentVert1.getPath();  
                }else{
                	for (int i = 0; i < currentVert1.getNeighbors().size(); i++){
                		List<Vertex<String>> depthPath = new ArrayList<Vertex<String>>();
                  		for (int j = 0; j < currentVert1.getPath().size(); j++)
                	 		 depthPath.add(currentVert1.getPath().get(j));
                	  
                  		Vertex<String> n1 = currentVert1.getNeighbors().get(i);                         
                        depthPath.add(currentVert1);
                    	n1.setPath(depthPath);
                    	stack.push(n1);	
                    }
                }
            }
    	}
    // returns a list of the graph path in a depth-first traversal if cannot find the endvert
    return VL1;
  }

  /******** 
   * This method should use Dijkstra's algorithm to find the shortest cost path through the 
   * maze, then return that path.
   ******/
  public static List<Vertex<String>> solveMaze(WeightedGraphADT<String> maze, String startvert, String endvert) {

    Comparator<Vertex<String>> com = new VertexComparator();
    PriorityQueue<Vertex<String>> priorityQueue = new PriorityQueue<Vertex<String>>(100, com);
    priorityQueue.add(maze.getVertex(startvert));
    List<Vertex<String>> VL2 = new ArrayList<Vertex<String>>();
      
    while(!priorityQueue.isEmpty()){            	

    	Vertex<String> currentVert2 = priorityQueue.poll();
    	VL2.add(currentVert2);

    	if (endvert.equals(currentVert2.getLabel())){
    		currentVert2.getPath().add(currentVert2);
    		return currentVert2.getPath();    	
    	} else{
    		for (int i = 0; i < currentVert2.getNeighbors().size(); i++){
    			Vertex<String> n2 = currentVert2.getNeighbors().get(i); 
    			// eliminate the vertexes that are already within the path
                if (!VL2.contains(n2) && !priorityQueue.contains(n2)){     		
    				List<Vertex<String>> newQueue = new ArrayList<Vertex<String>>();
                	for (int j = 0; j < currentVert2.getPath().size(); j++)
                    	newQueue.add(currentVert2.getPath().get(j));
                    
                double distance = currentVert2.getDistance();
                n2.setDistance(distance + maze.getEdgeWeight(currentVert2.getLabel(), n2.getLabel()));
                
                newQueue.add(currentVert2);
                n2.setPath(newQueue);
            	priorityQueue.add(n2);    
            	}
    		}
    	}  
    }
    // returns a list of the graph path using Dijkstra's algorithm if cannot find the endvert
    return VL2;
  }
}
