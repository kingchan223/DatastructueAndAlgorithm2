package week12_ShortestPath;

import week10_minimal_spanning_tree.kru_prim_by_prof.Prim;
import week10_minimal_spanning_tree.kru_prim_by_prof.WGraphInList;

import java.util.Arrays;
import java.util.HashSet;

public class DijkstraSP extends WGraphInList {
    int [] d  ;   // distance;
    int r = -1;   // start node
    HashSet<String> S, V ;

    //특정 정점의 이전 노드(경로를 기억하기 위해 필요하다.)
    String [] prev;
    public DijkstraSP(int max) {
        super(max);
    }

    public void init(String start) {
        d = new int [numOfV];
        S = new HashSet<>();
        V = new HashSet<>();
        prev = new String[numOfV];

        for (String s : vertices )
            V.add(s);
        r = vertices.indexOf(start);
        Arrays.fill(d, 9999);
        d[r]=0;
    }

    public void shortestPath() {
        while(S.size()<numOfV) {
            String u = extractMin(diff(V,S));  // diff(V,S) == V-S
            S.add(u);
            System.out.println(">>> "+u+" is selected.");
            for (String v : adjacent(u)) {  // L(u) == adjacent(u)
                HashSet<String> temp = diff(V,S);

                int wuv = getWeight(u, v);
                int dv = d[vertices.indexOf(v)];
                int du = d[vertices.indexOf(u)];

                if (temp.contains(v) &&  (du+wuv) < dv){
                    d[vertices.indexOf(v)] = du + wuv ;
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

        DijkstraSP dsSP = new DijkstraSP(vertices.length);

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
