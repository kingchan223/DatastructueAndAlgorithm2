package week2_Hashing;
/*
* 더블 해싱 교수님 방법
* */
public class OpenAddrDoubleHasing2 {
    int nOfHops =0;
    double threshold = 0.99;
    int [] table;
    int tableSize;
    int mprime;
    int numberOfItems;
    public OpenAddrDoubleHasing2(int n) {
        tableSize = n;
        table = new int[tableSize];
        numberOfItems = 0;
        // -1 : null, -999 : deleted
        for (int i=0; i<tableSize; i++)
            table[i]=-1;

        mprime = findPrime(tableSize);
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
        //곰셉방법
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * tableSize);
    }

    private int hashFunction2(int d) {
        double temp = (double)d * 0.6180339887;
        double res = temp - Math.floor(temp);
        return (int) (res * mprime);
    }

	public int hashInsert(int d){
        if(loadfactor()>=threshold) enlargeTable();
        int hashCode = hashFunction1(d);
        nOfHops = 1;
        //No Collision
        if(table[hashCode] == -1){
            table[hashCode] = d;
            numberOfItems++;
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int nOfCollision=1;
            int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
            while(table[probeIndex]!=-1 && table[probeIndex]!=999){
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


	public int hashSearch(int d){
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
            while(table[probeIndex]!=-1 && table[probeIndex]!=d){
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


	public int hashDelete(int d){
        int hashCode = hashFunction1(d);
        nOfHops = 1;

        if(table[hashCode] == d){
            table[hashCode]= -999;
            numberOfItems--;
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int nOfCollision=1;
            int probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
            while(table[probeIndex]!=-1 && table[probeIndex]!=d){
                nOfHops++;
                probeIndex = (hashCode+nOfCollision*hashFunction2(d))%tableSize;
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            if(table[probeIndex]==d){//찾은 경우
                table[probeIndex]= -999;
                numberOfItems--;
                return nOfHops;
            }
            else return -nOfHops;//못 찾은 경우
        }
    }

	private void enlargeTable(){
        int[] oldTable = table;
        int oldSize = tableSize;
        tableSize *= 2;
        table = new int[tableSize];
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
            System.out.print(table[i]+"  ");
        System.out.println();
    }


    public static void main(String[] args) {
        int tableSize = 17;

        int [] data = {10, 12, 18, 20, 22, 23, 26, 27, 42, 57};
        int dataSize = data.length;

        System.out.println("\n *** Open Addressing - Linear Probing ***");

        OpenAddrDoubleHasing2 myHash = new OpenAddrDoubleHasing2(tableSize);

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
            int count = myHash.hashSearch(data[i]+1);
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
            int count = myHash.hashDelete(data[i]+1);
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


