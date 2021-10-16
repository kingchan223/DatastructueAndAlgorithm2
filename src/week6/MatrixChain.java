package week6;

public class MatrixChain {
    int nOfMatrix;
    int [] p;//{p0:2, p1:3, p2:4, p3:3, p4:5, p5:3, p6:4, p7:5, p8:3, p9:2, p10:4, p11:6, p12:5, p13:4, p14:3, p15:4}
    int count;
    int [][] memo;

    public MatrixChain(int[] dimension){
        p = dimension;
        nOfMatrix = p.length-1;
        memo = new int[nOfMatrix+1][nOfMatrix+1];
    }

    void reset(){
        count = 0;
        for (int i = 0; i < nOfMatrix + 1; i++)
            for (int j = 0; j < nOfMatrix + 1; j++)
                memo[i][j] = -1;
        for (int i = 0; i <= nOfMatrix; i++)
            memo[i][i] = 0;
    }

    int getCount(){
        return count;
    }

    //행렬 Ai.부터 Aj.까지 최소비용을 구한다.
    int matrixChain(int i, int j) {
        count++;
        if(i==j) return 0;
        int min = 999999999;
        for(int k=i; k<j; k++){
            int q = matrixChain(i, k)+matrixChain(k+1, j)+p[i-1]*p[k]*p[j];
            if(q<min) min = q;
        }
        return min;
    }

    int matrixChainDP(int i, int j){
        count++;
        if(memo[i][j]>=0) return memo[i][j];
        int min = 999999999;
        for (int k = i; k < j; k++) {
            if(memo[i][k]<0) memo[i][k] = matrixChainDP(i,k);
            if(memo[k+1][j]<0) memo[k+1][j] = matrixChainDP(k+1,j);
            min = Math.min(min, memo[i][k]+memo[k+1][j]+p[i-1]*p[k]*p[j]);
        }
        return min;
    }

    public static void main(String[] args) {
        int[] dimension = {2, 3, 4, 3, 5, 3, 4, 5, 3, 2, 4, 6, 5, 4, 3, 4};
        MatrixChain mm = new MatrixChain(dimension);
        for (int i = 1; i <= dimension.length-1; i++) {
            mm.reset();
            System.out.print("Recursion : "+mm.matrixChain(1, i)+"    Count="+mm.getCount()+"  ");
            mm.reset();
            System.out.println(" ===> DP : "+mm.matrixChainDP(1, i)+"    Count="+mm.getCount());
        }
    }
}
