package Traverse;

public class TreeNode {
    char data;
    TreeNode left;
    TreeNode right;

    public TreeNode(TreeNode l, char val, TreeNode r) {
        this.data = val;
        this.left = l;
        this.right = r;
    }

    public String toString() {
        return ""+data;
    }
}
