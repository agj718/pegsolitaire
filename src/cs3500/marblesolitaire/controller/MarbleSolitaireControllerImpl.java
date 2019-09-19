package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import java.io.IOException;
import java.util.Scanner;

/**
 * An implementation of the MarbleSolitaireController Interface. This implementation runs a single
 * game.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final Readable rd;
  private final Appendable ap;

  /**
   * The constructor for MarbleSolitaireControllerImpl that takes in the Readable and the
   * Appendable. They can't be null.
   *
   * @param rd the user input of the game
   * @param ap the output of the game
   * @throws IllegalArgumentException if the Readable or the Appendable given is null
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Can't be null");
    }
    this.rd = rd;
    this.ap = ap;
  }


  @Override
  public void playGame(MarbleSolitaireModel model)
      throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    Scanner scan = new Scanner(rd);
    int fr = 0;
    int fc = 0;
    int tr = 0;
    int tc = 0;
    int counter = 0;

    while (!model.isGameOver()) {
      if (!scan.hasNext()) {
        throw new IllegalStateException("Game still running");
      }
      String currInput = scan.next();
      if (!(currInput.equals("Q") || currInput.equals("q"))) {
        try {
          if (Integer.parseInt(currInput) <= 0) {
            this.appender("Not valid input\n");
          }
        } catch (NumberFormatException e) {
          this.appender("Not valid input\n");
        }
      }

      try {
        while (Integer.parseInt(currInput) > 0 && counter < 4) {
          counter += 1;
          switch (counter) {
            case 1:
              fr = Integer.parseInt(currInput);
              if (scan.hasNext()) {
                currInput = scan.next();
                break;
              } else {
                break;
              }
            case 2:
              fc = Integer.parseInt(currInput);
              if (scan.hasNext()) {
                currInput = scan.next();
                break;
              } else {
                break;
              }
            case 3:
              tr = Integer.parseInt(currInput);
              if (scan.hasNext()) {
                currInput = scan.next();
                break;
              } else {
                break;
              }
            case 4:
              tc = Integer.parseInt(currInput);
              break;
            default:
              //do nothing so it can move on
          }
        }
      } catch (NumberFormatException e) {
        //do nothing so it can move on
      }

      if (counter == 4) {
        if (model.isGameOver()) {
          break;
        }
        try {
          model.move(fr - 1, fc - 1, tr - 1, tc - 1);
          counter = 0;
          this.appender(model.getGameState() + "\n");
          this.appender("Score: " + model.getScore() + "\n");
          if (model.isGameOver()) {
            break;
          }
        } catch (IllegalArgumentException e) {
          this.appender("Invalid Move. Play again. " + e.getMessage() + "\n");
          counter = 0;
        }
      }
      if (currInput.equals("q") || currInput.equals("Q")) {
        this.appender("Game quit!\n" +
            "State of game when quit:\n" +
            model.getGameState() + "\n" + "Score: " + model.getScore() + "\n"
        );
        return;
      }
    }
    this.appender("Game over!\n"
        + model.getGameState() + "\n"
        + "Score: " + model.getScore() + "\n");
    return;
  }

  /**
   * Appends a string to the appendable given in the class.
   *
   * @param output a string that will represent the state of a game
   * @return the appendable with the output attached
   * @throws IllegalStateException when there is an IOException
   */
  private Appendable appender(String output) throws IllegalStateException {
    try {
      this.ap.append(output);
    } catch (IOException e) {
      e.getMessage();
      throw new IllegalStateException("unacceptable");
    }
    return this.ap;
  }

}





