package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import java.io.InputStreamReader;

/**
 * This class is the implementation of the Marble Solitaire game. It contains a main method which
 * launches a game of the define type
 */
public final class MarbleSolitaire {

  /**
   * The main method of Marble Solitaire which starts a particular instance of a game.
   *
   * @param args the initial set up controls
   */
  public static void main(String[] args) {
    MarbleSolitaireController controller =
        new MarbleSolitaireControllerImpl(new InputStreamReader(System.in), System.out);
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    switch (args[0]) {
      case "english":
        if (args[1].equals("-size")) {
          model = new MarbleSolitaireModelImpl(Integer.parseInt(args[2]));
        } else if (args[1].equals("-hole")) {
          model = new MarbleSolitaireModelImpl(Integer.parseInt(args[2]),
              Integer.parseInt(args[3]));
        } else {
          model = new MarbleSolitaireModelImpl();
        }
        break;
      case "european":
        if (args[1].equals("-size")) {
          model = new EuropeanSolitaireModelImpl(Integer.parseInt(args[2]));
        } else if (args[1].equals("-hole")) {
          model = new EuropeanSolitaireModelImpl(Integer.parseInt(args[2]),
              Integer.parseInt(args[3]));
        } else {
          model = new EuropeanSolitaireModelImpl();
        }
        break;
      case "triangle":
        if (args[1].equals("-size")) {
          model = new TriangleSolitaireModelImpl(Integer.parseInt(args[2]));
        } else if (args[1].equals("-hole")) {
          model = new TriangleSolitaireModelImpl(Integer.parseInt(args[2]),
              Integer.parseInt(args[3]));
        } else {
          model = new TriangleSolitaireModelImpl();
        }
        break;
      default:
        //Do nothing
    }

    controller.playGame(model);

  }

}
