import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zenind
 */
public class Board {

    private final int[][] blocks;

    private int manhattan;

    private int zeroHash;

    private int twinHash;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        this.twinHash = -1;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] != 0 && blocks[i][j] != i * blocks.length + j + 1) {
                    int ci = blocks[i][j] / blocks.length;
                    int cj = blocks[i][j] % blocks.length;;
                    if (cj > 0) {
                        ci++;
                    } else if (cj == 0) {
                        cj = blocks.length;
                    }
                    manhattan += (Math.abs(i + 1 - ci) + Math.abs(j + 1 - cj));
                }

                if (this.twinHash < 0  && j < blocks.length - 1 && blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    twinHash = i * 100 + j;
                }

                if (blocks[i][j] == 0) {
                    zeroHash = i * 100 + j;
                }
            }
        }
    }

    // board dimension N
    public int dimension() {
        return this.blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * blocks.length + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        return neighbor(this.blocks, twinHash / 100, twinHash % 100, twinHash / 100, twinHash % 100 + 1);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        Board targetBoard = (Board) y;

        for (int i = 0; i < dimension(); i++) {
            if (!Arrays.equals(blocks[i], targetBoard.blocks[i])) {
                return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();

        int zeroRow = zeroHash / 100;
        int zeroCol = zeroHash % 100;
        if (zeroCol > 0) {
            neighbors.add(neighbor(blocks, zeroRow, zeroCol, zeroRow, zeroCol - 1));
        }

        if (zeroRow > 0) {
            neighbors.add(neighbor(blocks, zeroRow, zeroCol, zeroRow - 1, zeroCol));
        }

        if (zeroCol < dimension() - 1) {
            neighbors.add(neighbor(blocks, zeroRow, zeroCol, zeroRow, zeroCol + 1));
        }

        if (zeroRow < dimension() - 1) {
            neighbors.add(neighbor(blocks, zeroRow, zeroCol, zeroRow + 1, zeroCol));
        }

        return neighbors;
    }

    private Board neighbor(int[][] blocks, int i, int j, int k, int l) {
        int toReplace = blocks[i][j];
        blocks[i][j] = blocks[k][l];
        blocks[k][l] = toReplace;

        Board neighbor = new Board(blocks);

        toReplace = blocks[i][j];
        blocks[i][j] = blocks[k][l];
        blocks[k][l] = toReplace;

        return neighbor;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder toStr = new StringBuilder().append(dimension()).append("\n");
        for (int i = 0; i < dimension(); i++) {
            if (i != 0) {
                toStr.append("\n");
            }
            for (int j = 0; j < dimension(); j++) {
                toStr.append(" ").append(blocks[i][j]);
            }
        }

        return toStr.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        Board b = new Board(new int[][]{
            {4, 1, 3},
            {0, 2, 6},
            {7, 5, 8}
        });

        System.out.println(b);
        System.out.println(b.twin());
        System.out.println(b.neighbors());
        System.out.println(b.isGoal());
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
    }
}
