package week4_binaryTree;

public class AVLTree extends BST {

    public AVLTree() {
        super();
    }

    /* ----------------- insert ----------------- */
    //노드를 추가할 때마다 imabalance를 검사해서 바로 교정한다.
    public void insert(char c){
        Node r = insert(c, null, root);//일단은 트리에 노드를 추가한다.
        //imbalance 시작 노드인 x를 찾자!
        Node p = r.parent;
        while(p!=null){
            if(!isBalanced(p)) break;
            p=p.parent;//balance가 맞으면 위로 올라간다.
        }
        Node x = p;
        Node y = null;
        //x==null이라면 root까지 imbalance가 없어서 rotate할 필요가 없다.
        //하지만 x가 null이 아니라면, imbalance의 시작점 노드인 것이다.
        if(x!=null){
            if(c<x.key){// c가 x.key보다 작다는 것은 왼쪽에 있다는 것임. -> LL or LR imbalance인 경우
                y = x.left;
                if(c<y.key){// LL imbalance
                    rotateRight(x);
                }
                else{// LR imbalance
                    rotateLeft(y);
                    rotateRight(x);
                }
            }else{//RR or RL imbalance인 경우
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
    /* ---------------------------------------- */

    /* ------------ isBalanced -------------- */
    private boolean isBalanced(Node p) {
        if(p==null) return true;
        if(Math.abs(height(p.left)-height(p.right))<=1) return true;
        return false;
    }
    /* ---------------------------------------- */

    /* -------------- rotate -------------- */
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
        return y;
    }

    //rotate로 위로 올라오게 된 노드(x자리를 차지한 노드)를 반환한다.
    private Node rotateRight(Node x) {//imbalance가 발생 시작한 노드
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
        return y;
    }
    /* --------------------------------------- */

    /* -------------- delete -------------- */
//    public Node AVLdelete(char c){
//        Node r = delete(c);
//        Node p = r.parent;
//        while(p!=null){
//            if(!isBalanced(p)) break;
//            p=p.parent;//balance가 맞으면 위로 올라간다.
//        }
//        Node x = p;
//        Node y = null;
//        if(x!=null){//x==null이라면 root까지 imbalance가 없어서 rotate할 필요가 없다.
//            if(c<x.key){// c가 x.key보다 작다는 것은 왼쪽에 있다는 것임. -> LL or LR imbalance인 경우
//                y = x.left;
//                if(c<y.key){// LL imbalance
//                    return rotateRight(x);
//                }
//                else{// LR imbalance
//                    rotateLeft(y);
//                    return rotateRight(x);
//                }
//            }else{//RR or RL imbalance인 경우
//                y = x.right;
//                if(c>y.key){// RR imbalance
//                    return rotateLeft(x);
//                }
//                else{// RL imbalance
//                    rotateRight(y);
//                    return rotateLeft(x);
//                }
//            }
//        }
//        return null;
//    }

    public Node AVLdelete(char c){//교수님 코드
        Node x = delete(c);
        Node y = null;
        Node z = null;
        Node w = null;
        while(x!=null){
            if(!isBalanced(x)){
                if(height(x.left)>height(x.right)){//left의 height가 더 큰 경우 -> LL아니면 LR imbalance가 발생
                    y = x.left;
                    if(y.left!=null){//LL
                        z = y.left;
                        w = rotateRight(x);
                    }
                    else{//LR
                        z = y.right;
                        rotateLeft(y);
                        w = rotateRight(x);
                    }
                }
                else{//right의 height가 더 큰 경우 -> RR아니면 RL imbalance가 발생
                    y = x.right;
                    if (y.left != null) {//RL
                        z = y.left;
                        rotateRight(y);
                        w = rotateLeft(x);
                    }
                    else{//RR
                        z = y.right;
                        w = rotateLeft(y);
                    }
                }
                //root라면
                if(w.parent==null)root = w;
                x = w.parent;
            }
            else x = x.parent;//이제 위의 부모를 기준으로 검사한다.
        }
        return null;
    }

//    public Node AVLdelete(char c){//교수님 코드
//        Node x = delete(c);
//        Node y = null;
//        Node z = null;
//        Node w = null;
//        System.out.println(">>> Return Node : "+x.toString()+"  "+height(x.left)+"  "+height(x.right));
//        while(x!=null){
//            System.out.println(">>> While-Loop : "+x.toString()+"  "+height(x.left)+"  "+height(x.right));
//            if(!isBalanced(x)){
//                if(height(x.left)>height(x.right)){//left의 height가 더 큰 경우 -> LL아니면 LR imbalance가 발생
//                    y = x.left;
//                    if(y.left!=null){//LL
//                        System.out.println(" >>> LL");
//                        z = y.left;
//                        w = rotateRight(x);
//                        System.out.println(" --- After");
//                        showTree();
//                        if(w!=null)
//                            System.out.println(" > Rotate returns >> "+w.toString());
//                    }
//                    else{//LR
//                        System.out.println(" >>> LR");
//                        z = y.right;
//                        rotateLeft(y);
//                        System.out.println(" --- After");
//                        showTree();
//                        System.out.println(" --- After");
//                        showTree();
//
//                        if (w != null) System.out.println(" > Rotate returns >>" + w.toString());
//                    }
//                }
//                else{//right의 height가 더 큰 경우 -> RR아니면 RL imbalance가 발생
////                    y = x.right;
//                    if (y.left != null) {//RL
//                        System.out.println(" >>> RL");
//
//                        z = y.left;
//                        rotateRight(y);
//                        System.out.println(" --- After");
//                        showTree();
//
//                        w = rotateLeft(x);
//                        System.out.println(" --- After");
//                        showTree();
//
//                        if(w!=null)
//                            System.out.println(" > Rotate returns >> " + w.toString());
//                    }
//                    else{//RR
//                        System.out.println(" >>> RR");
//                        z = y.right;
//                        w = rotateLeft(y);
//                        System.out.println(" --- After");
//
//                        showTree();
//                        if(w!=null)
//                            System.out.println(" > Rotate returns >>"+w.toString());
//                    }
//                }
//                if(w.parent==null){//root라면
//                    root = w;
//                }
//                x = w.parent;
//            }
//            else{
//                x = x.parent;
//            }
//        }
//        return null;
//    }

    /* --------------------------------------- */



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
        System.out.println("Height = "+bt.height());
        System.out.println("IPL = "+bt.IPL());

        /////////////

        AVLTree bt1 = new AVLTree();

        for(int i=0; i<inputSize; i++){
            bt1.insert(data[i]);
        }
        System.out.println("AVL Tree");
        bt.showTree();
        System.out.println("Height = "+bt1.height());
        System.out.println("IPL = "+bt1.IPL());
        bt1.AVLdelete('A');


    }
}
