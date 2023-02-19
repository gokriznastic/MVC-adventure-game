package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import model.Direction;
import model.GameModel;
import model.Item;
import model.Player;
import model.Smell;

/**
 * This class represents a text-based adventure game controller.
 * It can read commands from the user and executes them using the model.
 */
public class TextBasedGameController implements GameController {
  private final Appendable out;
  private final Scanner scan;
  private final GameModel model;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public TextBasedGameController(Readable in, Appendable out, GameModel model) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    try {
      Objects.requireNonNull(model);
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.out = out;
    this.scan = new Scanner(in);
    this.model = model;
  }

  @Override
  public void playGame() {

    Player player = (Player) model.getPlayer();

    try {
      this.out.append("Playing game. Enter Q to quit abruptly at any point!\n\n");

      while (!model.isGameOver()) {
        // this.out.append(model.getGameState()).append("\n\n");

        this.out.append(this.locationInfo(player)).append("\n");

        if (!this.userAction(model)) {
          return;
        }
      }

      if (player.isAlive()) {
        this.out.append("\nBravo! Player wins!");
      } else {
        this.out.append("\nChomp, chomp, chomp, you are eaten by an un-slayed Otyugh!\n")
                .append("Better luck next time!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private boolean userAction(GameModel model) throws IOException {
    this.out.append("Move, Pickup, or Shoot (M-P-S)? ");

    String action = "";
    if (this.scan.hasNext()) {
      action = this.scan.next();
    }

    GameCommand cmd;

    try {
      String pickedItem = "";

      switch (action) {
        case "q":
        case "Q":
        case "quit":
          this.out.append("Quitting game...");
          return false;

        case "m":
        case "M":
        case "move":
          this.out.append("Where to? ");
          cmd = new Move(this.scan.next());
          break;

        case "p":
        case "P":
        case "pick":
          this.out.append("What? ");
          String item = this.scan.next();
          pickedItem = item;
          cmd = new Pick(item);
          break;

        case "s":
        case "S":
        case "shoot":
          this.out.append("Where to? ");
          String dir = this.scan.next();
          this.out.append("No. of caves (1-5)? ");
          cmd = new Shoot(dir, this.scan.next());
          break;

        default:
          this.out.append(String.format("Unknown game action entered - %s\n", action));
          cmd = null;
          break;
      }

      if (cmd != null) {
        cmd.execute(model);

        switch (action) {
          case "p":
          case "P":
          case "pick":
            this.out.append("You pick a ").append(pickedItem).append("\n");
            break;
          case "s":
          case "S":
          case "shoot":
            this.out.append("You shoot an arrow into the darkness\n");
            if (((Shoot) cmd).hitMonster()) {
              this.out.append("You hear a great howl in the distance\n");
            }
            if (model.getPlayer().getArrowsLeft() == 0) {
              this.out.append("You are out of arrows, explore to find more\n");
            }
            break;
          default:
            break;
        }
        cmd = null;
      }
    } catch (IllegalArgumentException | IllegalStateException ie) {
      this.out.append(ie.getMessage()).append("\n");
    } catch (InputMismatchException ime) {
      this.out.append("Invalid type for input!\n");
    }

    return true;
  }

  private String locationInfo(Player player) {
    StringBuilder sb = new StringBuilder();

    sb.append("\n");

    if (player.getCurrentLocation().isCave()) {
      sb.append("You are in a cave\n");
      sb.append("Doors lead to the ");
    } else {
      sb.append("You are in a tunnel\n");
      sb.append("that continues to the ");
    }

    String prefix = "";
    for (Direction d : player.getCurrentLocation().getPossibleDirections()) {
      sb.append(prefix);
      prefix = ", ";
      sb.append(d.toString().charAt(0));
    }

    sb.append("\n");

    List<Item> items = player.getCurrentLocation().getContent();
    if (items.size() > 0) {
      sb.append("You find ");
      Set<Item> distinct = new HashSet<>(items);

      prefix = "";
      for (Item item : distinct) {
        sb.append(prefix);
        prefix = ", ";
        sb.append(Collections.frequency(items, item)).append(" ").append(item.getName());
      }

      sb.append(" here\n");
    }

    Smell smell = player.getCurrentLocation().getSmell();
    if (smell == Smell.LESS_PUNGENT) {
      sb.append("You smell something at a distance\n");
    } else if (smell == Smell.MORE_PUNGENT) {
      sb.append("You smell something terrible nearby\n");
    }

    return sb.toString();
  }
}
