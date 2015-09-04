package project;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hoglan
 */
public class PuzzleUtils {

    // Needed because autoboxing to ArrayList from primitive arrays is Java 8
    public static List<Integer> createListFromArray(int[] array) {
        List<Integer> integerList = new ArrayList<>(array.length);

        for(int a : array) {
            integerList.add(a);
        }

        return integerList;
    }
}
