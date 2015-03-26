import java.util.Arrays;

/**
 * @author zenind
 */
public class Solver {

    private final SearchNode goal;

    private boolean isSolvable;

    // find a solution to the initialBoard board (using the A* algorithm)
    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            throw new NullPointerException();
        }

        MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>(10);
        SearchNode initial = new SearchNode(initialBoard, null, 0);
        priorityQueue.insert(initial);

        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<SearchNode>(10);
        SearchNode initialTwin = new SearchNode(initialBoard.twin(), null, 0);
        priorityQueueTwin.insert(initialTwin);

        SearchNode node = priorityQueue.delMin();
        SearchNode twinNode = priorityQueueTwin.delMin();

        int i = 0;
        while (!node.getBoard().isGoal() && !twinNode.getBoard().isGoal()) {
            for (Board neighbor : node.getBoard().neighbors()) {
                if (node.getPrevBoard() == null || !neighbor.equals(node.getPrevBoard().getBoard())) {
                    SearchNode searchNode = new SearchNode(neighbor, node, node.getMoves() + 1);
                    priorityQueue.insert(searchNode);
                }
            }

            if (i % 10 == 0) {
                for (Board neighbor : twinNode.getBoard().neighbors()) {
                    if (twinNode.getPrevBoard() == null || !neighbor.equals(twinNode.getPrevBoard().getBoard())) {
                        SearchNode searchNode = new SearchNode(neighbor, twinNode, twinNode.getMoves() + 1);
                        priorityQueueTwin.insert(searchNode);
                    }
                }
                twinNode = priorityQueueTwin.delMin();
            }

            i++;
            node = priorityQueue.delMin();
        }

        this.isSolvable = node.getBoard().isGoal();
        this.goal = isSolvable ? node : twinNode;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? goal.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        int movesCount = moves();
        Board[] solution = new Board[movesCount + 1];

        solution[movesCount] = goal.getBoard();
        SearchNode board = goal.getPrevBoard();
        while (board!= null) {
            solution[--movesCount] =  board.getBoard();
            board = board.getPrevBoard();
        }
        return Arrays.asList(solution);
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        System.out.println(initial);
        System.out.println(initial.twin());
        System.out.println(initial.manhattan());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {

        private final Board board;

        private final SearchNode prevNode;

        private final int moves;

        public SearchNode(Board board, SearchNode prevNode, int moves) {
            this.board = board;
            this.prevNode = prevNode;
            this.moves = moves;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPrevBoard() {
            return prevNode;
        }

        public int getMoves() {
            return moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            int priority = (board.manhattan() + moves) - (o.getBoard().manhattan() + o.getMoves());
            return priority == 0 ? board.manhattan() - o.getBoard().manhattan() : priority;
        }
    }
}
