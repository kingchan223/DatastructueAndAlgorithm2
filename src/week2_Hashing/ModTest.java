package week2_Hashing;

public class ModTest {

    public static void hashFunction(int d) {
        double temp = (double)d * 0.6180339887;
        System.out.println("temp = " + temp);
        double res = temp - Math.floor(temp);
        System.out.println("res = " + res);
        System.out.println("(int)(res*15) = " + (int)(res*15));
    }

    public static void main(String[] args) {
        int[] array = new int[]{ 3, 30, 14, 6, 23, 19, 5, 22, 16, 25, 61};
        for (int i : array) {
           hashFunction(i);
        }
    }
}
