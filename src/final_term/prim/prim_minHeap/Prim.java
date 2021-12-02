package final_term.prim.prim_minHeap;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/* extractMin에 minHeap을 사용한 Prim알고리즘 */

public class Prim extends WGraphInList {
    int [] dist  ;   // distance;
    int startIdx = -1;   // start node
    HashSet<String> V, S, P;
    PriorityQueue<Edge> edges;

    public Prim(int max) {
        super(max);
    }

    public void init(String start) {
        dist = new int [numOfV];
        S = new HashSet<>();
        V = new HashSet<>();

        //그래프의 모든 정점을 allVertex 에 담기
        V.addAll(vertices);
        //그래프의 모든 간선을 edges에 담기
        edges = new PriorityQueue<>();
        for (LinkedList<EdgeElement> edgeElements : adjacentList) {
            for (EdgeElement edgeElement : edgeElements) {
                Edge edge = new Edge(edgeElement.getSource(), edgeElement.getDestination(), edgeElement.getWeight());
                if(!edges.contains(edge)) edges.add(edge);
            }
        }
        startIdx = vertices.indexOf(start);
        Arrays.fill(dist, 9999);
        dist[startIdx]=0;
    }

    public void MST() {
        while(S.size()<numOfV) {
            String u = extractMin(V);  // diff(V,S) == V-S
            S.add(u);
            System.out.println(">>> "+u+" is selected.");
            for (String v : adjacent(u)) {  // L(u) == adjacent(u)
                HashSet<String> temp = V;
                int wuv = getWeight(u, v);
                int dv = dist[vertices.indexOf(v)];
                if (temp.contains(v) &&  wuv<dv)
                    dist[vertices.indexOf(v)] = wuv ;
            }
        }
        for (int i=0; i<numOfV; i++)
            System.out.print(vertices.get(i)+"("+dist[i]+")");
        System.out.println();
    }

    private int getWeight(String u, String v) {
        return getEdge(u, v).weight;
    }

    private HashSet<String> diff(HashSet<String> s1, HashSet<String> s2) {
        HashSet<String> result = s1;
        for (String s : s2) result.remove(s);
        return result;
    }

    private String extractMin(HashSet<String> diff) {
        String minVertex = null;
        int min = 9999;;
        for (String s : diff) {
            if (dist[vertices.indexOf(s)] < min) {
                minVertex = s;
                min = dist[vertices.indexOf(s)];
            }
        }
        return minVertex;
    }

    public class Edge implements Comparable<Edge>{
        String from;
        String to;
        int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge that) {
            if(this.weight == that.weight) return 0;
            return this.weight-that.weight;
        }

        @Override
        public boolean equals(Object obj) {
            Edge that = (Edge) obj;
            if(this.from.equals(that.from) && this.to.equals(that.to)) return true;
            return this.from.equals(that.to) && this.to.equals(that.from);
        }
    }
}