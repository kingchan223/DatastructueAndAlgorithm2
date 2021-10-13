package week6;

public class Pebbles {
    int[][] peb;
    int[][] memo;
    int[][] nextPattern = {
            {0,0,0},
            {2,3,0},//pattern = 1일때 그 전에는 2또는 3이 올 수 있다.
            {1,3,4},//2일때
            {1,2,0},//3일때
            {2,0,0},//4일때
    };
    int nCols;
    int count;

    public Pebbles(int[][] input){
        peb = input;
        nCols = peb[0].length;
        memo = new int[5][nCols];
        reset();
    }

    void reset(){
        count = 0;
        for (int mi = 0; mi < 5; mi++) {
            for(int mj=0; mj<nCols; mj++){
                memo[mi][mj] = -99999;
            }
        }
    }

    int getCount(){
        return count;
    }

    //p번째 패턴일때의 pep을 반환해준다.
    private int aPatternValue(int p, int n) {
        int retVal = 0;
        switch(n){
            case 1: retVal = peb[0][p];
                break;
            case 2: retVal = peb[1][p];
                break;
            case 3: retVal = peb[2][p];
                break;
            case 4: retVal = peb[0][p] + peb[2][p];
                break;
        }
        return retVal;
    }

    //메모이제이션 사용 X
    public int maxPebble(int n){
        int max = -99999;
        for (int j = 1; j <= 4; j++)
            max = Math.max(pebble(n, j), max);
        return max;
    }

    private int pebble(int n, int p){
        count ++;
        if(n==1) return aPatternValue(p,n);
        else{
            int max = -99999;
            int k=0;
            while(k<3 && nextPattern[p][k]!=0){//가능한 패턴을 모두 적용해본다.
                int q = nextPattern[p][k];
                max = Math.max(pebble(n-1, q), max);
                k++;
            }
            return aPatternValue(p,n)+max;
        }
    }


    //메모이제이션 사용 O
    public int maxPebbleMemo(int n){
        int max = -99999;
        for(int p=1; p<=4; p++){//n번째라인의 memo에 각 P1,P2,P3,P4를 적용했을 때의 값들을 찾아낸다.
            max = Math.max(pebbleMemo(n, p), max);
        }
        return max;//그 중에서도 가장 큰 값을 리턴
    }

    //N번째 열에 P번째 패턴을 적용했을 때 앞의 N-1열과의 값을 고려하여
    //가장 가장 큰 조합의 결과를 반환해준다.
    private int pebbleMemo(int  n, int p) {
        count++;
        if(memo[p][n]> -99999)
            return memo[p][n];
        if(n==1){//1번째 열이라면 N-1번째까지 고려할 필요가 없다.
            memo[p][n] = aPatternValue(p,n);
            return memo[p][n];
        }
        else{//1번째가 이상이라면 N-1번째 열도 고려해야한다.
            int max = -99999;
            int k=0;//nextPattern 안에서의 인덱스 역할
            while (k < 3 && nextPattern[p][k] != 0) {//가능한 패턴들확인
                int q = nextPattern[p][k];
                if(memo[q][n-1]==-99999)//memo 안되었다면 비교를 하고, memo가 되어있다면 바로 나온다.
                    memo[q][n-1] = pebbleMemo(n-1, q);
                max = Math.max(memo[q][n-1],max);//memo해둔 값을 바탕으로 비교한다.
                k++;
            }
            //N번째 열에 P1을 적용할 시에 가장 큰 memo[N-1]을 더해서 memo해준다.
            memo[p][n] = max + aPatternValue(p,n);
            return memo[p][n];
        }
    }

    public static void main(String[] args) {
        int[][] input = {{0,6,7,12,-5,5,3,11,3,7,-2,5,4},
                         {0,-8,10,14,9,7,13,8,5,6,1,3,9},
                         {0,11,12,7,4,8,-2,9,4,-4,3,7,10}};
        Pebbles myPeb = new Pebbles(input);
        for(int i=1; i<input[0].length; i++){
            myPeb.reset();
            System.out.print(">>> "+i+" : [Recursion] "+myPeb.maxPebble(i)+", Count = "+myPeb.getCount()+" ");
            myPeb.reset();
            System.out.println("===> [DP] "+myPeb.maxPebbleMemo(i)+", Count = "+myPeb.getCount());
        }
    }
}
