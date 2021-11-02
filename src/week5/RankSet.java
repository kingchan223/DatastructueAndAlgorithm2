package week5;

import java.util.ArrayDeque;
import java.util.Deque;

public class RankSet {
    int key;
    int rank;
    RankSet parent;

    public RankSet(int key) {
        this.key = key;
        this.rank = 0;
        this.parent = null;
    }

    public RankSet makeSet(int k){//자기 자신을 부모로 하는 set을 만든다.
        key = k;
        rank = 0;
        parent = this;
        return this;
    }

    public boolean equals(RankSet other){
        if(key==other.key) return true;
        else return false;
    }

    public void showParent(){
        RankSet p = this;
        System.out.println(p.toString());
        while(!p.equals(p.parent)){
            p = p.parent;
            System.out.println(" ---> "+p.toString());
        }
    }

    public String toString(){
        return "" + key + "[" + parent.key + "," + rank+"]";
    }

    //경로압축을 사용한 findSet
    public RankSet findSet(RankSet node){
        RankSet p = node;
        Deque<RankSet> q = new ArrayDeque<>();
        while(!equals(p.parent)){
            q.add(p);
            p = p.parent;
        }
        while(!q.isEmpty()){
            q.poll().parent = p;
        }
        return p;
    }

    public RankSet union(RankSet other) {
        RankSet u = findSet(this);
        RankSet v = findSet(other);
        if (u.rank > v.rank) {
            v.parent = u;
            return u;
        }
        else if(v.rank > u.rank){
            u.parent = v;
            return v;
        }
        else{
            u.parent = v;
            v.rank ++;
            return u;
        }
    }

    public static void main(String[] args) {
        //data={ 0, 1, 2, 3, 4, 5, 6 }
        int dataSize =  7;
        RankSet[] element = new RankSet[dataSize];

        for(int i=0; i<dataSize; i++){
            element[i] = new RankSet(i);
            element[i].makeSet(i);
            System.out.println(element[i].toString());
        }

        System.out.println("Union 0 & 1 ==> ");
        RankSet p = element[0].union(element[1]);
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

