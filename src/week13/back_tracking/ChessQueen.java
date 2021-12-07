package week13.back_tracking;



public class ChessQueen {
    int N;
    int[] index;

    public ChessQueen(int n) {
        N = n;
        index = new int[n];
    }

    private void find() {
        find(0);
    }

    private void find(int level) {
        if(level == N){
            for (int i = 0; i < N; i++)
                System.out.print(index[i]+" - ");
            System.out.println();
        }
        else{
            for(int i = 0; i < N; i++){
                index[level] = i;
                if(isPossible(level)) find(level+1);
            }
        }
    }

    private boolean isPossible(int level) {
        for(int i=0;i<level;i++){
            if(index[i]==index[level] || Math.abs(level-i)== Math.abs(index[level]-index[i])){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        ChessQueen chessQueen = new ChessQueen(10);
        chessQueen.find();

    }
}
