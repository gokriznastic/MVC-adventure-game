package test;

import org.junit.Before;
import org.junit.Test;

import controller.GuiGameController;
import mock.MockGameModel;
import mock.MockGameView;
import model.AdventureGameModel;
import model.GameModel;
import utils.Randomizer;
import view.GameView;

import static org.junit.Assert.assertEquals;

/**
 * This class represents a JUnit test for GUI-based Controller class.
 * It tests all the functionalities of the controller with mock view and models.
 */
public class GuiGameControllerTest {
  GuiGameController controller;
  StringBuilder log;

  @Before
  public void setUp() {
    this.log = new StringBuilder();
    GameView view = new MockGameView(log);
    GameModel model = new MockGameModel(log);

    controller = new GuiGameController(model, view);
  }

  @Test
  public void testGuiController() {
    controller.playGame();

    controller.move(0);
    controller.move(1);
    controller.move(2);
    controller.move(3);
    controller.move(4); // invalid direction, GUI controller does nothing

    controller.pick(0);
    controller.pick(1);
    controller.pick(2);
    controller.pick(3);
    controller.pick(4); // invalid item, GUI controller does nothing

    controller.shoot(0, "1");
    controller.shoot(1, "2");
    controller.shoot(2, "3");
    controller.shoot(3, "4");
    controller.shoot(0, "7");

    try {
      controller.resetGame();
    } catch (NullPointerException npe) {
      // do nothing, this is because the args are not initialized if model is given
      // to the controller as opposed to constructed inside
    }

    // controller.restartGame();

    // do not test restart as it brings up a new input view GUI to take user given args
    // and continues the same as reset above

    // do not test quit, for obvious reasons

    // do not test handle cell click as it requires an actual model to know
    // the dimensions of dungeon

    assertEquals("added action listeners\nadded keyboard listener\nadded mouse listener\n"
            + "focus was reset\n"
            + ""
            + "player moved north\nrepaint was called\ncheck game over condition\n"
            + "player moved south\nrepaint was called\ncheck game over condition\n"
            + "player moved east\nrepaint was called\ncheck game over condition\n"
            + "player moved west\nrepaint was called\ncheck game over condition\n"
            + "repaint was called\ncheck game over condition\n"
            + ""
            + "player picked arrow\nrepaint was called\n"
            + "player picked diamond\nrepaint was called\n"
            + "player picked ruby\nrepaint was called\n"
            + "player picked sapphire\nrepaint was called\n"
            + "repaint was called\n"
            + ""
            + "player shoots north over a distance of 1\nrepaint was called\n"
            + "player shoots south over a distance of 2\nrepaint was called\n"
            + "player shoots east over a distance of 3\nrepaint was called\n"
            + "player shoots west over a distance of 4\nrepaint was called\n"
            + "showed message dialog\n"
            + ""
            + "set invisible and disposed\n",
            log.toString());
  }

  @Test
  public void testClickFeature() {
    StringBuilder log = new StringBuilder();
    GameView view = new MockGameView(log);
    GameModel model = new AdventureGameModel(new Randomizer(42).getRandom(),
            5, 5, false, 0, 50, 5);

    controller = new GuiGameController(model, view);

    int[] playerLoc = model.getPlayer().getCurrentLocation().getCoordinates();

    controller.handleCellClick(playerLoc[0] - 1, playerLoc[1],
            playerLoc[0], playerLoc[1]);

    int[] newPlayerLoc = new int[]{2, 3};

    assertEquals(newPlayerLoc[0], model.getPlayer().getCurrentLocation().getCoordinates()[0]);
    assertEquals(newPlayerLoc[1], model.getPlayer().getCurrentLocation().getCoordinates()[1]);
  }
}
