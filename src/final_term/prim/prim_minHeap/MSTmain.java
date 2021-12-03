package final_term.prim.prim_minHeap;


public class MSTmain {
    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        Prim myG = new Prim(vertices.length);

        myG.createGraph("Prim-Test Graph");
        for (int[] graphEdge : graphEdges) myG.insertEdge(vertices[graphEdge[0]], vertices[graphEdge[1]], graphEdge[2]);
        myG.showGraph();

        myG.deleteVertex("서울");
        myG.showGraph();

        myG.insertEdge("서울", "인천", 11);
        myG.insertEdge("서울", "대전", 8);
        myG.insertEdge("서울", "대구", 9);
        myG.showGraph();

        System.out.println("\nAdjacent Set of "+"서울");
        System.out.println(myG.adjacent("서울"));
        System.out.println("\nPrim Algorithm starts from "+"서울");

        myG.init("서울");
        myG.MST();
    }
}
