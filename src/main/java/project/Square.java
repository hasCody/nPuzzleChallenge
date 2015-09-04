package  project;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Michael Hoglan
 */
public class Square {
    private String id;

    private static final int EDGE_WILDCARD = -1;

    // EnumMap guarantees ordering of .values() based on natural ordering of Enum values
    private Map<SquareEdgeOrientation, Integer> squareEdges = new EnumMap<>(SquareEdgeOrientation.class);

    public Square(String id, int edge1, int edge2, int edge3, int edge4) {
        this(id, new int[]{edge1, edge2, edge3, edge4});
    }

    public Square(String id, int[] edges) {
        this.id = id;
        squareEdges.put(SquareEdgeOrientation.N, edges[0]);
        squareEdges.put(SquareEdgeOrientation.E, edges[1]);
        squareEdges.put(SquareEdgeOrientation.S, edges[2]);
        squareEdges.put(SquareEdgeOrientation.W, edges[3]);
    }

    public Square(Square square) {
        this(square.getId(), square.getEdge(SquareEdgeOrientation.N), square.getEdge(SquareEdgeOrientation.E), square.getEdge(SquareEdgeOrientation.S), square.getEdge(SquareEdgeOrientation.W));
    }

    public Square newCopy() {
        return new Square(this);
    }

    public String getId() {
        return id;
    }

    public void rotate() {
        rotate(1);
    }

    public void rotate(int distance) {
        // No rotation requested
        if(distance == 0) {
            return;
        }

        List<Integer> currentEdges = new ArrayList<>(squareEdges.values());

        Map<SquareEdgeOrientation, Integer> newSquareEdges = new EnumMap<>(SquareEdgeOrientation.class);

        // Rotate the edges
        Collections.rotate(currentEdges, distance);

        // Create new map of edges
        newSquareEdges.put(SquareEdgeOrientation.N, currentEdges.get(0));
        newSquareEdges.put(SquareEdgeOrientation.E, currentEdges.get(1));
        newSquareEdges.put(SquareEdgeOrientation.S, currentEdges.get(2));
        newSquareEdges.put(SquareEdgeOrientation.W, currentEdges.get(3));

        squareEdges = newSquareEdges;
    }

    // TODO do something with this... basically check mask find number rotations, rotate
    public void rotate(int N, int E, int S, int W) {

        List<Integer> wantedEdges = PuzzleUtils.createListFromArray(new int[]{N, E, S, W});

        List<Integer> currentEdges = new ArrayList<>(squareEdges.values());

        if (currentEdges.size() != wantedEdges.size()) {
            return;
        }

        // Clear out any edges which are wildcard (-1) to allow comparison
        for (int i = 0; i < wantedEdges.size(); i++) {
            if (wantedEdges.get(i).equals(EDGE_WILDCARD)) {
                currentEdges.set(i, EDGE_WILDCARD);
            }
        }

        if (currentEdges.equals(wantedEdges)) {
            return;
        }


        // Try rotating the list up to size - 1 times and checking equal
        for (int r = 1; r < wantedEdges.size(); r++) {
            // Reset currentEdges since cleared from wildcards earlier
            currentEdges = new ArrayList<>(squareEdges.values());

            // Rotate currentEdges by distance of loop counter
            Collections.rotate(currentEdges, r);

            // Clear out any edges which are wildcard (-1) to allow comparison
            for (int i = 0; i < wantedEdges.size(); i++) {
                if (wantedEdges.get(i).equals(EDGE_WILDCARD)) {
                    currentEdges.set(i, EDGE_WILDCARD);
                }
            }

            if (currentEdges.equals(wantedEdges)) {
                rotate(r);
                return;
            }
        }

        // No rotation occurred... does not match
        return;
    }

    public int getEdge(SquareEdgeOrientation edgeOrientation) {
        return squareEdges.get(edgeOrientation);
    }

    public int[] getEdges() {
        return Ints.toArray(squareEdges.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(id, square.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t" + squareEdges.get(SquareEdgeOrientation.N) + "\t\t" + System.lineSeparator());
        sb.append("\t\t\t\t" + System.lineSeparator());
        sb.append("\t" + squareEdges.get(SquareEdgeOrientation.W) + "\t" + id + "\t" + squareEdges.get(SquareEdgeOrientation.E) + "\t" + System.lineSeparator());
        sb.append("\t\t\t\t" + System.lineSeparator());
        sb.append("\t\t" + squareEdges.get(SquareEdgeOrientation.S) + "\t\t" + System.lineSeparator());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}