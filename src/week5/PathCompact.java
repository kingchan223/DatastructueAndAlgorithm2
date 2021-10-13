package week5;

import java.util.ArrayDeque;
import java.util.Deque;

public class PathCompact {
    int key;
    int rank;
    PathCompact parent;

    public PathCompact() {
        this.key = -1;
        this.rank = 0;
        this.parent = null;
    }

    public boolean equals(PathCompact other){
        if(key==other.key)
            return true;
        else
            return false;
    }

    public String toString(){
        return "" + key + "[" + parent.key + "," + rank+"]";
    }

    public void showParent(){
        PathCompact p = this;
        System.out.println(p.toString());
        while(!p.equals(p.parent)){
            p = p.parent;
            System.out.println(" ---> "+p.toString());
        }
    }

    public PathCompact makeSet(int k){//자기 자신을 부모로 하는 set을 만든다.
        key = k;
        rank = 0;
        parent = this;
        return this;
    }
    public PathCompact findSet(PathCompact node){
        //대표노드를 부모로 보지 않는 노드를 저장하는 que
        Deque<PathCompact> que = new ArrayDeque<PathCompact>();
        PathCompact p = node;
        while(!p.equals(p.parent)){
            que.add(p);
            p = p.parent;
        }
        while(!que.isEmpty()){
            que.poll().parent = p;
        }
        return p;
    }
    public PathCompact union(PathCompact other){
        PathCompact u = findSet(this);
        PathCompact v = findSet(other);

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
        PathCompact[] element = new PathCompact[dataSize];

        for(int i=0; i<dataSize; i++){
            element[i] = new PathCompact();
            element[i].makeSet(i);
            System.out.println(element[i].toString());
        }

        System.out.println("Union 0 & 1 ==> ");
        PathCompact p = element[0].union(element[1]);
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

