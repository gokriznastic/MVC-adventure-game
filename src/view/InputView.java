package view;

/**
 * This interface represents the first view that is inflated by the controller to take in model
 * parameters from the user and decide between the GUI and console-based game options.
 */
public interface InputView {

  /**
   * The method to get the parameters for construction of the dungeon in the model.
   *
   * @return  the user given args as string array
   */
  String[] getDungeonParams();
}
