package CSCI1933P2;

public class Rook extends Piece{

    public Rook(int row, int col, boolean isBlack){

        super.row = row;


        super.col = col;


        super.isBlack = isBlack;


        if (isBlack){
            //Black queen
            super.representation = '\u265C';
        }
        else{
            // White queen
            super.representation = '\u2656';
        }

    }


    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        // check if the move is vertical or horizontal.
        if (endRow == row || endCol == col) {
            if (board.verifyVertical(row, col, endRow, endCol) || board.verifyHorizontal(row, col, endRow, endCol)) {
                // Case 1: Moving to an empty square.
                if (board.getPiece(endRow, endCol) == null) {
                    return true;
                }
                // Case 2: Capturing a piece.
                if (board.getPiece(endRow, endCol) != null && board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) {
                    return true;
                }
            }
        }
        // Case 3: Illegal move.
        return false;
    }
}
