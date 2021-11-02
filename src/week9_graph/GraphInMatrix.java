package week9_graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

public class GraphInMatrix {

    String graphName ;
    ArrayList<String> vertices ;
    int [][] adjacentMatrix ;
    int maxNumber = 0;
    boolean [] visited ;

    public GraphInMatrix(int maxN) {
        maxNumber = maxN ;
        visited = new boolean [maxNumber];
    }

    public void createGraph(String name) {
        graphName = name;
        vertices = new ArrayList<>();
        adjacentMatrix = new int [maxNumber][maxNumber];
    }

    public void showGraph() {
        showGraphInMatrix();
    }

    private void showGraphInMatrix() {
        System.out.println("\n< "+graphName+" in AdjacentMatrix >");
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+"  ");
            for (int j=0; j<vertices.size();j++)
                System.out.printf("%3d", adjacentMatrix[i][j] );
            System.out.println();
        }
    }

    public void insertVertex(String s) {
        if (!vertices.contains(s)) {
            vertices.add(s);
        }
    }

    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentMatrix[f][t] = 1 ;
        adjacentMatrix[t][f] = 1 ;
    }

    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            int n = vertices.size();
            // 두개의 중첩 for문은 없어진 v를 그래프에서 지우고 안쪽으로 그래프를 땡기기 위해 필요하다.
            for (int i=index+1; i<n; i++)//행
                for (int j=0; j<n; j++)
                    adjacentMatrix[i-1][j] = adjacentMatrix[i][j];
            for (int i=index+1; i<n; i++)//열
                for (int j=0; j<n; j++)
                    adjacentMatrix[j][i-1] = adjacentMatrix[j][i];
            // reset n-1 th row & column
            for (int i=0;i<n; i++) {
                adjacentMatrix[i][n-1] = 0;
                adjacentMatrix[n-1][i] = 0;
            }

            vertices.remove(s);
        }
    }

    public void deleteEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            adjacentMatrix[f][t] = 0 ;
            adjacentMatrix[t][f] = 0 ;
        }
    }

    public boolean isEmpty() {
        if (vertices.size()==0)
            return true;
        else
            return false;
    }

    public  HashSet<String> adjacent(String s){
        HashSet<String> result= new HashSet<>();

        int index = vertices.indexOf(s);
        if (index>=0) {
            for (int i=0; i<vertices.size();i++) {
                if (adjacentMatrix[index][i]!=0)
                    result.add(vertices.get(i));
            }
        }
        return result;
    }

    public void initVisited() {
        for (int i=0; i<vertices.size();i++)
            visited[i] = false;
    }

    public void DFS(String s) {
        initVisited();
        System.out.println("\n *** DFS Recursion *** \n");
        DFSRecusrsion(s);
    }
    private void DFSRecusrsion(String s) {
        int index = vertices.indexOf(s);
        visited[index]=true;
        System.out.println(s+" is visited ");
        for (int i=0; i<vertices.size();i++) {
            if (adjacentMatrix[index][i]==1 && !visited[i]) {
                DFSRecusrsion(vertices.get(i));
            }
        }
    }

    public void BFS(String s) {
        initVisited();
        System.out.println("\n *** BFS Iteration *** \n");
        BFSIteration(s);
    }

    public void BFSIteration(String s) {
        Deque<String> que = new ArrayDeque<String>();
        visited[vertices.indexOf(s)]=true;
        System.out.println(s+" is visited ");
        que.add(s);

        while (!que.isEmpty()) {
            String v = que.poll();
            int index = vertices.indexOf(v);
            for (int i=0; i<vertices.size();i++) {
                if (adjacentMatrix[index][i]==1 && !visited[i]) {
                    visited[i]=true;
                    System.out.println(vertices.get(i)+" is visited ");
                    que.add(vertices.get(i));
                }
            }
        }
    }

}