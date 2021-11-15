package week11;

import week9_graph.GraphInList;
import week9_graph.GraphInMatrix;

import java.util.Arrays;
import java.util.LinkedList;

public class TopologicalSortInList extends GraphInList {

    public TopologicalSortInList(int maxN) {
        super(maxN);
    }

    public void TPSort1(){
        String[] A = new String[vertices.size()];
        int nOfVertices = vertices.size();

        for (int i = 0; i < nOfVertices; i++) {
            A[i] = getNextNode();
            deleteVertex(A[i]);
            showGraph();
        }

        System.out.println(">>> Start");
        for (int i = 0; i < nOfVertices; i++)
            System.out.println("=> "+A[i]);
        System.out.println();
    }

    private String getNextNode(){
        for (int i = 0; i < vertices.size(); i++) {
            int tempSum = 0;
            for(int j=0; j<vertices.size(); j++)
                tempSum+=adjacentMatrix[j][i];
            if(tempSum == 0)
                return vertices.get(i);
        }
        return null;
    }

    public void TPSort2(){
        LinkedList<String> R = new LinkedList<>();
        boolean[] visited = new boolean[vertices.size()];
        Arrays.fill(visited, false);
        for(String s: vertices){
            if(visited[vertices.indexOf(s)]==false)
                dfsTS(visited, s, R);
        }
    }

    private LinkedList<String> dfsTS(boolean[] visited, String s, LinkedList<String> R){
        visited[vertices.indexOf(s)]=true;
        for(String x : adjacent(s))
            if(visited[vertices.indexOf(x)]==false)
                dfsTS(visited, x, R);
        System.out.println(s + " is added a the first");
        R.addFirst(s);
        return R;
    }

    public static void main(String[] args) {
        int maxNoVertex = 10;
        String[] vertices = {"물을 붓기", "점화 하기", "봉지 뜯기", "라면 넣기", "스프 넣기", "계란 넣기"};
        int[][] graphEdges = {
                {0, 1}, {1, 3}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {3, 5}, {4, 5}
        };
        TopologicalSortInList myTO = new TopologicalSortInList(maxNoVertex);
        myTO.createGraph("Topological Sort1");
        for (int i = 0; i < graphEdges.length; i++)
            myTO.insertEdge(vertices[graphEdges[i][0]], vertices[graphEdges[i][1]]);

        myTO.showGraph();
        System.out.println("Topological Sort1 : start");
        myTO.TPSort1();

        TopologicalSortInList myTO2 = new TopologicalSortInList(maxNoVertex);
        myTO2.createGraph("Topological Sort1");
        for (int i = 0; i < graphEdges.length; i++)
            myTO2.insertEdge(vertices[graphEdges[i][0]], vertices[graphEdges[i][1]]);
        myTO2.showGraph();
        System.out.println("Topological Sort2 : start ");
        myTO2.TPSort2();
    }
}
