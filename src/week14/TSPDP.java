package week14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TSPDP {

    int [][] adjacentMatrix, allDistance, allPath;
    int numberVertex;
    ArrayList<Integer> finalPath;

    public TSPDP(int[][] input) {
        adjacentMatrix = input;
        numberVertex=adjacentMatrix.length;
        allDistance=new int[numberVertex][(int) Math.pow(2, numberVertex)];
        allPath=new int[numberVertex][(int) Math.pow(2, numberVertex)];
        for (int[] temp: allDistance) Arrays.fill(temp,-1);
        for (int[] temp: allPath) Arrays.fill(temp,-1);
        finalPath = new ArrayList<>();
    }

    public int minDistance(int start) {
        HashSet<Integer> thruSet = new HashSet<>();
        for (int i=0; i<numberVertex; i++) {
            if (i!=start)
                thruSet.add(i);
        }
        return minDistance(start, thruSet, start);
    }

    private int minDistance(int start, HashSet<Integer> thruSet, int returnPoint) {

        if (allDistance[start][indexSet(thruSet)]!=-1)
            return allDistance[start][indexSet(thruSet)];

        if (thruSet.size()==0) {
            allDistance[start][indexSet(thruSet)]=adjacentMatrix[start][returnPoint];
            allPath[start][indexSet(thruSet)]=returnPoint;
            return adjacentMatrix[start][returnPoint];
        }
        int min=999;
        for (int i : thruSet) {
            HashSet<Integer> nextThru = reduce(thruSet,i);
            if (adjacentMatrix[start][i]!=999) {
                int tempDist = adjacentMatrix[start][i]+minDistance(i, nextThru, returnPoint);

                if (tempDist<=min) {
                    min=tempDist;
                    allDistance[start][indexSet(thruSet)]=min;
                    allPath[start][indexSet(thruSet)]=i;
                }
            }
        }
        System.out.println(start+" -> "+thruSet+" ("+min+")");
        return min;
    }

    private int indexSet(HashSet<Integer> thru) {
        int result=0;
        for (int i : thru) {
            result += Math.pow(2,i);
        }
        return result;
    }

    private HashSet<Integer> reduce(HashSet<Integer> set, int i) {
        HashSet<Integer> result= new HashSet<>();
        for (int j : set) result.add(j);
        result.remove(i);
        return result;
    }

    public void showAllDistAndPath() {
        System.out.println(">>> Distance Memoization \n");

        for (int i=0; i<numberVertex;i++) {
            for (int j=0; j< Math.pow(2, numberVertex); j++) {
                System.out.print(allDistance[i][j]+"  ");
            }
            System.out.println();
        }
        System.out.println(">>> Path \n");

        for (int i=0; i<numberVertex;i++) {
            for (int j=0; j< Math.pow(2, numberVertex); j++) {
                System.out.print(allPath[i][j]+"  ");
            }
            System.out.println();
        }
    }

    private void findPath(int start) {
        finalPath.add(start);
        System.out.println("***** "+start+" added");

        int indexJ=-1;

        for (int j=0; j< Math.pow(2, numberVertex); j++) {
            if (allDistance[start][j]>0)
                indexJ = j;
        }

        if (indexJ==-1) {
            System.out.println("No Path found ...");
            return ;
        }
        int dist = allDistance[start][indexJ]-adjacentMatrix[start][allPath[start][indexJ]];
        findPath(allPath[start][indexJ], dist, start);
    }

    private void findPath(int i, int dist, int endPoint) {
        finalPath.add(i);
        System.out.println("***** "+i+" added");

        if (i==endPoint)
            return;
        int indexJ=-1;

        for (int j=0; j< Math.pow(2, numberVertex); j++) {
            if (allDistance[i][j]==dist) {
                indexJ = j;
                break;
            }
        }
        if (indexJ==-1) {
            System.out.println("Wrong Index ...");
            return ;
        }
        findPath(allPath[i][indexJ], dist-adjacentMatrix[i][allPath[i][indexJ]], endPoint);
    }

    public static void main(String[] args) {
        int [][] input = { {0,10,10,30,25}, {10,0,14,21,10},
                {10,18,0,7,9}, {8,11,7,0,3}, {14,10,10,3,0}
        };

        TSPDP me = new TSPDP(input);
        int tsp = me.minDistance(0);
        System.out.println("Result = "+tsp);
        me.showAllDistAndPath();
        me.findPath(0);

    }


}