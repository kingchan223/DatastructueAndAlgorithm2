package week5;

public class DSet {
    int key;
    int rank;
    DSet parent;

    public DSet() {
        this.key = -1;
        this.rank = -1;
        this.parent = null;
    }

    public boolean equals(DSet other){
        if(key==other.key)
            return true;
        else
            return false;
    }

    public String toString(){
        return "" + key + "[" + parent.key + "," + rank+"]";
    }

    public void showParent(){
        DSet p = this;
        System.out.println(p.toString());
        while(!p.equals(p.parent)){
            p = p.parent;
            System.out.println(" ---> "+p.toString());
        }
    }

    public DSet makeSet(int k){//자기 자신을 부모로 하는 set을 만든다.
        key = k;
        rank = 0;
        parent = this;
        return this;
    }

    //node가 속한 set의 대표부모노드 반환
    public DSet findSet(DSet node){
        DSet p = node;
        while(!p.equals(p.parent))
            p = p.parent;
        return p;
    }

    public DSet union(DSet other){
        DSet u = findSet(this);
        DSet v = findSet(other);

        if(u.rank>v.rank){
            v.parent = u;
            return u;
        }
        else if(v.rank> u.rank){
            u.parent = v;
            return v;
        }
        else{//랭크가 같은 경우
            v.parent = u;
            u.rank++;
            return u;
        }
    }

    public static void main(String[] args) {

        //data={ 0, 1, 2, 3, 4, 5, 6 }
        int dataSize =  7;
        DSet[] element = new DSet[dataSize];

        for(int i=0; i<dataSize; i++){
            element[i] = new DSet();
            element[i].makeSet(i);
            System.out.println(element[i].toString());
        }

        System.out.println("Union 0 & 1 ==> ");
        DSet p = element[0].union(element[1]);
        System.out.println(p.toString());

        System.out.println("Union 2 & 1 ==> ");
        p = element[2].union(element[1]);
        System.out.println(p.toString());

        System.out.println("Union 3 & 4 ==> ");
        p = element[3].union(element[4]);
        System.out.println(p.toString());

        System.out.println("Union 2 & 4 ==> ");
        p = element[2].union(element[4]);
        System.out.println(p.toString());

        for(int i=0; i<dataSize; i++){
            element[i].showParent();
        }
    }
}
