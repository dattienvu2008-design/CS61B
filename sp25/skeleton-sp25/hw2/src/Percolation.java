import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {
    // TODO: Add any necessary instance variables.
    private HashSet opened;
    private WeightedQuickUnionUF connectedTile;
    private int size, last, numOpened;

    private int xyTo1D(int row, int col){
        return row*size + col;
        //ok
    }

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        size = N;
        last = size*size;
        numOpened = 0;
        opened = new HashSet(N*N);
        connectedTile = new WeightedQuickUnionUF(1 + N*N);
        for (int i = 0; i < N; i++) {
            connectedTile.union(xyTo1D(0, i), N*N);
        }
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (!opened.contains(xyTo1D(row, col))) {
            opened.add(xyTo1D(row, col));
            numOpened++;
            adjacentUnion(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return opened.contains(xyTo1D(row, col));
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        return opened.contains(xyTo1D(row, col)) && connectedTile.connected(xyTo1D(row, col), last);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numOpened;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        for (int i = 0; i < size; i++) {
            if (connectedTile.connected(xyTo1D(size - 1, i), last))
                return true;
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    private void adjacentUnion (int row, int col) {
        if (validTile(row + 1, col) && opened.contains(xyTo1D(row + 1, col)))
            connectedTile.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        if (validTile(row, col + 1) && opened.contains(xyTo1D(row, col + 1)))
            connectedTile.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        if (validTile(row - 1, col) && opened.contains(xyTo1D(row - 1, col)))
            connectedTile.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        if (validTile(row, col - 1) && opened.contains(xyTo1D(row, col - 1)))
            connectedTile.union(xyTo1D(row, col), xyTo1D(row, col - 1));
    }

    private boolean validTile (int row, int col) {
        if (row < size && col < size && row > -1 && col > -1)
            return true;
        return false;
    }
}