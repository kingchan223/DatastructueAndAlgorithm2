package week12_ShortestPath;

import java.util.ArrayList;


public class GraphInMatrixWD2 {

    protected String graphName ;
    protected ArrayList<String> vertices ;
    protected int [][] adjacentMatrix ;
    protected int maxNumber = 0;

    public GraphInMatrixWD2(int maxN) {
        maxNumber = maxN ;
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

    public void insertVertex(String s){
        if (!vertices.contains(s))
            vertices.add(s);
    }

    public void insertEdge(String from, String to, int weight) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentMatrix[f][t] = weight ;
    }

    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            int n = vertices.size();
            for (int i=0;i<n; i++) {
                adjacentMatrix[index][i] = 0;
                adjacentMatrix[i][index] = 0;
            }

            for (int i=0; i<n; i++)
                if (n - (index + 1) >= 0)
                    System.arraycopy(adjacentMatrix[i], index + 1, adjacentMatrix[i], index + 1 - 1, n - (index + 1));

            for (int i = 0; i < n; i++)
                adjacentMatrix[i][n - 1] = 0;

            for (int i=0; i<n; i++)
                for (int j=index+1; j<n; j++)
                    adjacentMatrix[j-1][i] = adjacentMatrix[j][i];

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
        }
    }
}


