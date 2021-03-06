package week7;

import java.io.IOException;

//최대 길이 문자를 반환하는 매소드
public class LCS2 {

    int len1;
    int len2;
    char[] char1;
    char[] char2;
    String[][] memo;

    public LCS2(String str1, String str2) {
        init(str1, str2);
//        for (char c : char1) {
//            System.out.print(c+" ");
//        }
//        System.out.println();
//        for (char c : char2) {
//            System.out.print(c+" ");
//        }
    }

    private void init(String str1, String str2) {
        this.len1 = str1.length();
        this.len2 = str2.length();
        this.char1 = new char[len1+1];
        this.char2 = new char[len2+1];

        for (int i = 0; i < len1; i++)
            char1[i+1] = str1.charAt(i);

        for (int i = 0; i < len2; i++)
            char2[i+1] = str2.charAt(i);

        memo = new String[len1 + 1][len2 + 1];
        for (int i = 0; i < len1 + 1; i++)
            for (int j = 0; j < len2 + 1; j++)
                memo[i][j] = "";
    }

    public void RecurLCS(){
        RecurLCS(len1, len2);
        System.out.println(memo[len1][len2]);
    }

    public String RecurLCS(int n, int m){
        if(n==0&&m==0) return "";
        if(n<0||m<0) return "";
        if(!memo[n][m].equals("")){
            return memo[n][m];
        }
        if(char1[n]==char2[m]) {
            memo[n][m] = RecurLCS(n-1,m-1)+char1[n];
            return memo[n][m];
        }
        else {
            if(RecurLCS(n-1,m).length() >= RecurLCS(n,m-1).length()){
                memo[n][m] = RecurLCS(n-1,m);
            }
            else{
                memo[n][m] = RecurLCS(n,m-1);
            }
            return memo[n][m];
        }
    }

    public static void main(String[] args){
        String str1 = "abaccdabbbca";
        String str2 = "acadacba";
        LCS2 lcs = new LCS2(str1, str2);
        lcs.RecurLCS();
    }
}
