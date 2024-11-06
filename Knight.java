package CSCI1933P2;

public class Knight extends Piece{

    public Knight(int row, int col, boolean isBlack){

        super.row = row;


        super.col = col;


        super.isBlack = isBlack;


        if (isBlack){
            //Black knight
            super.representation = '\u265E';
        }
        else{
            // White knight
            super.representation = '\u2658';
        }

    }


    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        // check if the move is in an L shape.
        if ((Math.abs(endRow - row) == 2 && Math.abs(endCol - col) == 1) ||
                (Math.abs(endRow - row) == 1 && Math.abs(endCol - col) == 2)) {
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
