package week4_binaryTree;

public class Test extends BST {

    public Test(){
        super();
    }

    public void rotateTest(int n) {
        for(int i=0; i<n; i++){
            Node p = root;
            if(p!=null){
                if(p.right!=null) rotateLeft(p);
                else
                    if(p.left!=null)
                        rotateRight(p);
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

    public static void main(String[] args) {
        int inputSize = 26;
        char[] data = new char[inputSize];

        for(int i=0; i<inputSize; i++){
            data[i] = (char) ((int)'A'+i);
        }

        Test bt = new Test();
        for(int i=0; i<inputSize; i++){
            bt.insert(data[i]);
        }
        System.out.println("Initial Skewed Tree");
        bt.showTree();
        System.out.println("Max. Height = "+bt.height());
        System.out.println("Max. IPL = "+bt.IPL());

        bt.rotateTest(5);
        System.out.println("After A few rotate");
        bt.showTree();
        System.out.println("Max. Height = "+bt.height());
        System.out.println("Max. IPL = "+bt.IPL());
    }
}
