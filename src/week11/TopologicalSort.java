package week11;

import week9_graph.GraphInMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class TopologicalSort extends GraphInMatrix {

    public TopologicalSort(int maxN) {
        super(maxN);
    }
    // override
    public void insertEdge(String from, String to){
        insertVertex(from);
        insertVertex(to);
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentMatrix[f][t] = 1;//유향 그래프
//        adjacentMatrix[t][f] = 1;  deleted
    }
    // override
    public void deleteEdge(String from, String to){
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f >= 0 && t >= 0) {
            adjacentMatrix[f][t]  = 0;
//            adjacentMatrix[t][f] = 0; deleted
        }
    }

    // 존나 구린 방법
    public void TPSort1(){
        String[] A = new String[vertices.size()];
        int nOfVertices = vertices.size();

        for (int i = 0; i < nOfVertices; i++) {
            A[i] = getNextNode();//진입간선이 0인 정점을 A[i]에 저장
            deleteVertex(A[i]);//그리고 진입간선이 0인 정점을 matrix 그래프에서 삭제한다.(정점이 삭제되면 간선도 삭제된다.)
            showGraph();
        }

        System.out.println(">>> Start");
        for (int i = 0; i < nOfVertices; i++)
            System.out.println("=> "+A[i]);
        System.out.println();
    }

    private String getNextNode(){//진입간선이 0인 정점을 반환해준다.
        for (int i = 0; i < vertices.size(); i++) {

            int tempSum = 0;
            for(int j=0; j<vertices.size(); j++)
                tempSum+=adjacentMatrix[j][i];
            if(tempSum == 0)
                return vertices.get(i);
        }
        return null;
    }

    //Recursive 한 방법
    public void TPSort2(){
        LinkedList<String> R = new LinkedList<>();//R은 결과인 위상 정렬된 정점들이 순서대로 담긴다.
        boolean[] visited = new boolean[vertices.size()];
        Arrays.fill(visited, false);
        for(String s: vertices){
            if(!visited[vertices.indexOf(s)])
                dfsTS(visited, s, R);
        }
    }
    //계속 타고 들어가면 진입차수가 0이 아닌애가 나올 것이다. 더이상 탈 것이 없을 때까지 타고 들어가고
    //얘는 결국 맨 뒤에 들어가게 된다.
    private LinkedList<String> dfsTS(boolean[] visited, String s, LinkedList<String> R){
        visited[vertices.indexOf(s)]=true;
        for(String x : adjacent(s))
            if(!visited[vertices.indexOf(x)])
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
        TopologicalSort myTO = new TopologicalSort(maxNoVertex);
        myTO.createGraph("Topological Sort1");
        for (int i = 0; i < graphEdges.length; i++)
            myTO.insertEdge(vertices[graphEdges[i][0]], vertices[graphEdges[i][1]]);

        myTO.showGraph();
        System.out.println("Topological Sort1 : start");
        myTO.TPSort1();

        TopologicalSort myTO2 = new TopologicalSort(maxNoVertex);
        myTO2.createGraph("Topological Sort1");
        for (int i = 0; i < graphEdges.length; i++)
            myTO2.insertEdge(vertices[graphEdges[i][0]], vertices[graphEdges[i][1]]);
        myTO2.showGraph();
        System.out.println("Topological Sort2 : start ");
        myTO2.TPSort2();
    }
}
