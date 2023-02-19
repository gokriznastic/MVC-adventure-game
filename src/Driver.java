import java.io.InputStreamReader;
import java.util.Random;

import controller.GameController;
import controller.GuiGameController;
import controller.GuiGameFeatures;
import controller.TextBasedGameController;
import model.AdventureGameModel;
import model.GameModel;
import utils.Randomizer;

/**
 * Driver class to demonstrate the functionality of the Dungeon Adventure Game.
 * This class initializes the game model and the game controller and passes the model to the
 * controller to start the game.
 */
public class Driver {

  /**
   * main method driving the text based game.
   *
   * @param args default args
   */
  public static void main(String[] args) {
    // args = new String[]{"", "10", "false", "0", "50", "20"};

    if (args.length == 6) {
      try {
        // PARSING THE USER GIVEN ARGUMENTS
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        boolean wrap = Boolean.parseBoolean(args[2]);
        int interconn = Integer.parseInt(args[3]);
        int percent = Integer.parseInt(args[4]);
        int difficulty = Integer.parseInt(args[5]);

        // CREATING A DETERMINISTIC RANDOM OBJECT
        Random rand = new Randomizer(42).getRandom();

        // CREATING THE ADVENTURE GAME MODEL
        GameModel model;

        try {
          System.out.printf("Building dungeon with %s rows, %s columns, "
                  + "interconnectivity = %s, %s percent of treasure caves, "
                  + "difficulty level = %s "
                  + "and wrapping = %s\n\n", rows, cols, interconn, percent, difficulty, wrap);

          model = new AdventureGameModel(rand, rows, cols, wrap, interconn, percent, difficulty);
        } catch (IllegalStateException | IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }

        // CREATING THE CONSOLE BASED ADVENTURE GAME CONTROLLER
        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;

        GameController controller = new TextBasedGameController(input, output, model);

        // STARTING THE GAME
        controller.playGame();
      } catch (NumberFormatException nfe) {
        System.out.println("Unable to parse dungeon arguments, please check and try again");
      }
    }
    else if (args.length == 0) {
      // CREATING AND STARTING THE GUI ADVENTURE GAME CONTROLLER
      try {
        GuiGameFeatures controller = new GuiGameController();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.println("Missing arguments. Required 6, given" + args.length);
    }
  }
}
