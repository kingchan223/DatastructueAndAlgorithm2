package week10_minimal_spanning_tree;

public class BST {Node root;
    public BST() {
        this.root = null;
    }

    public void insert(int weight, int index){
        // 비어있는 트리라면 root로 생성
        if (root == null)
            root = new Node(weight, index);
            // 이미 존재하면 삽입불가 --> /* 여기서 같은 노드는 삽입 불가하다는 것이 나타난다. */
        else if (search(root, weight) != null)
            return;
            // root보다 작다면 왼쪽에 삽입
        else if (weight < root.weight)
            insert(root.left, root, weight, index);
            // root보다 크다면 오른쪽에 삽입
        else
            insert(root.right, root, weight, index);
    }

    //재귀적 insert
    private void insert(Node node, Node parent, int weight, int index) {
        if (node == null) {
            Node temp = new Node(weight, index);
            temp.parent = parent;
            if (weight < parent.weight)
                parent.left = temp;
            else
                parent.right = temp;
        } else if (weight < node.weight)
            insert(node.left, node, weight, index);
        else
            insert(node.right, node, weight, index);
    }

    //반복적 insert
    private void insertIter(Node node, Node parent, int weight,int index){
        if(node == null) {
            Node newNode = new Node(weight,index, null, null, parent);
            if(parent.weight > weight) {
                parent.left = newNode;
            }else {
                parent.right = newNode;
            }
            return;
        }
        Node p = parent;
        Node prev = null;
        while(p!=null) {
            prev = p;
            if(p.weight > weight){
                p = p.left;
            }else {
                p = p.right;
            }
        }
        Node newNode = new Node(weight, index, null, null, prev);
        if(prev.weight > weight) {
            prev.left = newNode;
        }else {
            prev.right = newNode;
        }
    }

    //재귀적 search
    public Node search(Node startNode, int x) {
        Node p = startNode;
        // p==null에 의해 반환된다면 x가 없다는 것.
        // p.data==x에 의해 반환된다면 데이터가 있다느 것임.
        if (p == null || p.weight == x)
            return p;
        else if (x < p.weight)
            return search(p.left, x);
        else
            return search(p.right, x);
    }

    //반복적 search  null을 반환하면 데이터가 없는 거임
    public Node searchIter(Node startNode, int x) {
        Node p = startNode;
        while(p!=null&&p.weight!=x) {
            if(p.weight < x) {
                p = p.right;
            }else {
                p = p.left;
            }
        }
        return p;
    }

    public Integer poll(){
        return deleteRoot();
    }


    private Integer deleteRoot(){
        Node deleteN = root;
        if (deleteN == null)
            return deleteN.weight;

        /* case1: no child */ // 부모가 null을 보게 한다.
        if (deleteN.left == null && deleteN.right == null) {
            if (deleteN == deleteN.parent.left)
                deleteN.parent.left = null;
            else
                deleteN.parent.right = null;
            return deleteN.weight;
        }

        /* case2: degree==1 */ //부모가 삭제할 노드의 자식을 보게한다.
        if (deleteN.left == null || deleteN.right == null){
            // 삭제할 노드의 오른쪽 자식이 존재
            if (deleteN.right != null) {
                //부모가 새로운 직손 자식을 보게 하기
                deleteN.right.parent = deleteN.parent;
                //자식이 부모를 보게 하기
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.right;
                } else {
                    deleteN.parent.right = deleteN.right;
                }
                // 삭제할 노드의 왼쪽이 존재
            } else {
                deleteN.left.parent = deleteN.parent;
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.left;
                } else {
                    deleteN.parent.right = deleteN.left;
                }
            }
            return deleteN.weight;
        }
        Node q = succesor(deleteN);//삭제할 노드의 오른쪽 노드를 root로 하는 서브트리 중 제일 큰 값을 삭제할 노드 위체에 놓는 방식
        deleteN.weight = q.weight;
        return delete(deleteN.right, q.weight);
    }

    private Integer delete(Node startNode, int weight){
        // 삭제할 노드찾기
        Node deleteN = search(startNode, weight);
        //System.out.println(deleteN);
        // 삭제할 노드가 없는 경우
        if (deleteN == null)
            return deleteN.weight;

        /* case1: no child */ // 부모가 null을 보게 한다.
        if (deleteN.left == null && deleteN.right == null) {
            if (deleteN == deleteN.parent.left)
                deleteN.parent.left = null;
            else
                deleteN.parent.right = null;
            return deleteN.weight;
        }

        /* case2: degree==1 */ //부모가 삭제할 노드의 자식을 보게한다.
        if (deleteN.left == null || deleteN.right == null){
            // 삭제할 노드의 오른쪽 자식이 존재
            if (deleteN.right != null) {
                //부모가 새로운 직손 자식을 보게 하기
                deleteN.right.parent = deleteN.parent;
                //자식이 부모를 보게 하기
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.right;
                } else {
                    deleteN.parent.right = deleteN.right;
                }
                // 삭제할 노드의 왼쪽이 존재
            } else {
                deleteN.left.parent = deleteN.parent;
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.left;
                } else {
                    deleteN.parent.right = deleteN.left;
                }
            }
            return deleteN.weight;
        }
        //case 2의 아주 조금 다른 버전
