package week2_other;

public class Chaining {

    int nOfHops=0;

    private class HashNode {
        int key;
        HashNode next;
        public HashNode(int k) {
            key=k;
            next=null;
        }
        public String toString() {
            return "->"+key;
        }
    }

    HashNode [] table;
    int tableSize;
    int numberOfItems;

    public Chaining(int n) {
        tableSize = n;
        numberOfItems=0;
        table = new HashNode[tableSize];
        for (int i=0; i<tableSize; i++)
            table[i]=null;
    }

    private int hashFunction(int d) {
        return d%tableSize;
    }

	public int hashInsert(int d){
        int hashCode = hashFunction(d);
       HashNode newNode = new HashNode(d);
        newNode.next = table[hashCode];//newNode를 제일 앞에 저장힌다.
        table[hashCode] = newNode;//newNode가 원래 맨앞에 있던 노드를 가르키게 한다.
        numberOfItems++;
        nOfHops=1;
        return nOfHops;
    }

	public int hashSearch(int d){
        int hashCode = hashFunction(d);
        HashNode p = table[hashCode];
        nOfHops = 1;
        while(p!=null){
            if(p.key==d)
                return nOfHops;
            else{
                nOfHops++;
                p = p.next;
            }
        }
        //못찾은 경우
        return -nOfHops;
    }

	public int hashDelete(int d){
        int hashCode = hashFunction(d);
        HashNode prev = table[hashCode];
        nOfHops = 1;

        if(prev==null) return -nOfHops;
            //맨 앞에 있는 경우
        else if(prev.key==d){
            table[hashCode] = prev.next;
            numberOfItems--;
            return nOfHops;
        }
        //맨 앞에 있는게 아닌 경우
        HashNode post = prev.next;
        nOfHops++;
        while(post!=null){
            if(post.key==d){
                prev.next = post.next;//delete완료
                numberOfItems --;
                return nOfHops;
            }
            else{//prev, post둘다 한 칸씩 뒤로
                prev = post;
                post = prev.next;
                nOfHops++;
            }
        }
        return -nOfHops;
    }

	public double loadfactor(){
        return ((double)numberOfItems / tableSize);
    }

    public void showTable() {
        System.out.println("\n\n<< Current Table Status >>");
        for (int i=0;i<tableSize; i++) {
            HashNode p = table[i];
            System.out.print("\n "+i+" : ");
            while(p!=null) {
                System.out.print(p.toString());
                p=p.next;
            }
        }
    }


    public static void main(String[] args) {
        int tableSize = 16;

        int [] data = {10, 12, 18, 20, 22, 23, 26, 27, 42, 57};
        int dataSize = data.length;

        System.out.println("\n *** Chaining ***");

        Chaining myHash = new Chaining(tableSize);
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
