package org.nickgrealy.search_for_lowest_common_ancestor;

import org.nickgrealy.performance.QuickPerf;

import static java.util.Objects.nonNull;
import static org.nickgrealy.search_for_lowest_common_ancestor.Main.Node.of;

/**
 * Given a BST (Binary Search Tree), write a function that receives the root fo the tree, and 2 values, then find the LCA (Lowest Common Ancestor).
 */
public class Main {

    static class Node {
        public static Node of(String value) {
            Node node = new Node();
            node.value = value;
            return node;
        }
        public static Node of(String value, Node left, Node right) {
            Node node = new Node();
            node.value = value;
            node.left = left;
            node.right = right;
            return node;
        }
        public String value;
        public Node left, right;
    }

    static final Node root = of("A",
            of("B",
                    of("D",
                            of("G"),
                            null),
                    null),
            of ("C",
                    of("E"),
                    of("F",
                            of("H"),
                            of("I"))));

    public static void main(String[] args) {
        QuickPerf.start();
        NodeWrapper result = find(root, "E", "I");
        System.out.println(result.lca.value);
        QuickPerf.end();
    }

    /* --- Solution --- */

    static class NodeWrapper {
        Node lca;
        boolean foundLeft = false;
        boolean foundRight = false;
    }

    public static NodeWrapper find(Node root, String a, String b) {
        QuickPerf.iterate();
        // check for scenario where A or B are current node...
        NodeWrapper tmp = new NodeWrapper();
        if (root.value.equals(a)) {
            tmp.foundLeft = true;
            return tmp;
        }
        if (root.value.equals(b)) {
            tmp.foundRight = true;
            return tmp;
        }
        // look in left tree...
        NodeWrapper leftResult, rightResult;
        if (nonNull(root.left)) {
            leftResult = find(root.left, a, b);
            // lca already found? then return...
            if (nonNull(leftResult.lca)) {
                return leftResult;
            }
            // else, merge result with tmp...
            tmp.foundLeft |= leftResult.foundLeft;
            tmp.foundRight |= leftResult.foundRight;
        }
        // look in right tree...
        if (nonNull(root.right)) {
            rightResult = find(root.right, a, b);
            if (nonNull(rightResult.lca)) {
                return rightResult;
            }
            // else, merge result with tmp...
            tmp.foundLeft |= rightResult.foundLeft;
            tmp.foundRight |= rightResult.foundRight;
        }
        // otherwise... no lca found... lets check tmp's children, then feed back up...
        if (tmp.foundLeft && tmp.foundRight) {
            tmp.lca = root;
        }
        return tmp;
    }
}
