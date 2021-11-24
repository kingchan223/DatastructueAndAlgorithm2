package week12_ShortestPath;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DijkstraSP3 extends GraphInMatrixWD2 {
    int [] d  ;
    int r = -1;
    String [] prev;
    public DijkstraSP3(int max) {
        super(max);
    }

    public void init(String start) {
        d = new int [maxNumber];
        prev = new String[maxNumber];
        r = vertices.indexOf(start);
        Arrays.fill(d, 9999);
        d[r]=0;
    }

    public void shortestPath() {
        System.out.println("\n *** BFS Iteration *** \n");
        BFSSP(vertices.get(r));
    }

    public void BFSSP(String s) {
        Deque<String> que = new ArrayDeque<String>();
        System.out.println(s+" is visited ");
        que.add(s);

        while (!que.isEmpty()) {
            String v = que.poll();//출발점
            int index = vertices.indexOf(v);//출발점의 index
            for (int i=0; i<vertices.size();i++) {//모든 정점들과 비교. 즉 여기서
                if (adjacentMatrix[index][i]!=0) {//v와 어떤 노드 i사이에 간선이 있다면
                    String u = vertices.get(i);//도착점
                    if(d[i] > d[index]+adjacentMatrix[index][i]){
                        d[i] = d[index]+adjacentMatrix[index][i];
                        prev[i] = v;
                        System.out.println(u+"'s distance is updated.");
                    }
                    que.add(vertices.get(i));
                }
            }
        }
    }

    public void showShortestPath(){
        for (int i = 0; i < maxNumber; i++) {
            System.out.println(prev[i]+" => " +vertices.get(i) +"(가중치:"+d[i]+")");
        }
    }

    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        DijkstraSP3 dsSP = new DijkstraSP3(vertices.length);

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
