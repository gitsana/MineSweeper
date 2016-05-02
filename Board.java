package coding_challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private BoardCell[][] board;
    private int boardRows;
    private int boardColumns;
    private int numMines;
    private int numCoveredCells;
    private boolean isMineHit;

    public Board(int rows, int cols, int numMines) {
        // TODO add some error checking for valid boardRows, boardColumns, numMines values greater than or equal to 2
        // TODO numMines < boardRows * boardColumns (assumes board must have at least one non-mine cell)
        this.boardRows = rows;
        this.boardColumns = cols;
        this.numMines = numMines;
        this.numCoveredCells = rows * cols;
        this.board = new BoardCell[rows][cols];
        initializeBoard();
        addMinesToBoard();
    }

    // shows only information that player should see on each round
    public void printBoardToPlayer() {
        for (int i = 0; i < boardRows; i++) {
            for (int j = 0; j < boardColumns; j ++) {
                System.out.print(board[i][j].getShowToPlayer() + " ");
            }
            System.out.println();
        }
    }

    // show all information (for the end and debugging) -- includes mine locations and number of adjacent mines, whether or not cell is covered
    public void printBoardExposed() {
        for (int i = 0; i < boardRows; i++) {
            for (int j = 0; j < boardColumns; j ++) {
                if (board[i][j].isMine())
                    System.out.print('M' + " ");
                else if (board[i][j].isUncoveredCell())
                    System.out.print(board[i][j].getShowToPlayer() + " ");
                else // not uncovered cell
                    System.out.print(board[i][j].getNumAdjacentMines() + " ");
            }
            System.out.println();
        }
    }

    // uncover a cell by its coordinates, and if it is a mine, then update whether a mine has been hit
    public void uncoverCell(int clickedX, int clickedY) {
        if (isValidCoordinateClicked(clickedX, clickedY) == false) {
            System.out.println("Please select a valid coordinate to uncover. It must be within the range " + boardRows + " by " + boardColumns);
        }
        else {
            board[clickedX][clickedY].setUncoveredCell(true);
            numCoveredCells--;
            String adjMines = String.valueOf(board[clickedX][clickedY].getNumAdjacentMines());
            board[clickedX][clickedY].setShowToPlayer(adjMines);

            if (board[clickedX][clickedY].isMine() ) {
                isMineHit = true;
                board[clickedX][clickedY].setShowToPlayer("M");
            }

        }
    }

    // make sure a valid coordinate is chosen to uncover
    public boolean isValidCoordinateClicked(int xClick, int yClick) {
        if ((xClick >= 0) && (xClick < boardRows) && (yClick >= 0) && (yClick < boardColumns) )
            return true;
        else
            return false;
    }

    // get a board cell relative to another cell
    // assume error-checking/invalid value check done before calling this function
    public BoardCell getBoardCellByCoordinatesAndDirection(int x, int y, CellDirection direction) {
        if (direction == CellDirection.N)
            return board[x-1][y];
        if (direction == CellDirection.NE)
            return board[x-1][y+1];
        if (direction == CellDirection.E)
            return board[x][y+1];
        if (direction == CellDirection.SE)
            return board[x+1][y+1];
        if (direction == CellDirection.S)
            return board[x+1][y];
        if (direction == CellDirection.SW)
            return board[x+1][y-1];
        if (direction == CellDirection.W)
            return board[x][y-1];
        if (direction == CellDirection.NW)
            return board[x-1][y-1];

        return null;
    }

    // get a board cell by its coordinates
    public BoardCell getBoardCellByCoordinates(int x, int y) {
        return board[x][y];
    }

    // populate the board with all initial values
    private void initializeBoard() {
        // put in all initial values first
        for (int i=0; i < boardRows; i++) {
            for (int j=0; j < boardColumns; j++) {
                // initialize an empty board cell
                board[i][j] = new BoardCell(i, j, 0, false, false);
            }
        }

        // add neighboring arrays into list
        for (int i=0; i < boardRows; i++) {
            for (int j=0; j < boardColumns; j++) {
                // initialize an empty board cell
                List<BoardCell> adjacentCellsList = new ArrayList<>();
                // add adjacent cells
                if ((i == 0) && (j == 0)) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                }
                else if ((i == 0) && (j == boardColumns-1)) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                }
                else if ((i == boardRows-1) && (j == 0)) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                }
                else if ((i == boardRows-1) && (j == boardColumns-1)) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NW));
                }
                else if ( i == 0 ) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                }
                else if ( j == 0 ) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                }
                else if ( i == boardRows-1 ) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                }
                else if ( j == boardColumns-1 ) {
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                }
                else { // add all 8 adjacent cells
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.N));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.W));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SW));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.S));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.NE));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.E));
                    adjacentCellsList.add(getBoardCellByCoordinatesAndDirection(i, j, CellDirection.SE));
                }
                board[i][j].setAdjacentCellsList(adjacentCellsList);
            }
        }
    }

    // populates the initial board with mines up to the number of mines specified
    public void addMinesToBoard() {
        // add in mines randomly
        Random randomGenerator = new Random();
        for (int i=0; i < numMines; i++) {
            int randomX = randomGenerator.nextInt(boardRows);
            int randomY = randomGenerator.nextInt(boardColumns);

            // add checking to make sure same mine was not generated twice in same place
            if (board[randomX][randomY].isMine() == false) {
                board[randomX][randomY].setMine(true); // set this cell's property so it is a mine

                // increment surrounding cells' numAdjacentMines property
                for (BoardCell adjCell : board[randomX][randomY].getAdjacentCellsList()) {
                    //System.out.println("Incrementing adjacent mine count for (" + randomX + ", " + randomY + ")");
                    adjCell.incrementNumAdjacentMines();
                }
            }
            else
                i--;    // if it's already a mine, try again
        }
    }


    public int getBoardRows() {
        return boardRows;
    }

    public void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    public int getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(int boardColumns) {
        this.boardColumns = boardColumns;
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
