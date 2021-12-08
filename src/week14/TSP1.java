package week14;

import java.util.ArrayList;
import java.util.HashSet;
//다이나믹이 완벽히 적용되진 않음. 그냥 점화식을 사용했을 뿐이다.
public class TSP1 {
    public static final int INF = 999_999_999;

    int [][] adjacentMatrix;
    int numOfV;
//    ArrayList<Integer> path;
    int[] path;/**/

    public TSP1(int[][] in){
        adjacentMatrix = in;
        numOfV = adjacentMatrix.length;
        path = new int[numOfV];/**/
    }

    //알고리즘
    public int minDist(int start){
        HashSet<Integer> thruSet = new HashSet<>();
        int cnt = 0;
        for (int i = 0; i < numOfV; i++) thruSet.add(i);//모든 노드들을 hashSet에 넣어준다.
        thruSet.remove(start);
        path[cnt] = start;/**/
        return minDist(start, thruSet, start, cnt, path);/**/
    }
    //minDist => index노드를 제외하고 모든 thruSet을 거쳐서 end까지 도달한 경로 중 최소인 경로
    private int minDist(int index, HashSet<Integer> thruSet, int end, int cnt, int[] path) {/**/
        if(thruSet.size()==0) return adjacentMatrix[index][end];//공집합일 때는 현재노드까지의 거리를 반환해주면 된다.
        int min = INF;
        int cnt2 = cnt+1;/**/
        for(int i : thruSet){//남아있는 모든 노드들에 대해서
            HashSet<Integer> next = reduce(thruSet, i);
            if(adjacentMatrix[index][i]!=INF){//index 와 i 노드에 대해 경로가 존재한다면
                int tempDist = adjacentMatrix[index][i]+minDist(i, next, end, cnt2, path);/**/
                if(tempDist < min){
                    path[cnt2] = index;/**/
                    min = tempDist;
                }
            }
        }
        return min;
    }

    public void showResult(){/**/
        for (int i : path) {
            System.out.print(i+"  ");
        }
    }

    //concurrent hashSet exception 을 막기 위해 새로운 해쉬셋을 만들어서 반환해준다.
    private HashSet<Integer> reduce(HashSet<Integer> thruSet, int i) {
        HashSet<Integer> result = new HashSet<>(thruSet);
        result.remove(i);
        return result;
    }

    public static void main(String[] args) {
        int[][] input = {
                { 0,10,10,30,25},
                {10, 0,14,21,10},
                {10,18, 0, 7, 9},
                { 8,11, 7, 0, 3},
                {14,10,10, 3, 0}};
        TSP1 me = new TSP1(input);//시작점은 중요하지 않다.
        System.out.println(me.minDist(0));
        me.showResult();
    }
}
