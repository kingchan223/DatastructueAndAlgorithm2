package week7;

import java.io.IOException;


public class LCS {

    int len1;
    int len2;
    char[] char1;
    char[] char2;
    int[][] memo;

    public LCS(String str1, String str2) {
        init(str1, str2);
    }
    //초기화
    private void init(String str1, String str2) {
        this.len1 = str1.length();
        this.len2 = str2.length();
        this.char1 = new char[len1+1];
        this.char2 = new char[len2+1];

        for (int i = 0; i < len1; i++)

            char1[i+1] = str1.charAt(i);

        for (int i = 0; i < len2; i++)
            char2[i+1] = str2.charAt(i);

        memo = new int[len1 + 1][len2 + 1];
        for (int i = 0; i < len1 + 1; i++)
            for (int j = 0; j < len2 + 1; j++)
                memo[i][j] = -1;
    }

    public void RecurLCS(){
        RecurLCS(len1, len2);
        System.out.println(memo[len1][len2]);
    }

    public int RecurLCS(int n, int m){
        if(n==0&&m==0) return 0;
        if(n<0||m<0) return -1;
        if(memo[n][m]!=-1) return memo[n][m];
        if(char1[n]==char2[m]) {
            memo[n][m] = RecurLCS(n-1,m-1)+1;
            return memo[n][m];
        }
        else {
            memo[n][m] = Math.max(RecurLCS(n-1,m), RecurLCS(n,m-1));
            return memo[n][m];
        }
    }

    public static void main(String[] args) throws IOException {
        String str1 = "ababccd";
        String str2 = "abcd";
        LCS lcs = new LCS(str1, str2);
        lcs.RecurLCS();
    }
}
