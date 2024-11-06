package CSCI1933P2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean isBlackTurn;  // false for White's turn, true for Black's turn

    /**
     * Constructor initializes a new game with a board and sets the starting player to white.
     */
    public Game(String fen) {
        board = new Board();
        Fen.load(fen, board);  // loads the board with the FEN code
        isBlackTurn = false;  // white starts the game
    }

    /**
     * Main method to start a game session.
     */
    public static void main(String[] args) {

        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        Game game = new Game(fen);
        game.play();
    }

    /**
     * Starts the main game loop where players take turns making moves.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!board.isGameOver()) {
            //prints out board at beggining of each turn
            System.out.println(board.toString());
            System.out.println("It is currently " + (isBlackTurn ? "black's" : "white's") + " turn to play.");
            System.out.print("What is your move? (format: [start row] [start col] [end row] [end col])\n");

            // read the user input (includes error handling for invalid position/datatype choice)
            int startRow, startCol, endRow, endCol;

            try {
                startRow = scanner.nextInt();
                startCol = scanner.nextInt();
                endRow = scanner.nextInt();
                endCol = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter four integers.");
                scanner.nextLine(); // clear the invalid input
                continue; // skip to the next iteration of the loop
            }

            // check that the selected piece belongs to the player
            Piece piece = board.getPiece(startRow, startCol);
            if (piece == null || piece.getIsBlack() != isBlackTurn) {
                System.out.println("Invalid selection. Please select a " + (isBlackTurn ? "black" : "white") + " piece.");
                continue;
            }

            //check if piece is available for promotion
            if (board.movePiece(startRow, startCol, endRow, endCol)) {
                // Promote the pawn if applicable
                if (piece instanceof Pawn) {
                    // check if the pawn is on the promotion square
                    if ((!isBlackTurn && endRow == 0) || (isBlackTurn && endRow == 7)) {
                        ((Pawn) piece).promotePawn(board, endRow, endCol, isBlackTurn);
                    }
                }

                // check for game over condition
                if (board.isGameOver()) {
                    System.out.println((isBlackTurn ? "Black" : "White") + " has won the game!");
                    break;
                }

                isBlackTurn = !isBlackTurn;  // switch turns
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        scanner.close();
    }
}
