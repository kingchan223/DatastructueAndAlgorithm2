package week13;

import java.util.ArrayList;

public class AstarPuzzle2 {
    private static final double INF = 999_999_999;
    int[][] nowPuzzle;
    int[][] goalPuzzle;
    Point nowPoint;
    ArrayList<Integer> open;
    ArrayList<Integer> closed;
    ArrayList<Integer> path;


    public AstarPuzzle2(int[][] nowPuzzle, int[][] goalPuzzle) {
        this.nowPuzzle = nowPuzzle;
        this.goalPuzzle = goalPuzzle;
        init();
    }

    private void init(){
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(nowPuzzle[i][j]==0){
                    nowPoint = new Point(i, j);
                    break;
                };

        open = new ArrayList<>();
        closed = new ArrayList<>();
        path = new ArrayList<>();
    }

    public boolean checkSuccess(int[][] nowPuzzle){
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(nowPuzzle[i][j]!=goalPuzzle[i][j]) return false;
        return true;
    }

    public void algo(){
        showPuzzle(nowPuzzle);
        double nowGx = 0;
        while(true) {
            System.out.println("cnt");
            for (Direction d : Direction.values()){
                move(d, nowGx);
            }
            showPuzzle(nowPuzzle);
            if (checkSuccess(nowPuzzle)) break;
        }
    }


    private void showPuzzle(int[][] nowPuzzle) {
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(nowPuzzle[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public boolean validate(Point p, Direction d){
        if(d== Direction.L){
            return p.col != 0;
        }
        else if(d== Direction.R){
            return p.col != 2;
        }
        else if(d== Direction.U){
            return p.row != 0;
        }
        else{//(d==Direction.D)
            return p.row != 2;
        }
    }

//    {2, 0, 3},
//    {1, 8, 4},
//    {7, 6, 5}};
    public void move(Direction d, double nowGx){
        int[][] temp = new int[3][3];
        for(int i=0; i<3; i++)
            System.arraycopy(nowPuzzle[i], 0, temp[i], 0, 3);

        Point tempPoint = new Point(nowPoint.row, nowPoint.col);
        if(!validate(nowPoint, d)) return;
        switch(d){
            case L:{//row = 0, col = 1
                int t = temp[nowPoint.row][nowPoint.col-1];
                temp[nowPoint.row][nowPoint.col-1] = temp[nowPoint.row][nowPoint.col];
                temp[nowPoint.row][nowPoint.col] = t;
                tempPoint = new Point(nowPoint.row,nowPoint.col-1 );
                break;
            }
            case R:{
                int t = temp[nowPoint.row][nowPoint.col + 1];
                temp[nowPoint.row][nowPoint.col+1] = temp[nowPoint.row][nowPoint.col];
                temp[nowPoint.row][nowPoint.col] = t;
                tempPoint = new Point(nowPoint.row,nowPoint.col+1 );
                break;
            }
            case U:{
                int t = temp[nowPoint.row-1][nowPoint.col];
                temp[nowPoint.row-1][nowPoint.col] = temp[nowPoint.row][nowPoint.col];
                temp[nowPoint.row][nowPoint.col] = t;
                tempPoint = new Point(nowPoint.row-1,nowPoint.col );
                break;
            }
            case D:{
                int t = temp[nowPoint.row+1][nowPoint.col];
                temp[nowPoint.row+1][nowPoint.col] = temp[nowPoint.row][nowPoint.col];
                temp[nowPoint.row][nowPoint.col] = t;
                tempPoint = new Point(nowPoint.row+1,nowPoint.col );
                break;
            }
        }
        double fn = f(nowPuzzle, nowGx);
        double ft = f(temp, nowGx);
        if(fn >= ft){
            nowPoint = tempPoint;
            nowPuzzle = temp;
        }
    }

//    public int[][] move(int[][] nowPuzzle, Point p, Direction d, ){
//        int[][] temp = new int[3][3];
//        for(int i=0; i<3; i++)
//            for(int j=0; j<3; j++)
//                temp[i][j]=nowPuzzle[i][j];
//        if(!validate(p, d)) return null;
//        switch(d){
//            case L:{//row = 0, col = 1
//                int t = temp[p.row][p.col-1];
//                temp[p.row][p.col-1] = temp[p.row][p.col];
//                temp[p.row][p.col] = t;
//                break;
//            }
//            case R:{
//                int t = temp[p.row][p.col + 1];
//                temp[p.row][p.col+1] = temp[p.row][p.col];
//                temp[p.row][p.col] = t;
//                break;
//            }
//            case U:{
//                int t = temp[p.row-1][p.col];
//                temp[p.row-1][p.col] = temp[p.row][p.col];
//                temp[p.row][p.col] = t;
//                break;
//            }
//            case D:{
//                int t = temp[p.row+1][p.col];
//                temp[p.row+1][p.col] = temp[p.row][p.col];
//                temp[p.row][p.col] = t;
//                break;
//            }
//        }
//        return temp;
//    }

    //매력함수 + 휴리스틱함수
    public double f(int[][] movedPuzzle, double nowGx){
        if(movedPuzzle==null) return INF;
        return g(nowGx)+h(movedPuzzle);
    }
    //매력 함수 - 움직인 횟수(어느 단계에서든 똑같아서 이를 더하는 것이 무의미함)
    private double g(double nowGx){
        return nowGx;
    }
    //휴리스틱 함수 - goalPuzzle과 얼마나 다른지.
    private double h(int[][] movedPuzzle){
        double f = 0;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(movedPuzzle[i][j]!=goalPuzzle[i][j]) f+=1;

        return f;
    }

    public static void main(String[] args) {
        int[][] goal = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}};

        int[][] start = {
                {2, 0, 3},
                {1, 8, 4},
                {7, 6, 5}};

        AstarPuzzle2 aStar = new AstarPuzzle2(start, goal);
        aStar.algo();
    }

    public enum Direction{
        //left, right, up, down
        L, R, U, D
    }

    public static class Point{
        int row;
        int col;
        double weight;
        Direction d;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Point(double weight, Direction d) {
            this.weight = weight;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}
