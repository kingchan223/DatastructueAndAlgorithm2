package week2_Hashing;

public class Chaining {

    int nOfHops = 0;
    HashNode [] table;
    int tableSize;
    int numberOfItems;//같은 해쉬값에 속하는 노드가 몇개나 있는지?

    public Chaining(int n){
        tableSize = n;
        numberOfItems = 0;
        table = new HashNode[tableSize];
        for(int i=0; i<tableSize; i++)
            table[i] = null;//테이블안을 null로 초기화
    }

    private static class HashNode{
        int key;
        HashNode next;
        public HashNode(int k){// key = dataValue
            key = k;
            next = null;
        }
        public String toString(){
            return "-> " + key;
        }
    }

    private int hashFunction(int d) {
        // 어떤 수 x를 어떤 수 y로 나눈 나머지는 y보다 클 수 없다.
        return d%tableSize;//나머지는 데이터가 저장될 인덱스가 된다.
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

    public int hashInsert(int dataValue){
        int hashCode = hashFunction(dataValue);
        HashNode newNode = new HashNode(dataValue);
        newNode.next = table[hashCode];//newNode를 제일 앞에 저장힌다.
        table[hashCode] = newNode;//특정 인덱스에 있는 테이블 로우가 newNode를 가르키게 한다.
        numberOfItems++;//data추가
        nOfHops=1;

        return nOfHops;
    }

    public int hashSearch(int dataValue){
        int hashCode = hashFunction(dataValue);
        HashNode p = table[hashCode];
        nOfHops = 1;

        while(p!=null){
            if(p.key==dataValue)
                return nOfHops;
            else{
                nOfHops++;
                p = p.next;
            }
        }
        //못찾은 경우
        return -nOfHops;
    }

    public int hashDelete(int dataValue){
        int hashCode = hashFunction(dataValue);
        HashNode prev = table[hashCode];
        nOfHops = 1;

        if(prev==null)
            return -nOfHops;
        //맨 앞에 있는 경우
        else if(prev.key==dataValue){
            table[hashCode] = prev.next;
            numberOfItems--;
            return nOfHops;
        }
        //맨 앞에 있는게 아닌 경우
        HashNode post = prev.next;
        nOfHops++;
        while(post!=null){

            if(post.key==dataValue){
                prev.next =  post.next;//delete완료
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

    //loadFactor는 적재율. 만약 10개의 공간이 있는 테이블에 7개의 데이커가 있다면 적재율은 0.7
    public double loadFactor(){
        return ((double)numberOfItems / tableSize);
    }


    public static void main(String[] args) {
        int tableSize = 16;

        int [] data = {10, 12, 18, 20, 22, 23, 26, 27, 42, 57};
        int dataSize = data.length;

        System.out.println("\n *** Chaining ***");

        Chaining myHash = new Chaining(tableSize);
        // Insert
        int sumOfSuccess = 0;//찾은 거 합계
        int sumOfFailure = 0;//못 찾은 거 합계
        int maxCount = 0;//가장 오랜 시간이 걸린 카운트
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
        System.out.println("\n Load Factors ="+myHash.loadFactor());

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
