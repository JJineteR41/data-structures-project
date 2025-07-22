package org.example.interf;

public class AVLTree<T extends Comparable<T>> extends BST<T> {

    public void printTreeHeight() {
        if(nodesAmount<100000) printTreeHeight(root, "", true);
    }

    private void printTreeHeight(Node node, String prefix, boolean isTail) {
        if (node == null) return;
        if(node.equals(root)) System.out.println("Tree:");
        System.out.println(prefix + (isTail ? "'---" : "|---") + node.data + "/" + getHeight(node));

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft || hasRight) {
            if (hasRight)
                printTreeHeight(node.right, prefix + (isTail ? "    " : "|   "), false);
            if (hasLeft)
                printTreeHeight(node.left, prefix + (isTail ? "    " : "|   "), true);
        }
    }

    @Override
    public void insert(T key){
        super.insert(key);
        rebalance(find(key));
    }

    @Override
    public void delete(T key){
        if(find(key) != null){
            Node<T> parent = find(key).parent;
            super.delete(key);

            rebalance(parent);
        }
    }

    private void deleteFirst() {
        delete(minimum(this.root).getData());
    }

    private void rebalance(Node node) {
        if (node == null) return;

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        int balanceFactor = rightHeight - leftHeight;

        if (balanceFactor > 1) rebalanceLeft(node);
        if (balanceFactor < -1) rebalanceRight(node);

        if (node.parent != null) rebalance (node.parent);
    }

    private void rebalanceRight(Node node) {
        Node left = node.left;
        if (getHeight(left.right) > getHeight(left.left)) {
            node.left = rotateLeft(left);
        }
        rotateRight(node);
    }

    private void rebalanceLeft(Node node) {
        Node right = node.right;
        if (getHeight(right.left) > getHeight(right.right)){
            node.right = rotateRight(right);
        }

        rotateLeft(node);
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) newRoot.left.parent = node;
        newRoot.left = node;

        newRoot.parent = node.parent;
        if(node.parent != null){
            if (node.parent.left != null){
                if(node.parent.left == node) node.parent.left = newRoot;
            }
            if (node.parent.right != null){
                if(node.parent.right == node) node.parent.right = newRoot;
            }
        }
        node.parent = newRoot;

        adjustHeight(node);
        adjustHeight(newRoot);

        if (newRoot.parent == null) root = newRoot;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) newRoot.right.parent = node;
        newRoot.right = node;

        newRoot.parent = node.parent;
        if(node.parent != null){
            if (node.parent.left != null){
                if(node.parent.left == node) node.parent.left = newRoot;
            }
            if (node.parent.right != null){
                if(node.parent.right == node) node.parent.right = newRoot;
            }
        }
        node.parent = newRoot;

        adjustHeight(node);
        adjustHeight(newRoot);

        if (newRoot.parent == null) root = newRoot;
        return newRoot;


    }

    protected int getHeight(Node<T> node){
        return node == null ? 0 : node.height;
    }

    public void adjustHeight(Node<T> node){
        int leftHeight, rightHeight;
        leftHeight = (node.left == null)? 0 : getHeight(node.left);
        //System.out.println(leftHeight);
        rightHeight = (node.right == null)? 0 : getHeight(node.right);
        //System.out.println(rightHeight);
        node.height = 1 + (Math.max(leftHeight, rightHeight));
        //System.out.println(node.height);
        if (node.parent != null) adjustHeight(node.parent);
    }
}
