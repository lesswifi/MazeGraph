/***********************
 * Represents an undirected weighted graph
 *****/
public interface WeightedGraphADT<T> extends BasicGraphADT<T> {

  /******************
   * Add an edge to this graph between the two given labels with the given weight
   * @return Whether the edge was successfully added
   ********************/
  public boolean addEdge(T beg, T end, double weight);

  /******************
   * Gets the weight of the edge between two vertices
   * @return the weight, or Double.POSITIVE_INFINITY if no edge exists
   ********************/
  public double getEdgeWeight(T beg, T end);

}
