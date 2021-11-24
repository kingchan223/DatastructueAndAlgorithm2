package week12_ShortestPath;
import week9_graph.GraphInMatrix;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;

public class DijkstraSP2 extends GraphInMatrixWD {
    int [] d  ;   // distance;
    int r = -1;   // start node
    HashSet<String> S, V ;

    //특정 정점의 이전 노드(경로를 기억하기 위해 필요하다.)
    String [] prev;
    public DijkstraSP2(int max) {
        super(max);
    }

    public void init(String start) {
        d = new int [maxNumber];
        S = new HashSet<>();
        V = new HashSet<>();
        prev = new String[maxNumber];

        V.addAll(vertices);
        r = vertices.indexOf(start);
        Arrays.fill(d, 9999);
        d[r]=0;
    }

    public void shortestPath() {
        initVisited();
        System.out.println("\n *** BFS Iteration *** \n");
        BFSSP(vertices.get(r));
    }

    public void BFSSP(String s) {
        Deque<String> que = new ArrayDeque<String>();
        visited[vertices.indexOf(s)]=true;
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

    private int getWeight(String u, String v) {
        return getEdge(u, v);
    }

    private HashSet<String> diff(HashSet<String> s1, HashSet<String> s2) {
        HashSet<String> result = s1;
        for (String s : s2)
            result.remove(s);
        return result;
    }

    private String extractMin(HashSet<String> diff) {
        String minVertex = null;
        int min = 9999;;
        for (String s : diff) {
            if (d[vertices.indexOf(s)] < min) {
                minVertex = s;
                min = d[vertices.indexOf(s)];
            }
        }
        return minVertex;
    }

    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        DijkstraSP2 dsSP = new DijkstraSP2(vertices.length);

        dsSP.createGraph("Dijkstra-Test Graph");
        for (int i = 0; i<graphEdges.length; i++)
            dsSP.insertEdge(vertices[graphEdges[i][0]],vertices[graphEdges[i][1]], graphEdges[i][2]);
        dsSP.showGraph();

        System.out.println("\nDijkstra Algorithm starts from "+"서울");

        dsSP.init("서울");
        dsSP.shortestPath();
        dsSP.showShortestPath();
    }
}
