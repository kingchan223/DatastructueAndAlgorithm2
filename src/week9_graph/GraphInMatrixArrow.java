package week9_graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

public class GraphInMatrixArrow {

    String graphName ;
    ArrayList<String> vertices ;
    int [][] adjacentMatrix ;
    int maxNumber = 0;
    boolean [] visited ;

    public GraphInMatrixArrow(int maxN) {
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
        System.out.println("< "+graphName+" in AdjacentMatrix >");
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+"  ");
            for (int j=0; j<vertices.size();j++)
                System.out.printf("%3d", adjacentMatrix[i][j] );
            System.out.println();
        }
    }

    //정점 추가
    public void insertVertex(String s) {
        if (!vertices.contains(s)) {
            vertices.add(s);
        }
    }

    //간선 추가
    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentMatrix[f][t] = 1 ;
//        adjacentMatrix[t][f] = 1 ;
    }

    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            int n = vertices.size();
            // 삭제될 정점과 연결된 간선은 모두 0으로 만든다.
            for (int i=0;i<n; i++) {
                adjacentMatrix[index][i] = 0;
                adjacentMatrix[i][index] = 0;
            }

            for (int i=0; i<n; i++)
                for (int j=index+1; j<n; j++)
                    adjacentMatrix[i][j-1] = adjacentMatrix[i][j];

            for (int i = 0; i < n; i++)
                adjacentMatrix[i][n - 1] = 0;

            for (int i=0; i<n; i++)
                for (int j=index+1; j<n; j++){
                    adjacentMatrix[j-1][i] = adjacentMatrix[j][i];
                }
            for (int i = 0; i < n; i++)
                adjacentMatrix[n-1][i] = 0;

            vertices.remove(s);
        }
    }

    public void deleteEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            adjacentMatrix[f][t] = 0 ;
//            adjacentMatrix[t][f] = 0 ;
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
}