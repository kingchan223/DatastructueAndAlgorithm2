package week2_Hashing;

public class OpenAddrDoubleHasing {
    int nOfHops =0;
    double threshold = 0.99;

    int [] table;
    int tableSize;
    int m2;
    int numberOfItems;

    public OpenAddrDoubleHasing(int n) {
        tableSize = n;
        table = new int[tableSize];
        numberOfItems = 0;
        m2 = findDemical(n);
        // -1 : null, -999 : deleted
        for (int i=0; i<tableSize; i++)
            table[i]=-1;
    }

    // 보조 해싱함수의 m'로 사용하기 위해 table사이즈 보다 작은 소수를 구하기
    public static int findDemical(int tableSize){
        int[] prime = new int[tableSize]; // 소수 저장
        int pn=0; // 소수의 개수
        boolean[] check = new boolean[tableSize+1];
        for (int i=2; i<=tableSize; i++) {
            if (!check[i]) {
                prime[pn++] = i;
                for (int j = i*i; j<=tableSize; j+=i) {
                    check[j] = true;
                }
            }
        }
        int result = 0;
        for(int i=tableSize-1; i>=2; i--){
            if(!check[i]){
                result = i;
                break;
            }
        }
        return result;
    }

    private int firstHashFunction(int d) {
        return d%tableSize;
    }

    private int secondHashFunction(int i, int d) {
        return i*(d%m2);//나머지는 데이터가 저장될 인덱스가 된다.
    }

    private int hashFunction(int i, int d){
        return (firstHashFunction(d) + secondHashFunction(i,d)) % tableSize;
    }

	public int hashInsert(int d){
        if(loadfactor()>=threshold) enlargeTable();
        int hashCode = hashFunction(0, d);
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
            int i=1;
            int probeIndex = hashFunction(i, d);
            while(table[probeIndex]!=-1 && table[probeIndex]!=999){
                nOfHops++;
                probeIndex = hashFunction(i++, d);
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            table[probeIndex] = d;
            numberOfItems++;
            return nOfHops;
        }
    }


	public int hashSearch(int d){
        int hashCode = hashFunction(0, d);
        nOfHops = 1;
        //No Collision
        if(table[hashCode] == d){
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int i=1;
            int probeIndex = hashFunction(i, d);
            while(table[probeIndex]!=-1 && table[probeIndex]!=d){
                nOfHops++;
                probeIndex = hashFunction(i++, d);
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            if(table[probeIndex]==d) return nOfHops;//찾은 경우
            else return -nOfHops;//못 찾은 경우
        }
    }


	public int hashDelete(int d){
        int hashCode = hashFunction(0, d);
        nOfHops = 1;

        if(table[hashCode] == d){
            table[hashCode]= -999;
            numberOfItems--;
            return nOfHops;
        }
        //Collision
        else{
            nOfHops++;
            int i=1;
            int probeIndex = hashFunction(i, d);
            while(table[probeIndex]!=-1 && table[probeIndex]!=d){
                nOfHops++;
                probeIndex = hashFunction(i++, d);
                if(probeIndex==hashCode)//한 바퀴를 돌아버린 경우
                    return 0;
            }
            if(table[probeIndex]==d){//찾은 경우
                table[hashCode]= -999;
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

        OpenAddrDoubleHasing myHash = new OpenAddrDoubleHasing(tableSize);

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


