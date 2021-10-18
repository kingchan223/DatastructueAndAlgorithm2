package week7;

//3*N의 면적에, 크기 1*2인 타일을 놓는 방법
public class Tile3NdivoneBYtwo {
    public int n;
    public long memo[];

    public Tile3NdivoneBYtwo(int n) {
        this.n = n;
        memo = new long[n + 1];
    }

    public void RecurTail(){
        System.out.println(RecurTail(n));
    }

    public long RecurTail(int n){
        if(n<0) return 0;
        if(n==0) return 0;
        if(n==1) return 0;
        if(n==2) return 2;
        if(memo[n]>0) return memo[n];
        else{
            memo[n] = 3*RecurTail(n - 2);
            for(int i=4; i<=n; i*=2){
                memo[n] += 2*RecurTail(n - i);
            }
            return  memo[n];
        }
    }

    public static void main(String[] args) {
        Tile3NdivoneBYtwo tile = new Tile3NdivoneBYtwo(10);
        tile.RecurTail();
    }
}
