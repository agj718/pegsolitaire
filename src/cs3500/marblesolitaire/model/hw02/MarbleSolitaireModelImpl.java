package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AbstractMarbleSolitaireImpl;
import java.util.ArrayList;


/**
 * The implementation of MarbleSolitaireModel. It takes in an armThickness, a row and a column
 * coordinate to make an instance of Marble Solitaire. In this implementation of
 * MarbleSolitaireModelImpl, one change I made was making this class extend the abstract class
 * instead of implementing MarbleSolitaireModel directly. With the addition of the abstract class, I
 * abstracted all of the public methods into the abstract class for the other two Model
 * implementations. Another addition was the protected method getBoard() which just supplies the
 * board to abstract class.
 */
public class MarbleSolitaireModelImpl extends AbstractMarbleSolitaireImpl {

  private int armThickness;
  private int sRow;
  private int sCol;


  private ArrayList<ArrayList<Pieces>> board;

  /**
   * The constructor for MarbleSolitaireImpl which sets the board to have an armThickness of 3 and
   * the center to have an empty slot.
   */
  public MarbleSolitaireModelImpl() {
    this.armThickness = 3;
    this.sRow = 3;
    this.sCol = 3;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * The constructor for MarbleSolitaireImpl which takes in a row and column and sets that
   * coordinate as the empty slot on the board. The armThickness remains the default 3.
   *
   * @param sRow the row of the empty cell
   * @param sCol the column of the empty cell
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    this.armThickness = 3;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException(
          "Invalid empty cell position(" + sRow + ", " + sCol + ")");
    }
    this.sRow = sRow;
    this.sCol = sCol;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * The constructor for MarbleSolitaireModelImpl which takes in an armThickness and initializes the
   * empty slot in the center of the board. Arm thickness must be an odd, positive number.
   *
   * @param armThickness determines how thick each square that makes the plus shape is.
   * @throws IllegalArgumentException if an invalid armThickness is inputted
   */
  public MarbleSolitaireModelImpl(int armThickness) {
    if (armThickness <= 1 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }
    this.armThickness = armThickness;
    this.sRow = (2 * armThickness + (armThickness - 2)) / 2;
    this.sCol = (2 * armThickness + (armThickness - 2)) / 2;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  /**
   * The constructor for MarbleSolitaireModelImpl which takes in the armThickness and the row and
   * column of the empty slot. Arm thickness must be an odd, positive number.
   *
   * @param armThickness determines how thick each square that makes the plus shape is.
   * @param sRow the row of the empty slot
   * @param sCol the column of the empty slot
   * @throws IllegalArgumentException if an invalid armThickness is inputted
   * @throws IllegalArgumentException if the empty cell coordinates are invalid
   */
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness <= 1 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Invalid arm thickness");
    }
    this.armThickness = armThickness;
    if (limit(sRow, sCol)) {
      throw new IllegalArgumentException(
          "Invalid empty cell position(" + sRow + ", " + sCol + ")");
    }
    this.sRow = sRow;
    this.sCol = sCol;
    this.board = new ArrayList<ArrayList<Pieces>>();
    this.board.addAll(this.boardState());
  }

  @Override
  protected boolean limit(int i, int j) {
    return ((i <= armThickness - 2 && j <= armThickness - 2) ||
        (i > ((2 * armThickness + (armThickness - 2)) / 2) + (armThickness / 2)
            && j <= armThickness - 2)
        || (i <= armThickness - 2 && j > ((2 * armThickness + (armThickness - 2)) / 2) +
        (armThickness / 2)) || (
        i > ((2 * armThickness + (armThickness - 2)) / 2) + (armThickness / 2)
            && j > ((2 * armThickness + (armThickness - 2)) / 2) + (armThickness / 2)));
  }

  @Override
  protected ArrayList<ArrayList<Pieces>> boardState() {
    ArrayList<ArrayList<Pieces>> boardPieces = new ArrayList<ArrayList<Pieces>>();
    int boardWidth = (armThickness * 2) + (armThickness - 2);
    for (int i = 0; i < boardWidth; i++) {
      boardPieces.add(new ArrayList<Pieces>());
      for (int j = 0; j < boardWidth; j++) {
        if (i == sRow && j == sCol) {
          boardPieces.get(i).add(Pieces.E);
        } else if (!limit(i, j)) {
          boardPieces.get(i).add(Pieces.P);
        } else if ((i <= armThickness - 2 && j <= armThickness - 2)
            || (i > (2 * armThickness + (armThickness - 2)) / 2 + (armThickness / 2)
            && j <= armThickness - 2)) {
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


