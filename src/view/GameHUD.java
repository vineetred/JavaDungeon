package view;

import model.Dungeon;
import model.Monster;
import model.Player;
import model.PlayerImpl;
import model.Point2D;
import model.Point2DImpl;

import model.Treasure;
import model.Weapon;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static javax.swing.JOptionPane.showMessageDialog;

class GameHUD extends JFrame {

  JMenu menu;
  JFrame mainFrame;
  String userChoice;
  String stringBuff;
  JPanel mazePanel;
  JPanel playerStatsCard;

  private String userInputDirection;
  private boolean userPickUp;
  private boolean userMove;
  private boolean userShoot;
  private boolean userShootPrompt;

  JTextField directionTextField = new JTextField(2);

  public static JLabel userTreasure;
  public static JLabel userArrows;
  public static JLabel userAlive;
  JLabel directionLabel = new JLabel("Direction (N/S/E/W): ");

  JPanel mainPanel;

  JButton movePanelButton = new JButton("Move");
  JButton vitalsPanelButton = new JButton("Vitals");
  JButton mazePanelButton = new JButton("Maze");
  JButton shootPanelButton = new JButton("Shoot");
  JButton fireTriggerButton = new JButton("Fire!");
  JButton pickUpButton = new JButton("Pick");
  JButton anotherMazePanelButton = new JButton("Maze");

  ArrayList<String> shootingParameters;

  private boolean left, right, up, down, letterM, letterP, letterS;



  protected GameHUD(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                    Point2DImpl playerLocation, PlayerImpl inputPlayer,
                    Map<String, Boolean> visited) {
    mazePanel = new JPanel(null);

    playerStatsCard = new JPanel();
    initialize(inputDungeon, inputRows, inputCols, inputMessage, playerLocation, inputPlayer, visited);


  }

