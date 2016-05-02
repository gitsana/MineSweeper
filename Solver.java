package coding_challenge;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

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
            //System.out.println("\t***** SOLVER ATTEMPT # " + solverAttempts);
            Minesweeper minesweeper = new Minesweeper(NUM_ROWS, NUM_COLS, NUM_MINES);
            minesweeper.startGame(PlayerType.SOLVER);
            solverAttempts++;
            if (minesweeper.getGameStatus() == GameStatus.PLAYER_WON)   // increment puzzlesSolved if the game was won
                solverPuzzlesSolved++;
        }
        long endTime = System.nanoTime();
        this.timeElapsed = endTime - startTime;

        System.out.println("solverAttempts: " + solverAttempts + "\nsolverPuzzlesSolved: " + solverPuzzlesSolved +
            "\nTime elapsed: " + timeElapsed + " nanoseconds.");
        System.out.println(TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS) + " seconds");
    }
}
