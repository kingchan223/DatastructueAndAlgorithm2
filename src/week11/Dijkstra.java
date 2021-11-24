package week11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Dijkstra {
    private static final int INF = 999_999_999;
    int numOfVertex;
    ArrayList<LinkedList<Edge>> edges;
    boolean visited[];
    int[] minWeight;
    int start;

    public Dijkstra(int numOfVertex, int start) {
        this.numOfVertex = numOfVertex;
        minWeight = new int[numOfVertex];
        visited = new boolean[numOfVertex];
        edges = new ArrayList<>();
        this.start = start;
        init();
    }

    public void init(){
        for(int i=0; i<numOfVertex;i++){
            minWeight[i] = INF;
            edges.add(new LinkedList<Edge>());
        }
        minWeight[start] = 0;
        Arrays.fill(visited, false);

    }

    private class Edge{
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public void insertEdge(int from, int to, int weight){
        edges.get(from).add(new Edge(to, weight));
    }

    public void dijkstra(){
        Queue<Integer> q = new LinkedList<>();
        visited[start] = true;
        q.add(start);
        while(!q.isEmpty()){
            Integer now = q.poll();
            LinkedList<Edge> edges = this.edges.get(now);
            for (Edge edge : edges) {
                if(minWeight[now] + edge.weight < minWeight[edge.to]){
                    minWeight[edge.to] = minWeight[now] + edge.weight;
                }
                if(!visited[edge.to]){
                    visited[edge.to] = true;
                    q.add(edge.to);
                }
            }
        }
    }

    public void showShortValue(){
        for (int i=0 ; i< numOfVertex; i++) {
            System.out.println(i+"번 정점까지 가는 최소 비용: "+minWeight[i]);
        }
    }


    public static void main(String[] args) {
        int numOfVertex = 8;
        int [][] graphEdges = {
                {0,1, 4}, {0,2, 3}, {0,3, 6},
                {1,2, 3}, {2,3, 7}, {2,4, 2},
                {3,5, 1}, {3,6, 5}, {5,6, 2},{5, 7, 10}, {6,7, 9}};

        Dijkstra di = new Dijkstra(numOfVertex, 0);

        for(int i = 0; i < graphEdges.length; i++)
            di.insertEdge(graphEdges[i][0], graphEdges[i][1], graphEdges[i][2]);

        di.dijkstra();
        di.showShortValue();
    }
}
