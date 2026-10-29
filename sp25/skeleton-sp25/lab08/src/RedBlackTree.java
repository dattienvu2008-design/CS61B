public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;
    int size = 0;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        static void main(String[] args) {
            RedBlackTree<Integer> test1 = new RedBlackTree<>();
            test1.insert(5);
            test1.insert(11);
            test1.insert(3);
            test1.insert(9);
            test1.insert(7);
            test1.insert(1);
        }

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }

        protected boolean isRed() {
            return !isBlack;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newRoot = node.left, cache = newRoot.right;
        newRoot.right = node;
        node.left = cache;
        newRoot.isBlack = true;
        newRoot.right.isBlack = false;
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        RBTreeNode<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.isBlack = true;
        newRoot.left.isBlack = false;
        return newRoot;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        if (size == 0) {
            root = new RBTreeNode<>(true, item, null, null);
            size++;
        } else {
            root = insertHelper(root, item);
            size++;
            root.isBlack = true;
        }
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        if (item.compareTo(node.item) > 0) {
            if (node.right != null) {
                RBTreeNode<T> newRightBranch = insertHelper(node.right, item);
                if (newRightBranch == null) return null;
                node.right = newRightBranch;
                return rightAddFixedTree(node);
            } else { //Add item
                RBTreeNode<T> leaf = node.right = new RBTreeNode<>(false, item);
                return rightAddFixedTree(node);
            }
        } else if (item.compareTo(node.item) < 0) {
            if (node.left != null) {
                RBTreeNode<T> newLeftBranch = insertHelper(node.left, item);
                if (newLeftBranch == null) return null;
                node.left = newLeftBranch;
                return leftAddFixedTree(node);
            } else {
                node.left = new RBTreeNode<>(false, item);
                return node;
            }
        } else {
            return null;
        }
    }

    private RBTreeNode<T> rightAddFixedTree(RBTreeNode<T> node) {
        if (node.right.isRed()) {
            if (node.left == null || node.left.isBlack) {
                return rotateLeft(node);
            } else {
                flipColors(node);
                return node;
            }
        }
        return node;
    }

    private RBTreeNode<T> leftAddFixedTree(RBTreeNode<T> node) {
        if (node.left.left.isRed() && node.left.isRed()) {
            RBTreeNode<T> newRoot = rotateRight(node);
            flipColors(newRoot);
            return newRoot;
        }
        return node;
    }
}
