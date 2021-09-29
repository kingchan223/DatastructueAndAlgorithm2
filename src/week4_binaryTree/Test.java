package week4_binaryTree;

public class Test extends BST {

    public Test(){
        super();
    }

    public void rotateTest(int n) {
        for(int i=0; i<n; i++){
            Node x = root;
            if(x!=null){
                if(x.right!=null) rotateLeft(x);
                else
                    if(x.left!=null)
                        rotateRight(x);
            }
        }
    }

    private void rotateLeft(Node x) {//imbalance가 발생한 노드
        Node y = x.right;
        y.parent = x.parent;/* 1. y의 parent가 x의 parent를 보게한다.*/
        if (y.parent == null) {//rotate를 시도한 x가 root였을 시에
            root = y;//y가 root가 된다.
        }
        else{/* 2. 아직 x가 x.parent의 right인지 left인지 모르므로
              아래에서 if문으로 확인한 뒤에 y를 left또는 right로 붙인다. */
            if(x==x.parent.left) x.parent.left = y;
            else x.parent.right = y;
        }
        x.parent = y;/*3.x의 parent를 y로 한다.*/
        x.right = y.left;/* 4. y의 left를 x의 right로 옮긴다.*/
        if(y.left!=null) y.left.parent = x;//y.left의 parent가 x임을 알려준다.
        y.left = x;/* 5. y가 left로 x를 보게 한다.*/
    }

    private void rotateRight(Node x) {//imbalance가 발생한 노드
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
    }

    public static void main(String[] args) {
        int inputSize = 26;
        char[] data = new char[inputSize];

        //data 초기화
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
