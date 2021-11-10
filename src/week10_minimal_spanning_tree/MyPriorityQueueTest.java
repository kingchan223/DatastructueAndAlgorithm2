package week10_minimal_spanning_tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MyPriorityQueueTest{

    public static void main(String[] args) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(1, 5));
        pq.add(new Vertex(2, 10));
        pq.add(new Vertex(3, 2));
        pq.add(new Vertex(4, 3));
        pq.add(new Vertex(5, 7));
        pq.add(new Vertex(6, 4));
        pq.add(new Vertex(7, 7));
        Vertex poll1 = pq.poll();
        System.out.println("poll1 = " + poll1);
        Vertex poll2 = pq.poll();
        System.out.println("poll2 = " + poll2);
        Vertex poll3 = pq.poll();
        System.out.println("poll3 = " + poll3);
        Vertex poll4 = pq.poll();
        System.out.println("poll4 = " + poll4);
    }

    public static class Vertex implements Comparable<Vertex> {
        int number;
        int weight;
        public int getNumber() {return number;}
        public int getWeight() {return weight;}

        Vertex(int number, int weight){
            this.number = number;
            this.weight = weight;
        }

        @Override
        public int compareTo(Vertex that) {
            if(this.weight == that.weight)
                return that.weight-this.weight;
            return this.weight-that.weight;
        }

        @Override
        public String toString() {
            return "(w:" + this.weight+", num:"+number+")";
        }
    }
}
