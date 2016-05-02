package coding_challenge;

import java.util.ArrayList;
import java.util.List;

public class BoardCell {
    private int xCoordinate;
    private int yCoordinate;
    private List<BoardCell> adjacentCellsList = new ArrayList<>();
    private int numAdjacentMines;
    private boolean isUncoveredCell;
    private boolean isMine;
    private String showToPlayer;

    // constructor
    public BoardCell(int xCoordinate, int yCoordinate, int numAdjacentMines,
                     boolean isUncoveredCell, boolean isMine) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.numAdjacentMines = numAdjacentMines;
        this.isUncoveredCell = isUncoveredCell;
        this.isMine = isMine;
        this.showToPlayer = "X";

    }

    // the probability that a random adjacent uncovered cell will be a mine for this cell
    public long probabilityAdjacentUncoveredCellIsMine() {
        // uncovered surrounding cells keep changing, so need to calculate
        int countAdjacentUncoveredCells = getNumberAdjacentUncoveredCells();
        return (numAdjacentMines/countAdjacentUncoveredCells);
    }

    // uncovered surrounding cells keep changing and we don't update on each uncovering of cell, so need to calculate when the info is needed
    public int getNumberAdjacentUncoveredCells() {
        int countAdjacentUncoveredCells = 0;
        for (BoardCell adjCell : adjacentCellsList) {
            if (adjCell.isUncoveredCell())
                countAdjacentUncoveredCells++;
        }

        return (countAdjacentUncoveredCells);
    }

    // find out the first uncovered cell we can relative to another cell
    public BoardCell getFirstUncoveredAdjacentCell() {
        for (BoardCell adjCell : adjacentCellsList) {
            if (adjCell.isUncoveredCell())
                return adjCell;
        }
        return null;    // if there is no uncovered adjacent cell
    }

    // when we find out this cell has another mine next to it
    public void incrementNumAdjacentMines() {
        this.numAdjacentMines++;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }


    public int getNumAdjacentMines() {
        return numAdjacentMines;
    }

    public void setNumAdjacentMines(int numAdjacentMines) {
        this.numAdjacentMines = numAdjacentMines;
    }

    public boolean isUncoveredCell() {
        return isUncoveredCell;
    }

    public void setUncoveredCell(boolean uncoveredCell) {
        isUncoveredCell = uncoveredCell;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public List<BoardCell> getAdjacentCellsList() {
        return adjacentCellsList;
    }

    public void setAdjacentCellsList(List<BoardCell> adjacentCellsList) {
        this.adjacentCellsList = adjacentCellsList;
    }

    public String getShowToPlayer() {
        return showToPlayer;
    }

    public void setShowToPlayer(String showToPlayer) {
        this.showToPlayer = showToPlayer;
    }
}
