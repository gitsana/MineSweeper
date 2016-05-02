package coding_challenge;

import java.util.Random;
import java.util.Scanner;

// class for the Minesweeper game
public class Minesweeper {
    private Board gameboard;
    private int attempts;
    private GameStatus gameStatus;
    private PlayerType playerType;

    // constructor - input the number of rows and columns and num of mines
    public Minesweeper(int gridx, int gridy, int mines) {
        this.gameboard = new Board(gridx, gridy, mines);
        this.attempts = 0;
        this.gameStatus = GameStatus.NEW_GAME;
    }

    // returns a single coordinate, either x or y. Type of player and maxCoordinate number determine how to generate the coordinate
    private int getCoordinate(PlayerType playerType, int maxCoordinateNumber) {
        if (playerType == PlayerType.KEYBOARD_INPUT) {  // for user keyboard input
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter a coordinate to hit (Value must be between 0 and " + (maxCoordinateNumber-1) + ": ");
            return (keyboard.nextInt());
        }
        else { // for Solver
            Random randomGenerator = new Random();
            return (randomGenerator.nextInt(maxCoordinateNumber));
        }

    }

    // this game is designed just for the solver
    public void startSolverGame() {
        // start with random coordinates
        this.playerType = PlayerType.SOLVER;
        int x = getCoordinate(playerType, gameboard.getBoardRows());
        int y = getCoordinate(playerType, gameboard.getBoardColumns());
        BoardCell currentCell = gameboard.getBoardCellByCoordinates(x, y);

        while ((gameStatus == GameStatus.IN_PROGRESS) || (gameStatus == GameStatus.NEW_GAME)) {
            gameboard.uncoverCell(x, y);
            if (gameboard.isMineHit()) {
                gameStatus = GameStatus.PLAYER_LOST;
                break;
            }
            if (gameboard.getNumMines() == gameboard.getNumCoveredCells()) {
                gameStatus = GameStatus.PLAYER_WON;
                break;
            }

            // if neither lost or won, continue and figure out next cell to uncover
            // if there is more 50% chance of hitting a mine, we'd rather take our chances and randomly hit somewhere else
            if ((currentCell.getNumberAdjacentUncoveredCells() > 0) && (currentCell.probabilityAdjacentUncoveredCellIsMine() <= 0.5)) {
                // hit first uncovered cell ( get x, y coords for uncovering cell)
                currentCell = currentCell.getFirstUncoveredAdjacentCell();
                x = currentCell.getxCoordinate();
                y = currentCell.getyCoordinate();
            }
            else {
                // randomly hit somewhere on the board - TODO optimize so it doesn't repeat the same place
                x = getCoordinate(playerType, gameboard.getBoardRows());
                y = getCoordinate(playerType, gameboard.getBoardColumns());
                currentCell = gameboard.getBoardCellByCoordinates(x, y);
            }
        }
    }

    // this game is for a player or a less optimal solver which has a random strategy
    public void startGame(PlayerType playerType) {

        this.playerType = playerType; // either keyboard user or solver
        // if playerType is a user, give directions on input console; otherwise, no need.
        if (playerType == PlayerType.KEYBOARD_INPUT) {
            System.out.println("Starting the Minesweeper game. Press -99 for both coordinates to quit the game. Below is your board.");
            gameboard.printBoardToPlayer();
            //System.out.println("exposed:");
            //gameboard.printBoardExposed();    // for debugging
        }

        while ((gameStatus == GameStatus.IN_PROGRESS) || (gameStatus == GameStatus.NEW_GAME)) {

            int x = getCoordinate(playerType, gameboard.getBoardRows());
            int y = getCoordinate(playerType, gameboard.getBoardColumns());

            if ((x == -99) && (y == -99) && (playerType == PlayerType.KEYBOARD_INPUT)) {
                gameStatus = GameStatus.GAME_QUIT;
                break;
            }
            else if (!gameboard.isValidCoordinateClicked(x,y) && (playerType == PlayerType.KEYBOARD_INPUT)){
                System.out.println("Coordinates are not in the acceptable range - please try again.");
            }
            else {
                gameboard.uncoverCell(x, y);
                attempts++;

                if (gameboard.isMineHit()) {
                    gameStatus = GameStatus.PLAYER_LOST;
                    if (playerType == PlayerType.KEYBOARD_INPUT) {
                        showStats();
                    }
                    break;
                }
                if (gameboard.getNumMines() == gameboard.getNumCoveredCells()) {
                    gameStatus = GameStatus.PLAYER_WON;
                    if (playerType == PlayerType.KEYBOARD_INPUT) {
                        showStats();
                    }
                    break;
                }

                if (playerType == PlayerType.KEYBOARD_INPUT) {
                    System.out.println("player board");
                    gameboard.printBoardToPlayer();
                    System.out.println("exposed board:");
                    gameboard.printBoardExposed(); //debugging
                }
            } // else

        } // while loop
    }

    // for the end of the game, shows the status of the game, how many uncovering attempts were made, and a printout of the exposed board
    private void showStats() {
        if (playerType == PlayerType.KEYBOARD_INPUT) { // show user information
            System.out.println("Game status: " + gameStatus + "\tAttempts: " + attempts + "\nCurrent board state, showing mines:");
            gameboard.printBoardExposed();
        }
    }

    public Board getGameboard() {
        return gameboard;
    }

    public void setGameboard(Board gameboard) {
        this.gameboard = gameboard;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
