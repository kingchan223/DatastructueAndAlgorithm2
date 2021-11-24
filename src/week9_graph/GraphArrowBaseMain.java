package week9_graph;

public class GraphArrowBaseMain {
    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////
        int maxNoVertex = 10;
        String [] vertices2 = { "서울", "부산", "인천", "울산", "대구", "제주", "대전", "광주"};
        int [][] graphEdges2 = {
                {1,2}, {1,6},
                {2,6}, {2,7},
                {3,2}, {3,6}, {3,4}, {3,5},
                {4,1}, {4,5},
                {7,3}, {7,8}};
        ///////////////////////////////////////////////////////////////////
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        GraphInMatrixArrow myGMA = new GraphInMatrixArrow(maxNoVertex);
        myGMA.createGraph("TestGraph in Array Arrow");
        myGMA.showGraph();
        for (int[] graphEdge2 : graphEdges2)
            myGMA.insertEdge(vertices2[graphEdge2[0] - 1], vertices2[graphEdge2[1] - 1]);
        System.out.println("\n⬇️⬇️After 모든 정점과 간선 추가 ⬇️⬇️");
        myGMA.showGraph();
        myGMA.deleteVertex("부산");
        System.out.println("\n⬇️⬇️After delete 부산⬇️⬇️");
        myGMA.showGraph();
        myGMA.insertEdge("제주", "광주");
        System.out.println("\n⬇️⬇️After add Edge 제주-광주⬇️⬇️");
        myGMA.showGraph();
        myGMA.insertEdge("서울", "대구");
        System.out.println("\n⬇️⬇️After add Edge 서울-대구⬇️⬇️");
        myGMA.showGraph();
        System.out.println("\n⬇️⬇️Adjacent Set of "+"서울⬇️⬇️");
        System.out.println(myGMA.adjacent("서울"));

        ///////////////////////////////////////////////////////////////////
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        GraphInListArrow myGLA = new GraphInListArrow(maxNoVertex);
        myGLA.createGraph();
        myGLA.showGraph();
        for (int[] graphEdge2 : graphEdges2)
            myGLA.insertEdge(vertices2[graphEdge2[0] - 1], vertices2[graphEdge2[1] - 1]);
        System.out.println("\n⬇️⬇️After 모든 정점과 간선 추가 ⬇️⬇️");
        myGLA.showGraph();
        myGLA.deleteVertex("부산");
        System.out.println("\n⬇️⬇️After delete 부산⬇️⬇️");
        myGLA.showGraph();
        myGLA.insertEdge("제주", "광주");
        System.out.println("\n⬇️⬇️After add Edge 제주 -> 광주⬇️⬇️");
        myGLA.showGraph();
        myGLA.insertEdge("서울", "대구");
        System.out.println("\n⬇️⬇️After add Edge 서울 -> 대구⬇️⬇️");
        myGLA.showGraph();
        System.out.println("\n⬇️⬇️Adjacent Set of "+"서울⬇️⬇️");
        System.out.println(myGLA.adjacent("서울"));
    }
}

