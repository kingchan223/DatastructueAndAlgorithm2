package week7;

//맨 앞의 물건의 무게는 1. 뒤로 갈수록 무게가 1씩 증가한다고 가정한 알고리즘.
public class Knapsack01 {
    int bagWeight;
    int[] price;
    int[] weight;
    int d[][];

    //초기화
    public Knapsack01(int bagWeight, int[] price) {
        this.bagWeight = bagWeight;
        init(price);
        this.d = new int[price.length+1][bagWeight+1];
        for (int i = 1; i < bagWeight + 1; i++)
            d[1][i] = this.price[1];
    }

    //{3, 2, 5, 3}
    public void init(int[] price){
        this.price = new int[price.length + 1];
        this.weight = new int[price.length + 1];
        for (int i = 1; i < price.length; i++) {
            this.price[i] = price[i-1];
            this.weight[i] = i;
        }
    }

    public void RecurKnapsap(){
        RecurKnapsap(this.bagWeight, this.weight.length-1);
        System.out.println(d[this.weight.length-1][bagWeight]);
    }

    // 맨처음에 8, 4
    public int RecurKnapsap(int bw, int n){
        if(bw <= 0 || n <= 0) return 0;
        if(d[n][bw]!=0) return d[n][bw];

        //현재물건의 무게가 현재 가방의 무게보다 큰 경우
        if(bw < weight[n]){//더작은 무게를 가진 물건으로 시도한다.
            return RecurKnapsap(bw,n-1);
        }
        //현재 가방의 무게가 현재 물건의 무게보다 큰 경우
        //(bw >= weight[n])
        else{//현재 물건을 넣은 경우와, 넣지 않은 경우 중 비교하여 최대값을 d에 메모한다.
            d[n][bw] = Math.max(
                    RecurKnapsap(bw-weight[n],n-1) + price[n],
                    RecurKnapsap(bw,n-1) );
            return d[n][bw];
        }
    }

    public static void main(String[] args) {
        int bagWeight = 8;
        int[] price = {3, 2, 5, 3};
        Knapsack01 knapsap01 = new Knapsack01(bagWeight, price);
        knapsap01.RecurKnapsap();
    }
}
