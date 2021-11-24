package week11;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TopologicalSortInList extends GraphInListArrow {

    Queue<Integer> q;
    int[] entryLevel;

    public TopologicalSortInList(int maxN) {
        super(maxN);
        entryLevel = new int[verNum];
        for (int i=0; i<vertices.size(); i++) {
            entryLevel[i] = 0;
        }
        q = new LinkedList<>();
    }

    public void TPSort1(){
        for (int i=0; i< vertices.size(); i++)
            if(entryLevel[i]==0) q.add(i);

        ArrayList<String> A = new ArrayList<>();
        int nOfVertices = vertices.size();

        while(!q.isEmpty()){
            Integer now = q.poll();

            String nowStr = vertices.get(now);
            A.add(nowStr);
            deleteVertex(nowStr);
        }

        for (int i = 0; i < nOfVertices; i++)
            System.out.println("=> "+A.get(i));
        System.out.println();
    }

    //override
    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        adjacentList.get(f).add(to);
        //진입차수 올리기
        int t = vertices.indexOf(to);
        System.out.println(t);
        entryLevel[t] += 1;
    }

    //override
    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            for (int i=0; i<vertices.size(); i++) {
                deleteEdge(s, vertices.get(i));
            }
        }
    }

    //override
    public void deleteEdge(String from, String to) {
        int f = vertices.indexOf(from);
        int t = vertices.indexOf(to);
        if (f>=0 && t>=0) {
            adjacentList.get(f).remove(to);
            adjacentList.get(t).remove(from);
            entryLevel[t] -= 1;
            if(entryLevel[t] == 0) q.add(t);
        }
    }


    public static void main(String[] args) {
        String[] vertices = {"물을 붓기", "점화 하기",
                            "봉지 뜯기", "라면 넣기",
                            "스프 넣기", "계란 넣기"};
        int[][] graphEdges = {
                {0, 1}, {1, 3}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {3, 5}, {4, 5}
        };
        TopologicalSortInList myTO = new TopologicalSortInList(vertices.length);
        for (int i = 0; i < graphEdges.length; i++)
            myTO.insertEdge(vertices[graphEdges[i][0]], vertices[graphEdges[i][1]]);
        for (int i = 0; i < vertices.length; i++)
            myTO.insertVertex(vertices[i]);

        System.out.println("Topological Sort1 : start");
        myTO.TPSort1();
    }
}
