package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;

/**
 * The Triangle Solitaire implementation of Marble Solitaire. This board should be shaped as an
 * triangle.
 */
public class TriangleSolitaireModelImpl extends AbstractMarbleSolitaireImpl {

  private int dim;
  private int sRow;
  private int sCol;
  private ArrayList<ArrayList<Pieces>> board;

  /**
   * The default constructor where the longest row is 5 and the empty slot is at the top of the
   * triangle.
   */
  public TriangleSolitaireModelImpl() {
    dim = 5;
    sRow = 0;
    sCol = 0;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in the dimension of the triangle and puts the empty slot at the top of
   * the triangle.
   *
   * @param dim a positive number that represents the bottom row length
   * @throws IllegalArgumentException if dim is negative or 0
   */
  public TriangleSolitaireModelImpl(int dim) {
    if (dim <= 0) {
      throw new IllegalArgumentException("Invalid Dimensions");
    }
    this.dim = dim;
    this.sCol = 0;
    this.sRow = 0;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in the coordinates for the empty slot and sets the dim to 5.
   *
   * @param sRow a positive number within the triangle
   * @param sCol a positive number within the triangle
   * @throws IllegalArgumentException if the empty slot coordinates don't fall within the shape
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    this.dim = 5;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid Position");
    }
    this.sRow = sRow;
    this.sCol = sCol;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in the dimension of the board and the coordinates for the empty slot.
   *
   * @param dim a positive number that represents the bottom row length
   * @param sRow a positive number within the triangle
   * @param sCol a positive number within the triangle
   * @throws IllegalArgumentException if dim is negative or 0
   * @throws IllegalArgumentException if the empty slot coordinates don't fall within the shape
   */
  public TriangleSolitaireModelImpl(int dim, int sRow, int sCol) {
    if (dim <= 0) {
      throw new IllegalArgumentException("Invalid Dimensions");
    }
    this.dim = dim;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException("Invalid Position");
    }
    this.sRow = sRow;
    this.sCol = sCol;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }


  @Override
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
      this.getBoard().get(fromRow).set(fromCol, Pieces.E);
      this.getBoard().get(toRow).set(toCol, Pieces.P);
      this.getBoard().get((toRow + fromRow) / 2).set(((toCol + fromCol) / 2), Pieces.E);
    }
  }

  @Override
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
      } else if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
        if (this.getBoard().get((toRow + fromRow) / 2).get(((toCol + fromCol) / 2)).type != 'O') {
          throw new IllegalArgumentException("Has to jump over another piece");
        }
      } else {
        throw new IllegalArgumentException("Not a valid move");
      }
    }
  }


  @Override
  public String getGameState() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.board.size(); i++) {
      if (i > 0 && i <= this.board.size()) {
        result.append("\n");
      }
      for (int j = 0; j < this.board.get(i).size(); j++) {
        if (j != 0) {
          result.append(" " + this.board.get(i).get(j).type);
        } else {
          int spaceCount = dim - i - 1;
          while (spaceCount > 0) {
            result.append(" ");
            spaceCount--;
          }
          result.append(this.board.get(i).get(j).type);

        }
      }
    }
    return result.toString();
  }

  @Override
  protected boolean noValidMoves() {
    boolean status = true;
    boolean boolStat;
    for (int i = 0; i < this.getBoard().size(); i++) {
      for (int j = 0; j < this.getBoard().get(i).size(); j++) {
        if (this.getBoard().get(i).get(j).type == 'O') {
          boolStat = (!validMove(i, j, i + 2, j)) && (!validMove(i, j, i - 2, j))
              && (!validMove(i, j, i, j + 2)) && (!validMove(i, j, i, j - 2)) &&
              (!validMove(i, j, i + 2, j + 2)) && (!validMove(i, j, i - 2, j - 2));
          status = status && boolStat;
        }

      }
    }
    return status;
  }

  @Override
  protected boolean limit(int i, int j) {
    return (i < 0 || i >= this.boardState().size() ||
        j < 0 || j >= this.boardState().get(i).size());
  }


  @Override
  protected ArrayList<ArrayList<Pieces>> boardState() {
    ArrayList<ArrayList<Pieces>> boardPieces = new ArrayList<ArrayList<Pieces>>();
    for (int i = 0; i < dim; i++) {
      boardPieces.add(new ArrayList<Pieces>());
      for (int j = 0; j <= i; j++) {
        if (i == sRow && j == sCol) {
          boardPieces.get(i).add(Pieces.E);
        } else {
          boardPieces.get(i).add(Pieces.P);
        }
      }

    }
    return boardPieces;
  }

  @Override
  protected ArrayList<ArrayList<Pieces>> getBoard() {
    return this.board;
  }


}
