package coding_challenge;

import java.util.Scanner;

// use this for the player on input keyboard to play Minesweeper
public class MainPlayer {
    public static void main(String[] args) {

        // user input by keyboard
        Scanner scanner = new Scanner(System.in);
        boolean continuePlaying = true;

        while (continuePlaying) {
            System.out.println("How many rows do you want in the minesweeper grid? ");
            int rows = scanner.nextInt();
            System.out.println("How many columns do you want in the minesweeper grid? ");
            int cols = scanner.nextInt();
            System.out.println("How many mines do you want in the minesweeper grid? (enter a value less than " + (rows * cols) + "): ");
            int mines = scanner.nextInt();
            Minesweeper minesweeper = new Minesweeper(rows, cols, mines);
            minesweeper.startGame(PlayerType.KEYBOARD_INPUT);

            System.out.println("Would you like to play another game? yes/no");
            String playMore = scanner.next();

            if ((playMore.equals("n")) || (playMore.equals("no"))){
                continuePlaying = false;
                System.out.println("Thanks for playing. Quitting minesweeper for now.");
            }

        }


    } // psvm
} // class