  protected void initialize(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                            Point2DImpl playerLocation, PlayerImpl inputPlayer, Map<String, Boolean> visited) {


    if (mainFrame != null) {
      mainFrame.setVisible(false); //you can't see me!
      mainFrame.dispose();
      mainFrame.revalidate();
    }

    mainFrame = initializeGameHUD(inputDungeon, inputRows, inputCols, inputMessage,
        playerLocation, inputPlayer, visited);
    initializeGameMenu(mainFrame, new JMenuBar());

    mainFrame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public synchronized void keyPressed(KeyEvent e) {



        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
        if (e.getKeyCode() == KeyEvent.VK_M) letterM = true;
        if (e.getKeyCode() == KeyEvent.VK_P) letterP = true;
        if (e.getKeyCode() == KeyEvent.VK_S) letterS = true;


        if (letterP) {
          userPickUp = true;
        }

        if (letterS) {
          shootPanelButton.doClick();
          userShootPrompt = true;
//          userShoot = true;
        }

        if (letterM) {
          if (up) {
            userInputDirection = "N";
            userMove = true;
          }

          else if (down) {
            userInputDirection = "S";
            userMove = true;
          }

          else if (left) {
            userInputDirection = "W";
            userMove = true;
          }

          else if (right) {
            userInputDirection = "E";
            userMove = true;
          }


        }

        if (userShootPrompt) {
          if (up) {
            directionTextField.setText("N");
          }

          else if (down) {
            directionTextField.setText("S");
          }

          else if (left) {
            directionTextField.setText("W");
          }

          else if (right) {
            directionTextField.setText("E");
          }

        }
      }


      @Override
      public synchronized void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
        if (e.getKeyCode() == KeyEvent.VK_M) letterM = false;
        if (e.getKeyCode() == KeyEvent.VK_P) letterP = false;
        if (e.getKeyCode() == KeyEvent.VK_S) letterS = false;
      }

    });

    mainFrame.setVisible(true);
    mainFrame.setFocusable(true);


  }


  static class exitMenu implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      System.exit(0);
    }
  }

  private void initializeGameMenu(JFrame inputJFrame, JMenuBar inputJMenuBar) {

    this.userChoice = "";
    JMenuItem reuseButton, newGameButton, restartButton;

    menu = new JMenu("Options");

    reuseButton = new JMenuItem("Reuse Game");
    newGameButton = new JMenuItem("New Game");
    restartButton = new JMenuItem("Restart game");

    menu.add(reuseButton);
    menu.add(newGameButton);
    menu.add(restartButton);

    inputJMenuBar.add(menu);
    inputJFrame.setJMenuBar(inputJMenuBar);

    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new exitMenu());
    menu.add(exit);

    reuseButton.addActionListener(e -> {
      this.userChoice = "Reuse Game";
    });

    newGameButton.addActionListener(e -> {
      this.userChoice = "New Game";
    });

    restartButton.addActionListener(e -> {
      this.userChoice = "Restart Game";
    });



  }

  private JFrame initializeGameHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                                   String inputMessage, Point2DImpl playerLocation,
                                   PlayerImpl inputPlayer, Map<String, Boolean> visited) {
    JFrame mainFrameBuff = new JFrame("Dungeon HUD");
    CardLayout cardLayoutBuff = new CardLayout(20, 20);

    mainPanel = new JPanel(cardLayoutBuff);
    initializePlayerStats(inputMessage, inputPlayer.getPlayerWeapons().size(),
        inputPlayer.getPlayerTreasure().size(), inputPlayer.isAlive());
    JPanel card2 = initializeDPad();
    // TODO: Visited map
//    JPanel card3 = generateDungeonGraphics(inputDungeon, inputRows, inputCols,
//        null, playerLocation);
    generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        visited, playerLocation);
    JPanel card4 = initializePlayerShootingPrompt();

    JScrollPane scrollPane = new JScrollPane(mazePanel);
    mazePanel.setPreferredSize(new Dimension(new Dimension(Constants.OFFSET * (inputCols + 4),
        Constants.OFFSET * (inputRows + 5))));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();


    scrollPane.getViewport().setPreferredSize(new Dimension((int) width, (int) height));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    mainPanel.add(playerStatsCard, "Player Vitals");
    mainPanel.add(card2, "DPad");
    mainPanel.add(scrollPane, "Maze");
    mainPanel.add(card4, "Shoot");


    movePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "DPad"));
    vitalsPanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Player Vitals"));
    mazePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Maze"));
    anotherMazePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Maze"));
    shootPanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Shoot"));
    pickUpButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Maze"));
    fireTriggerButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Player Vitals"));


    mainFrameBuff.add(mainPanel);

    cardLayoutBuff.show(mainPanel, "Maze");
    mainFrameBuff.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    mainFrameBuff.setVisible(true);
    mainFrameBuff.pack();

    return mainFrameBuff;

  }

  private JPanel initializeDPad() {


    JPanel card = new JPanel();

    JButton northArrowButton = new BasicArrowButton(BasicArrowButton.NORTH);
    JButton southArrowButton = new BasicArrowButton(BasicArrowButton.SOUTH);
    JButton eastArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
    JButton westArrowButton = new BasicArrowButton(BasicArrowButton.WEST);

    card.add(northArrowButton);
    card.add(southArrowButton);
    card.add(eastArrowButton);
    card.add(westArrowButton);
    card.add(mazePanelButton);

    northArrowButton.addActionListener(e -> {
      this.userInputDirection = "N";

    });

    southArrowButton.addActionListener(e -> {
      this.userInputDirection = "S";
    });

    eastArrowButton.addActionListener(e -> {
      this.userInputDirection = "E";
    });

    westArrowButton.addActionListener(e -> {
      this.userInputDirection = "W";
    });

    return card;
  }

  protected void initializePlayerStats(String inputMessage, int numArrows, int numTreasure,
                                       boolean playerAlive) {

    playerStatsCard.setVisible(false);
    playerStatsCard.removeAll();



    // userArrows
    userArrows = new JLabel("Arrows: " + numArrows, SwingConstants.CENTER);
    playerStatsCard.add(userArrows);


    // userTreasure
    userTreasure = new JLabel("Treasure: " + numTreasure);
    userTreasure.setForeground(Color.RED);
    playerStatsCard.add(userTreasure);

    // userAlive
    userAlive = new JLabel("Alive: " + playerAlive);
    playerStatsCard.add(userAlive);

    // inputMessageLabel
    JLabel inputMessageLabel = new JLabel(inputMessage, SwingConstants.CENTER);
    playerStatsCard.add(inputMessageLabel);

    playerStatsCard.add(anotherMazePanelButton);
//    playerStatsCard.revalidate();
//    playerStatsCard.setVisible(true);

  }

  private JPanel initializePlayerShootingPrompt() {


    JPanel card = new JPanel();
    shootingParameters = new ArrayList<>();


    // Distance in Caves
    card.add(directionLabel);

    // the text field
    card.add(directionTextField);

    // Distance in Caves
    JLabel display = new JLabel("Distance in Caves: ");
    card.add(display);

    // the text field
    JTextField shootingDistanceTextField = new JTextField(2);
    card.add(shootingDistanceTextField);

    // Fire button
    fireTriggerButton.setActionCommand("Fire Button");

    card.add(fireTriggerButton);


    fireTriggerButton.addActionListener(e -> {

      stringBuff = directionTextField.getText();
      shootingParameters.add(stringBuff);
      clearString(directionTextField);

      stringBuff = shootingDistanceTextField.getText();
      shootingParameters.add(stringBuff);
      clearString(shootingDistanceTextField);

      this.userShoot = true;


    });

    return card;
  }

  protected void generateDungeonGraphics(Dungeon dungeonModel, int inputRows,
                                         int inputCols,
                                       Map<String, Boolean> visited, Point2DImpl playerLocation) {
//    JPanel mazePanel = new JPanel(null);
    mazePanel.setVisible(false);
    mazePanel.removeAll();
    ArrayList<String> cavePossibleDirection;
    ArrayList<Treasure> cavePossibleTreasure;
    ArrayList<Weapon> cavePossibleArrows;
    ArrayList<Monster> cavePossibleMonsters;
    ArrayList<String> outputString;

    for (int row = 0; row < inputRows; row++) {
      for (int col = 0; col < inputCols; col++) {

        if (row == playerLocation.getRow() && col == playerLocation.getColumn()) {
          ImageIcon playerImageIcon = new ImageIcon(Constants.RUBY_IMAGE_FILEPATH);
          JLabel playerLocationLabel = new JLabel();
          playerLocationLabel.setIcon(playerImageIcon);
          playerLocationLabel.setBounds(Constants.OFFSET * (col + 1),
              Constants.OFFSET * (row + 1), 100, 100);
          mazePanel.add(playerLocationLabel);
        }



        cavePossibleDirection = (ArrayList<String>)
            dungeonModel.getMovesAtCaveIndex(new Point2DImpl(row, col));

        outputString = new ArrayList<>();

        if (cavePossibleDirection.contains("North")) {
          outputString.add("N");
        }

        if (cavePossibleDirection.contains("South")) {
          outputString.add("S");
        }

        if (cavePossibleDirection.contains("East")) {
          outputString.add("E");
        }

        if (cavePossibleDirection.contains("West")) {
          outputString.add("W");
        }

        String flattenedCardinalDirections = String.join("", outputString);

        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon(Constants.FOG_TILE_FILEPATH);
        if (visited.containsKey(new Point2DImpl(row, col).toString())) {
          if (visited.get(new Point2DImpl(row, col).toString())) {
            // Adding Bad smell
            if (dungeonModel.isMinorSmell(new Point2DImpl(row, col))) {
              ImageIcon badSmell = new ImageIcon(Constants.BAD_SMELL_FILEPATH);
              JLabel badSmellLabel = new JLabel();
              badSmellLabel.setIcon(badSmell);
              badSmellLabel.setBounds(Constants.OFFSET * (col + 1),
                  Constants.OFFSET * (row + 1), 100, 100);
              mazePanel.add(badSmellLabel);

            }

            // Adding Major bad smell
            else if (dungeonModel.isMajorSmell(new Point2DImpl(row, col))) {

              ImageIcon superBadSmell = new ImageIcon(Constants.SUPER_BAD_SMELL_FILEPATH);
              JLabel superBadSmellLabel = new JLabel();
              superBadSmellLabel.setIcon(superBadSmell);
              superBadSmellLabel.setBounds(Constants.OFFSET * (col + 1),
                  Constants.OFFSET * (row + 1), 100, 100);
              mazePanel.add(superBadSmellLabel);
            }

            image = new ImageIcon(Constants.DIRECTION_IMAGE_FILEPATH.get(flattenedCardinalDirections));
            // Adding treasure
            cavePossibleTreasure = (ArrayList<Treasure>)
                dungeonModel.peekCaveTreasure(new Point2DImpl(row, col));

            if (cavePossibleTreasure != null) {
              if (cavePossibleTreasure.size() > 0) {
                ImageIcon diamond = new ImageIcon(Constants.DIAMOND_IMAGE_FILEPATH);
                JLabel treasureLabel = new JLabel();
                treasureLabel.setIcon(diamond);
                treasureLabel.setBounds(Constants.OFFSET * (col + 1), Constants.OFFSET * (row + 1),
                    30, 30);
                mazePanel.add(treasureLabel);
              }
            }

            // Adding Arrows
            cavePossibleArrows = (ArrayList<Weapon>)
                dungeonModel.peekCaveWeapons(new Point2DImpl(row, col));

            if (cavePossibleArrows != null) {
              if (cavePossibleArrows.size() > 0) {
                ImageIcon arrow = new ImageIcon(Constants.ARROW_IMAGE_FILEPATH);
                JLabel arrowLabel = new JLabel();
                arrowLabel.setIcon(arrow);
                arrowLabel.setBounds(Constants.OFFSET * (col + 1), Constants.OFFSET * (row + 1),
                    25, 8);
                mazePanel.add(arrowLabel);
              }

            }

            // Adding Monsters
            cavePossibleMonsters = (ArrayList<Monster>)
                dungeonModel.peekCaveMonsters(new Point2DImpl(row, col));

            if (cavePossibleMonsters != null) {
              if (cavePossibleMonsters.size() > 0) {
                // Only alive monsters
                if (cavePossibleMonsters.get(0).getHits() < 2) {
                  ImageIcon monster = new ImageIcon(Constants.MONSTER_IMAGE_FILEPATH);
                  JLabel monsterLabel = new JLabel();
                  monsterLabel.setIcon(monster);
                  monsterLabel.setBounds(Constants.OFFSET * (col + 1), Constants.OFFSET * (row + 1),
                      25, 25);
                  mazePanel.add(monsterLabel);
                }
              }
            }
          }
        }

        label.setIcon(image);
        label.setBounds(Constants.OFFSET * (col + 1),
            Constants.OFFSET * (row + 1), 100, 100);
        mazePanel.add(label);



      }
    }



//    vitalsPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
//        Constants.OFFSET * (inputRows + 1), 100, 25);
//    shootPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
//        Constants.OFFSET * (inputRows + 2), 100, 25);
//    pickUpButton.setBounds(Constants.OFFSET * (inputCols + 1),
//        Constants.OFFSET * (inputRows + 3), 100, 25);
//    movePanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
//        Constants.OFFSET * (inputRows + 4), 100, 25);

    vitalsPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 30, 100, 25);
    shootPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows ) + 60, 100, 25);
    pickUpButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 90, 100, 25);
    movePanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 120, 100, 25);


    pickUpButton.addActionListener(e -> {
      this.userPickUp = true;
    });



    movePanelButton.addActionListener(e -> {
      this.userMove = true;
    });

    shootPanelButton.addActionListener(e -> {
      this.userShootPrompt = true;
    });


    mazePanel.add(vitalsPanelButton);
    mazePanel.add(shootPanelButton);
    mazePanel.add(pickUpButton);
    mazePanel.add(movePanelButton);



    mazePanel.revalidate();
    mazePanel.setVisible(true);
