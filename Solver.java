package coding_challenge;

// class to automatically solve MineSweeper puzzles
public class Solver {
    private int solverAttempts;
    private int solverPuzzlesSolved;
    long timeElapsed;
    private static int NUM_ROWS = 10;
    private static int NUM_COLS = 10;
    private static int NUM_MINES = 10;
    private static int NUM_ATTEMPTS_ALLOWED = 100000;

    public Solver() {
        this.solverAttempts = 0;
        this.solverPuzzlesSolved = 0;
    }

    public void startSolving() {

        long startTime = System.nanoTime();
        while (solverAttempts < NUM_ATTEMPTS_ALLOWED) {
            Minesweeper minesweeper = new Minesweeper(NUM_ROWS, NUM_COLS, NUM_MINES);
            //minesweeper.startGame(PlayerType.SOLVER); // switch to this "startGame" to see the difference between solver success rate
            minesweeper.startSolverGame();  // this holds the logic for the solver game - it will continue until the game is finished and we can get the game status
            solverAttempts++;
            if (minesweeper.getGameStatus() == GameStatus.PLAYER_WON)   // increment puzzlesSolved if the game was won
                solverPuzzlesSolved++;
        }
        long endTime = System.nanoTime();
        this.timeElapsed = endTime - startTime;

        System.out.println("solverAttempts: " + solverAttempts + "\nsolverPuzzlesSolved: " + solverPuzzlesSolved +
            "\nTime elapsed: " + timeElapsed + " nanoseconds.");
    }
}
