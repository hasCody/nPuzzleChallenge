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


# Challenge

### Implement a PuzzleWorker which can find a solution state 
There is already a PuzzleWorker that can do this.  

We can stub out the current PuzzleWorker and have teams bootstrapped to work on the RUN

The utility classes are still available which allow different operations.  The logic in the PuzzleWorker is mainly using these different utility classes to know how to operate on the PuzzleState (the board).

__Working on having the utility / puzzle board accessible via REST service, then teams can implement their 'PuzzleWorker' in whatever langauge__

__Or people live with my Java code :)__

### Implement a PuzzleWorker which can determine how to transition from starting state to solution state
Once a solution state is found, how do you go from START -> SOLUTION.  List of 'operations'

* SWAP
* ROTATE
* MOVE

### Assortment of starting puzzle states
Puzzles can be different sizes, for instance a 2x2 can be solved by hand.  4x4 not so.
Puzzles can defined which are solveable without different operations, for instance no rotating is needed to solve one.

### Puzzles which require specific features
As 'features' are unlocked, the team can be given a new puzzle state which requires the need for the feature.  Give the team a puzzle that requires rotation.

Utility classes will already have the support for helping with the operations.

Can have a file (similar to license file) that allows the utility class to allow that operation.



