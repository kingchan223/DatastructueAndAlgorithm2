package week13;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AstarSP {
    private static final double INF =999_999_999;
    int numOfV;
    char start;
    char goal;
    HashMap<Character, Vertex> open;
    HashMap<Character, Vertex> closed;
    ArrayList<Vertex> path;
    HashMap<Character, ArrayList<Vertex>> adjacentVertexes;

    public AstarSP(int numOfV, char start, char goal) {
        this.numOfV = numOfV;
        this.start = start;
        this.goal = goal;
        open = new LinkedHashMap<>();
        closed = new LinkedHashMap<>();
        path = new ArrayList<>();
        adjacentVertexes = new LinkedHashMap<>();
    }

    private void insertVertex(int[][] vertexes) {
        for (int[] v : vertexes) {
            adjacentVertexes.put((char) v[2], new ArrayList<>());
            if(v[2]!=start) open.put((char) v[2], new Vertex(v[0], v[1], INF, (char) v[2]));
            else open.put((char) v[2], new Vertex(v[0], v[1], 0, (char) v[2]));
        }
    }

    private void insertEdge(char[][] edges) {
        for (char[] e : edges) {
            adjacentVertexes.get(e[0]).add(open.get(e[1]));
            adjacentVertexes.get(e[1]).add(open.get(e[0]));
        }
    }

    // A* 알고리즘.
    public void aStar() {
        Vertex nowV = open.get(start);
        double nowGx = nowV.weight;
        while(true) {
            ArrayList<Vertex> linkedVs = adjacentVertexes.get(nowV.vName);//{A, G}
            double nowFx = INF;
            Vertex next = null;
            double tempGx = 0;
            //nowV에 연결된 정점들의 f(x)를 비교하여 최적의 값을 다음 경로로 설정환다.
            for (Vertex linkedV : linkedVs) {//{A, G}
                //이미 검사한 노드(closed)는 건너 뛴다.
                if(closed.get(linkedV.vName)!=null) continue;
                double tempFx = f(nowV.x, nowV.y, linkedV.x, linkedV.y, nowGx);
                if (nowFx > tempFx) {
                    nowFx = tempFx;
                    next = linkedV;
                    tempGx =  g(nowV.x, nowV.y, linkedV.x, linkedV.y, nowGx);
                } else {
                    Vertex remove = open.remove(linkedV.vName);
                    closed.put(remove.vName, remove);
                }
            }
            nowV = next;
            nowGx = tempGx;
            nowV.weight = nowGx;
            path.add(nowV);
            if(nowV.vName == goal) break;
        }
    }

    public void showSP(){
        System.out.print(start+"(0) --> ");
        for (Vertex vertex : path)
            System.out.print(vertex.vName+"("+vertex.weight+") --> ");
    }
    //매력함수 + 휴리스틱함수
    public double f(int fromX , int fromY, int toX, int toY, double preGx){
        return g(fromX, fromY, toX, toY, preGx) + h(toX, toY);
    }
    //매력 함수 - 피라고라스
    private double g(int fromX ,int fromY, int toX,int toY, double preGx){
        return Math.sqrt(Math.pow(fromX-toX, 2)+ Math.pow(fromY-toY,2)) + preGx;
    }
    //휴리스틱 함수 - 피라고라스
    private double h(int toX ,int toY){
        Vertex endVertex = open.get(this.goal);
        return Math.sqrt(Math.pow(toX-endVertex.x, 2)+ Math.pow(toY-endVertex.y,2));
    }

    public static void main(String[] args) {
        AstarSP astarSP = new AstarSP(7, 'B', 'E');

        //{4, 6, 'A'}라면 이차원 좌표계 (4,6)에 정점 A가 존재한다.
        int[][] vertexInfo = {{4, 6, 'A'}, {0, 6, 'B'}, {3, 3, 'C'},
                {5, 3, 'D'}, {5, 0, 'E'}, {4, 0, 'F'}, {0, 0, 'G'}};
        //{'B', 'A'} 라면 B와 A 사이에 무향 간선이 존재
        char[][] edgeInfo = {{'B', 'A'}, {'B', 'G'}, {'A', 'C'},
                {'A', 'D'}, {'C', 'D'}, {'C', 'F'}, {'D', 'E'},
                {'F', 'E'}, {'G', 'F'}};

        astarSP.insertVertex(vertexInfo);
        astarSP.insertEdge(edgeInfo);
        astarSP.aStar();
        astarSP.showSP();
    }

    private static class Vertex //implements Comparable<Vertex>{
    {
        int x;
        int y;
        double weight;
        char vName;

        public Vertex(int x, int y, double weight, char vName) {
            this.x = x;
            this.y = y;
            this.weight = weight;
            this.vName = vName;
        }

//        @Override
//        public int compareTo(Vertex that) {
//            if(this.weight == that.weight) return 0;
//            return (int) (this.weight - that.weight);
//        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "x=" + x +
                    ", y=" + y +
                    ", weight=" + weight +
                    ", vName=" + vName +
                    '}';
        }
    }
}
