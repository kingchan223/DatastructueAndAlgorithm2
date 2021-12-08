package week14;

import week12_ShortestPath.WGraphInList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class HamiltonianInList {
    public static final int INF = 999_999_999;
    int N;
    ArrayList<LinkedList<Vertex>> adjacentList ;

    public HamiltonianInList(int[][] in){
        N = in.length;
        adjacentList = new ArrayList<>();
        insertEdge(in);
    }

    public void insertEdge(int[][] in){
        for(int i=0; i<in.length; i++){
            adjacentList.add(new LinkedList<Vertex>());
            for(int j=0; j<in.length; j++){
                if(in[i][j]!=0 && in[i][j]!=INF){
                    adjacentList.get(i).add(new Vertex(j, in[i][j]));
                }
            }
        }
    }

    public void findAllPath(){
        boolean[] visited = new boolean[N] ;//정점 방문 여부
        int[] path = new int[N];//정점 방문 경로
        int start = 0;//시작점은 0으로 한다.
        int vCount = 0;//vCount가 정점의 개수가 되면 모든 곳을 다 탐방한 것이다.
        Arrays.fill(visited, false);
        Arrays.fill(path, -1);

        visited[start] = true;//시작점은 한번 방문한 것으로 한다.
        path[0] = start;//경로에 시작점을 넣어준다.
        vCount++;//시작점을 추가했으므오 COUNT를 올려준다.
        findAllPath(start, visited, path, start, vCount);//빙 돌아서 start한 곳까지 와야하므로 end에 start를 넣어준다.
    }

    private void findAllPath(int start, boolean[] visited, int[] path, int end, int count) {
        LinkedList<Vertex> adjacentVertices = adjacentList.get(start);
        for (Vertex adjacentVertex : adjacentVertices) {
            if(count==N){
                System.out.print("PATH FOUND : ");
                for (int j = 0; j < N; j++)
                    System.out.print("-"+path[j]);
                System.out.println();
                return;
            }
            if(!visited[adjacentVertex.vertexName]){//해당 vertex를 방문하지 않았다면
                int i = adjacentVertex.vertexName;
                visited[i] = true;
                path[count] = i;
                count++;
                findAllPath(i,visited, path, end, count);
                visited[i] = false;
                count--;
                path[count] = -1;
            }
        }
    }

    public static void main(String[] args) {
        int[][] input = {{ 0,10,10,30,25},
                         {10, 0,14,21,10},
                         {10,18, 0, 7, 9},
                         { 8,11, 7, 0, 3},
                         {14,10,10, 3, 0}};

        HamiltonianInList me = new HamiltonianInList(input);//시작점은 중요하지 않다.
        me.findAllPath();
    }

    public static class Vertex{
        int vertexName;
        int weight;

        public Vertex(int vertexName, int weight) {
            this.vertexName = vertexName;
            this.weight = weight;
        }
    }
}
