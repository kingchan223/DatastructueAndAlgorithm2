package week14;

import java.util.HashSet;
//다이나믹이 완벽히 적용되진 않음. 그냥 점화식을 사용했을 뿐이다.
public class TSP1 {
    public static final int INF = 999_999_999;

    int [][] adjacentMatrix;
    int numOfV;

    public TSP1(int[][] in){
        adjacentMatrix = in;
        numOfV = adjacentMatrix.length;
    }

    public int minDist(int start) {
        HashSet<Integer> thruSet = new HashSet<>();
        for (int i = 0; i < numOfV; i++) thruSet.add(i);
        thruSet.remove(start);
        return minDist(start, thruSet, start);
    }

    private int minDist(int index, HashSet<Integer> thruSet, int end) {
        if(thruSet.size()==0) return adjacentMatrix[index][end];//공집합일때
        int min = INF;
        for(int i : thruSet){
            HashSet<Integer> next = reduce(thruSet, i);
            if(adjacentMatrix[index][i]!=INF){
                int tempDist = adjacentMatrix[index][i]+minDist(i, next, end);
                if(tempDist < min) min = tempDist;
            }
        }
        return min;
    }

    private HashSet<Integer> reduce(HashSet<Integer> thruSet, int i) {
        HashSet<Integer> result = new HashSet<>();
        for(int k : thruSet) result.add(k);
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
    }

}