//		if(deleteN.left==null || deleteN.right==null){
//			//삭제하려는 노드가 왼쪽노드임
//			if(deleteN == deleteN.parent.left) {
//				if(deleteN.right != null){
//					deleteN.parent.left = deleteN.right;
//					deleteN.right.parent = deleteN.parent;
//				}else {
//					deleteN.parent.left = deleteN.left;
//					deleteN.left.parent = deleteN.parent;
//				}
//			}else {
//				if(deleteN.right!=null) {
//					deleteN.parent.right = deleteN.right;
//					deleteN.right.parent = deleteN.parent;
//				}else{
//					deleteN.parent.right = deleteN.left;
//					deleteN.left.parent = deleteN.parent;
//				}
//			}
//			return;
//		}
        /* case3: degree==2 */
        /* 방법1: 왼쪽 트리의 최대 노드로 매꿔주던가. predecessor */
        /* 방법2: 오른쪽 트리의 최소 노드로 매꿔주던가. succesor */
        Node q = succesor(deleteN);//삭제할 노드의 오른쪽 노드를 root로 하는 서브트리 중 제일 큰 값을 삭제할 노드 위체에 놓는 방식
        deleteN.weight = q.weight;
        return delete(deleteN.right, q.weight);
    }

    private Node succesor(Node v){
        Node p = v.right;
        while (p.left != null)
            p = p.left;
        return p;
    }

    // 여기부터
    private Node succesorRecur1(Node v){
        return succesorRecur2(v.right);
    }

    private Node succesorRecur2(Node node) {
        if(node.left == null)
            return node;
        else
            return succesorRecur2(node.left);
    }
    //여기까지 recur한 succesor

    private Node preDecessor(Node v) {
        Node p = v.left;
        while (p.right != null)
            p = p.right;
        return p;
    }

    //여기부터
    private Node predecessorRecur1(Node v){
        return predecessorRecur2(v.left);
    }

    private Node predecessorRecur2(Node node) {
        if(node.right==null)
            return node;
        else
            return predecessorRecur2(node.right);
    }
    //여기까지 recur한 predeccesor

    public void delete2(char x){
        delete(root, x);
    }

    private void delete2(Node startNode, char x){

        // 삭제할 노드찾기
        Node deleteN = search(startNode, x);
        // 삭제할 노드가 없는 경우
        if (deleteN == null)
            return;

        // case1: no child
        if (deleteN.left == null && deleteN.right == null){
            if (deleteN == deleteN.parent.left)
                deleteN.parent.left = null;
            else
                deleteN.parent.right = null;
            return;
        }

        // case2: degree==1
        if (deleteN.left == null || deleteN.right == null){
            // 삭제할 노드의 오른쪽 자식이 존재
            if (deleteN.right != null) {
                //부모가 새로운 직손 자식을 보게 하기
                deleteN.right.parent = deleteN.parent;
                //자식이 부모를 보게 하기
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.right;
                } else {
                    deleteN.parent.right = deleteN.right;
                }
                // 삭제할 노드의 왼쪽이 존재
            } else{
                deleteN.left.parent = deleteN.parent;
                if (deleteN == deleteN.parent.left) {
                    deleteN.parent.left = deleteN.left;
                } else {
                    deleteN.parent.right = deleteN.left;
                }
            }
            return;
        }
        // case3: degree==2
        int val = succesor2(deleteN);//삭제할 노드의 오른쪽 노드를 root로 하는 서브트리 중 제일 큰 값을 삭제할 노드 위체에 놓는 방식
        deleteN.weight = val;
//		char val = preDecessor(v);
//		v.data = q.data;
//		delete(v.left, q.data);
    }

    private int succesor2(Node v){
        Node p = v.right;
        while (p.left != null)
            p = p.left;
        int retVal = p.weight;
        Node pParent = p.parent;
        p.parent = null;
        pParent = null;
        return retVal;
    }

    private int preDecessor2(Node v) {
        Node p = v.left;
        while (p.right != null)
            p = p.right;
        int retVal = p.weight;
        Node pParent = p.parent;
        p.parent = null;
        pParent = null;
        return retVal;
    }



    public void showTree(){
        System.out.println(toString(root));
    }

    private String toString(Node t) {
        return inorder(t);
    }

    private String inorder(Node t) {
        if (t == null)
            return "";
        else
            return inorder(t.left) + " " + t.weight + " " + inorder(t.right);
    }

    public static void main(String[] args) {
        char[] data = { 4, 5, 6, 34, 8, 3, 7, 3, 6, 2, 1, 8};
        BST bst = new BST();

        // data insertion
        for (int i = 0; i < data.length; i++)
            bst.insert(data[i], i);

        // print
        System.out.println("\nTree created : ");
        bst.showTree();
        bst.poll();

        bst.poll();
        bst.showTree();

        bst.poll();
        bst.showTree();

        System.out.println();

    }
}
