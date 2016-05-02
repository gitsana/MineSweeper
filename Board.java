package coding_challenge;

import java.util.Random;

public class Board {

    private char[][] board;
    private int x;
    private int y;
    private int numMines;
    private int numCoveredCells;
    private boolean isMineHit;

    public Board(int x, int y, int numMines) {
        // TODO add some error checking for valid x, y, numMines values greater than or equal to 1
        // TODO numMines < x * y (assumes board must have at least one non-mine cell)
        this.x = x;
        this.y = y;
        this.numMines = numMines;
        this.isMineHit = false;
        this.numCoveredCells = x * y;
        this.board = new char[x][y];
        initializeBoard(x, y, numMines);
    }

    public void printBoardShowingMines() {
        for (int i=0; i < x; i++) {
            for (int j=0; j < y; j ++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printBoardWithoutMines() {
        for (int i=0; i < x; i++) {
            for (int j=0; j < y; j ++) {
                if (board[i][j] == 'M')
                    System.out.print("X ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void uncoverCell(int clickedX, int clickedY) {
        if (isValidCoordinateClicked(clickedX, clickedY) == false) {
            System.out.println("Please select a valid coordinate to uncover. It must be within the range " + x + " by " + y);
        }
        else if (isMineHit){
            System.out.println("This board doesn't uncover cells after a mine has been hit.");
        }
        else if (board[clickedX][clickedY] == 'X') {
            board[clickedX][clickedY] = '.';
            numCoveredCells--;
            System.out.println("Luckily, you hit an empty cell:");
        }
        else if (board[clickedX][clickedY] == 'M') {
            numCoveredCells--;
            isMineHit = true;
            System.out.println("Oops, you hit a mine!");
        }
        else {
            System.out.println("The mine location has already been hit at " + clickedX + ", " + clickedY);
        }
        //printBoardShowingMines();
        //printBoardWithoutMines();
    }

    public boolean isValidCoordinateClicked(int xClick, int yClick) {
        if ((xClick >= 0) && (xClick < x) && (yClick >= 0) && (yClick < y) )
            return true;
        else
            return false;
    }

    // populate the board with all X's and random mine locations based on number of mines
    private void initializeBoard(int x, int y, int numMines) {
        // put in all X's first
        for (int i=0; i < x; i++) {
            for (int j=0; j < y; j++) {
                board[i][j] = 'X';
            }
        }
        // add in mines randomly
        Random randomGenerator = new Random();
        for (int i=0; i < numMines; i++) {
            int randomX = randomGenerator.nextInt(x);
            int randomY = randomGenerator.nextInt(y);
            board[randomX][randomY] = 'M';
            // TODO add checking to make sure same mine was not generated twice in same place
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public int getNumCoveredCells() {
        return numCoveredCells;
    }

    public void setNumCoveredCells(int numCoveredCells) {
        this.numCoveredCells = numCoveredCells;
    }

    public boolean isMineHit() {
        return isMineHit;
    }

    public void setMineHit(boolean mineHit) {
        isMineHit = mineHit;
    }
} // Board
