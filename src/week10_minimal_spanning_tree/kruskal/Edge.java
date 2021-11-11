package week10_minimal_spanning_tree.kruskal;

public class Edge implements Comparable<Edge> {
    int to;
    int from;
    int weight;

    public Edge( int from, int to,int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(w:" + this.weight+", "+from+"=>"+to+")";
    }

    @Override
    public int compareTo(Edge that) {
        if(this.weight == that.weight) return 0;
        return this.weight-that.weight;
    }

    public int getTo() {return to;}
    public int getWeight() {return weight;}

    public int getFrom() {
        return from;
    }
}
