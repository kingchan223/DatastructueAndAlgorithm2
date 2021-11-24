package week12_ShortestPath;

import java.util.Arrays;
import java.util.HashSet;

public class FloydWarshallSP extends GraphInMatrixWD {
    private static final int INF = 999_999_999;
    int [] d  ;   // distance;
    int r = -1;   // start node
    HashSet<String> S, V ;
    int [][] dp; // result will be stored at this dp list

    String [] prev;
    public FloydWarshallSP(int max) {
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
        d[r]=0;

        dp = new int[maxNumber][maxNumber];
        for(int i=0; i<maxNumber; i++){
            for (int j = 0; j < maxNumber; j++) {
                if(i==j) dp[i][j] = 0;
                else{
                    if(adjacentMatrix[i][j]!=0) dp[i][j] = adjacentMatrix[i][j];
                    else dp[i][j] = INF;
                }
            }
        }
    }

    public void shortestPath() {
        for(int i=0; i<maxNumber; i++){
            for(int j=0; j<maxNumber; j++){
                for(int k=0; k<maxNumber; k++){
                    dp[i][k] = Math.min(dp[i][k], dp[i][j] + dp[j][k]);
                }
            }
        }
    }

    public void showShortestPath(){
        System.out.print("    .");
        for (String v : vertices) {
            System.out.printf("%4s",v);
        }
        System.out.println();
        for(int i=0; i<maxNumber; i++){
            System.out.print(vertices.get(i)+" ");
            for(int j=0; j<maxNumber; j++){
                int n = dp[i][j];
                if(n!=INF) System.out.printf("%5d", n);
                else System.out.printf("%5s","INF");
            }
            System.out.println();
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
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7},
                {1, 0, 11 }, {2, 0, 8}, {3, 0, 9}, {3, 1, 13},
                {6, 1, 8}, {4, 2, 10}, {4, 3, 5}, {5, 3, 12}, {6, 5, 7}};

        FloydWarshallSP dsSP = new FloydWarshallSP(vertices.length);

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
