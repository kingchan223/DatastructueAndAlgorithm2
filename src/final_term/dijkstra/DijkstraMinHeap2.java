package final_term.dijkstra;


import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class DijkstraMinHeap2 extends WGraphInList {
    public static final int INF = 999_999_999;
    int [] d  ;   // distance;
    int r = -1;   // start node
    HashSet<String> S, V ;
    PriorityQueue<Vertex> verticesP;
    boolean[] visited;

    //특정 정점의 이전 노드(경로를 기억하기 위해 필요하다.)
    String [] prev;
    public DijkstraMinHeap2(int max) {
        super(max);
    }

    public void init(String start) {
        d = new int [numOfV];
        S = new HashSet<>();
        V = new HashSet<>();
        prev = new String[numOfV];
        // -- 방문처리 --
        visited = new boolean[numOfV];
        Arrays.fill(visited, false);
        visited[vertices.indexOf(start)] = true;//start 노드는 방문처리한다.
        // ------------

        V.addAll(vertices);
        r = vertices.indexOf(start);
        Arrays.fill(d, INF);
        d[r]=0;//시작점의 dist 는 0이다.

        // -- 우선순위 큐에 담기는 정점들 초기화 --
        verticesP = new PriorityQueue<>();
        verticesP.add(new Vertex(start, 0));//start 노드의 weight 는 0으로 한다.
        for (String name : vertices) {
            if(name.equals(start)) continue;
            verticesP.add(new Vertex(name, INF));
        }
        // --------------------------------
    }

    public void shortestPath() {
        while(S.size()<numOfV) {
            String u = extractMin();  // diff(V,S) == V-S
            S.add(u);
            System.out.println(">>> "+u+" is selected.");
            for (String v : adjacent(u)) {  // L(u) == adjacent(u)
                HashSet<String> temp = diff(V,S);
                int wuv = getWeight(u, v);
                int dv = d[vertices.indexOf(v)];
                int du = d[vertices.indexOf(u)];

                if (!visited[vertices.indexOf(v)] &&  (du+wuv) < dv){
                    d[vertices.indexOf(v)] = du + wuv ;
                    verticesP.remove(new Vertex(v));
                    Vertex vertex = new Vertex(v, du + wuv);
                    visited[vertices.indexOf(v)] = true;
                    verticesP.add(vertex);
                    //이전에 어떤 정점을 거쳤는지 알려준다.
                    prev[vertices.indexOf(v)] = u;
                }
            }
        }
        for (int i=0; i<numOfV; i++)
            System.out.print(vertices.get(i)+"("+d[i]+")");
        System.out.println();
    }

    public void showShortestPath(){
        for (int i = 0; i < numOfV; i++) {
            System.out.println(prev[i]+" => " +vertices.get(i) +"(가중치:"+d[i]+")");
        }
    }

    private int getWeight(String u, String v) {
        return getEdge(u, v).weight;
    }
    private HashSet<String> diff(HashSet<String> s1, HashSet<String> s2) {
        HashSet<String> result = s1;
        for (String s : s2)
            result.remove(s);
        return result;
    }

    private String extractMin() {
        Vertex minV = verticesP.poll();
        return minV.getName();
    }

    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        DijkstraMinHeap2 dsSP = new DijkstraMinHeap2(vertices.length);

        dsSP.createGraph("Dijkstra-Test Graph");
        for (int i = 0; i<graphEdges.length; i++)
            dsSP.insertEdge(vertices[graphEdges[i][0]],vertices[graphEdges[i][1]], graphEdges[i][2]);
        dsSP.showGraph();

        System.out.println("\nDijkstra Algorithm starts from "+"서울");

        dsSP.init("서울");
        dsSP.shortestPath();
        dsSP.showShortestPath();
    }

    public static class Vertex implements Comparable<Vertex>{
        public String name ;
        public int weight ;

        public Vertex(String name) {
            this.name = name;
        }
        public Vertex (String name, int w){
            this.name = name;
            this.weight = w;
        }
        public String getName() {
            return name;
        }
        public int getWeight() {
            return weight;
        }
        @Override
        public int compareTo(Vertex that) {
            if(this.weight == that.weight) return 0;
            return this.weight-that.weight;
        }
        @Override
        public boolean equals(Object obj) {
            Vertex that = (Vertex) obj;
            if(this.name.equals(that.name)) return true;
            return false;
        }
    }
}
