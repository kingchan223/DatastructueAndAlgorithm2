package week12_ShortestPath;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;

public class BellmanFordSP extends GraphInMatrixWD {
    private static final int INF = 999_999_999;
    int [] d  ;   // distance;
    int r = -1;   // start node
    HashSet<String> S, V ;

    //특정 정점의 이전 노드(경로를 기억하기 위해 필요하다.)
    String [] prev;
    public BellmanFordSP(int max) {
        super(max);
    }

    public void init(String start) {
        d = new int [maxNumber];
        S = new HashSet<>();
        V = new HashSet<>();
        prev = new String[maxNumber];

        V.addAll(vertices);
        r = vertices.indexOf(start);
        Arrays.fill(d, INF);
        d[r]=0;//시작점은 0으로 초기화
    }

    public void shortestPath() {
        Deque<String> que = new ArrayDeque<String>();
        String s = vertices.get(r);
        System.out.println(s+" is visited ");
        que.add(s);

        for (int i = 0; i < vertices.size() - 1; i++) {
            String u = que.poll();
            int index = vertices.indexOf(u);
            for (int j=0; j<vertices.size();j++) {//모든 정점들과 비교. 즉 여기서
                if (adjacentMatrix[index][j]!=0) {//v와 어떤 노드 i 사이에 간선이 있다면
                    String v = vertices.get(j);//도착점
                    if(d[j] > d[index]+adjacentMatrix[index][j]){
                        d[j] = d[index]+adjacentMatrix[index][j];
                        prev[j] = u;
                        System.out.println(v+"'s distance is updated.");
                    }
                    que.add(vertices.get(j));
                }
            }
        }
        que.add(s);
        String u = que.poll();
        int index = vertices.indexOf(u);
        for (int i=0; i<vertices.size();i++){//모든 정점들과 비교. 즉 여기서
            if (adjacentMatrix[index][i]!=0) {//v와 어떤 노드 i 사이에 간선이 있다면
                if(d[i] > d[index]+adjacentMatrix[index][i]){
                    System.out.println("Negative Cycle!!! ----");
                    break;
                }
                que.add(vertices.get(i));
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
                {1, 6, 8}, {2, 4, -10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        BellmanFordSP dsSP = new BellmanFordSP(vertices.length);

        dsSP.createGraph("BellmanFord-Test Graph");
        for (int[] graphEdge : graphEdges)
            dsSP.insertEdge(vertices[graphEdge[0]],vertices[graphEdge[1]],graphEdge[2]);
        dsSP.showGraph();

        System.out.println("\nBellmanFord Algorithm starts from "+"서울");

        dsSP.init("서울");
        dsSP.shortestPath();
        dsSP.showShortestPath();
    }
}
