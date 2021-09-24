package week4_binaryTree;

public class BSTOld {

    Node root;
    int numNode;
    public BSTOld() {
        root=null;
        numNode = 0;
    }

    public void insert(char x) {
        insert(x, null, root);//null = parent of root
    }

    private Node insert(char x, Node parent, Node r) {
        if (r==null) {
            if(parent==null){//root
                root = insertNode(x, null);
                return root;
            }
            else{ // leaf
                if(x < parent.key) {
                    parent.left = insertNode(x, parent);
                    return parent.left;
                }
                else if(x > parent.key){
                    parent.right = insertNode(x, parent);
                    return parent.right;
                }
                else return null;
            }
        }
        else{
            if(x < r.key) return insert(x, r, r.left);
            else if(x > r.key) return insert(x, r, r.right);
            else return null;
        }
    }

    private Node insertNode(char x, Node parent) {
        Node newNode = new Node(x);
        newNode.parent = parent;
        numNode++;
        return newNode;
    }

    public Node search(Node startNode, char x) {
        Node p = startNode;
        if (p==null||p.key==x)
            return p;
        else if (x<p.key)
            return search(p.left, x);
        else
            return search(p.right, x);
    }

    public Node delete(char x) {
        Node r = search(root, x);
        if(r!=null){
            numNode--;
            return delete(r);
        }
        else{
            return null;
        }
    }

    private Node delete(Node r) {

        if (r.parent == null) {//r == root
            root = deleteNode(r);
            return null;
        } else if (r == r.parent.left) {
            r.parent.left = deleteNode(r);
            return r.parent;
        } else {
            r.parent.right = deleteNode(r);
            return r.parent;
        }
    }

    private Node deleteNode(Node r) {

        // case 1 : no child
        if (r.left==null && r.right==null) {
            return null;
        }
        // case 2 : 1 child
        else if (r.left==null && r.right!=null) {
            r.right.parent = r.parent;
            return r.right;
        }
        else if(r.left!=null && r.right==null) {
            r.left.parent = r.parent;
            return r.left;
        }
        else{// case3: 2 child
            Node s = sccessor(r);
            r.key = s.key;
            r.key = s.key;
            if(s==s.parent.left)
                s.parent.left = s.right;
            else
                s.parent.right = s.right;
            return s.parent;
        }
    }

    private Node sccessor(Node v) {
        if (v==null)
            return null;
        Node p=v.right;
        while(p.left!=null)
            p=p.left;
        return p;
    }

    private Node predecessor(Node v) {
        if (v==null)
            return null;
        Node p=v.left;
        while(p.right!=null)
            p=p.right;
        return p;
    }

    public void showTree() {
        System.out.println(toString(root));
    }

    private String toString(Node t) {
        return inorder(t);
    }

    private String inorder(Node t) {
        if (t==null)
            return "";
        else
            return inorder(t.left)+" "+t.key+" "+inorder(t.right);
    }

    public static void main(String[] args) {

        char [] key = {'M','Y','U','N','G','I','S','W'};
        BSTOld bt = new BSTOld();

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
        bt.showTree();

    }

}
