package final_term.prim.prim_minHeap;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/* extractMin에 minHeap을 사용한 Prim알고리즘 */

public class Prim extends WGraphInList {
    public static final int INF = 999_999_999;
    int [] dist  ;   // distance;
    int startIdx = -1;   // start node
    HashSet<String> V, S;
    PriorityQueue<EdgeElement> edgesInS;

    public Prim(int max) {
        super(max);
    }

    public void init(String start) {
        dist = new int [numOfV];
        S = new HashSet<>();
        V = new HashSet<>();

        //그래프의 모든 정점을 allVertex 에 담기
        V.addAll(vertices);
        //그래프의 모든 간선을 edgesInS에 담기
        startIdx = vertices.indexOf(start);
        S.add(start);
        edgesInS = new PriorityQueue<>();
        LinkedList<EdgeElement> edgeElements = adjacentList.get(vertices.indexOf(start));//시작노드 edges 넣어주기
        edgesInS.addAll(edgeElements);
        Arrays.fill(dist, INF);
        dist[startIdx]=0;
    }

    public void MST() {
        int result = 0;
        while(S.size()<numOfV) {//S가 V와 같아질 때까지 반복
            EdgeElement sstEdge = extractMin();// diff(V,S) == V-S
            String u = sstEdge.to;
            S.add(u);
            result += sstEdge.weight;
            System.out.println(">>> "+u+" is selected.");
            System.out.println("dist: " + sstEdge.weight);

        }
        for (int i=0; i<numOfV; i++)
            System.out.print(vertices.get(i)+"("+dist[i]+")");
        System.out.println();
        System.out.println("result : "+result);
    }


    private EdgeElement extractMin(){
        while(true){
            EdgeElement sstEdge = edgesInS.poll();//minHeap 에서 poll
            String to = sstEdge.to;
            if(!S.contains(to)){//현재 가장 작은 간선의 목적지가 아직 S에 포함되지 않았다면(방문하지 않은 정점이라면)
                dist[vertices.indexOf(to)] = sstEdge.weight;
                LinkedList<EdgeElement> edgeElements = adjacentList.get(vertices.indexOf(to));
                edgesInS.addAll(edgeElements);
                return sstEdge;
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