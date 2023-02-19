package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import controller.GuiGameController;
import controller.GuiGameFeatures;
import model.ReadonlyGameModel;

/**
 * This class represents a GUI based view for the dungeon adventure game.
 * It sets all the features described in the controller and attaches listeners to appropriate
 * components in the view. It also gives callbacks to the controller by capturing listener events.
 */
public class GuiGameView extends JFrame implements GameView {
  private final ReadonlyGameModel m;

  private final JMenuItem m1;
  private final JMenuItem m2;
  private final JMenuItem m3;

  private final DungeonPanel dungeonPanel;

  /**
   * Constructor for GUI based view for the dungeon adventure game that takes in read-only model.
   *
   * @param rom the read-only model
   */
  public GuiGameView(ReadonlyGameModel rom) {
    super("DAG - Dungeon Adventure Game");

    if (Objects.isNull(rom)) {
      this.showMessage("Read-only model specified cannot be null",
              "Error", JOptionPane.ERROR_MESSAGE);
    }

    this.setMinimumSize(new Dimension(650, 650));
    this.setLocation(300, 50);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.m = rom;

    this.getContentPane().setLayout(new BorderLayout());

    // menu bar
    this.m1 = new JMenuItem("New Game");
    this.m2 = new JMenuItem("Reset Game");
    this.m3 = new JMenuItem("Quit Game");

    JMenu menu = new JMenu("Game Menu");
    menu.add(this.m1);
    menu.add(this.m2);
    menu.add(this.m3);

    JMenuBar mb = new JMenuBar();
    mb.add(menu);

    // menu bar and visual cues for playing the game
    JPanel topBar = new JPanel();
    topBar.setLayout(new BorderLayout());
    topBar.add(mb, BorderLayout.WEST);
    JPanel vCues = new JPanel();
    vCues.setLayout(new FlowLayout());
    vCues.add(new JLabel("Shoot : [W] [A] [S] [D] "));
    vCues.add(new JLabel("Pick : "));
    vCues.add(createInfoJLabel(ImageCategory.DIAMOND, "[X] "));
    vCues.add(createInfoJLabel(ImageCategory.RUBY, "[C] "));
    vCues.add(createInfoJLabel(ImageCategory.SAPPHIRE, "[V] "));
    vCues.add(createInfoJLabel(ImageCategory.ARROW, "[Z] "));
    topBar.add(vCues, BorderLayout.CENTER);
    this.add(topBar, BorderLayout.NORTH);

    // scrollable dungeon map
    this.dungeonPanel = new DungeonPanel(rom);
    JScrollPane scrollablePanel = new JScrollPane(this.dungeonPanel);
    scrollablePanel.setAutoscrolls(true);
    scrollablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollablePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    // scrollablePanel.setPreferredSize(new Dimension(this.getWidth(),
    //                                (int) (0.8 * this.getHeight())));
    this.getContentPane().add(scrollablePanel, BorderLayout.CENTER);

    // information panel for player and location details
    InfoPanel infoPanel = new InfoPanel(rom);
    // infoPanel.setPreferredSize(new Dimension(this.getWidth(), (int) (0.1 * this.getHeight())));
    this.getContentPane().add(infoPanel, BorderLayout.SOUTH);

    // System.out.println(rom.getGameState());
  }

  @Override
  public void setFeatures(GuiGameFeatures f) {
    this.m1.addActionListener(l -> f.restartGame());
    this.m2.addActionListener(l -> f.resetGame());
    this.m3.addActionListener(l -> f.quitGame());

    this.addKeyListener(new GameKeyListener(this.m, f, this));
    this.dungeonPanel.addMouseListener(f);

    this.setVisible(true);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void delete() {
    this.setVisible(false);
    this.dispose();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void showMessage(String error, String title, int type) {
    JOptionPane.showMessageDialog(this, error,
            title, type);
  }

  private static class GameKeyListener implements KeyListener {
    private final ReadonlyGameModel m;
    private final GuiGameView v;
    private final GuiGameController f;

    public GameKeyListener(ReadonlyGameModel m, GuiGameFeatures f, GuiGameView v) {
      this.m = m;
      this.f = (GuiGameController) f;
      this.v = v;
    }

    @Override
    public void keyTyped(KeyEvent e) {
      // not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (!this.m.isGameOver()) {
        // move
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          f.move(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          f.move(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          f.move(2);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          f.move(3);
        }

        //pick
        if (e.getKeyCode() == KeyEvent.VK_Z) {
          f.pick(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
          f.pick(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
          f.pick(2);
        }
        if (e.getKeyCode() == KeyEvent.VK_V) {
          f.pick(3);
        }

        // shoot
        if (e.getKeyCode() == KeyEvent.VK_W) {
          f.shoot(0, this.popUp());
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
          f.shoot(1, this.popUp());
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
          f.shoot(2, this.popUp());
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
          f.shoot(3, this.popUp());
        }
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      // not used
    }

    private String popUp() {
      String input = JOptionPane.showInputDialog(this.v,
              "Enter number of caves to shoot over?", null);


      if (Objects.equals(input, "")) {
        this.v.showMessage("Shooting distance invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        return "";
      }

      return input;
    }
  }


  private JLabel createInfoJLabel(ImageCategory item, String key) {
    ImageIcon icon;

    try {
      InputStream imageStream = getClass().getResourceAsStream(item.getFilePath());
      icon = new ImageIcon(ImageIO.read(imageStream));
    } catch (IOException ioe) {
      throw new IllegalStateException("Cannot find icon location image!");
    }

    JLabel thumbnail = new JLabel();
    thumbnail.setIcon(icon);
    thumbnail.setText(" " + key + " ");

    return thumbnail;
  }
}
