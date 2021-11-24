package week9_graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphInList {

    protected ArrayList<String> vertices ;
    protected ArrayList<LinkedList<String>> adjacentList ;
    protected int maxNumber = 0;
    protected boolean [] visited ;

    public GraphInList(int maxN) {
        maxNumber = maxN ;
        visited = new boolean [maxNumber];
        createGraph();
    }

    public void createGraph() {
        vertices = new ArrayList<>();
        adjacentList = new ArrayList<>();
    }

    public void showGraph() {
        showGraphInList();
    }

    private void showGraphInList() {
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+"  ");
            for (String s : adjacentList.get(i))
                System.out.print(" => "+ s );
            System.out.println();
        }
    }

    public void insertVertex(String s) {
        if (!vertices.contains(s)) {
            vertices.add(s);
            adjacentList.add(new LinkedList<String>());
        }
    }

    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        //adjacentList.get() : 정점을 가져오고
        //adjacentList.get().add() : 정점의 링크드 리스트에 추가한다.
        adjacentList.get(f).add(to);
        adjacentList.get(t).add(from);
    }

    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            for (int i=0; i<vertices.size(); i++) {
                deleteEdge(s, vertices.get(i));
                deleteEdge(vertices.get(i), s);
            }
            adjacentList.remove(index);
            vertices.remove(index);
        }
    }

    public void deleteEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            adjacentList.get(f).remove(to); ;
            adjacentList.get(t).remove(from); ;
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
            for (String v : adjacentList.get(index))
                result.add(v);
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
        for (String v : adjacentList.get(index))
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
            for (String u : adjacentList.get(index)) {
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