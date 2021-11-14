package week10_minimal_spanning_tree.kru_prim_by_prof;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Kruskal extends WGraphInList {

    ArrayList<String> parent;
    HashSet<EdgeElement> T ;
    LinkedList<EdgeElement> Q;

    public Kruskal(int max) {
        super(max);
        parent = new ArrayList<>() ;
        Q = new LinkedList<EdgeElement>();
        T = new HashSet<>();
    }


    public void init() {
        for (int i=0; i<maxNumber;i++) {
            parent.add(vertices.get(i)); // MakeSet
        }
    }

    // overriding
    public void insertEdge(String from, String to, int w) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);

        EdgeElement newEdge = new EdgeElement(from, to, w);

        adjacentList.get(f).add(newEdge);
        adjacentList.get(t).add(new EdgeElement(to, from, w));

        sortInsert(newEdge);
    }

    private void sortInsert(EdgeElement newEdge) {
        int index=0;
        Iterator<EdgeElement> iter = Q.iterator();
        while (iter.hasNext()) {
            if (newEdge.weight>iter.next().weight)
                index++;
        }
        Q.add(index,newEdge);
        showQ();
    }

    private void showQ() {
        System.out.print("\n>>> Q state : ");
        for (EdgeElement e : Q) {
            System.out.print("-> "+e.weight);
        }
        System.out.println();

    }

    public void MST() {

        while(T.size()<maxNumber-1) {
            EdgeElement euv = Q.remove(0);

            if (findSet(euv.source)!=findSet(euv.destination)) {
                union(euv.source, euv.destination);
                System.out.println(euv+"  is selected");
                T.add(euv);
            }
        }
    }

    private String findSet(String s) {
        String p= parent.get(vertices.indexOf(s));
        if (p==s)
            return s;
        else
            return findSet(p);
    }

    private void union(String s, String d) {
        parent.set(vertices.indexOf(d), parent.get(vertices.indexOf(s)));
    }


}