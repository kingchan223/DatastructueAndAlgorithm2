package week10_minimal_spanning_tree;

public class Node {
	int weight;
	int index;
	Node left;
	Node right;
	Node parent;

	public Node(int weight, int index) {
		this.weight = weight;
		this.index = index;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	public Node(int weight, int index, Node left, Node right, Node parent) {
		this.weight = weight;
		this.index = index;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	public String toString() {
		return "" + weight+"("+index+")";
	}

}
