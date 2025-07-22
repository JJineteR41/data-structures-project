package org.example.interf;

public class BST<T extends Comparable<T>> {
    protected Node<T> root;
    int nodesAmount;

    public class Node<T> {
        protected Node<T> right;
        protected Node<T> left;
        protected Node<T> parent;
        protected T data;
        int height;

        public Node(T data, Node parent) {
            this.right = null;
            this.left = null;
            this.parent = parent;
            this.data = data;
            this.height = 1;
        }

        public Node<T> getRight() {
            return right;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getParent() {
            return parent;
        }

        public T getData() {
            return data;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public BST() {
        this.root = null;
        this.nodesAmount = 0;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public void printTree() {
        if(nodesAmount<100000) printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isTail) {
        if (node == null) return;
        if(node.equals(root)) System.out.println("Tree:");
        System.out.println(prefix + (isTail ? "'---" : "|---") + node.data );

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft || hasRight) {
            if (hasRight)
                printTree(node.right, prefix + (isTail ? "    " : "|   "), false);
            if (hasLeft)
                printTree(node.left, prefix + (isTail ? "    " : "|   "), true);
        }
    }



    public Node<T> find(T key) {
        return findRec(root, key);
    }

    private Node<T> findRec(Node<T> node, T key) {
        if (node == null) return null;

        int comparison = key.compareTo(node.data);

        if (comparison == 0) return node;
        else if (comparison < 0) return findRec(node.left, key);
        else return findRec(node.right, key);
    }

    public void insert(T key){
        if(find(key) == null) nodesAmount++;

        root=insertRec(root, key, null);

    }

    private Node<T> insertRec(Node<T> node, T key, Node<T> parent){

        if (node == null){
            Node<T> newNode = new Node<>(key, parent);
            return newNode;
        }

        if(key.compareTo(node.data) < 0){
            node.left = insertRec(node.left, key, node);
        }

        if (key.compareTo(node.data) > 0){
            node.right = insertRec(node.right, key, node);
        }
        return node;
    }

    public Node<T> next(Node<T> node) {
        if (node.right == null) return null;
        return minimum(node.right);
    }

    protected Node<T> minimum(Node<T> node) {
        if (node == null) return null;
        if (node.left == null) return node;
        return minimum(node.left);
    }

    public void delete (T key) {
        root = deleteRec(root, key);
    }

    private Node<T> deleteRec(Node<T> node, T key) {
        if (node == null) return null;
        if (find(key) == null) return node;


        int comparison = key.compareTo(node.data);

        if (comparison == 0){
            if (node.left == null && node.right == null) return null;
            else if (node.left == null){
                node.right.parent = null;
                return node.right;
            }
            else if (node.right == null){
                node.left.parent = null;
                return node.left;
            }
            else{
                Node<T> successor = next(node);
                deleteRec(node, successor.data);
                node.data = successor.data;
                return node;
            }
        }

        if (comparison < 0){
            if (key.compareTo(node.left.data)==0){
                if(node.left.left == null && node.left.right == null){
                    node.left = null;
                }

                else if(node.left.left == null) {
                    node.left.right.parent = node;
                    node.left = node.left.right;
                }
                else if(node.left.right == null) {
                    node.left.left.parent = node;
                    node.left = node.left.left;
                }
                else{
                    Node<T> successor = next(node.left);
                    deleteRec(node.left, successor.data);
                    node.left.data = successor.data;
                }
            }
            else deleteRec(node.left, key);
        }

        if (comparison > 0){
            if(key.compareTo(node.right.data) == 0){
                if(node.right.left == null && node.right.right == null){
                    node.right = null;
                }

                else if(node.right.left == null){
                    node.right.right.parent = node;
                    node.right = node.right.right;
                }
                else if(node.right.right == null) {
                    node.right.left.parent = node;
                    node.right = node.right.left;
                }
                else{
                    Node<T> successor = next(node.right);
                    deleteRec(node.right, successor.data);
                    node.right.data = successor.data;
                }
            }
            else deleteRec(node.right, key);
        }


        return node;
    }
}
