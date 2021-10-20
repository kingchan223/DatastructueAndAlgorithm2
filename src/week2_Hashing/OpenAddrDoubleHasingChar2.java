package week2_Hashing;
/*
* 더블 해싱 교수님 방법
* */
public class OpenAddrDoubleHasingChar2 {
    int nOfHops =0;
    double threshold = 0.99;
    char [] table;
    int tableSize;
    int mprime;
    int numberOfItems;
    public OpenAddrDoubleHasingChar2(int n) {
        tableSize = n;
        table = new char[tableSize];
        numberOfItems = 0;
        // -1 : null, -999 : deleted
        for (int i=0; i<tableSize; i++)
            table[i] = ' ';

        mprime = findPrime(tableSize);
    }

    // --- m보다작은 소수를 찾아주는 메소드 ---
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
    // -------------------------------

    //m을 적용한 hashFunction1(곱하기 방식)
    private int hashFunction1(char d) {
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * tableSize);
    }
    //m`을 적용한 hashFunction2(곱하기 방식)
    private int hashFunction2(char d) {
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * mprime);
    }

	public int hashInsert(char d){
        if(loadfactor()>=threshold) enlargeTable();
        int hashCode = hashFunction1(d);
        nOfHops = 1;
        //No Collision -> 충돌이 발상하지 않으면 hashF1만 사용한다.
        if(table[hashCode] == ' '){
            table[hashCode] = d;
            numberOfItems++;
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int nOfCollision=1;
            int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
            while(table[probeIndex]!=' ' && table[probeIndex]!='-'){
                nOfCollision++;
                nOfHops++;
                probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            table[probeIndex] = d;
            numberOfItems++;
            return nOfHops;
        }
    }

	public int hashSearch(char d){
        int hashCode = hashFunction1(d);
        nOfHops = 1;
        //No Collision
        if(table[hashCode] == d){
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int nOfCollision=1;
            int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
            while(table[probeIndex]!=' ' && table[probeIndex]!=d){
                nOfCollision++;
                nOfHops++;
                probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            if(table[probeIndex]==d) return nOfHops;//찾은 경우
            else return -nOfHops;//못 찾은 경우
        }
    }

	public int hashDelete(char d){
        int hashCode = hashFunction1(d);
        nOfHops = 1;

        if(table[hashCode] == d){
            table[hashCode]= '-';
            numberOfItems--;
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int nOfCollision=1;
            int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
            while(table[probeIndex]!=' ' && table[probeIndex]!='-'){
                nOfHops++;
                probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            if(table[probeIndex]==d){//찾은 경우
                table[probeIndex]= '-';
                numberOfItems--;
                return nOfHops;
            }
            else return -nOfHops;//못 찾은 경우
        }
    }

	private void enlargeTable(){
        char[] oldTable = table;
        int oldSize = tableSize;
        tableSize *= 2;
        table = new char[tableSize];
        for(int i=0; i<tableSize; i++){
            table[i]-=1;
        }
        for(int i=0; i<oldSize;i++){ //rehashing
            if(oldTable[i]>=0) hashInsert(oldTable[i]);
        }
    }

    public double loadfactor() {
        return (double)numberOfItems/tableSize;
    }

    public void showTable() {
        System.out.println("Current Hash Table : ");
        for (int i = 0; i<tableSize; i++)
            System.out.println(i+":"+table[i]+"  ");
        System.out.println();
    }

    public static void main(String[] args) {
        int tableSize = 17;

        char [] data = {'V', 'N', 'Y', 'Z', 'E', 'L', 'B', 'Q', 'A', 'P'};
        int dataSize = data.length;

        System.out.println("\n *** Open Addressing - Linear Probing ***");

        OpenAddrDoubleHasingChar2 myHash = new OpenAddrDoubleHasingChar2(tableSize);

        // Insert
        int sumOfSuccess = 0;
        int sumOfFailure = 0;
        int maxCount = 0;
        for (int i =0; i<dataSize; i++) {
            int count = myHash.hashInsert(data[i]);
            if (count>=0) {
                sumOfSuccess += count;
                if (count>maxCount) maxCount = count;
            }
            else
                sumOfFailure += count;
        }
        myHash.showTable();

        System.out.println("\n\n [Insert] No. of Hops : Success ="+sumOfSuccess
                + "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);
        System.out.println("\n Load Factors ="+myHash.loadfactor());

        // Search with existing data set
        sumOfSuccess = 0;
        sumOfFailure = 0;
        maxCount = 0;
        for (int i =0; i<dataSize; i++) {
            int count = myHash.hashSearch(data[i]);
            if (count>=0) {
                sumOfSuccess += count;
                if (count>maxCount) maxCount = count;
            }
            else {
                sumOfFailure += count;
                if ((-count)>maxCount) maxCount = -count;
            }
        }
        System.out.println("\n\n [Search 1] No. of Hops : Success ="+sumOfSuccess
                + "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);

        // Search with non-existing data set
        sumOfSuccess = 0;
        sumOfFailure = 0;
        maxCount = 0;
        for (int i =0; i<dataSize; i++) {
            int count = myHash.hashSearch(data[i]);
            if (count>=0) {
                sumOfSuccess += count;
                if (count>maxCount) maxCount = count;
            }
            else {
                sumOfFailure += count;
                if ((-count)>maxCount) maxCount = -count;
            }
        }
        System.out.println("\n\n [Search 2] No. of Hops : Success ="+sumOfSuccess
                + "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);

        // Delete with non-existing data set
        sumOfSuccess = 0;
        sumOfFailure = 0;
        maxCount = 0;
        for (int i =0; i<dataSize; i++) {
            int count = myHash.hashDelete(data[i]);
            if (count>=0) {
                sumOfSuccess += count;
                if (count>maxCount) maxCount = count;
            }
            else {
                sumOfFailure += count;
                if ((-count)>maxCount) maxCount = -count;
            }
        }
        System.out.println("\n\n [Delete] No. of Hops : Success ="+sumOfSuccess
                + "  Failure = "+sumOfFailure+"   Max. Hop Count = "+ maxCount);
    }
}


