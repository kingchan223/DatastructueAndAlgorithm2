package week2_Hashing;

public class Test {
    int tableSize;
    int mprime;

    public Test(int tableSize, int mprime) {
        this.tableSize = tableSize;
        this.mprime = mprime;
    }

    private int findPrime(int m) {
        for(int i=m-1; i>(m/2); i--){
            if(isPrime(i)){
                System.out.println("** M-prime = "+i);
                return i;
            }
        }
        return 0;
    }

    private boolean isPrime(int i) {
        for(int j=2; j<(i/2); j++){
            float x = (float) i/j;
            int y=j;
            if(x==y) return false;
        }
        return true;
    }

    private int hashFunction1(int d) {
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * tableSize);
    }

    private int hashFunction2(int d) {
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * mprime);
    }

    public static void main(String[] args) {
        Test test = new Test(13, 11);

        int i = test.hashFunction1(10);
        int i1 = (i +1*test.hashFunction2(10));
        int i2 = (i +2*test.hashFunction2(10));
        int i3 = (i +3*test.hashFunction2(10));
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);
//        int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
    }
}
