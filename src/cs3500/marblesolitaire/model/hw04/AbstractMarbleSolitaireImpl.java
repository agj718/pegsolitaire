package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import java.util.ArrayList;

/**
 * The parent abstract class for the English, European and Triangle Marble Solitaire Model
 * implementations. This class implements MarbleSolitaireModel.
 */
abstract public class AbstractMarbleSolitaireImpl implements MarbleSolitaireModel {


  /**
   * An enum that consists of three char values: ' ', '_' and 'O'.
   */
  protected enum Pieces {
    S(' '), P('O'), E('_');
    public char type;

    Pieces(char type) {
      this.type = type;
    }
  }

  @Override
  public boolean isGameOver() {
    return this.getScore() == 1 || this.noValidMoves();
  }

  @Override
  /**
   * @throws IllegalArgumentException if any of the given arguments is out of bounds of the board
   * @throws IllegalArgumentException if the from coordinates are empty or if the to
   *          coordinates contain a piece
   * @throws IllegalArgumentException if the to and from coordinates are the same
   * @throws IllegalArgumentException if the from piece isn't exactly two spaces away
   * @throws IllegalArgumentException if the piece in between the start and the destination is empty
   * @throws IllegalArgumentException if the move is diagonal
   */
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    this.validMoveHelper(fromRow, fromCol, toRow, toCol);
    if (fromRow == toRow) {
      this.getBoard().get(fromRow).set(fromCol, Pieces.E);
      this.getBoard().get(fromRow).set(toCol, Pieces.P);
      this.getBoard().get(fromRow).set((toCol + fromCol) / 2, Pieces.E);
    } else if (fromCol == toCol) {
      this.getBoard().get(fromRow).set(fromCol, Pieces.E);
      this.getBoard().get(toRow).set(fromCol, Pieces.P);
      this.getBoard().get((toRow + fromRow) / 2).set(fromCol, Pieces.E);
    } else {
      throw new IllegalArgumentException("No diagonals");
    }
  }

  /**
   * Determines if a single move is valid.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow the row number of the position to be moved to (starts at 0)
   * @param toCol the column number of the position to be moved to (starts at 0)
   * @return true if the move is valid
   */
  protected boolean validMove(int fromRow, int fromCol, int toRow, int toCol) {
    boolean status = true;
    try {
      this.validMoveHelper(fromRow, fromCol, toRow, toCol);
    } catch (IllegalArgumentException e) {
      status = false;
    }
    return status;

  }

  @Override
  public String getGameState() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.getBoard().size(); i++) {
      if (i > 0 && i <= this.getBoard().size()) {
        result.append("\n");
      }
      for (int j = 0; j < this.getBoard().get(i).size(); j++) {

        if (j != this.getBoard().get(i).size() - 1) {
          result.append(this.getBoard().get(i).get(j).type + " ");
        } else {
          result.append(this.getBoard().get(i).get(j).type);
        }
      }
    }
    return result.toString();
  }


  /**
   * This function serves as a helper for the validMove and move functions. It handles all of the
   * possible exceptions.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow the row number of the position to be moved to (starts at 0)
   * @param toCol the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException if any of the given arguments is out of bounds of the board
   * @throws IllegalArgumentException if the from coordinates are empty or if the to coordinates
   *          contain a piece
   * @throws IllegalArgumentException if the to and from coordinates are the same
   * @throws IllegalArgumentException if the from piece isn't exactly two spaces away
   * @throws IllegalArgumentException if the piece in between the start and the destination is
   *          empty
   */
  protected void validMoveHelper(int fromRow, int fromCol, int toRow, int toCol)
      throws IllegalArgumentException {
    if (fromRow < 0 || toRow < 0 || fromCol < 0 || toCol < 0
        || fromRow > this.getBoard().size() - 1 || toRow > this.getBoard().size() - 1
        || fromCol > this.getBoard().get(fromRow).size() - 1
        || toCol > this.getBoard().get(toRow).size() - 1) {
      throw new IllegalArgumentException("Out of bounds");
    }
    Pieces dest = this.getBoard().get(toRow).get(toCol);
    Pieces start = this.getBoard().get(fromRow).get(fromCol);
    if (dest.type == 'O' || dest.type == ' ' || start.type == ' ' || start.type == '_') {
      throw new IllegalArgumentException("Starting place or destination place is illegal");
    }
    if (fromRow == toRow && fromCol == toCol) {
      throw new IllegalArgumentException("Same piece!");
    } else {
      if (fromRow == toRow) {
        if (Math.abs(fromCol - toCol) != 2) {
          throw new IllegalArgumentException("Needs to be two spaces away");
        }
        if (this.getBoard().get(fromRow).get((toCol + fromCol) / 2).type != 'O') {
          throw new IllegalArgumentException("Has to jump over another piece");
        }
      } else if (fromCol == toCol) {
        if (Math.abs(fromRow - toRow) != 2) {
          throw new IllegalArgumentException("Needs to be two spaces away");
        }
        if (this.getBoard().get((toRow + fromRow) / 2).get(fromCol).type != 'O') {
          throw new IllegalArgumentException("Has to jump over another piece");
        }
      }
    }
  }

  /**
   * Determines if there are no possible valid moves left.
   *
   * @return true if the board does not have valid moves
   */

  protected boolean noValidMoves() {
    boolean status = true;
    boolean boolStat;
    for (int i = 0; i < this.getBoard().size(); i++) {
      for (int j = 0; j < this.getBoard().get(i).size(); j++) {
        if (this.getBoard().get(i).get(j).type == 'O') {
          boolStat = (!validMove(i, j, i + 2, j)) && (!validMove(i, j, i - 2, j))
              && (!validMove(i, j, i, j + 2)) && (!validMove(i, j, i, j - 2));
          status = status && boolStat;
        }

      }
    }
    return status;
  }

  @Override
  public int getScore() {
    int pieces = 0;
    for (int i = 0; i < this.getBoard().size(); i++) {
      for (int j = 0; j < this.getBoard().get(i).size(); j++) {
        if (this.getBoard().get(i).get(j).type == 'O') {
          pieces += 1;
        }
      }
    }
    return pieces;
  }

  /**
   * Returns true if the coordinate pair is not within the needed shape.
   *
   * @param i position of the row
   * @param j position of the column
   * @return true if not within the limits
   */
  protected abstract boolean limit(int i, int j);

  /**
   * Produces a 2D ArrayList which represents the game board.
   *
   * @return the gameboard represented in a 2D ArrayList
   */
  protected abstract ArrayList<ArrayList<Pieces>> boardState();

  protected abstract ArrayList<ArrayList<Pieces>> getBoard();
}
