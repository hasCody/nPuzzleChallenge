package project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Hoglan
 */
public class PuzzleRunner {
    public Map<String, Set<Square>> puzzles = new HashMap<>();

    public PuzzleRunner() {
        loadPuzzles();

        for(Map.Entry<String, Set<Square>> puzzle : puzzles.entrySet()) {
            System.out.println("################################################################");
            System.out.println("# Solving puzzle: " + puzzle.getKey());
            System.out.println("################################################################");

            Set<Square> pieces = puzzle.getValue();

            PuzzleTracker puzzleTracker = new PuzzleTracker();

            // Iterate through and create the different ways to start
            // the puzzle with a square in the top left corner.  There
            // are N * 4 ways to start the puzzle.
            for (Square s : pieces) {
                for (int r = 0; r < 4; r++) {
                    Square startingPiece = new Square(s);
                    startingPiece.rotate(r);

                    PuzzleState startingPuzzle = new PuzzleState(this, (int) Math.sqrt(pieces.size()), (int) Math.sqrt(pieces.size()), pieces);
                    startingPuzzle.get(s.getId()).rotate(r);
                    startingPuzzle.place(s.getId());

                    PuzzleWorker puzzleWorker = new PuzzleWorker(startingPuzzle, puzzleTracker);

                    // false will have the tree create all possible states and find
                    // all solutions.
                    // true will have it stop on the first solution encountered.
                    // Goes by depth first search.
                    puzzleWorker.build(false);
                }
            }

            // Output the different success states.  Also outputs the number
            // of states created overall.
            System.out.println("\tSUCCESS: " + puzzleTracker.getSuccessPuzzleStates().size());
            for (int a = 0; a < puzzleTracker.getSuccessPuzzleStates().size(); a++) {
                System.out.println("\tSolution " + (a + 1));
                System.out.println(puzzleTracker.getSuccessPuzzleStates().get(a).toString().replaceAll("(?m)^", "\t"));
            }

            System.out.println("\tSTATES CREATED: " + puzzleTracker.getStates());
            System.out.println("");
        }
    }

    private void loadPuzzles() {
        puzzles.put("9 Puzzle Repeating Edges", new LinkedHashSet<>(Arrays.asList(
                new Square("A", 2, 1, 5, 7),
                new Square("B", 1, 3, 0, 2),
                new Square("C", 0, 2, 6, 5),
                new Square("D", 1, 2, 4, 7),
                new Square("E", 7, 5, 6, 2),
                new Square("F", 1, 3, 6, 4),
                new Square("G", 5, 0, 2, 7),
                new Square("H", 0, 1, 6, 4),
                new Square("I", 7, 1, 4, 0))));

        puzzles.put("9 Puzzle", new LinkedHashSet<>(Arrays.asList(
                new Square("A", 1, 2, 3, 4),
                new Square("B", 5, 6, 7, 2),
                new Square("C", 8, 9, 10, 6),
                new Square("D", 3, 11, 12, 13),
                new Square("E", 7, 14, 15, 11),
                new Square("F", 10, 16, 17, 14),
                new Square("G", 12, 18, 19, 20),
                new Square("H", 15, 21, 22, 18),
                new Square("I", 17, 23, 24, 21))));

        puzzles.put("4 Puzzle", new LinkedHashSet<>(Arrays.asList(
                new Square("D", 11, 12, 8, 7),
                new Square("B", 7, 2, 5, 6),
                new Square("C", 10, 3, 8, 9),
                new Square("A", 2, 3, 4, 1))));
    }

    public static void main(String[] args) {
        new PuzzleRunner();
    }
}