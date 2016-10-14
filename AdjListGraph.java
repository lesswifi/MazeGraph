import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collection;
import java.util.Set;


/*********************
 * AdjListGraph class to build a maze by an adjacency-list implementation of a Graph ADT
 *
 * by Megan Zhao & Fangge Deng
 *
 **********************/

public class AdjListGraph<T> implements BasicGraphADT<T>
//implements BasicGraphADT<T>
{
	private HashMap<T,Vertex<T>> map;
	
	public AdjListGraph(){
		this.map = new HashMap<T,Vertex<T>>();
	}
    
  /*******************
   * Add a vertex to this graph with given label
   * @return Whether the vertex was successfully added
   ********************/
	public boolean addVertex(T vert){
  		Vertex<T> newVertex = new Vertex<T>(vert);
  		map.put(vert, newVertex);
  		return true;
	}
	
	/******************
   * Add an edge to this graph between the two given labels
   * @return Whether the edge was successfully added
   ********************/
	public boolean addEdge(T beg, T end){
		Vertex<T> beg1 = getVertex(beg);
		Vertex<T> end1 = getVertex(end);
		beg1.addNeighbor(end1);
		end1.addNeighbor(beg1);
		return true;
	}
	
	
	/******************
   * Tests whether a vertex exists in the graph
   * @return Whether the vertex exists
   ********************/
	public boolean hasVertex(T vert){
		return map.containsKey(vert);
	}

  /******************
   * Tests whether an edge exists in the graph
   * @return Whether the edge exists
   ********************/
	public boolean hasEdge(T beg, T end){
  		boolean containsEdge = false;
  		Vertex<T> begVertex= getVertex(beg);
  		List<Vertex<T>> neighbors = begVertex.getNeighbors();
  		for (int j = 0; j < neighbors.size(); j++){
  			Comparable compareNeighbor = (Comparable)(neighbors.get(j).getLabel());
  			Comparable compareEnd = (Comparable)end;
  				if (compareNeighbor.equals(compareEnd))
  					containsEdge = true;
  		}
  		return containsEdge;
	}

  /*****************
   * Gets a list containing all the neighbors of a given vertex
   * @return the neighbor list as a java List
   *********************/
	public List<Vertex<T>> getNeighbors(T vert){
		Vertex<T> vertex = getVertex(vert);
		return vertex.getNeighbors();
	}
	
	/****************************
   * Gets the vertex object associated with the given label
   * @return the vertex
   ************************/
	public Vertex<T> getVertex(T lab){
		return map.get(lab);
	}

  /*****************
   * Tests if the graph is empty
   * @return Whether the graph is empty
   *******************/
	public boolean isEmpty(){
		return map.isEmpty();
	}

  /********************
   * Gets the size of the map
   * @return The number of vertices
   *********************/
	public int getNumVertices(){
		return map.size();
	}

  /********************
   * Gets the number of edges
   * @return The number of edges
   *********************/
	public int getNumEdges(){
		int sum = 0;
		Collection<Vertex<T>> collection = map.values();
		Vertex[] b = new Vertex[1];
		Vertex<T>[] array = collection.toArray(b);
		
		for (int i = 0; i < array.length; i++){
			int numOfNeighbors = array[i].getNeighbors().size();
			sum += numOfNeighbors;
		}
		return sum/2;
	}
  /**************
   * Clear all edges and vertices from the graph
   ********************/
	public void clear(){
		map.clear();
	}
	
	public String toString(){
		String string = "";
		Collection<Vertex<T>> collection = map.values();
		Vertex[] b = new Vertex[1];
		Vertex<T>[] array = collection.toArray(b);
		
		for (int i = 0; i < array.length; i++){
			string += array[i].getLabel();
			List<Vertex<T>> neighbors = array[i].getNeighbors();			
			string += " -> ";
			for (int j = 0; j < neighbors.size(); j++){
				string += neighbors.get(j).getLabel();
				string += " ";			
		}		
			string += "\n";
		}
		return string;
			
	}

  /********************
   * Test AdjListGraph
   *********************/
public static void main(String[] args) {
  AdjListGraph<String> gr = new AdjListGraph<String>();
  gr.addVertex("foo");
  gr.addVertex("bar");
  gr.addVertex("baz");
  gr.addVertex("ninja");
  gr.addVertex("robot");
  gr.addEdge("foo", "bar");
  gr.addEdge("foo", "baz");
  gr.addEdge("foo", "ninja");
  gr.addEdge("ninja", "robot");

  System.out.println(gr.toString()); 

}

}




















