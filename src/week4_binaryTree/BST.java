package week4_binaryTree;

import java.util.ArrayDeque;
import java.util.Deque;

public class BST {
    Node root;
    int numNode;
    public BST() {
        root=null;
        numNode = 0;
    }

    public void insert(char x) {//사용하는 입장에서 노드를 반환하거나 하면 안되기 때문에.이와 같은 사용자용 메서드를 하나 만든다.
        insert(x, null, root);//null = parent of root. 부모부터 차례대로 추가해줄 것이다.
    }

    //재귀적으로 insert
    protected Node insert(char x, Node parentOfr, Node r) {//데이터 x를 추가한다.
        if (r==null) {//r이 null 이라면
            // 1.아직 비어있는 트리(r==root)
            // 2.맨 아래에 도달해서 parentOfr 이 leaf node 이고, 그의 자식인 r은 null 인 경우.
            if(parentOfr==null){// 그 중 1.아직 비어있는 트리라면(r==root)
                root = insertNode(x, null);//root 로 추가한다.
                return root;
            }
            else{ // 2.leaf 에 도달해서 r이 null 이고 parentOfr 이 현재 leaf 라면
                if(x < parentOfr.key) {//추가될 노트의 key 인 x가 부모보다 작다면
                    parentOfr.left = insertNode(x, parentOfr);//왼쪽에 붙여주고
                    return parentOfr.left;
                }
                else if(x > parentOfr.key){//추가될 노드의 key 인 x가 부모보다 크다면
                    parentOfr.right = insertNode(x, parentOfr);//오른쪽에 붙여준다.
                    return parentOfr.right;
                }//같다면 추가할 수 없다.
                else return null;
            }
        }
        else{//아직 leaf 까지 도달하지 못했다면 recursive 하게 타고 내려간다.
            if(x < r.key) return insert(x, r, r.left);
            else if(x > r.key) return insert(x, r, r.right);
            else return null;//key 가 같은 경우라면 트리에 들어올 수 없으므로 null 반환
        }
    }

    //parent 의 아래에 노드를 추가하고, 추가된 노드를 반환.
    private Node insertNode(char x, Node parent) {
        Node newNode = new Node(x);
        newNode.parent = parent;
        numNode++;
        return newNode;
    }

    public void search(char x) {
        if(search(root, x)==null){
            System.out.println("Search에 실패하였습니다.");
        }
        else{
            System.out.println("Search에 성공하였습니다.");
        }
    }

    //recursive하게 노드를 찾아가는 메서드
    public Node search(Node startNode, char x) {
        Node p = startNode;
        if (p==null||p.key==x) return p;//null을 반환하면 못찾은거임
        else if (x<p.key) return search(p.left, x);
        else return search(p.right, x);
    }

    //삭제한 노드에 새로 위치한 노드를 반환
    public Node delete(char x) {
        Node r = search(root, x);//삭제하려는 노드 r
        if(r!=null){//r이 null이 아니면 삭제.
            numNode--;
            return delete(r);
        }
        else return null;//r이 Null이라면 search에서 실패.
    }

    //해당 delete메소드는 삭제한 노드의 부모를 반환한다.
    private Node delete(Node r) {
        if (r.parent == null) {//삭제하려는 노드가 root 라면
            root = deleteNode(r);
            return null;//root의 부모는 null이므로, null반환
        } else if (r == r.parent.left) {//삭제하려는 노드가 left 라면
            r.parent.left = deleteNode(r);//삭제
            if(r.parent.left!=null){//삭제한 노드가 leaf가 아니였다면.
                return r.parent.left;//null 반환
            }
            return r.parent;//leaf가 아니였다면 부모반환
        } else {//삭제하려는 노드가 right 라면
            r.parent.right = deleteNode(r);//삭제
            if(r.parent.right!=null){
                return r.parent.right;//삭제한 노드가 leaf 였다면. null반환
            }
            return r.parent;//leaf가 아니였다면 부모반환
        }
    }

