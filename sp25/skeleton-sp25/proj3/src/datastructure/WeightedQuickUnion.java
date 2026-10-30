package datastructure;

import java.util.ArrayList;

public class WeightedQuickUnion {
    private int[] vertices;

    static void main(String[] args) {
        WeightedQuickUnion ds = new WeightedQuickUnion(5);
        ds.connect(0, 1);
        ds.connect(1, 2);
        ds.connect(2, 3);
        ds.connect(3, 4);
        ds.findRoot(0);
    }

    /**
     * Element index alway in range [0; length - 1]
     */
    public WeightedQuickUnion(int length) {
        vertices = new int[length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = -1;
        }
    }

    public void connect (int a, int b) {
        int rootA = findRoot(a);
        int rootB = findRoot(b);
        if (rootA == rootB) {
            return;
        } else if (vertices[rootA] < vertices[rootB]) {
            vertices[rootA] -= vertices[rootB];
            vertices[rootB] = rootA;
        } else if (vertices[rootA] >= vertices[rootB]) {
            vertices[rootB] += vertices[rootA];
            vertices[rootA] = rootB;
        }
    }

    public boolean isConnected(int a, int b) {
        return findRoot(a) == findRoot(b);
    }

    public int findRoot(int a) {
        return findRootHelper(a, new ArrayList<>());
    }

    public int findRootHelper(int a, ArrayList<Integer> path) {
        if (vertices[a] < 0) {
            for (Integer i : path) {
                vertices[i] = a;
            }
            return a;
        } else {
            path.add(a);
            return findRootHelper(vertices[a], path);
        }
    }
}
