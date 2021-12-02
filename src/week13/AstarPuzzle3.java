package week13;

import java.util.ArrayList;

public class AstarPuzzle3 {
    private static final double INF = 999_999_999;
    int[][] nowPuzzle;
    int[][] goalPuzzle;
    Point nowPoint;
    ArrayList<Integer> open;
    ArrayList<Integer> closed;
    ArrayList<Integer> path;
    private static final int[] dr = { 0, 0, -1, 1};
    private static final int[] dc = {-1, 1,  0, 0};
    private static final String[] direction = {"L","R","U","D"};


    public AstarPuzzle3(int[][] nowPuzzle, int[][] goalPuzzle) {
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

    public int[][] copy(int[][] ogPuzzle){
        int[][] copied = new int[3][3];
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                copied[i][j] = ogPuzzle[i][j];

        return copied;
    }

    public void algo(){
        showPuzzle(nowPuzzle);
        double nowGx = 0;
        while(true) {
            double[] fxArr = {INF, INF, INF, INF};
            Point[] points = {null, null, null, null};
            for(int i=0; i<4; i++){
                int[][] tempPuzzle = copy(nowPuzzle);
                int[][] movedPuzzle = move(tempPuzzle, nowPoint, Direction.valueOf(direction[i]));
                fxArr[i] = f(movedPuzzle, nowGx);
                points[i] = new Point(fxArr[i], Direction.valueOf(direction[i]));
            }

            Point point = extract(points);

            if (point.d == Direction.L) {
                nowPoint = new Point(nowPoint.row, nowPoint.col - 1);
                nowPuzzle = move(nowPuzzle, nowPoint, Direction.L);
            }
            else if (point.d == Direction.R) {
                nowPoint = new Point(nowPoint.row, nowPoint.col + 1);
                nowPuzzle = move(nowPuzzle, nowPoint, Direction.R);
            }
            else if (point.d == Direction.U) {
                nowPoint = new Point(nowPoint.row - 1, nowPoint.col);
                nowPuzzle = move(nowPuzzle, nowPoint, Direction.U);
            }
            else{// (point.d == Direction.U) {
                nowPoint = new Point(nowPoint.row + 1, nowPoint.col);
                nowPuzzle = move(nowPuzzle, nowPoint, Direction.D);
            }
            showPuzzle(nowPuzzle);
            if (checkSuccess(nowPuzzle)) break;
        }
    }

    public int[][] move(int[][] nowPuzzle, Point p, Direction d){
        if(!validate(p, d)) return null;
        switch(d){
            case L:{//row = 0, col = 1
                int t = nowPuzzle[p.row][p.col-1];
                nowPuzzle[p.row][p.col-1] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = t;
                break;
            }
            case R:{
                int t = nowPuzzle[p.row][p.col + 1];
                nowPuzzle[p.row][p.col+1] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = t;
                break;
            }
            case U:{
                int t = nowPuzzle[p.row-1][p.col];
                nowPuzzle[p.row-1][p.col] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = t;
                break;
            }
            case D:{
                int t = nowPuzzle[p.row+1][p.col];
                nowPuzzle[p.row+1][p.col] = nowPuzzle[p.row][p.col];
                nowPuzzle[p.row][p.col] = t;
                break;
            }
        }
        return nowPuzzle;
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

    private Point extract(Point[] points) {
        Point temp;
        if(points[0].weight <= points[1].weight) temp = points[0];
        else temp = points[1];

        if(temp.weight > points[2].weight) temp = points[2];
        if(temp.weight > points[3].weight) temp = points[3];
        return temp;
    }

    public boolean validate(Point p, Direction d){
        if(d== Direction.L) return p.col != 0;
        else if(d== Direction.R) return p.col != 2;
        else if(d== Direction.U) return p.row != 0;
        else return p.row != 2;
    }

    //매력함수 + 휴리스틱함수
    public double f(int[][] movedPuzzle, double nowGx){
        if(movedPuzzle==null) return INF;
        return g(nowGx)+h(movedPuzzle);
    }

    //매력 함수
    private double g(double nowGx){
        return nowGx;
    }

    //휴리스틱 함수
    private double h(int[][] movedPuzzle){
        double f = 0;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(movedPuzzle[i][j]!=goalPuzzle[i][j]) f+=1;

        return f;
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

    public static void main(String[] args) {
        int[][] goal = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}};

        int[][] start = {
                {2, 0, 3},
                {1, 8, 4},
                {7, 6, 5}};

        AstarPuzzle3 aStar = new AstarPuzzle3(start, goal);
        aStar.algo();
    }
}
