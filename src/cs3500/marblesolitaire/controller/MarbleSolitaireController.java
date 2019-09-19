package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * This interface represents the controller for one game of Marble Solitaire.
 */

public interface MarbleSolitaireController {

  /**
   * This method plays a new game of Marble Solitaire using the provided model.
   *
   * @param model an instance of MarbleSolitaireModel
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalStateException if the controller is unable to receive input or transmit output
   */
  void playGame(MarbleSolitaireModel model) throws IllegalStateException, IllegalArgumentException;

}
