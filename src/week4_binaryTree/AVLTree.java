package week4_binaryTree;

public class AVLTree extends BST {

    public AVLTree() {
        super();
    }

    public void insert(char c){
        Node r = insert(c, null, root);
        //find x
        Node p = r.parent;
        while(p!=null){
            if(!isBalanced(p))
                break;
            p=p.parent;//balance가 맞으면 위로 올라간다.
        }
        Node x = p;
        Node y = null;
        if(x!=null){
            if(c<x.key){// c가 x.key보다 작다는 것은 왼쪽에 있다는 것임.
                y = x.left;
                if(c<y.key){// LL imbalance
                    rotateRight(x);
                }
                else{// LR imbalance
                    rotateLeft(y);
                    rotateRight(x);
                }
            }else{
                y = x.right;
                if(c>y.key){// RR imbalance
                    rotateLeft(x);
                }
                else{// RL imbalance
                    rotateRight(y);
                    rotateLeft(x);
                }
            }
        }
    }

    private Node rotateLeft(Node x) {//imbalance가 발생한 노드
        Node y = x.right;
        y.parent = x.parent;
        if (y.parent == null) {//rotate를 시도한 x가 root였을 시에
            root = y;
        }
        else{
            if(x==x.parent.left) x.parent.left = y;
            else x.parent.right = y;
        }
        x.parent = y;
        x.right = y.left;
        if(y.left!=null) y.left.parent = x;
        y.left = x;
        return y;//정점 반환
    }

    private Node rotateRight(Node x) {//imbalance가 발생한 노드
        Node y = x.left;
        y.parent = x.parent;
        if (y.parent == null) {//rotate를 시도한 x가 root였을 시에
            root = y;
        }
        else{
            if(x==x.parent.left) x.parent.left = y;
            else x.parent.right = y;
        }
        x.parent = y;
        x.left = y.right;
        if(y.right!=null) y.right.parent = x;
        y.right = x;
        return y;//정점 반환
    }

    private boolean isBalanced(Node p) {
        if(p==null)
            return true;
        if(Math.abs(height(p.left)-height(p.right))<=1)
            return true;
        else return false;
    }

    public static void main(String[] args) {
        int inputSize = 26;
        char[] data = new char[inputSize];
        /////////////
        for(int i=0; i<inputSize; i++)
            data[i] = (char) ((int)'A'+i);
        /////////////

        BST bt = new BST();
        for(int i=0; i<inputSize; i++){
            bt.insert(data[i]);
        }
        System.out.println("Initial Skewed Tree");
        bt.showTree();
        System.out.println("Max. Height = "+bt.height());
        System.out.println("IPL = "+bt.IPL());

        /////////////

        AVLTree bt1 = new AVLTree();

        for(int i=0; i<inputSize; i++){
            bt1.insert(data[i]);
        }
        System.out.println("AVL Tree");
        bt.showTree();
        System.out.println("Max. Height = "+bt1.height());
        System.out.println("IPL = "+bt1.IPL());

    }
}
