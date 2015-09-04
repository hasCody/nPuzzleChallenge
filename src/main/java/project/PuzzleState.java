package project;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Hoglan
 */
public class PuzzleState {

    private Square[][] board = null;
    private int rows;
    private int cols;

    // Use LinkedHashMap to have deterministic ordering based on insertion
    private Map<String, Square> pieces = new LinkedHashMap<>();
    private Map<String, Square> usedPieces = new LinkedHashMap<>();

    private int currentPos = 0;

    public PuzzleRunner puzzleRunner = null;

    public PuzzleState(PuzzleRunner puzzleRunner, int rows, int cols, Set<Square> pieces) {
        this.puzzleRunner = puzzleRunner;
        this.rows = rows;
        this.cols = cols;
        this.board = new Square[rows][cols];

        for(Square[] row : this.board) {
            Arrays.fill(row, null);
        }

        for(Square piece : pieces) {
            this.pieces.put(piece.getId(), piece);
        }
    }

    public PuzzleState(PuzzleState puzzleState) {
        this.puzzleRunner = puzzleState.puzzleRunner;
        this.rows = puzzleState.rows;
        this.cols = puzzleState.cols;
        this.currentPos = puzzleState.currentPos;
        this.board = new Square[rows][cols];

        for(Square[] row : this.board) {
            Arrays.fill(row, null);
        }

        // Get a deep copy of pieces
        for(Map.Entry<String, Square> entry : puzzleState.pieces.entrySet()) {
            Square squareCopy = entry.getValue().newCopy();

            this.pieces.put(squareCopy.getId(), squareCopy);
        }

        // Regenerate board by placing pieces that exist
        // Will generate the usedPieces while placing
        for(int x = 0; x < this.rows; x++) {
            for(int y = 0; y < this.cols; y++) {
                Square piece = puzzleState.get(x, y);

                // No piece at that position
                if(piece == null) {
                    continue;
                }

                // Place piece by ID in same position
                place(piece.getId(), x, y);
            }
        }



    }
    public PuzzleState newCopy() {
        return new PuzzleState(this);
    }

//    public void add(Square s) {
//        board[currentPos / rows][currentPos % cols] = s;
//        usedPieces.put(s.getId(), s);
////        used[pieces.getId()] = true;
//        currentPos++;
//    }

    public boolean place(String squareId, int x, int y) {
        Square piece = pieces.get(squareId);

        if (piece == null) {
            return false;
        }

        board[x][y] = piece;
        usedPieces.put(piece.getId(), piece);

        return true;
    }

    public boolean place(String squareId) {
        boolean result = place(squareId, getCurrentXPosition(), getCurrentYPosition());

        if(result) {
            currentPos++;
        }

        return result;
    }

    public Square get(int x, int y) {
        Square temp = null;
        try {
            temp = board[x][y];
        } catch (Exception e) {
            temp = null;
        }

        return temp;
    }

    public boolean exists(int x, int y) {
        boolean flag = false;
        Square temp = null;
        try {
            temp = board[x][y];
            if (temp != null)
                flag = true;
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    public Square get(int i) {
        Square temp = null;
        try {
            temp = board[i / rows][i % cols];
        } catch (Exception e) {
            temp = null;
        }

        return temp;
    }

    public Square get(String squareId) {
        return pieces.get(squareId);
    }

    public Set<Square> getUsed() {
        return new LinkedHashSet<>(usedPieces.values());
    }

    public Set<Square> getPieces() {
        return new LinkedHashSet<>(pieces.values());
    }

    public int getCurrentPosition() {
        return currentPos;
    }

    public int getCurrentXPosition() {
        return currentPos / rows;
    }

    public int getCurrentYPosition() {
        return currentPos % cols;
    }

    public void rotate(int x, int y) {
        board[x][y].rotate();
    }

    public void swap(int x1, int y1, int x2, int y2) {
        Square temp = null;
        temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    public int evaluate() {
        int sum = 0;

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                sum += evaluate(r, c);
            }
        }

        return sum;
    }

    private int evaluate(int x, int y) {
        int sum = 0;

        Square s = null;
        Square s0 = null;
        Square s1 = null;
        Square s2 = null;
        Square s3 = null;

        try {
            s = board[x][y];
        } catch (Exception e) {
            s = null;
        }

        if (s == null) {
            sum = 100;
        } else {

            try {
                s0 = board[x - 1][y];
            } catch (Exception e) {
                s0 = null;
            }

            try {
                s1 = board[x][y + 1];
            } catch (Exception e) {
                s1 = null;
            }

            try {
                s2 = board[x + 1][y];
            } catch (Exception e) {
                s2 = null;
            }

            try {
                s3 = board[x][y - 1];
            } catch (Exception e) {
                s3 = null;
            }

            if (s0 != null) {
                if (s.getEdge(SquareEdgeOrientation.N) != s0.getEdge(SquareEdgeOrientation.S))
                    sum++;
            }

            if (s1 != null) {
                if (s.getEdge(SquareEdgeOrientation.E) != s1.getEdge(SquareEdgeOrientation.W))
                    sum++;
            }

            if (s2 != null) {
                if (s.getEdge(SquareEdgeOrientation.S) != s2.getEdge(SquareEdgeOrientation.N))
                    sum++;
            }

            if (s3 != null) {
                if (s.getEdge(SquareEdgeOrientation.W) != s3.getEdge(SquareEdgeOrientation.E))
                    sum++;
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                sb.append("----------------");
            }

            sb.append("-\n");

            for (int y = 0; y < cols; y++) {
                sb.append("|\t\t" + board[x][y].getEdge(SquareEdgeOrientation.N) + "\t\t");
            }

            sb.append("|\n");

            for (int y = 0; y < cols; y++) {
                sb.append("|\t\t\t\t");
            }

            sb.append("|\n");

            for (int y = 0; y < cols; y++) {
                sb.append("|\t" + board[x][y].getEdge(SquareEdgeOrientation.W) + "\t" + board[x][y].getId() + "\t" + board[x][y].getEdge(SquareEdgeOrientation.E) + "\t");
            }

            sb.append("|\n");


            for (int y = 0; y < cols; y++) {
                sb.append("|\t\t\t\t");
            }

            sb.append("|\n");

            for (int y = 0; y < cols; y++) {
                sb.append("|\t\t" + board[x][y].getEdge(SquareEdgeOrientation.S) + "\t\t");
            }

            sb.append("|\n");
        }

        for (int y = 0; y < cols; y++) {
            sb.append("----------------");
        }

        sb.append("-\n");

        return sb.toString();
    }
}