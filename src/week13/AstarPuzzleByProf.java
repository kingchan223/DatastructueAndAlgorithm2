package week13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class AstarPuzzleByProf {

    public static final int INF = 999_999_999;
    LinkedList<State> Q;
    State current;
    ArrayList<int[][]> prev;
    int [][] target;
    int size;

    public AstarPuzzleByProf(int [][] a, int [][] b) {
        current = new State(a);
        target = b;
        prev = new ArrayList<>();
        Q = new LinkedList<>();
        size = target.length;
    }

    public void ShortestPath() {
        Q.add(current);//Q를 사용하여 최신 상태를 저장하는 좋은 방법
        while(!Q.isEmpty()) {
            State u = Q.get(0);
            Q.remove(0);
            u.fVal=u.mCount+u.diffCount(target);
            u.showMatrix();
            System.out.println(" F-value = "+u.fVal+" ="+u.mCount+" + "+u.diffCount(target));
            if (sameMatrix(u.matrix,target)) return;
            prev.add(u.matrix);

            HashSet<State> adjacent = getCandidate(u);
            int min = INF;

            for (State v : adjacent) {
                if (!visited(prev, v.matrix)) {//이전에 이미 검사한 matrix 가 아니라면
                    v.fVal=v.mCount+v.diffCount(target);
                    if (v.fVal<=min) {
                        min = v.fVal;
                        for(int i=0;i<Q.size();i++) {
                            if(Q.get(i).fVal>v.fVal) Q.remove(i);
                        }
                        Q.add(v);//Q에는 현재 최대 4가지의 움직임을 한 후 가장 fVal이 작은 애가 저장된다.(한개만 저장된다.)
                    }
                }
            }
        }
    }

    //이전에 검사했던 matrix 인지 check 한다.
    private boolean visited(ArrayList<int[][]> prev, int[][] matrix) {
        if (prev.size()==0) return false;
        else
            for (int[][] ints : prev)
                if (sameMatrix(ints, matrix)) return true;
        return false;
    }

    private boolean sameMatrix(int[][] a, int [][] b) {
        for (int i=0;i<a.length;i++)
            for (int j=0;j<a.length;j++)
                if (a[i][j]!=b[i][j])
                    return false;
        return true;
    }

    private HashSet<State> getCandidate(State u) {
        HashSet<State> retSet = new HashSet<State>();
        if (u.emptyX-1>=0) retSet.add(createNewState(u, u.emptyX-1, u.emptyY));//왼쪽으로 이동
        if (u.emptyX+1<size) retSet.add(createNewState(u, u.emptyX+1, u.emptyY));//오른쪽으로 이동
        if (u.emptyY-1>=0) retSet.add(createNewState(u, u.emptyX, u.emptyY-1));//위로 이동
        if (u.emptyY+1<size) retSet.add(createNewState(u, u.emptyX, u.emptyY+1));//아래로 이동
        return retSet;
    }

    private State createNewState(State u, int i, int j) {
        State newState = new State(u.matrix);
        newState.matrix[u.emptyX][u.emptyY]=newState.matrix[i][j];
        newState.matrix[i][j]=0;
        newState.mCount=u.mCount+1;
        newState.emptyX=i;
        newState.emptyY=j;
        return newState;
    }

    class State{
        int [][] matrix;
        int mCount ; //몇 번 move 했나
        int fVal;//fVal  = g(x) + h(x)
        int emptyX, emptyY;//0이 있는 좌표

        public State(int[][] input){
            matrix = new int[input.length][input.length];
            for (int i=0;i<matrix.length;i++)
                for (int j=0;j<matrix.length;j++){
                    matrix[i][j]=input[i][j];
                    if(matrix[i][j]==0) {
                        emptyX=i;
                        emptyY=j;
                    }
                }
            mCount = 0;
        }

        //target matrix와 얼마나 다른가?
        public int diffCount(int[][] target) {
            int retVal = 0;
            for (int i=0;i<matrix.length;i++)
                for (int j=0;j<matrix.length;j++){
                    if(this.matrix[i][j]!=target[i][j])
                        retVal++;
                }
            return retVal;
        }
        //matrix 상태 출력
        public void showMatrix() {
            System.out.println();
            for (int i=0;i<matrix.length;i++) {
                for (int j=0;j<matrix.length;j++){
                    System.out.print(matrix[i][j]+"  ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
//        int [][] as_is = {{2,8,3},{1,6,4},{7,0,5}};
        int [][] as_is = {{2,3,8},{7,6,0},{1,4,5}};
        int [][] to_be = {{1,2,3},{8,0,4},{7,6,5}};

        AstarPuzzleByProf me = new AstarPuzzleByProf(as_is, to_be);
        me.ShortestPath();
    }
}
