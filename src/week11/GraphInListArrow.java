package week11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphInListArrow {

    protected ArrayList<String> vertices ;
    protected ArrayList<LinkedList<String>> adjacentList ;
    protected int verNum;
    protected boolean [] visited ;

    public GraphInListArrow(int verNum) {
        this.verNum = verNum ;
        visited = new boolean [verNum];
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
            adjacentList.add(new LinkedList<>());
        }
    }

    public void insertEdge(String from, String to) {
//        insertVertex(from);
//        insertVertex(to);
//
//        int f = vertices.indexOf(from);
//        adjacentList.get(f).add(to);
    }

    public void deleteVertex(String s) {
//        int index = vertices.indexOf(s);
//        if (index>=0) {
//            for (int i=0; i<vertices.size(); i++) {
//                deleteEdge(s, vertices.get(i));
//            }
//            adjacentList.remove(index);
////            vertices.remove(index);
//        }
    }

    public void deleteEdge(String from, String to) {
//        int f = vertices.indexOf(from);
//        int t = vertices.indexOf(to);
//        if (f>=0 && t>=0) {
//            adjacentList.get(f).remove(to);
//            adjacentList.get(t).remove(from); ;
//        }
    }

    public boolean isEmpty() {
        return vertices.size() == 0;
    }

    public  HashSet<String> adjacent(String s){
        HashSet<String> result= new HashSet<>();

        int index = vertices.indexOf(s);
        if (index>=0) {
            result.addAll(adjacentList.get(index));
        }
        return result;
    }


}