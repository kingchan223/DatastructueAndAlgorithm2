package final_term.prim.kruskal_path_shrink;


public class MSTmain {
    public static void main(String[] args) {
        String [] vertices = { "서울", "인천", "대전", "대구", "광주", "부산", "울산"};
        int [][] graphEdges = { {0, 1, 11 }, {0, 2, 8}, {0, 3, 9}, {1, 3, 13},
                {1, 6, 8}, {2, 4, 10}, {3, 4, 5}, {3, 5, 12}, {5, 6, 7} };

        Kruskal myK = new Kruskal(vertices.length);
        myK.createGraph("Kruskal-Test Graph");
        for (int[] graphEdge : graphEdges) myK.insertEdge(vertices[graphEdge[0]], vertices[graphEdge[1]], graphEdge[2]);
        myK.showGraph();

        System.out.println("\nKruskal Algorithm");
        myK.init();
        myK.MST();
        System.out.println("myK.cnt:"+myK.cnt);
    }
}
