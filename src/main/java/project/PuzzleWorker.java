package project;

import java.util.List;
import java.util.Set;

/**
 * @author Michael Hoglan
 */
public class PuzzleWorker {

    private PuzzleState puzzleState = null;
    private PuzzleTracker puzzleTracker = null;

    public PuzzleWorker(PuzzleState puzzleState, PuzzleTracker puzzleTracker) {
        this.puzzleState = puzzleState;
        this.puzzleTracker = puzzleTracker;
    }

    public void build(boolean stopOnFirst) {
        if (!puzzleTracker.isStopAll()) {
            int x = puzzleState.getCurrentXPosition();
            int y = puzzleState.getCurrentYPosition();

            Square s0 = null;
            Square s1 = null;
            Square s2 = null;
            Square s3 = null;

            if (puzzleState.exists(x - 1, y)) {
                s0 = puzzleState.get(x - 1, y);
            }

            if (puzzleState.exists(x, y + 1)) {
                s1 = puzzleState.get(x, y + 1);
            }

            if (puzzleState.exists(x + 1, y)) {
                s2 = puzzleState.get(x + 1, y);
            }

            if (puzzleState.exists(x, y - 1)) {
                s3 = puzzleState.get(x, y - 1);
            }

            int m0 = -1;
            int m1 = -1;
            int m2 = -1;
            int m3 = -1;

            if (s0 == null)
                m0 = -1;
            else {
                m0 = s0.getEdge(SquareEdgeOrientation.S);
            }

            if (s1 == null)
                m1 = -1;
            else {
                m1 = s1.getEdge(SquareEdgeOrientation.W);
            }

            if (s2 == null)
                m2 = -1;
            else {
                m2 = s2.getEdge(SquareEdgeOrientation.N);
            }

            if (s3 == null)
                m3 = -1;
            else {
                m3 = s3.getEdge(SquareEdgeOrientation.E);
            }

            List<Integer> maskList = PuzzleUtils.createListFromArray(new int[]{m0, m1, m2, m3});

            Set<Square> used = puzzleState.getUsed();
            Set<Square> pieces = puzzleState.getPieces();

            for(Square piece : pieces) {
                if (!used.contains(piece)) {
                    int rotations = SquareChecker.matchesMaskReturnRotations(piece, maskList);

                    if (rotations >= 0) {
                        // Create a new puzzle and add piece
                        // Allows a form of fan out / recursion for puzzles
                        // which terminate if no pieces can be added
                        PuzzleState newPuzzleState = puzzleState.newCopy();

                        // We already know the square matches mask, so rotation count positive
                        // rotate then place
                        newPuzzleState.get(piece.getId()).rotate(rotations);
                        newPuzzleState.place(piece.getId());

                        puzzleTracker.incrementStates();

                        int e = newPuzzleState.evaluate();
                        if (e == 0) {
                            puzzleTracker.addSuccess(newPuzzleState);

                            if (stopOnFirst) {
                                puzzleTracker.stopAll();
                                break;
                            }
                        }

                        PuzzleWorker newPuzzleWorker = new PuzzleWorker(newPuzzleState, puzzleTracker);
                        newPuzzleWorker.build(stopOnFirst);

                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return puzzleState.toString();
    }
}