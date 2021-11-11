package week10_minimal_spanning_tree.kruskal;

import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {

    int numOfV;
    RankSet[] sets;
    public ArrayList<Edge> edges = new ArrayList<>();
    ArrayList<Integer> MST = new ArrayList<>();
    int numOfEdges;
    int totalW;

    public Kruskal(int numOfV) {
        this.numOfV = numOfV;
        this.numOfEdges = 0;
        this.totalW = 0;
        this.sets = new RankSet[numOfV];
        for (int i = 0; i < numOfV; i++){
            sets[i] = new RankSet(i);
            sets[i].makeSet(i);
        }
    }

    private void showMST() {
        System.out.println("경로의 총 합은 :"+totalW);
    }


    private void findMST() {
        while(this.numOfEdges < numOfV-1){
            Edge edge = edges.remove(0);
            RankSet rankSet1 = sets[edge.getFrom()];
            RankSet rankSet2 = sets[edge.getTo()];
            int key1 = rankSet1.findSet(rankSet1).key;
            int key2 = rankSet2.findSet(rankSet2).key;

            if(key1!=key2){
                sets[edge.getFrom()].union(sets[edge.getTo()]);
                numOfEdges+=1;
                MST.add(edge.getFrom());
                MST.add(edge.getTo());
                totalW += edge.getWeight();
            }
        }
    }

    private void showGraph() {

    }

    private void addWeightOfEdges(int[][] graphEdges) {
        for (int[] graphEdge : graphEdges)
            edges.add(new Edge(graphEdge[0],graphEdge[1],graphEdge[2]));
        Collections.sort(edges);
    }

    public static void main(String[] args) {
        int numOfNode = 8;
        Kruskal kruskal = new Kruskal(numOfNode);
        //{0, 1, 4} 일때 정점 0에서 정점 1로 이동하는데 weight가 4라는 의미이다.
        int [][] graphEdges = {
                {0,1, 4}, {0,2, 3}, {0,3, 6},
                {1,2, 3}, {2,3, 7}, {2,4, 2},
                {3,5, 1}, {3,6, 5}, {5,6, 2},{5, 7, 10}, {6,7, 9}};

        kruskal.addWeightOfEdges(graphEdges);
        kruskal.showGraph();
        kruskal.findMST();
        kruskal.showMST();
    }
}
