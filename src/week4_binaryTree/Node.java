package week4_binaryTree;

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
        return "" + key;
    }

    public static void main(String[] args) {

        Node A = new Node('a', null,null,null);
        Node B = new Node('b', null,null, A);

        System.out.println("Node A: "+A);
        System.out.println("Node B: "+B);

        A.right = B;

        System.out.println("A.right: "+A.right);
        System.out.println("B.parent: "+B.parent);
        B.parent = null;

        System.out.println(A.right);
        System.out.println(B.parent);
    }
}