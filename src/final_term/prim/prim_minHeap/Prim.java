package final_term.prim.prim_minHeap;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/* extractMin에 minHeap을 사용한 Prim알고리즘 */

public class Prim extends WGraphInList {
    int [] dist  ;   // distance;
    int startIdx = -1;   // start node
    HashSet<String> V, S;
    PriorityQueue<EdgeElement> edges;

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
        startIdx = vertices.indexOf(start);
        S.add(start);
        edges = new PriorityQueue<>();
        LinkedList<EdgeElement> edgeElements = adjacentList.get(vertices.indexOf(start));//시작노드 간선 넣어주기
        edges.addAll(edgeElements);
        Arrays.fill(dist, 9999);
        dist[startIdx]=0;
    }

    public void MST() {
        while(S.size()<numOfV) {
            String u = extractMin();  // diff(V,S) == V-S
            S.add(u);
            System.out.println(">>> "+u+" is selected.");
        }
        for (int i=0; i<numOfV; i++)
            System.out.print(vertices.get(i)+"("+dist[i]+")");
        System.out.println();
    }


    private String extractMin(){
        while(true){
            EdgeElement sstEdge = edges.poll();//minHeap에서 poll
            String to = sstEdge.to;
            if(!S.contains(to)){
                dist[vertices.indexOf(to)] = sstEdge.weight;
                LinkedList<EdgeElement> edgeElements = adjacentList.get(vertices.indexOf(to));
                edges.addAll(edgeElements);
                return sstEdge.to;
            }
        }
    }
//    private int getWeight(String u, String v) {
//        return getEdge(u, v).weight;
//    }

//    private HashSet<String> diff(HashSet<String> s1, HashSet<String> s2) {
//        HashSet<String> result = s1;
//        for (String s : s2) result.remove(s);
//        return result;
//    }

//    public class Edge implements Comparable<Edge>{
//        String from;
//        String to;
//        int weight;
//
//        public Edge(String from, String to, int weight) {
//            this.from = from;
//            this.to = to;
//            this.weight = weight;
//        }
//
//        @Override
//        public int compareTo(Edge that) {
//            if(this.weight == that.weight) return 0;
//            return this.weight-that.weight;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            Edge that = (Edge) obj;
//            if(this.from.equals(that.from) && this.to.equals(that.to)) return true;
//            return this.from.equals(that.to) && this.to.equals(that.from);
//        }
//    }
}