package week10_minimal_spanning_tree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

public class GenericHeapInArrayList <T extends Comparable<T>>{

    ArrayList<T> heapTree = new ArrayList<T>();

    //Heap을 ArrayList로 구현시 맨 앞은 쓰지 않으므로 널로 초기화한다.
    public GenericHeapInArrayList(){
        heapTree.add(null);
    }

    public void heapSort(List<T> input){
        buildHeap(input);
        sortOut();
    }

    private void buildHeap(List<T> input) {
        for(int i=0; i<input.size(); i++) {
            insertHeap(input.get(i));
        }
    }

    private void insertHeap(T data) {
        int nowIndex = heapTree.size();
        heapTree.add(data);
        int parentIndex = nowIndex/2;
        heapifyUpward(parentIndex);
        System.out.println(heapTree.toString());
    }

    private void heapifyUpward(int parentIndex){
        int now = 2*parentIndex;
        if(now > heapTree.size() || parentIndex  < 1)
            return;

        if(now+1 < heapTree.size() && heapTree.get(now).compareTo(heapTree.get(now+1)) > 0 )
            now += 1;

        if(heapTree.get(now).compareTo(heapTree.get(parentIndex)) < 0){
            swap(heapTree, now, parentIndex);
            heapifyUpward(parentIndex/2);
        }
    }

    private void sortOut(){
        while (heapTree.size() > 1){
            System.out.println(deleteHeap() + "   " + heapTree.toString());
        }
    }

    private T deleteHeap() {
        if(heapTree.size() < 1)
            return null;
        T retVal = heapTree.remove(1);
        if(heapTree.size() > 2) {
            heapTree.add(1, heapTree.remove(heapTree.size()-1));
            heapifyDownward(1);
        }
        return retVal;
    }

    private void heapifyDownward(int i) {
        int k = 2*i;
        if(k >= heapTree.size() || i < 1)
            return;
        if(k+1 < heapTree.size() && heapTree.get(k).compareTo(heapTree.get(k+1)) > 0)
            k += 1;
        if(heapTree.get(i).compareTo(heapTree.get(k)) > 0){
            swap(heapTree, i, k);
            heapifyDownward(k);
        }
    }

    private void swap(ArrayList<T> heapTree, int now, int parentIndex){
        T temp = heapTree.get(parentIndex);
        heapTree.set(parentIndex, heapTree.get(now));
        heapTree.set(now, temp);
    }

    public static void main(String[] args) {

        MyPriorityQueueTest.Vertex vertex1 = new MyPriorityQueueTest.Vertex(1, 10);
        MyPriorityQueueTest.Vertex vertex2 = new MyPriorityQueueTest.Vertex(2, 7);
        MyPriorityQueueTest.Vertex vertex3 = new MyPriorityQueueTest.Vertex(3, 3);
        MyPriorityQueueTest.Vertex vertex4 = new MyPriorityQueueTest.Vertex(4, 12);
        MyPriorityQueueTest.Vertex vertex5 = new MyPriorityQueueTest.Vertex(5, 5);

        ArrayList<MyPriorityQueueTest.Vertex> data = new ArrayList<>();
        data.add(vertex1);
        data.add(vertex2);
        data.add(vertex3);
        data.add(vertex4);
        data.add(vertex5);

        Hashtable<Integer, Integer> objectObjectHashtable = new Hashtable<>();
        GenericHeapInArrayList<MyPriorityQueueTest.Vertex> h = new GenericHeapInArrayList<>();
        h.heapSort(data);
    }
}
