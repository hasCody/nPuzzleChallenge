# N Puzzle Challenge
Will solve N puzzles (2x2, 3x3, 4x4, etc...)

Given a set of pieces which are squares, will determine how to place the pieces in a board of the N puzzle.

Squares are defined with an ID and list of integer edges.  For a square to match a square next to it, the edges must match.

Here is a 4 puzzle with squares `[A, B, C, D]`

```
-----------------------------
|      1      |      1      |
|             |             |
|  4   A   2  |  2   B   6  |
|             |             |
|      3      |      7      |
-----------------------------
|      3      |      7      |
|             |             |
|  10  A   8  |  8   B   11 |
|             |             |
|      9      |      12     |
-----------------------------
```

The labels of the squares and edges do not have to be in order, they can be shuffled and the solver will still work.

Edges can repeat which means there are multiple matches.  Think about if each edge was a certain color and there were multiple squares that had that color on that side.  

For instance a 4 puzzle where the squares make a black white picture with a diamond in the middle could be represented like:

```
-----------------------------
|      1      |      1      |
|             |             |
|  1   A   2  |  2   B   1  |
|             |             |
|      2      |      2      |
-----------------------------
|      2      |      2      |
|             |             |
|  1   A   2  |  2   B   1  |
|             |             |
|      1      |      1      |
-----------------------------
```

Typically there are 4x solutions since you could rotate the whole puzzle 4 times.

## Build
Use maven and shaded jar will be made in target

```
mvn clean install test
```

## Usage
Currently the puzzle defintions are in the code in the PuzzleRunner under the loadPuzzles() functon.  Pulling them out to a resource file later.

```
java -jar ./target/nPuzzle-1.0-SNAPSHOT.jar
```



