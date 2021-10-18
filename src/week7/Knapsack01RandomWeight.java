package week7;

public class Knapsack01RandomWeight {
    public int bagWeight;
    public int[] price;
    public int[] weight;
    public int d[][];

    //맨 앞의 물건의 무게는 1. 뒤로갈수록 1씩 증가한다고 가정한 알고리즘.
    public Knapsack01RandomWeight(int bagWeight, int[] price, int[] weight) {
        this.bagWeight = bagWeight;
        init(price, weight);
        this.d = new int[price.length+1][bagWeight+1];
//        d[1][1] = this.price[1];
    }

    //{3, 2, 5, 3}
    public void init(int[] price, int[] weight){
        this.price = new int[price.length + 1];
        this.weight = new int[weight.length + 1];
        for (int i = 1; i < price.length; i++) {
            this.price[i] = price[i-1];
            this.weight[i] = price[i-1];
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
        if(bw < weight[n]){//다른 무게를 가진 물건으로 시도한다.
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
        int[] weight = {2, 1, 3, 4};
        Knapsack01RandomWeight knapsap01 = new Knapsack01RandomWeight(bagWeight, price, weight);
        knapsap01.RecurKnapsap();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 8; j++) {
                System.out.print(knapsap01.d[i][j]+" ");
            }
            System.out.println();
        }
    }
}
