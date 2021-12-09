package week12_ShortestPath;

import week10_minimal_spanning_tree.kruskal.Edge;

import java.util.*;

public class DijkstraSPMinHeap extends WGraphInList {
    int [] dist  ;
    int r = -1;
    String [] prev;
    boolean[] visited;

    PriorityQueue<Vertex> PQ;
    public DijkstraSPMinHeap(int max) {
        super(max);
    }

    public void init(String start) {
        dist = new int [numOfV];
        prev = new String[numOfV+1];
        visited = new boolean[numOfV];
        PQ = new PriorityQueue<>();
        Arrays.fill(visited, false);
        r = vertices.indexOf(start);
        Arrays.fill(dist, 9999);
        dist[r]=0;
        for(int i=0; i<numOfV; i++) PQ.add(new Vertex(vertices.get(i), dist[i]));
        prev[0] = start;
    }

    public void shortestPath() {
        System.out.println("\n *** BFS Iteration *** \n");
        BFSSP();
    }

    public void BFSSP(){
        int cnt = 1;
        while (!PQ.isEmpty()) {
            Vertex v = PQ.poll();
            if(!visited[vertices.indexOf(v.name)]){
                visited[vertices.indexOf(v.name)]= true;
                for(EdgeElement edge : adjacentList.get(vertices.indexOf(v.name))){
                    if(dist[vertices.indexOf(edge.destination)] > dist[vertices.indexOf(v.name)] + edge.weight){
                        PQ.remove(new Vertex(edge.destination));
                        PQ.add(new Vertex(edge.destination, dist[vertices.indexOf(v.name)] + edge.weight));
                        dist[vertices.indexOf(edge.destination)] = dist[vertices.indexOf(v.name)] + edge.weight;
                        prev[cnt++] = v.name;
                    }
                }
            }
        }
    }

    public void showShortestPath(){
        for (int i = 0; i < numOfV; i++) {
            System.out.println(prev[i]+" => " +vertices.get(i) +"(가중치:"+dist[i]+")");
        }
    }

    public static class Vertex implements Comparable<Vertex>{
        String name;
        int weight;

        public Vertex(String name) {
            this.name = name;
        }

        public Vertex(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
        @Override
        public int compareTo(Vertex that) {
            if(this.weight == that.weight) return 0;
            return this.weight-that.weight;
        }

        @Override
        public boolean equals(Object obj) {
            Vertex that = (Vertex) obj;
            return this.name.equals(that.name);
        }
    }

    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        DijkstraSPMinHeap dsSP = new DijkstraSPMinHeap(vertices.length);

        dsSP.createGraph("Dijkstra-Test Graph");
        for (int[] graphEdge : graphEdges)
            dsSP.insertEdge(vertices[graphEdge[0]], vertices[graphEdge[1]], graphEdge[2]);
        dsSP.showGraph();

        System.out.println("\nDijkstra Algorithm starts from "+"서울");

        dsSP.init("서울");
        dsSP.shortestPath();
        dsSP.showShortestPath();
    }
}
