package week14;

import java.util.Arrays;

public class Hamiltonian {
    public static final int INF = 999_999_999;
    int [][] adjacentMatrix;

    public Hamiltonian(int[][] in){
        adjacentMatrix = in;
    }

    public void findAllPath(){
        boolean[] visited = new boolean[adjacentMatrix.length] ;//정점 방문 여부
        int[] path = new int[adjacentMatrix.length];//정점 방문 경로
        int start = 0;//시작점은 0으로 한다.
        int vCount = 0;//vCount가 정점의 개수가 되면 모든 곳을 다 탐방한 것이다.
        Arrays.fill(visited, false);
        Arrays.fill(path, -1);

        visited[start] = true;//시작점은 한번 방문한 것으로 한다.
        path[0] = start;//경로에 시작점을 넣어준다.
        vCount++;//시작점을 추가했으므오 COUNT를 올려준다.
        findAllPath(start, visited, path, start, vCount);//빙 돌아서 start한 곳까지 와야하므로 end에 start를 넣어준다.
    }

    private void findAllPath(int nowIndex, boolean[] visited, int[] path, int end, int count) {
        for (int i = 0; i < adjacentMatrix.length; i++) {//인접한 모든 정점을 방문하기 위해 length 만큼 반복문을 돌린다.
            //모든 정점을 방문했다면
            if(i==end && count == adjacentMatrix.length){
                System.out.print("PATH FOUND :");
                for (int j = 0; j < adjacentMatrix.length; j++)
                    System.out.print("-"+path[j]);
                System.out.println();
                return;
            }
            //방문안했다면
            if(!visited[i] && adjacentMatrix[nowIndex][i]!=0 && adjacentMatrix[nowIndex][i]!=INF){//경로가 있다면
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

        Hamiltonian me = new Hamiltonian(input);//시작점은 중요하지 않다.
        me.findAllPath();
    }
}
