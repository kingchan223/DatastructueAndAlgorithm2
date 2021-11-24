package week11;

import java.util.ArrayList;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        ArrayList<LinkedList<String>> adjacentList  = new ArrayList<>();

        adjacentList.add(new LinkedList<String>());
        adjacentList.add(new LinkedList<String>());
        System.out.println(adjacentList.get(0).isEmpty());
    }
}
