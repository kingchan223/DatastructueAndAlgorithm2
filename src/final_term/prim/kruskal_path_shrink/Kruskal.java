package final_term.prim.kruskal_path_shrink;


import java.util.*;

public class Kruskal extends WGraphInList {

    ArrayList<String> parent;
    ArrayList<Integer> rank;
    HashSet<EdgeElement> T ;
    LinkedList<EdgeElement> Q;
    public int cnt = 0;
    public Kruskal(int max) {
        super(max);
        parent = new ArrayList<>();
        rank = new ArrayList<>();
        Q = new LinkedList<EdgeElement>();
        T = new HashSet<>();
    }

    public void init() {
        for (int i=0; i<numOfV;i++) {
            parent.add(vertices.get(i)); // MakeSet
            rank.add(0); // Rank 0으로 초기화
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

        while(T.size()<numOfV-1) {
            EdgeElement euv = Q.remove(0);

            if (!findSet(euv.source).equals(findSet(euv.destination))) {
                union(euv.source, euv.destination);
                System.out.println(euv+"  is selected");
                T.add(euv);
            }
        }
    }

    private String findSet(String s) {
        cnt++;
        String parentOfs = parent.get(vertices.indexOf(s));
        if (parentOfs.equals(s)) return s;
        else{
            Queue<String> q = new LinkedList<>();
            q.add(parentOfs);
            return findSet(parentOfs, q);
        }
    }

    private String findSet(String s, Queue<String> q){
        String parentOfs = parent.get(vertices.indexOf(s));
        if (parentOfs.equals(s)){
            while(!q.isEmpty()){
                String poll = q.poll();
                parent.set(vertices.indexOf(poll), parentOfs);
            }
            return s;
        }
        else{
            q.add(parentOfs);
            return findSet(parentOfs, q);
        }
    }

    private void union(String s, String d) {
        int di = vertices.indexOf(d);
        int si = vertices.indexOf(s);

        if(rank.get(di) > rank.get(si)) parent.set(vertices.indexOf(s), parent.get(vertices.indexOf(d)));
        else if(rank.get(di) < rank.get(si)) parent.set(vertices.indexOf(d), parent.get(vertices.indexOf(s)));
        else//(rank.get(di) == rank.get(si)) //rank 가 같다면 d를 s에 붙인다. 그리고 s의 rank 를 1 올려준다.
        {
            parent.set(vertices.indexOf(d), parent.get(vertices.indexOf(s)));
            rank.set(si, rank.get(si) + 1);
        }
    }
}
