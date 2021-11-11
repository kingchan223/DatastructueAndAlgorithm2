package week10_minimal_spanning_tree.prim;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Prim {
    int numOfV;
    boolean[] t;
    int totalW = 0;
    ArrayList<PriorityQueue<Edge>> minEdges;
    ArrayList<ArrayList<Edge>> adjacentArray;
    ArrayList<Integer> MST;

    public Prim(int numOfV) {
        this.numOfV = numOfV;
        createGraph();
    }

    public void createGraph() {
        adjacentArray = new ArrayList<>();
        minEdges = new ArrayList<>();
        MST = new ArrayList<>();
        t = new boolean[numOfV];
        for (int i = 0; i < numOfV; i++){
            adjacentArray.add(new ArrayList<Edge>());
            minEdges.add(new PriorityQueue<Edge>());
        }
    }

    private void addWeightOfEdges(int[][] graphEdges) {
        for (int[] graphEdge : graphEdges) {
            insertEdge(graphEdge[0], graphEdge[1], graphEdge[2]);
        }
    }

    private void findMST() {
        //MST에 추가된 V와 연결된 edge들을 담는 minHeap
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        // 시작점은 무조건 0으로 한다.
        t[0] = true;
        MST.add(0);
        PriorityQueue<Edge> edges = minEdges.get(0);
        pq.addAll(edges);
        while(MST.size() < numOfV){
            Edge edge = pq.poll();
            if(edge==null){
                break;
            }
            //방문한 노드가 아니라면 추가해준다.
            else if(!t[edge.getTo()]){
                t[edge.getTo()] = true;
                totalW += edge.getWeight();
                edges = minEdges.get(edge.getTo());
                pq.addAll(edges);
                MST.add(edge.getTo());
            }
            //방문한 노드라면 건너뛴다.
        }
    }

    private void showMST() {
        System.out.println("가중치의 합은 : "+ totalW);
        for (Integer i : MST) {
            System.out.print(i+" ===> ");
        }

    }

    private void showGraph(){
        for (int i=0; i<numOfV;i++){
            System.out.print(i+"  ");
            for (Edge v : adjacentArray.get(i))
                System.out.print(" => "+ v.getTo()+" ("+v.getWeight()+")" );
            System.out.println();
        }
    }

    private void insertEdge(int from, int to, int weight) {
        if(!hasEdge(from, to)){
            adjacentArray.get(from).add(new Edge(from, to, weight));
            adjacentArray.get(to).add(new Edge(to, from, weight));
            minEdges.get(from).add(new Edge(from, to, weight));
            minEdges.get(to).add(new Edge(to, from, weight));
        }
    }

    private boolean hasEdge(int from, int to) {
        ArrayList<Edge> edges = adjacentArray.get(from);
        for (Edge edge : edges)
            if(edge.getTo() == to) return true;
        return false;
    }

    public static void main(String[] args) {
        int numOfV = 8;
        Prim prim = new Prim(numOfV);
        //{0, 1, 4} 이라면 정점 0에서 정점 1로 이동하는데 weight가 4라는 의미이다.
        int [][] graphEdges = {
                {0,1, 4}, {0,2, 3}, {0,3, 6},
                {1,2, 3}, {2,3, 7}, {2,4, 2},
                {3,5, 1}, {3,6, 5}, {5,6, 2},{5, 7, 10}, {6,7, 9}};

        prim.addWeightOfEdges(graphEdges);
        prim.findMST();
        prim.showMST();
    }
}
