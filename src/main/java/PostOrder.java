import java.util.*;

class PostOrder {

/* you only have to complete the function given below.
Node is defined as

class Node {
    int data;
    Node left;
    Node right;
}

*/

    /*
    more simple:
    public static void postOrder(Node node) {
        if (node == null) {
            return;
        }

        Node left = node.left;
        Node right = node.right;
        postOrder(left);
        postOrder(right);
        System.out.print(node.data + " ");
    }
     */

    public static void postOrder(Node root) {
        Stack<Integer> stack = new Stack<>();
        traverse(root, stack);
        Integer data;
        do {
            data = stack.pop();
            System.out.print(data + " ");
        } while (!stack.empty());
    }

    private static void traverse(Node node, Stack<Integer> stack) {
        if (node == null) {
            return;
        }
        stack.push(node.data);
        Node left = node.left;
        Node right = node.right;
        traverse(right, stack);
        traverse(left, stack);
    }

    public static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        } else {
            Node cur;
            if (data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while (t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        postOrder(root);
    }
}