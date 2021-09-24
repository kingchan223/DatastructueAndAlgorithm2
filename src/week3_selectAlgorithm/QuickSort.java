package week3_selectAlgorithm;

public class QuickSort {
    int nOfIteration;
    int[] data;
    int size;

    public QuickSort(int[] myData){
        data = myData;
        size = data.length;
        nOfIteration = 0;
    }

    public void showInfo(){
        System.out.println("\n>>> QuickSort <<<");
        System.out.println("-- result");
        for(int i=0; i<size; i++){
            System.out.println(data[i]+" ");
        }
        System.out.println("\n-- Iteration(RecursiveCalls) = "+nOfIteration);

    }
    public int[] qSort(){
        for(int i=0; i<size; i++){
            System.out.println(data[i]+" ");
        }
        return quickSort(data, 0, size - 1);
    }

    private int[] quickSort(int[] data, int p, int r){
        nOfIteration++;
        if(p<r){
            int q = partition(data, p, r);
            quickSort(data, p, q-1);
            quickSort(data, q+1, r);
        }
        return data;
    }
    private int partition(int[] data, int p, int r){
        /////////////////////-----~~~~~~~----/////////////////////////////
        int pValue = median(data, p, r);
        System.out.println("\n-- Quasi-median between "+p+" and "+r+" :"+pValue);
        int index = 0;
        for(int i=p; i<=r; i++){
            if(data[i]==pValue){
                index = i;
                break;
            }
        }
        swapData(data, r, index);
        /////////////////////----~~~~~~~----/////////////////////////////
        int pivot = r;

        int left = p;
        int right = r;
        while(left<right){
            while(data[left]<data[pivot] && left<right) left++;
            while(data[right]>=data[pivot] && left<right) right--;
            if(left<right) swapData(data, left, right);
        }
        swapData(data, pivot, right);
        return right;
    }
    ////////////////////////////////////////////////////////////////
    private int median(int[] data, int p, int r) {
        if((r-p+1)<=5)
            return median5(data, p, r);
        float f = r-p+1;
        int arrSize = (int) Math.ceil(f / 5);
        int[] medianArr = new int[arrSize];
        for (int i = 0; i < arrSize; i++)
            medianArr[i] = median5(data, p+5*i, Math.min(p+5*i+4, r));

        return median(medianArr, 0, arrSize - 1);
    }

    private int median5(int[] data, int p, int r) {
        if(p==r)
            return data[p];
        sort5(data, p, r);
        return data[p + (int) ((r - p + 1) / 2)];
    }

    private void sort5(int[] data, int p, int r) {
        for(int i=p; i<r;i++)
            for(int j=i+1; j<=r; j++)
                if(data[i]>data[j])
                    swapData(data, i, j);
    }
    ////////////////////////////////////////////////////////////////
    private void swapData(int[] data, int r, int index) {
        int temp = data[r];
        data[r] = data[index];
        data[index] = temp;
    }
    ////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        int[] data = new int[100];
        for(int i=0; i<100; i++){
            data[i] =i;
        }
        QuickSort q = new QuickSort(data);
        q.qSort();
        q.showInfo();
    }
}
