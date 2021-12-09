package final_term.prim.kruskal_path_shrink;


import java.util.*;

public class Kruskal extends WGraphInList {

    ArrayList<String> parent;
    ArrayList<Integer> rank;
    HashSet<EdgeElement> T ;
    LinkedList<EdgeElement> Q;
    PriorityQueue<EdgeElement> PQ;

    public Kruskal(int max) {
        super(max);
        parent = new ArrayList<>();
        rank = new ArrayList<>();
        T = new HashSet<>();
        Q = new LinkedList<>();
        PQ = new PriorityQueue<>();
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
        PQ.add(newEdge);/*아래 sortInsert를 사용하는 대신 Priority Queue를 사용한다.*/
//        sortInsert(newEdge);
    }

    /*모든 edge를 Q에 크기 순으로 놔주는 메소드 but 나는 PQ 사용할꺼임*/
    private void sortInsert(EdgeElement newEdge) {
        int index=0;
        for (EdgeElement edgeElement : Q) {
            if (newEdge.weight > edgeElement.weight)
                index++;
        }
        Q.add(index,newEdge);
        showQ();
    }

    private void showQ() {
        System.out.print("\n>>> Q state : ");
        for (EdgeElement e : Q) System.out.print("-> "+e.weight);
        System.out.println();
    }

    public void MST() {
        int result = 0;

        while(T.size()<numOfV-1) {
//            EdgeElement euv = Q.remove(0);
            EdgeElement euv = PQ.poll();

            if (!findSet(euv.source).equals(findSet(euv.destination))) {
                union(euv.source, euv.destination);//destination 노드가 source 노드에 붙는다.
                System.out.println(euv+"  is selected");
                System.out.println("dist: "+ euv.weight);
                result += euv.weight;
                T.add(euv);
            }
        }
        System.out.println("result : "+result);
    }
    public void hi(){
        findSet("울산");
        findSet("광주");
        findSet("부산");
    }

    public String findSet(String s) {
        String parentOfs = parent.get(vertices.indexOf(s));
        if (parentOfs.equals(s)) return s;
        else{
            Queue<String> q = new LinkedList<>();
            q.add(parentOfs);
            return findSet(parentOfs, q);
        }
    }

    private String findSet(String s, Queue<String> q){
        //parentOfs 는 s의 parent
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
        int si = vertices.indexOf(s);
        int di = vertices.indexOf(d);
        String parentOfs = findSet(s);
        String parentOfd = findSet(d);
        int indexOfsParent = vertices.indexOf(parentOfs);
        int indexOfdParent = vertices.indexOf(parentOfd);

        if(rank.get(indexOfdParent) > rank.get(indexOfsParent))
            parent.set(si, parentOfd);
        else if(rank.get(indexOfdParent) < rank.get(indexOfsParent))
            parent.set(di, parentOfs);
        else//(rank.get(di) == rank.get(si)) //rank 가 같다면 d를 s에 붙인다. 그리고 s의 rank 를 1 올려준다.
        { // rank 가 같다면 오른쪽에 있는 집합을 왼쪽에 붙힌다.
            parent.set(di, parentOfs);
            rank.set(indexOfsParent, rank.get(indexOfsParent) + 1);
        }
    }
}
