package week9_graph;

import java.util.*;

public class GraphInArray {

    String graphName ;
    ArrayList<String> vertices ;
    ArrayList<TableEntry> adjacentArray ;
    int maxNumber = 0;
    boolean [] visited ;

    private class TableEntry{
        int arrSize;
        ArrayList<String> arrayEntry;
        private TableEntry(){
            arrSize = 0;
            arrayEntry = new ArrayList<>();
        }
    }

    public GraphInArray(int maxN) {
        maxNumber = maxN ;
        visited = new boolean [maxNumber];
    }

    public void createGraph(String name) {
        graphName = name;
        vertices = new ArrayList<>();
        adjacentArray = new ArrayList<>();
    }

    public void showGraph() {
        showGraphInList();
    }

    private void showGraphInList() {
        System.out.println("< "+graphName+" in AdjacentArray >");
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+" : ");
            for (String s : adjacentArray.get(i).arrayEntry)
                System.out.print( s +", ");
            System.out.println();
        }
    }

    public void insertVertex(String s) {
        if (!vertices.contains(s)) {
            vertices.add(s);
            adjacentArray.add(new TableEntry());
        }
    }

    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        //adjacentList.get() : 정점을 가져오고
        //adjacentList.get().arrayEntry.add() : 정점의 링크드 리스트에 추가한다.
        adjacentArray.get(f).arrayEntry.add(to);
        adjacentArray.get(t).arrayEntry.add(from);
    }

    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            for (int i=0; i<vertices.size(); i++) {
                deleteEdge(s, vertices.get(i));
                deleteEdge(vertices.get(i), s);
            }
            adjacentArray.remove(index);
            vertices.remove(index);
        }
    }

    public void deleteEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            adjacentArray.get(f).arrayEntry.remove(to); ;
            adjacentArray.get(t).arrayEntry.remove(from); ;
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
            result.addAll(adjacentArray.get(index).arrayEntry);
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
        DFSRecursion(s);
    }

    private void DFSRecursion(String s) {
        int index = vertices.indexOf(s);
        visited[index]=true;
        System.out.println(s+" is visited ");
        for (String v : adjacentArray.get(index).arrayEntry)
            if (!visited[vertices.indexOf(v)])
                DFSRecursion(v);
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
            for (String u : adjacentArray.get(index).arrayEntry) {
                int ui = vertices.indexOf(u);
                if (!visited[ui]) {
                    visited[ui]=true;
                    System.out.println(u+" is visited ");
                    que.add(u);
                }
            }
        }
    }
}



