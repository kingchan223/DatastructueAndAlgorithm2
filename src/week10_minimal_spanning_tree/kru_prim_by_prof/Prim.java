package week10_minimal_spanning_tree.kru_prim_by_prof;

import java.util.Arrays;
import java.util.HashSet;

public class Prim extends WGraphInList {
    int [] dist  ;   // distance;
    int startIdx = -1;   // start node
    HashSet<String> passedVertex, allVertex ;

    public Prim(int max) {
        super(max);
    }

    public void init(String start) {
        dist = new int [numOfV];
        passedVertex = new HashSet<>();
        allVertex = new HashSet<>();

        //그래프의 모든 정점을 allVertex 에 담기
        allVertex.addAll(vertices);
        startIdx = vertices.indexOf(start);
        Arrays.fill(dist, 9999);
        dist[startIdx]=0;
    }

    public void MST() {
        while(passedVertex.size()<numOfV) {
            String u = extractMin(diff(allVertex,passedVertex));  // diff(V,S) == V-S
            passedVertex.add(u);
            System.out.println(">>> "+u+" is selected.");
            for (String v : adjacent(u)) {  // L(u) == adjacent(u)
                HashSet<String> temp = diff(allVertex,passedVertex);
                int wuv = getWeight(u, v);
                int dv = dist[vertices.indexOf(v)];
                if (temp.contains(v) &&  wuv<dv)
                    dist[vertices.indexOf(v)] = wuv ;
            }
        }
        for (int i=0; i<numOfV; i++)
            System.out.print(vertices.get(i)+"("+dist[i]+")");
        System.out.println();
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
            if (dist[vertices.indexOf(s)] < min) {
                minVertex = s;
                min = dist[vertices.indexOf(s)];
            }
        }
        return minVertex;
    }

    public static class Edge{
        String from;
        String to;


    }
}
