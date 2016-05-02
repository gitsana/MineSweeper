package coding_challenge;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private Board gameboard;
    private int attempts;
    private GameStatus gameStatus;
    private PlayerType playerType;

    public Minesweeper(int gridx, int gridy, int mines) {
        this.gameboard = new Board(gridx, gridy, mines);
        this.attempts = 0;
        this.gameStatus = GameStatus.NEW_GAME;
    }

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

    public void startGame(PlayerType playerType) {

        this.playerType = playerType; // either keyboard user or solver
        // if playerType is a user, give directions on input console; otherwise, no need.
        if (playerType == PlayerType.KEYBOARD_INPUT) {
            System.out.println("Starting the Minesweeper game. Press -99 for both coordinates to quit the game. Below is your board.");
            gameboard.printBoardWithoutMines();
        }

        while ((gameStatus == GameStatus.IN_PROGRESS) || (gameStatus == GameStatus.NEW_GAME)) {

            int x = getCoordinate(playerType, gameboard.getX());
            int y = getCoordinate(playerType, gameboard.getY());

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
                    gameboard.printBoardWithoutMines();
                }
            }

        } // while loop
    }

    private void showStats() {
        if (playerType == PlayerType.KEYBOARD_INPUT) { // show user information
            System.out.println("Game status: " + gameStatus + "\tAttempts: " + attempts + "\nCurrent board state, showing mines:");
            gameboard.printBoardShowingMines();
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
