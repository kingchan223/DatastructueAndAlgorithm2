package week10_minimal_spanning_tree;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Prim {
    int numOfNode;
    ArrayList<Integer> vertices;
    ArrayList<PriorityQueue<Vertex>> minEdges;
    boolean[] t;
    ArrayList<ArrayList<Vertex>> adjacentArray;
    ArrayList<Integer> MST;
    int totalW = 0;

    public Prim(int numOfNode) {
        this.numOfNode = numOfNode;
        createGraph();
        for (int i = 0; i < numOfNode; i++)
            vertices.add(i);
    }

    public void createGraph() {
        vertices = new ArrayList<>();
        adjacentArray = new ArrayList<>();
        minEdges = new ArrayList<>();
        MST = new ArrayList<>();
        t = new boolean[numOfNode];
        for (int i = 0; i < numOfNode; i++){
            adjacentArray.add(new ArrayList<Vertex>());
            minEdges.add(new PriorityQueue<Vertex>());
        }
    }

    private void addWeightOfEdges(int[][] graphEdges) {
        for (int[] graphEdge : graphEdges) {
            insertEdge(graphEdge[0], graphEdge[1], graphEdge[2]);
        }
    }
//        0   => 1 (4) => 2 (3) => 3 (6)
//        1   => 0 (4) => 2 (3)
//        2   => 0 (3) => 1 (3) => 3 (7) => 4 (2)
//        3   => 0 (6) => 2 (7) => 5 (1) => 6 (5)
//        4   => 2 (2)
//        5   => 3 (1) => 6 (2) => 7 (10)
//        6   => 3 (5) => 5 (2) => 7 (9)
//        7   => 5 (10) => 6 (9)

//        0   : 2 (3) , 1 (4), 3 (6)
//        1   : 2 (3) , 0 (4)
//        2   : 4 (2) , 0 (3) , 1 (3) , 3 (7)
//        3   : 5 (1) , 6 (5) , 0 (6) , 2 (7)
//        4   : 2 (2)
//        5   : 3 (1) , 6 (2) , 7 (10)
//        6   : 5 (2),  3 (5) , 7 (9)
//        7   : 6 (9),  5 (10)

    // stack : 0, 2,
//        0   : 1 (4), 3 (6)
//        1   : 2 (3) , 0 (4)
//        2   : 4 (2) , 0 (3) , 1 (3) , 3 (7)
//        3   : 5 (1) , 6 (5) , 0 (6) , 2 (7)
//        4   : 2 (2)
//        5   : 3 (1) , 6 (2) , 7 (10)
//        6   : 5 (2),  3 (5) , 7 (9)
//        7   : 6 (9),  5 (10)
    private void findMST(int start) {
        Stack<Integer> stack = new Stack<>();
        t[start] = true;
        stack.push(start);
        MST.add(start);
        //
        Vertex v = minEdges.get(start).poll();
        totalW += v.weight;
        t[v.to] = true;
        stack.push(v.to);
        MST.add(v.to);
        // w: 3
       // stack : 0 2

        while(!hasAllV()){
            while(!stack.isEmpty()){
                Integer nIdx = stack.pop();
                Vertex vertex = minEdges.get(nIdx).poll();
                System.out.println("vertex = " + vertex);
                if(!t[vertex.to]){
                    stack.push(vertex.to);
                    totalW += vertex.weight;
                    MST.add(vertex.to);
                    t[vertex.to] = true;
                    break;
                }
            }
        }
    }

    public boolean hasAllV(){
        for (boolean b : t) {
            if(!b) return false;
        }
        return true;
    }

    private void showMST() {
        System.out.println("가중치의 합은 : "+ totalW);
        for (Integer i : MST) {
            System.out.print(i+" => ");
        }

    }

    private void showGraph(){
        for (int i=0; i<vertices.size();i++){
            System.out.print(vertices.get(i)+"  ");
            for (Vertex v : adjacentArray.get(i))
                System.out.print(" => "+ v.to+" ("+v.weight+")" );
            System.out.println();
        }
    }

    private void insertEdge(int from, int to, int weight) {
        if(!hasEdge(from, to)){
            adjacentArray.get(from).add(new Vertex(to, weight));
            adjacentArray.get(to).add(new Vertex(from, weight));
            minEdges.get(from).add(new Vertex(to, weight));
            minEdges.get(to).add(new Vertex(from, weight));
        }
    }

    private boolean hasEdge(int from,int to) {
        ArrayList<Vertex> vertices = adjacentArray.get(from);
        for (Vertex vertex : vertices)
            if(vertex.to == to) return true;
        return false;
    }

    public static void main(String[] args) {
        int numOfNode = 8;
        Prim prim = new Prim(numOfNode);
        //{0, 1, 4} 일때 정점 0에서 정점 1로 이동하는데 weight가 4라는 의미이다.
        int [][] graphEdges = { {0,1, 4}, {0,2, 3}, {0,3, 6}, {1,2, 3},
                {2,3, 7}, {2,4, 2}, {3,5, 1}, {3,6, 5}, {5,6, 2},{5, 7, 10}, {6,7, 9},  };

        prim.addWeightOfEdges(graphEdges);
        prim.showGraph();
        prim.findMST(0);
        prim.showMST();
    }


    public static class Vertex implements Comparable<Vertex> {
        int to;
        int weight;
        public int getTo() {return to;}
        public int getWeight() {return weight;}

        Vertex(int to, int weight){
            this.to = to;
            this.weight = weight;
        }


        @Override
        public String toString() {
            return "(w:" + this.weight+", to:"+to+")";
        }

        @Override
        public int compareTo(Vertex that) {
            if(this.weight == that.weight) return 0;
            return this.weight-that.weight;
        }
    }
}
