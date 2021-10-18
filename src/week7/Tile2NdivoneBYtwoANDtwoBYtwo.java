package week7;

//2*N의 면적에, 크기 1*2, 2*2인 타일을 놓는 방법
public class Tile2NdivoneBYtwoANDtwoBYtwo {
    public int n;
    public long memo[];

    public Tile2NdivoneBYtwoANDtwoBYtwo(int n) {
        this.n = n;
        memo = new long[n + 1];
    }

    public void RecurTail(){
        System.out.println(RecurTail(n));
    }

    public long RecurTail(int n){
        if(n==0) return 0;
        if(n==1) return 1;
        if(n==2) return 3;
        if(memo[n]>0) return memo[n];
        else{
            memo[n] = RecurTail(n - 1) + 2*RecurTail(n - 2);
            return  memo[n];
        }
    }

    public static void main(String[] args) {
        Tile2NdivoneBYtwoANDtwoBYtwo tile = new Tile2NdivoneBYtwoANDtwoBYtwo(5);
        tile.RecurTail();
    }
}
