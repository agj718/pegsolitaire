package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;

/**
 * The European Solitaire implementation of Marble Solitaire. This board should be shaped as an
 * octagon.
 */
public class EuropeanSolitaireModelImpl extends AbstractMarbleSolitaireImpl {

  private int length;
  private int sRow;
  private int sCol;
  private ArrayList<ArrayList<Pieces>> board;

  /**
   * The default constructor where the length of a side is 3 and the empty slot is in the center.
   */
  public EuropeanSolitaireModelImpl() {
    this.length = 3;
    this.sRow = 3;
    this.sCol = 3;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in a specific length and puts the empty slot in the center of the
   * board.
   *
   * @param length an odd positive number that represents the side length
   * @throws IllegalArgumentException if the length is not odd or positive
   */
  public EuropeanSolitaireModelImpl(int length) {
    if (length <= 1 || length % 2 == 0) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }
    this.length = length;
    this.sRow = (2 * length + (length - 2)) / 2;
    this.sCol = (2 * length + (length - 2)) / 2;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in empty slot coordinates and instantiates it. The board length defaults
   * to 3.
   *
   * @param sRow a positive number within bounds that represents the row
   * @param sCol a positive number within bounds that represents the column
   * @throws IllegalArgumentException if the given position doesn't fall within the shape
   */
  public EuropeanSolitaireModelImpl(int sRow, int sCol) {
    this.length = 3;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException(
          "Invalid empty cell position(" + sRow + ", " + sCol + ")");
    }
    this.sCol = sCol;
    this.sRow = sRow;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * This constructor takes in the length and the empty slot coordinates.
   *
   * @param length an odd positive number that represents the side length
   * @param sRow a positive number within bounds that represents the row
   * @param sCol a positive number within bounds that represents the column
   * @throws IllegalArgumentException if the length is not odd or positive
   * @throws IllegalArgumentException if the given position doesn't fall within the shape
   */
  public EuropeanSolitaireModelImpl(int length, int sRow, int sCol) {
    if (length <= 1 || length % 2 == 0) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }
    this.length = length;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException(
          "Invalid empty cell position(" + sRow + ", " + sCol + ")");
    }
    this.sCol = sCol;
    this.sRow = sRow;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  @Override
  protected boolean limit(int i, int j) {
    int lastIndex = (length * 2) + (length - 2) - 1;

    return (i < 0 || j < 0 || i > lastIndex || j > lastIndex || (i <= length - 2
        && j <= length - 2 - i) || (i <= length - 2 && j >= length * 2 - 1 + i)
        || (i >= length * 2 - 1 && j <= length - 2 - (lastIndex - i)) || (i >= length * 2 - 1
        && j >= length * 2 - 1 + lastIndex - i));

  }


  @Override
  protected ArrayList<ArrayList<Pieces>> boardState() {
    ArrayList<ArrayList<Pieces>> boardPieces = new ArrayList<ArrayList<Pieces>>();
    int boardWidth = (length * 2) + (length - 2);

    for (int i = 0; i < boardWidth; i++) {
      boardPieces.add(new ArrayList<Pieces>());
      for (int j = 0; j < boardWidth; j++) {
        if (i == sRow && j == sCol) {
          boardPieces.get(i).add(Pieces.E);
        } else if (!limit(i, j)) {
          boardPieces.get(i).add(Pieces.P);
        } else if ((i <= length - 2 && j <= length - 2 - i) ||
            (i >= length * 2 - 1 && j <= length - 2 - (boardWidth - 1 - i)
            )) {
          boardPieces.get(i).add(Pieces.S);
        } else {
          //do nothing so that it can move to next row
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