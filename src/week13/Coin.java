package week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Coin {
        public static final int maxC = 999_999_999;
        public int[] coins;
        public int goalWon;
        public int nOfCoin;
        int[] dp;

    public Coin(int[] coins, int goalWon, int nOfCoin) {
        this.coins = coins;
        this.goalWon = goalWon;
        this.nOfCoin = nOfCoin;
        dp = new int[goalWon+1];
        Arrays.fill(dp, maxC);
    }

    private void init() {
        for(int coin : coins){
            if(coin <= goalWon)
                dp[coin] = 1;
        }
    }

    private void algo() {
        init();
        for(int nWon=1; nWon<= goalWon; nWon++)
            for(int coin : coins)// 1 2 5
                if(nWon - coin > 0)
                    if(dp[nWon-coin]+1 < dp[nWon]) dp[nWon] = dp[nWon-coin]+1;

        showResult();
    }

    private void showResult() {
        System.out.println(dp[goalWon]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] coins = new int[k];
        for(int i=0; i<n; i++) coins[i] = Integer.parseInt(br.readLine().trim());

        Coin coin = new Coin(coins, k, n);
        coin.algo();
    }

}
