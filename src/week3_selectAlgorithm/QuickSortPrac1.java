package week3_selectAlgorithm;
/* median7로 만들어보기 */
public class QuickSortPrac1 {
    int nOfIteration;
    int[] data;
    int size;

    public QuickSortPrac1(int[] myData){
        data = myData;
        size = data.length;
        nOfIteration = 0;
    }

    public void showInfo(){
        System.out.println("\n>>> QuickSort <<<");
        System.out.println("-- result : ");
        for(int i=0; i<size; i++){
            System.out.print(data[i]+" ");
        }
        System.out.println("\n-- Iteration(RecursiveCalls) = "+nOfIteration);
    }

    public int[] qSort(){
        for(int i=0; i<size; i++)
            System.out.print(data[i]+" ");//정렬 되기 이전의 데이터들 출력
        return quickSort(data, 0, size - 1);
    }

    private int[] quickSort(int[] data, int p, int r){
        nOfIteration++;
        if(p<r){
            int q = partition(data, p, r);
            quickSort(data, p, q-1);//p의 앞부분과
            quickSort(data, q+1, r);//p의 뒷부분에도 같은 작압을 해준다.
        }
        return data;
    }

    private int partition(int[] data, int p, int r){
        int pValue = median(data, p, r);//data의 p~r중 중앙값인 pValue
        int index = 0;
        for(int i=p; i<=r; i++){//pValue의 인덱스를 찾아서 index에 넣는다.
            if(data[i]==pValue){
                index = i;
                break;
            }
        }
        swapData(data, r, index);//맨 뒤에 중앙값 pValue가 위치한다.
        int pivot = r;//이제 pivot이 중앙값이다.

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

    private int median(int[] data, int p, int r) {
        //만약 data의 길이가 7보다 작거나 같으면 바로 중앙값을 찾아서(median7실행)바로 반환한다.
        if((r-p+1)<=7)
            return median7(data, p, r);
        //만약 data의 길이가 7보다 크다면. 길이가 7인 배열들을 만든다.
        float f = r-p+1;//f는 현재 우리가 중앙값을 찾아야 하는 배열의 길이이다.
        int arrSize = (int) Math.ceil(f / 7);//arrSize는 나눠진 배열들의 개수이다.
        int[] medianArr = new int[arrSize];//medianValue에 나눈 배열들의 중앙값들이 저장될 것이다.
        for (int i = 0; i < arrSize; i++)//medianValue에 나눈 배열들의 중앙값들을 저장.
            medianArr[i] = median7(data, p+7*i, Math.min(p+7*i+6, r));
        return median(medianArr, 0, arrSize - 1);
    }

    //median에 들어오는 data의 길이는 5이하이다.
    private int median7(int[] data, int p, int r) {
        if(p==r)//p==r은 길이가 1이므로, 바로 반환
            return data[p];
        sort7(data, p, r);//7개의 이하의 데이터들을 정렬한 뒤
        return data[p + ((r - p + 1) / 2)];//가운데에 있는 값을 반환한다.
    }

    private void sort7(int[] data, int p, int r) {
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
        int[] data = {5, 27, 24, 6, 35, 3, 7, 8, 18, 71, 77, 9, 11, 32, 21, 4};
        QuickSortPrac1 q = new QuickSortPrac1(data);
        q.qSort();
        q.showInfo();
    }
}