//    mazePanel.setFocusable(true);

//    return mazePanel;

  }



  private void clearString(JTextField inputJButton) {
    inputJButton.setText("");
  }

  // Will always return empty string unless some choice is made
  protected String getUserInputDirection() {
    if (userInputDirection == null) {
      return "";
    }
    return new String(userInputDirection);
  }

  protected void resetUserDirection() {
    this.userInputDirection = "";
  }

  protected boolean getUserPickUp() {
   return this.userPickUp;
  }

  protected void resetUserPickUp() {
    this.userPickUp = false;
  }

  protected boolean getUserMove() {
    return this.userMove;
  }

  protected void resetUserMove() {
    this.userMove = false;
  }

  protected boolean getUserShoot() {
    return this.userShoot;
  }

  protected void resetUserShoot() {
    this.userShoot = false;
  }

  protected ArrayList<String> getUserShootingParameters() {
    return new ArrayList<>(shootingParameters);
  }

  protected void resetUserShootingParameters() {
    shootingParameters = new ArrayList<>();
    resetUserShoot();
  }

  protected void setListeners(KeyListener keys) {
    this.addKeyListener(keys);
  }

  protected void displayUserMessage(String inputMessage) {
    showMessageDialog(null, inputMessage);
  }

  protected String getUserChangeGame() {
    return new String(this.userChoice);
  }

  protected void resetUserChangeGame() {
    this.userChoice = "";
  }


}