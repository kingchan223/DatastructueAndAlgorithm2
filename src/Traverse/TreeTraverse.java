package Traverse;

public class TreeTraverse {
    TreeNode root = null;

    public TreeNode makeTree(TreeNode l, char v, TreeNode r) {
        root = new TreeNode(l, v, r);
        return root;
    }

    public void inorder(TreeNode t) {// L D R
        if (t != null) {
            inorder(t.left);
            System.out.print(t.data);
            inorder(t.right);
        }
    }

    public void preorder(TreeNode t) {// D L R
        if (t != null) {
            System.out.print(t.data);
            preorder(t.left);
            preorder(t.right);
        }
    }

    public void postorder(TreeNode t) {// L R D
        if (t != null) {
            postorder(t.left);
            postorder(t.right);
            System.out.print(t.data);
        }
    }

    public static void main(String[] args) {

        System.arraycopy(args, 0, args, 0, 0);

        TreeTraverse bt1 = new TreeTraverse();

        TreeNode n1 = bt1.makeTree(null, 'A', null);
        TreeNode n2 = bt1.makeTree(null, 'B', null);
        TreeNode n3 = bt1.makeTree(n1, '*', n2);

        TreeNode n4 = bt1.makeTree(null, 'C', null);
        TreeNode n5 = bt1.makeTree(null, 'D', null);
        TreeNode n6 = bt1.makeTree(n4, '/', n5);
        TreeNode n7 = bt1.makeTree(n3, '-', n6);

        System.out.println("\n InOrder Traverse ");
        bt1.inorder(n7);
        System.out.println();
        System.out.println("\n preOrder Traverse ");
        bt1.preorder(n7);
        System.out.println();
        System.out.println("\n postOrder Traverse ");
        bt1.postorder(n7);

    }
}