package week11;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TopologicalSortInList extends GraphInListArrow {

    Queue<Integer> q;
    int[] entryLevel;//진입차수

    public TopologicalSortInList(int maxN) {
        super(maxN);
        entryLevel = new int[nOfVertices];
        Arrays.fill(entryLevel, 0);
        q = new LinkedList<>();
    }

    //큐를 사용한 방법///////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////


    //Recursive 한 방법/////////////////////////////////////////////////////////////////////
    public void TPSort2(){
        LinkedList<String> R = new LinkedList<>();//R은 결과인 위상 정렬된 정점들이 순서대로 담긴다.
        boolean[] visited = new boolean[vertices.size()];
        Arrays.fill(visited, false);
        for(String s: vertices){
            if(!visited[vertices.indexOf(s)])
                dfsTS(visited, s, R);
        }
    }
    //계속 타고 들어가면 진입차수가 0이 아닌애가 나올 것이다. 더이상 탈 것이 없을 때까지 타고 들어가고
    //얘는 결국 맨 뒤에 들어가게 된다.
    private LinkedList<String> dfsTS(boolean[] visited, String s, LinkedList<String> R){
        visited[vertices.indexOf(s)]=true;
        for(String x : adjacent(s))
            if(!visited[vertices.indexOf(x)])
                dfsTS(visited, x, R);
        System.out.println(s + " is added a the first");
        R.addFirst(s);
        return R;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    //override
    public void insertEdge(String from, String to) {
        insertVertex(from);
        insertVertex(to);

        int f = vertices.indexOf(from);
        adjacentList.get(f).add(to);
        //진입차수 올리기
        int t = vertices.indexOf(to);
//        System.out.println(t);
        entryLevel[t] += 1;
    }

    //override
    public void deleteVertex(String s) {
        int index = vertices.indexOf(s);
        if (index>=0) {
            for (String vertex : vertices) {
                deleteEdge(s, vertex);
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
        for (int[] graphEdge : graphEdges) myTO.insertEdge(vertices[graphEdge[0]], vertices[graphEdge[1]]);
        for (String vertex : vertices) myTO.insertVertex(vertex);

        System.out.println("Topological Sort1 : start");
        myTO.TPSort1();

        TopologicalSortInList myTO2 = new TopologicalSortInList(vertices.length);
        for (int[] graphEdge : graphEdges) myTO2.insertEdge(vertices[graphEdge[0]], vertices[graphEdge[1]]);
        for (String vertex : vertices) myTO2.insertVertex(vertex);

        System.out.println("Topological Sort2 Recursive : start");
        myTO2.TPSort2();
    }
}
