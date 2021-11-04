package week9_graph;

public class GraphBaseMain {
    public static void main(String[] args) {

        int maxNoVertex = 10;
        String [] vertices = { "철수", "영희", "동건", "준호", "재상", "승우"};
        int [][] graphEdges = { {1,2}, {1,3}, {1,4}, {2,3},
                {3,5}, {1,6}, {5,6}, {4,6} };
        //--------------------------------------------------------------------------------

//        GraphInMatrix myGM = new GraphInMatrix(maxNoVertex);
//
//        myGM.createGraph("TestGraph in Matrix");
//        myGM.showGraph();
//
//        for (int i = 0; i<graphEdges.length; i++)
//            myGM.insertEdge(vertices[graphEdges[i][0]-1],vertices[graphEdges[i][1]-1]);
//        System.out.println("\n⬇️⬇️After 모든 정점과 간선 추가 ⬇️⬇️");
//        myGM.showGraph();
//
//        myGM.deleteVertex("영희");
//        System.out.println("\n⬇️⬇️After delete 영희⬇️⬇️");
//        myGM.showGraph();
//
//        myGM.insertEdge("철수", "재상");
//        System.out.println("\n⬇️⬇️After add Edge 철수-재상⬇️⬇️");
//        myGM.showGraph();
//
//        myGM.insertEdge("승우", "동건");
//        System.out.println("\n⬇️⬇️After add Edge 승우-동건⬇️⬇️");
//        myGM.showGraph();
//
//
//        System.out.println("\n⬇️⬇️Adjacent Set of "+"철수⬇️⬇️");
//        System.out.println(myGM.adjacent("철수"));
//
//        myGM.BFS(vertices[0]);
//        myGM.DFS(vertices[0]);
//
//        ///////////////////////////////////////////////////////////////////
//        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//        GraphInList myGL = new GraphInList(maxNoVertex);
//
//        myGL.createGraph("TestGraph in List");
//        myGL.showGraph();
//
//        for (int i = 0; i<graphEdges.length; i++)
//            myGL.insertEdge(vertices[graphEdges[i][0]-1],vertices[graphEdges[i][1]-1]);
//        System.out.println("\n⬇️⬇️After 모든 정점과 간선 추가 ⬇️⬇️");
//        myGL.showGraph();
//
//        myGL.deleteVertex("영희");
//        System.out.println("\n⬇️⬇️After delete 영희⬇️⬇️");
//        myGL.showGraph();
//
//        myGL.insertEdge("철수", "재상");
//        System.out.println("\n⬇️⬇️After add Edge 철수-재상⬇️⬇️");
//        myGL.showGraph();
//
//        myGL.insertEdge("승우", "동건");
//        System.out.println("\n⬇️⬇️After add Edge 승우-동건⬇️⬇️");
//        myGL.showGraph();
//
//
//        System.out.println("\n⬇️⬇️Adjacent Set of "+"철수⬇️⬇️");
//        System.out.println(myGL.adjacent("철수"));

        ///////////////////////////////////////////////////////////////////
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        GraphInArray myGA = new GraphInArray(maxNoVertex);

        myGA.createGraph("TestGraph in Array");
        myGA.showGraph();

        for (int i = 0; i<graphEdges.length; i++)
            myGA.insertEdge(vertices[graphEdges[i][0]-1],vertices[graphEdges[i][1]-1]);
        System.out.println("\n⬇️⬇️After 모든 정점과 간선 추가 ⬇️⬇️");
        myGA.showGraph();

        myGA.deleteVertex("영희");
        System.out.println("\n⬇️⬇️After delete 영희⬇️⬇️");
        myGA.showGraph();

        myGA.insertEdge("철수", "재상");
        System.out.println("\n⬇️⬇️After add Edge 철수-재상⬇️⬇️");
        myGA.showGraph();

        myGA.insertEdge("승우", "동건");
        System.out.println("\n⬇️⬇️After add Edge 승우-동건⬇️⬇️");
        myGA.showGraph();


        System.out.println("\n⬇️⬇️Adjacent Set of "+"철수⬇️⬇️");
        System.out.println(myGA.adjacent("철수"));

    }

}