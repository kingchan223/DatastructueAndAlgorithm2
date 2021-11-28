package week13;

import java.util.ArrayList;

public class AstarPuzzle {
    private static final double INF = 999_999_999;
    int[][] nowPuzzle;
    int[][] goalPuzzle;
    Point nowPoint;
    ArrayList<Integer> open;
    ArrayList<Integer> closed;
    ArrayList<Integer> path;

    public AstarPuzzle(int[][] nowPuzzle, int[][] goalPuzzle) {
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
        int[][] tempPuzzle = nowPuzzle.clone();
        double nowGx = 0;
        double nowFx = INF;
        do {
            System.out.println(nowPoint);
            int[][] moveL = move(tempPuzzle, nowPoint, Direction.L);
            int[][] moveR = move(tempPuzzle, nowPoint, Direction.R);
            int[][] moveU = move(tempPuzzle, nowPoint, Direction.U);
            int[][] moveD = move(tempPuzzle, nowPoint, Direction.D);

            double L = f(moveL, nowGx);
            Point pointL = new Point(L, Direction.L);

            double R = f(moveR, nowGx);
            Point pointR = new Point(R, Direction.R);

            double U = f(moveU, nowGx);
            Point pointU = new Point(U, Direction.U);

            double D = f(moveD, nowGx);
            Point pointD = new Point(D, Direction.D);

            Point point = extract(pointL, pointR, pointU, pointD);

            if (point.d == Direction.L) {
                nowPoint = new Point(nowPoint.row, nowPoint.col - 1);
                nowPuzzle = moveL;
                nowFx = L;
            }
            else if (point.d == Direction.R) {
                nowPoint = new Point(nowPoint.row, nowPoint.col + 1);
                nowPuzzle = moveR;
                nowFx = R;
            }
            else if (point.d == Direction.U) {
                nowPoint = new Point(nowPoint.row - 1, nowPoint.col);
                nowPuzzle = moveU;
                nowFx = U;
            }
            else{// (point.d == Direction.U) {
                nowPoint = new Point(nowPoint.row + 1, nowPoint.col);
                nowPuzzle = moveD;
                nowFx = D;
            }
        } while (!checkSuccess(nowPuzzle));
    }

    private Point extract(Point pointL, Point pointR, Point pointU, Point pointD) {
        Point temp;
        if(pointL.weight < pointR.weight) temp = pointL;
        else temp = pointR;

        if(temp.weight < pointU.weight) temp = temp;
        else temp = pointU;

        if(temp.weight < pointD.weight) temp = temp;
        else temp = pointD;

        return temp;
    }


    public boolean validate(Point p, Direction d){
        if(d==Direction.L){
            return p.col != 0;
        }
        else if(d==Direction.R){
            return p.col != 2;
        }
        else if(d==Direction.U){
            return p.row != 0;
        }
        else{//(d==Direction.D)
            return p.row != 2;
        }
    }

    public int[][] move(int[][] nowPuzzle, Point p, Direction d){
        if(!validate(p, d)) return null;
        switch(d){
            case L:{
                nowPuzzle[p.row][p.col-1] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = 0;
                break;
            }
            case R:{
                nowPuzzle[p.row][p.col+1] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = 0;
                break;
            }
            case U:{
                nowPuzzle[p.row-1][p.col] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = 0;
                break;
            }
            case D:{
                nowPuzzle[p.row+1][p.col] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = 0;
                break;
            }
        }
        return nowPuzzle;
    }

    //매력함수 + 휴리스틱함수
    public double f(int[][] movedPuzzle, double nowGx){
        if(movedPuzzle==null) return INF;
        return g(nowGx)+h(movedPuzzle);
    }
    //매력 함수 - 피라고라스
    private double g(double preGx){
        return preGx+1;
    }
    //휴리스틱 함수 - 피라고라스
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

        AstarPuzzle aStar = new AstarPuzzle(start, goal);
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

    public static class Number{
        int n;
        Direction direction;
        public Number(int n, Direction direction) {
            this.n = n;
            this.direction = direction;
        }
    }
}
