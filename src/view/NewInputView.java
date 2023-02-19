package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class represents a new input view where the user is prompted to enter the
 * parameters to the dungeon model. This also provides user the functionality of deciding
 * between the GUI or console-based game.
 */
public class NewInputView extends JFrame implements InputView {
  private String[] args;

  /**
   * Constructor for the new input view.
   */
  public NewInputView() {
    JTextField rows = new JTextField("5");
    JTextField cols = new JTextField("5");
    JTextField wrap = new JTextField("false");
    JTextField interconn = new JTextField("0");
    JTextField percent = new JTextField("50");
    JTextField diff = new JTextField("15");

    Object[] message = {
        "No. of rows:", rows,
        "No. of cols:", cols,
        "Wrapping:", wrap,
        "Interconnectivity:", interconn,
        "Percentage of items:", percent,
        "Difficulty:", diff,
    };

    Object[] options = {
        "GUI",
        "Console",
        "Cancel",
    };

    int option = JOptionPane.showOptionDialog(this, message,
            "Enter parameters for dungeon creation:", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, null);


    if (option == JOptionPane.YES_OPTION) {
      args = new String[6];

      args[0] = rows.getText().trim();
      args[1] = cols.getText().trim();
      args[2] = wrap.getText().trim();
      args[3] = interconn.getText().trim();
      args[4] = percent.getText().trim();
      args[5] = diff.getText().trim();
    } else if (option == JOptionPane.NO_OPTION) {
      args = new String[7];

      args[0] = rows.getText().trim();
      args[1] = cols.getText().trim();
      args[2] = wrap.getText().trim();
      args[3] = interconn.getText().trim();
      args[4] = percent.getText().trim();
      args[5] = diff.getText().trim();
      args[6] = "console";
    } else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
      System.exit(0);
    }

    this.setVisible(true);
  }

  @Override
  public String[] getDungeonParams() {
    this.setVisible(false);
    this.dispose();

    return this.args;
  }
}
