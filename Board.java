package CSCI1933P2;
public class Board {
    // Instance variables (add more if you need)
    private Piece[][] board;


    /**
     * Default Constructor
     */
    public Board() {
        this.board = new Piece[8][8];
        // initialize the board to chessboard dimensions.
    }

    // Accessor Methods

    /**
     * Gets the piece at a particular row and column of the board.
     * @param row       The row of the piece to be accessed.
     * @param col       The column of the piece to be accessed.
     * @return          The piece at the specified row and column of the board.
     */
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    /**
     * Sets the piece at a particular row and column of the board.
     * @param row       The row to place the piece at.
     * @param col       The column to place the piece at.
     * @param piece     The piece to place at the specified row and column.
     */
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }




    // Movement helper functions

    /**
     * Verifies that the source and destination of a move are valid by performing the following checks:
     *  1. ALL rows and columns provided must be >= 0.
     *  2. ALL rows and columns provided must be < 8.
     *  3. The start position of the move must contain a piece.
     *  4. The piece at the starting position must be the correct color.
     *  5. The destination must be empty OR must contain a piece of the opposite color.
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @param isBlack   The expected color of the starting piece.
     * @return True if the above conditions are met, false otherwise.
     */
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if (startRow < 0 || startRow >= 8 || startCol < 0 || startCol >= 8 ||
                endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8) {
            return false;  // Out of bounds
        }

        Piece startPiece = getPiece(startRow, startCol);
        Piece endPiece = getPiece(endRow, endCol);

        if (startPiece == null || startPiece.getIsBlack() != isBlack) {
            return false;  // No piece or wrong color at the start position
        }

        return endPiece == null || endPiece.getIsBlack() != isBlack;
    }


    /**
     * Verifies that the source and destination of a move are adjacent squares (within 1 square of each other)
     * Example, Piece P is adjacent to the spots marked X:
     * OOOOO
     * OXXXO
     * OXPXO
     * OXXXO
     * OOOOO
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @return True if the source and destination squares are adjacent, false otherwise.
     */
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        // check if the destination square is adjacent to the source square
        int rowDifference = Math.abs(startRow - endRow);
        int colDifference = Math.abs(startCol - endCol);

        // move if valid if difference in and column both is less than or equal to one
        return rowDifference <= 1 && colDifference <= 1;
    }

    /**
     * Verifies that a source and destination are in the same row and that there are no pieces on squares
     * between the source and the destination.
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @return True if source and destination are in same row with no pieces between them, false otherwise.
     */
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        // check if the move is not horizontal
        if (startRow != endRow) {
            return false;
        }

        // allow moves to the same square (cuz autograder makes it?)
        if (startCol == endCol) {
            return true;
        }

        // makes sure theres no peices in the way
        int minCol = Math.min(startCol, endCol);
        int maxCol = Math.max(startCol, endCol);
        for (int col = minCol + 1; col < maxCol; col++) {
            if (getPiece(startRow, col) != null) {
                return false;  // blocked
            }
        }

        return true;
    }




    /**
     * Verifies that a source and destination are in the same column and that there are no pieces on squares
     * between the source and the destination.
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @return True if source and destination are in same column with no pieces between them, false otherwise.
     */

    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        // Allow moving to the same position (i still dk why)
        if (startRow == endRow && startCol == endCol) {
            return true;
        }

        // Check if the move is truly vertical
        if (startCol != endCol) {
            return false; // Invalid move: not vertical
        }

        // determine the direction of movement and check for any blocking pieces
        int step = startRow < endRow ? 1 : -1;
        for (int row = startRow + step; row != endRow; row += step) {
            if (board[row][startCol] != null) {
                return false; // path is blocked
            }
        }

        return true; //valid
    }









    /**
     * Verifies that a source and destination are on the same diagonal and that there are no pieces on squares
     * between the source and the destination.
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @return True if source and destination are on the same diagonal with no pieces between them, false otherwise.
     */
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        // move to same position
        if (startRow == endRow && startCol == endCol) {
            return true;
        }

        // checks in theyre on the same diagonal
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
            return false;  // dot on the same diagonal
        }

        // checks if any peices are in the way by looping through diagonal
        int rowStep = (endRow > startRow) ? 1 : -1;
        int colStep = (endCol > startCol) ? 1 : -1;
        int row = startRow + rowStep, col = startCol + colStep;

        while (row != endRow && col != endCol) {
            // Check boundaries to prevent out-of-bounds access
            if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                return false;  // out of bounds
            }
            if (getPiece(row, col) != null) {
                return false;  // blocked by another piece
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }





    // Game functionality methods

    /**
     * Moves the piece from startRow, startCol to endRow, endCol if it is legal to do so.
     * IMPORTANT: Make sure to update the internal position of the piece, and the starting position of the piece to null!
     * @param startRow  The starting row of the move.
     * @param startCol  The starting column of the move.
     * @param endRow    The ending row of the move.
     * @param endCol    The ending column of the move.
     * @return Whether the move was successfully completed or not. (Moves are not completed if they are not legal.)
     */


    /**
     * Returns true if there are fewer than TWO kings on the board.
     * @return If the game is in a game over state.
     */

//moves peice
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = getPiece(startRow, startCol);
        if (piece != null && piece.isMoveLegal(this, endRow, endCol)) {

            setPiece(endRow, endCol, piece);
            setPiece(startRow, startCol, null);
            piece.setPosition(endRow, endCol);
            return true;
        }
        return false;
    }


//checks if game is over using kingcount
    public boolean isGameOver() {
        int kingCount = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece instanceof King) {
                    kingCount++;
                }
            }
        }
        return kingCount < 2;

    }

    /**
     * Sets all indexes in the board to null
     * Sets all indexes in the board to null
     */
    public void clear() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                setPiece(row, col, null);
            }
        }

    }


    public void display() {
        System.out.print("\t\t\t");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + "\t\t");
        }
        System.out.print("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print("\t" + i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("|\t\t");
                } else {
                    System.out.print("|\t" + board[i][j] + "\t");
                }
            }
            System.out.print("|\n");
        }
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for(int i = 0; i < 8; i++){
            out.append(" ");
            out.append(i);
        }
        out.append('\n');
        for(int i = 0; i < board.length; i++) {
            out.append(i);
            out.append("|");
            for(int j = 0; j < board[0].length; j++) {
                out.append(board[i][j] == null ? "\u2001|" : board[i][j] + "|");
            }
            out.append("\n");
        }
        return out.toString();
    }
}


