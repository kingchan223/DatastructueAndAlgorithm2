package week12_ShortestPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class WGraphInList {
    protected String graphName ;
    protected ArrayList<String> vertices ;
    protected ArrayList<LinkedList<EdgeElement>> adjacentList ;
    protected int numOfV = 0;

    public class
    EdgeElement {
        public String source ;
        public String destination ;
        public int weight ;

        public EdgeElement (String s, String d, int w){
            source = s;
            destination = d;
            weight = w;
        }

        public String toString() {
            return source+"->"+destination+"("+weight+")";
        }
    }

    public WGraphInList(int maxN) {
        numOfV = maxN ;
    }

    public void createGraph(String name) {
        graphName = name;
        vertices = new ArrayList<>();
        adjacentList = new ArrayList<>();
    }

    public void showGraph() {
        showGraphInList();
    }

    private void showGraphInList() {
        System.out.println("\n< "+graphName+" in AdjacentList >");
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+"  ");
            for (EdgeElement s : adjacentList.get(i))
                System.out.print(" => "+ s.destination +"("+s.weight+")");
            System.out.println();
        }
    }

    public void insertVertex(String s) {
        if (!vertices.contains(s)) {
            vertices.add(s);
            adjacentList.add(new LinkedList<EdgeElement>());
        }
    }

    public void insertEdge(String from, String to, int w) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        adjacentList.get(f).add(new EdgeElement(from, to, w));
        adjacentList.get(t).add(new EdgeElement(to, from, w));

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
            adjacentList.get(f).remove(getEdge(from, to));
            adjacentList.get(t).remove(getEdge(to, from));
        }
    }

    public EdgeElement getEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            for (EdgeElement e : adjacentList.get(f))
                if (to==e.destination) {
                    return e;
                }
        }
        return null;
    }

    public boolean isEmpty() {
        if (vertices.size()==0) return true;
        else return false;
    }

    public HashSet<String> adjacent(String s){
        HashSet<String> result= new HashSet<>();

        int index = vertices.indexOf(s);
        if (index>=0) {
            for (EdgeElement e : adjacentList.get(index))
                result.add(e.destination);
        }
        return result;
    }

}

