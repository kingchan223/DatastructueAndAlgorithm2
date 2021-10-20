package week3_selectAlgorithm;

public class SelectTest {

    private int recursiveCount = 0;
    public void resetCount() {
        recursiveCount = 0;
    }
    public int getRecursiveCount() {
        return recursiveCount;
    }
/*-------------------------일반 select-----------------------*/
    //배열 data의 p에서 r의 범위에서 i번째 데이터를 찾고싶다.
    public int select(int[] data, int p, int r, int i){
        recursiveCount++;
        if(p>r){//시작 인덱스가 마지막 인덱스보다 크다면 진행할 수 없다.
            System.out.println("Invalid argument");
            return -1;
        }
        if(p==r) return data[p];
        int q = partition(data, p, r);
        int k = q-p;
        if(i<k) return select(data, p, q - 1, i);
        else if(i==k) return data[q];
        else return select(data, q + 1, r, i - (q - p) - 1);
    }

    private int partition(int[] data, int p, int r) {
        int pivot = p;
        int left = p;
        int right = r;

        while(true){
            while(data[left]<data[pivot] && left<right) left++;
            while(data[right]>=data[pivot] && left < right) right--;
            if(left<right) swapData(data, left, right);
            else break;
        }
        swapData(data, pivot, right);
        return right;
    }
/*---------------------------------------------------------------*/



/*------------------------ linear select ------------------------*/
    public int linearSelect(int[] data, int p, int r, int i){
        recursiveCount++;

        if(p>r){
            System.out.println("Invalid argument");
            return -1;
        }
        if(p==r) return data[p];
        int q = linearPartition(data, p, r);
        int k = q-p;
        if(i<k) return linearSelect(data, p, q - 1, i);
        else if(i==k) return data[q];
        else return linearSelect(data, q + 1, r, i - (q - p) - 1);
    }

    private int linearPartition(int[] data, int p, int r) {
        int pValue = median(data, p, r);
        int index=0;
        for(int i=p; i<=r; i++)
            if(data[i]==pValue){
                index = i;
                break;
            }
        swapData(data, r, index);
        int pivot = r;
        int left = p;
        int right = r;
        while(true){
            while(data[left]<data[pivot] && left<right) left++;
            while(data[right]>=data[pivot] && left < right) right--;
            if(left<right) swapData(data, left, right);
            else break;
        }
        swapData(data, pivot, right);
        return right;
    }

    private int median(int[] data, int p, int r) {
        if((r-p+1)<=5)
            return median5(data, p, r);
        float f = (r-p+1);
        int arrSize = (int) Math.ceil(f/5);
        // medianArray는 data를 다섯개씩 끊은 어레이들의 중간값들을 모아둔 것임.
        int[] medianArray = new int[arrSize];
        for(int i=0; i<arrSize; i++){
            medianArray[i] = median5(data, p+5*i, Math.min(p+5*i+4,r));
        }
        return median(medianArray, 0, arrSize-1);
    }
    //원소 5개중 가운데 데이터 뽑아서 주기(5개 이하일 수도)
    private int median5(int[] data, int p, int r) {
        if(p==r)
            return data[p];
        sort5(data, p, r);
        return data[p+ ((r-p+1)/2)];//(r-p+1)/2 : data의 중간
    }

    private void sort5(int[] data, int p, int r) {
        for(int i=p; i<r; i++){
            for(int j=i+1; j<=r;j++){
                if(data[i]>data[j])
                    swapData(data, i, j);
            }
        }
    }
/*-----------------------------------------------------------------*/

    private void swapData(int[] data, int left, int right) {
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

/*================================================================*/
    public static void main(String[] args) {
        int[] data = {5, 27, 24, 6, 35, 3, 7, 8, 18, 71, 77, 9, 11, 32, 21, 4};

        SelectTest s = new SelectTest();
        for(int i=0; i<data.length;i++){
            System.out.print(s.select(data, 0, data.length-1, i)+"  ");
        }
        System.out.println("# of Recursive Calls of Select = "+s.getRecursiveCount());
        System.out.println();
        s.resetCount();
        for(int i=0; i<data.length;i++){
            System.out.print(s.linearSelect(data, 0, data.length-1, i)+"  ");
        }
        System.out.println("# of Recursive Calls of LinearSelect = "+s.getRecursiveCount());
    }
}
