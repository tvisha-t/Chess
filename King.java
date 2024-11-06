package CSCI1933P2;

public class King extends Piece{

    public King(int row, int col, boolean isBlack){

        super.row = row;


        super.col = col;


        super.isBlack = isBlack;


        if (isBlack){
            //Black queen
            super.representation = '\u265A';
        }
        else{
            // White queen
            super.representation = '\u2654';
        }

    }


    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        // Check if the move is one square in any direction.
        if (Math.abs(endRow - row) <= 1 && Math.abs(endCol - col) <= 1) {
            // Case 1: Moving to an empty square.
            if (board.getPiece(endRow, endCol) == null) {
                return true;
            }
            // Case 2: Capturing a piece.
            if (board.getPiece(endRow, endCol) != null && board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) {
                return true;
            }
        }
        // Case 3: Illegal move.
        return false;
    }
}
