/**
 * @author zenind
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    public void put(Key key, Value value, Node node) {
        root = doPut(key, value, root);
    }

    private Node doPut(Key key, Value value, Node node) {
        if (node == null) {
            return new Node(key, value);
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.setLeft(doPut(key, value, node.getLeft()));
        } else if (comparison > 0) {
            node.setRight(doPut(key, value, node.getRight()));
        } else if (comparison == 0) {
            node.setValue(value);
        }

        return node;
    }

    private boolean less(Key value, Key to) {
        return value.compareTo(to) < 0;
    }


    class Node {

        private final Key key;

        private Node left;

        private Node right;

        private Value value;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }
}
