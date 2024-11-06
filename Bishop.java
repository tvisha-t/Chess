package CSCI1933P2;

public class Bishop extends Piece{

    public Bishop(int row, int col, boolean isBlack){

        super.row = row;


        super.col = col;


        super.isBlack = isBlack;


        if (isBlack){
            //Black queen
            super.representation = '\u265D';
        }
        else{
            // White queen
            super.representation = '\u2657';
        }

    }


    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        // check if the move is diagonal and there are no pieces in the way.
        if (Math.abs(endRow - row) == Math.abs(endCol - col) && board.verifyDiagonal(row, col, endRow, endCol)) {
            // case 1: Moving to an empty square
            if (board.getPiece(endRow, endCol) == null) {
                return true;
            }
            // case 2: Capturing a piece
            if (board.getPiece(endRow, endCol) != null && board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) {
                return true;
            }
        }
        // case 3: Illegal move
        return false;
    }
}
