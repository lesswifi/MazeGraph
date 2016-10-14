import java.util.Comparator;

/********
 * Simple String Comparator class that just uses String's 
 * built-in compare method to compare two strings
 *
 * by Megan Zhao & Fangge Deng
 *
 ********/
public class VertexComparator implements Comparator<Vertex<String>> {
  public int compare(Vertex<String> s1, Vertex<String> s2) {
    return Double.valueOf(s1.getDistance()).compareTo(Double.valueOf(s2.getDistance()));
  }
}
