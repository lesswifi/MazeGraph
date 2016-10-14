/*********************
 * Edge class for use in building a weighted graph
 *
 * by Megan Zhao & Fangge Deng
 *
 **********************/


public class Edge<T>{

    private Vertex<T> one, two;
    private double weight;

    public Edge(Vertex<T> first, Vertex<T> second){
        this.one = first;
        this.two = second;
    }

    public Edge(Vertex<T> first, Vertex<T> second, double weight){
        this.one = first;
        this.two = second;
        this.weight = weight;
    }

    public double getWeight(){
        return this.weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }
    
    public Vertex<T> getOne(){
        return this.one;
    }
    
    public Vertex<T> getTwo(){
        return this.two;
    }
 
}