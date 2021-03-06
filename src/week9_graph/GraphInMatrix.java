package week9_graph;

import java.util.*;

public class GraphInMatrix {

    protected String graphName ;
    protected ArrayList<String> vertices ;
    protected int [][] adjacentMatrix ;
    protected int maxNumber = 0;
    protected boolean [] visited ;
    protected boolean [] visited2 ;
    protected boolean [] visited3 ;

    public GraphInMatrix(int maxN) {
        maxNumber = maxN ;
        visited = new boolean [maxNumber];
        visited2 = new boolean [maxNumber];
        visited3 = new boolean [maxNumber];
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
    public void insertVertex(String s){
        if (!vertices.contains(s))
            vertices.add(s);
    }

    //간선 추가
    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentMatrix[f][t] = 1 ;
        adjacentMatrix[t][f] = 1 ;
    }

    public void deleteVertex(String s) {//matrix에서 정점을 삭제한다. 그러면 간선도 삭제된다.
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
            adjacentMatrix[t][f] = 0 ;
        }
    }

    public boolean isEmpty() {
        if (vertices.size()==0) return true;
        else return false;
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

    //하나짜리 DFS
    public void DFS2(String startV){
        if(!visited2[vertices.indexOf(startV)]){
            visited2[vertices.indexOf(startV)] = true;
            System.out.println(startV+" ^^ ");
            for(int i=0; i<adjacentMatrix.length;i++){
                if(adjacentMatrix[vertices.indexOf(startV)][i]!=0){
                    DFS2(vertices.get(i));
                }
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

    public void BFS2(String start){
        Queue<String> q = new ArrayDeque<>();
        q.add(start);
        BFS2(q);
    }

    private void BFS2(Queue<String> q) {
        Queue<String> nextQ = new ArrayDeque<>();
        while(!q.isEmpty()){
            String p = q.poll();
            if(!visited3[vertices.indexOf(p)]){
                visited3[vertices.indexOf(p)] = true;
                System.out.println(p+" ~.~");
            }
            for(int i=0; i<adjacentMatrix.length; i++){
                if (adjacentMatrix[vertices.indexOf(p)][i]==1 && !visited3[i])
                    nextQ.add(vertices.get(i));
            }
        }
        if(!nextQ.isEmpty()) BFS2(nextQ);
    }
}