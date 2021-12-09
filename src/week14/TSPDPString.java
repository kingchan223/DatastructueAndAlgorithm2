package week14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class TSPDPString {

    int [][] adjacentMatrix, allDistance;
    String[][] allPath;
    int numberVertex;
    ArrayList<String> finalPath;
    ArrayList<String> vertices;

    public TSPDPString(int[][] input, String[] names) {
        adjacentMatrix = input;
        numberVertex=adjacentMatrix.length;
        vertices = new ArrayList<>();
        vertices.addAll(Arrays.asList(names));
        allDistance=new int[numberVertex][(int) Math.pow(2, numberVertex)];
        allPath=new String[numberVertex][(int) Math.pow(2, numberVertex)];
        for (int[] temp: allDistance) Arrays.fill(temp,-1);
        for (String[] temp: allPath) Arrays.fill(temp,"X");
        finalPath = new ArrayList<>();
    }

    public int minDistance(String start) {
        HashSet<String> thruSet = new HashSet<>();
        for (int i=0; i<numberVertex; i++) {
            if (!vertices.get(i).equals(start))
                thruSet.add(vertices.get(i));
        }
        return minDistance(start, thruSet, start);
    }

    private int minDistance(String start, HashSet<String> thruSet, String returnPoint) {

        if (allDistance[vertices.indexOf(start)][indexSet(thruSet)]!=-1)
            return allDistance[vertices.indexOf(start)][indexSet(thruSet)];

        if (thruSet.size()==0) {
            allDistance[vertices.indexOf(start)][indexSet(thruSet)]=adjacentMatrix[vertices.indexOf(start)][vertices.indexOf(returnPoint)];
            allPath[vertices.indexOf(start)][indexSet(thruSet)]=returnPoint;
            return adjacentMatrix[vertices.indexOf(start)][vertices.indexOf(returnPoint)];
        }
        int min=999;
        for (String i : thruSet) {
            HashSet<String> nextThru = reduce(thruSet,i);
            if (adjacentMatrix[vertices.indexOf(start)][vertices.indexOf(i)]!=999) {
                int tempDist = adjacentMatrix[vertices.indexOf(start)][vertices.indexOf(i)]+minDistance(i, nextThru, returnPoint);

                if (tempDist<=min) {
                    min=tempDist;
                    allDistance[vertices.indexOf(start)][indexSet(thruSet)]=min;
                    allPath[vertices.indexOf(start)][indexSet(thruSet)]=i;
                }
            }
        }
        System.out.println(start+" -> "+thruSet+" ("+min+")");
        return min;
    }

    private int indexSet(HashSet<String> thru) {
        int result=0;
        for (String i : thru) {
            result += Math.pow(2,vertices.indexOf(i));
        }
        return result;
    }

    private HashSet<String> reduce(HashSet<String> set, String i) {
        HashSet<String> result= new HashSet<>();
        for (String j : set) result.add(j);
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

    private void findPath(String start) {
        finalPath.add(start);
        System.out.println("***** "+start+" added");

        int indexJ=-1;

        for (int j=0; j< Math.pow(2, numberVertex); j++) {
            if (allDistance[vertices.indexOf(start)][j]>0)
                indexJ = j;
        }

        if (indexJ==-1) {
            System.out.println("No Path found ...");
            return ;
        }
        int dist = allDistance[vertices.indexOf(start)][indexJ]-adjacentMatrix[vertices.indexOf(start)][vertices.indexOf(allPath[vertices.indexOf(start)][indexJ])];
        findPath(allPath[vertices.indexOf(start)][indexJ], dist, start);
    }

    private void findPath(String i, int dist, String endPoint) {
        finalPath.add(i);
        System.out.println("***** "+i+" added");

        if (i.equals(endPoint)) return;
        int indexJ=-1;

        for (int j=0; j< Math.pow(2, numberVertex); j++) {
            if (allDistance[vertices.indexOf(i)][j]==dist) {
                indexJ = j;
                break;
            }
        }
        if (indexJ==-1) {
            System.out.println("Wrong Index ...");
            return ;
        }
        findPath(allPath[vertices.indexOf(i)][indexJ], dist-adjacentMatrix[vertices.indexOf(i)][vertices.indexOf(allPath[vertices.indexOf(i)][indexJ])], endPoint);
    }

    public static void main(String[] args) {
        int [][] input = { {0,10,10,30,25}, {10,0,14,21,10},
                {10,18,0,7,9}, {8,11,7,0,3}, {14,10,10,3,0}
        };
        String[] names = {"A", "B", "C", "D", "E"};

        TSPDPString me = new TSPDPString(input, names);
        int tsp = me.minDistance("A");
        System.out.println("Result = "+tsp);
        me.showAllDistAndPath();
        me.findPath("A");

    }


}