    //삭제된 노드의 자리를 차지하게 되는 노드를 반환한다.
    private Node deleteNode(Node r) {
        // case 1 : no child
        if (r.left==null && r.right==null) {
            return null;//이때는 r이 leaf이므로 null을 반환
        }
        // case 2 : 1 child
        else if (r.left==null && r.right!=null) {//삭제될 노드r의 right가 있다면
            r.right.parent = r.parent;//삭제될 노드 r의 right가 r의 부모를 보게 만든다.
            return r.right;//그 위치를 차지하는 r의 right를 반환
        }
        else if(r.left!=null && r.right==null) {//삭제될 노드r의 left가 있다면
            r.left.parent = r.parent;//r의 left가 r의 부모를 가지게 만들고
            return r.left;//r의 left반환
        }
        // case3: 2 child
        else{//predecessor의 key를 삭제노드 r에 복사한 후, predecessor를 삭제하는 방법
            Node s = predecessor(r);
            r.key = s.key;
            if(s == s.parent.left){
                s.parent.left = s.right;
            }
            else{//s == s.parent.left
                s.parent.right = s.right;
            }
            return s.parent;
        }
    }

    private Node predecessor(Node v) {
        if (v==null)
            return null;
        Node p=v.left;
        while(p.right!=null)
            p=p.right;
        return p;
    }

    private Node sccessor(Node v) {
        if (v==null)
            return null;
        Node p=v.right;
        while(p.left!=null)
            p=p.left;
        return p;
    }

    // TODO
    //level-order 출력
    public void showTree() {
        if(root==null)
            return;
        Deque<Node> que = new ArrayDeque<>();
        que.add(root);
        int depthLevel = 0;
        //que에 부모 노드를 담고, temp에 옮긴 후 temp에서 poll()을 사용해 출력한 뒤,
        //poll()로 반환된 노드의 lefr, right를 que담는 작업을 반복한다.
        while (que.peek()!=null) {
            Deque<Node> temp = new ArrayDeque<>();
            System.out.print("Depth-level "+depthLevel+" : ");
            while(que.peek()!=null){
                temp.add(que.poll());
            }
            while(temp.peek()!=null){
                Node e = temp.poll();
                System.out.print(e.toString()+" ");
                if(e.left!=null) que.add(e.left);
                if(e.right!=null) que.add(e.right);
            }
            System.out.println();
            depthLevel++;
        }
    }

    public void showTreeRecur() {
        Deque<Node> temp = new ArrayDeque<>();
        temp.add(root);
        showTreeRecur (temp);
    }

    public void showTreeRecur(Deque<Node> temp) {
        if(temp.peek()==null) return;

        Deque<Node> temp2 = new ArrayDeque<>();
        while(temp.peek() != null) {
            Node r = temp.poll();
            if(r.left!=null) temp2.add(r.left);
            if(r.right!=null) temp2.add(r.right);
            System.out.print(r +" ");
        }
        System.out.println();
        showTreeRecur(temp2);
    }

    private String toString(Node t) {
        return inorder(t);
    }

    private String inorder(Node t) {
        if (t==null)
            return "";
        else
            return inorder(t.left)+" "+t.toString()+" "+inorder(t.right);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int height() {//root를 기준으로한 height
        return height(root);
    }

    public int height(Node r){//노드 r을 기준으로 height
        if(r == null) return -1;
        return 1 + Math.max(height(r.left), height(r.right));
    }

    public int IPL(){//root를 기준으로 IPL
        int count = 0;
        return IPLCount(root, count);
    }

    private int IPLCount(Node r, int count) {
        if(r==null) return count;
        else {
            count++;
            int lCount = IPLCount(r.left, count);
            int rCount = IPLCount(r.right, count);
            return count + lCount + rCount;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        char [] key = {'M','Y','U','N','G','I','S','W'};
        BST bt = new BST();

        for (int i=0;i<key.length;i++) {
            bt.insert(key[i]);
            bt.showTree();
        }
        System.out.print("\nTree Created :");
        bt.showTree();

        bt.delete('S');
        System.out.print("\nAfter deleting 'S' :");
        bt.showTree();
        bt.delete('G');
        System.out.print("\nAfter deleting 'G' :");
        bt.showTree();
        bt.delete('U');
        System.out.print("\nAfter deleting 'U' :");
        System.out.println();
        System.out.println("=======================");
        bt.showTree();
        System.out.println("=======================");
        bt.showTreeRecur();

    }

    /* Node */
    public class Node {
        char key;
        Node left;
        Node right;
        Node parent;

        public Node(char key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node(char key, Node left, Node right, Node parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public String toString() {
            String retVal = "";
            return retVal + key + "(" + height(this) + ")";
        }
    }

}
