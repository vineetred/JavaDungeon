package view;

import model.Dungeon;
import model.DungeonImpl;
import model.Monster;
import model.Point2D;
import model.Point2DImpl;

import model.Treasure;
import model.Weapon;
import view.Constants;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showMessageDialog;

class GameHUD extends JFrame {

  JMenu menu;
  JFrame mainFrame;
  String userChoice;
  String stringBuff;

  private String userInputDirection;

  public static JLabel userTreasure;
  public static JLabel userArrows;
  public static JLabel userAlive;

  JPanel mainPanel;
  public static CardLayout cardLayoutBuff;

  JButton movePanelButton = new JButton("Move");
  JButton vitalsPanelButton = new JButton("Vitals");
  JButton mazePanelButton = new JButton("Maze");
  JButton shootPanelButton = new JButton("Shoot");
  JButton fireTriggerButton = new JButton("Fire!");

  ArrayList<String> shootingParameters;

  JPanel playerStatsCard;

  protected GameHUD(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                    Point2DImpl playerLocation) {

    init(inputDungeon, inputRows, inputCols, inputMessage, playerLocation);

  }

  protected void init(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                      Point2DImpl playerLocation) {
    mainFrame = initializeGameHUD(inputDungeon, inputRows, inputCols, inputMessage, playerLocation);
    initializeGameMenu(mainFrame, new JMenuBar());
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
    JMenuItem restartButton, newGameButton;

    inputJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    menu = new JMenu("Options");

    restartButton = new JMenuItem("Restart Game");
    newGameButton = new JMenuItem("New Game");

    menu.add(restartButton);
    menu.add(newGameButton);

    inputJMenuBar.add(menu);
    inputJFrame.setJMenuBar(inputJMenuBar);
//    inputJFrame.setSize(1500,1500);
//    inputJFrame.setLayout(null);
//    inputJFrame.setVisible(true);

    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new exitMenu());
    menu.add(exit);

    restartButton.addActionListener(e -> {
      this.userChoice = "Restart Game";
    });

    newGameButton.addActionListener(e -> {
      this.userChoice = "New Game";
    });

  }

  private JFrame initializeGameHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                                   String inputMessage, Point2DImpl playerLocation) {
    JFrame mainFrame = new JFrame("Dungeon HUD");
    cardLayoutBuff = new CardLayout(50, 50);

    mainPanel = new JPanel(cardLayoutBuff);
    JPanel card1 = initializePlayerStats(inputMessage);
    JPanel card2 = initializeDPad();
    JPanel card3 = generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        null, playerLocation);
    JPanel card4 = initializePlayerShootingPrompt();

    JScrollPane scrollPane = new JScrollPane(card3);
    card3.setPreferredSize(new Dimension(new Dimension(Constants.OFFSET * (inputCols + 3),
        Constants.OFFSET * (inputRows + 3))));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();


    scrollPane.getViewport().setPreferredSize(new Dimension((int) width, (int) height));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    mainPanel.add(card1, "Player Vitals");
    mainPanel.add(card2, "DPad");
    mainPanel.add(scrollPane, "Maze");
    mainPanel.add(card4, "Shoot");


    movePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "DPad"));
    vitalsPanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Player Vitals"));
    mazePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Maze"));
    shootPanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Shoot"));
    fireTriggerButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Player Vitals"));

    mainFrame.add(mainPanel);
    cardLayoutBuff.show(mainPanel, "Maze");
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainFrame.setVisible(true);
    mainFrame.pack();


    return mainFrame;

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

  private JPanel initializePlayerStats(String inputMessage) {
    playerStatsCard = new JPanel();

    // userArrows
    userArrows = new JLabel("Arrows: 0 ", SwingConstants.CENTER);
    playerStatsCard.add(userArrows);


    // userTreasure
    userTreasure = new JLabel("Treasure: None ");
    userTreasure.setForeground(Color.RED);
    playerStatsCard.add(userTreasure);

    // userAlive
    userAlive = new JLabel("Alive: True ");
    playerStatsCard.add(userAlive);

    // inputMessageLabel
    JLabel inputMessageLabel = new JLabel(inputMessage, SwingConstants.CENTER);
    playerStatsCard.add(inputMessageLabel);


    playerStatsCard.add(movePanelButton);
    return playerStatsCard;


  }

  private JPanel initializePlayerShootingPrompt() {


    JPanel card = new JPanel();
    shootingParameters = new ArrayList<>();


    // Distance in Caves
    JLabel directionLabel = new JLabel("Direction (N/S/E/W): ");
    card.add(directionLabel);

    // the text field
    JTextField directionTextField = new JTextField(2);
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

    });

    return card;
  }

  private JPanel generateDungeonGraphics(Dungeon dungeonModel, int inputRows, int inputCols,
                                       Map<Point2D, Boolean> visited, Point2DImpl playerLocation) {
    JPanel mazePanel = new JPanel(null);
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
        ImageIcon image = new ImageIcon(Constants.DIRECTION_IMAGE_FILEPATH.get(flattenedCardinalDirections));
        label.setIcon(image);
        label.setBounds(Constants.OFFSET * (col + 1),
            Constants.OFFSET * (row + 1), 100, 100);
        mazePanel.add(label);

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

    vitalsPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows + 1), 100, 25);
    shootPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows + 2), 100, 25);
    mazePanel.add(vitalsPanelButton);
    mazePanel.add(shootPanelButton);

    return mazePanel;

  }



  private void clearString(JTextField inputJButton) {
    inputJButton.setText("");
  }

  // Will always return empty string unless some choice is made
  protected String getUserInputDirection() {
    return new String(userInputDirection);
  }

  protected void resetUserDirection() {
    this.userInputDirection = "";
  }

  protected ArrayList<String> getUserShootingParameters() {
    return new ArrayList<>(shootingParameters);
  }

  protected void resetUserShootingParameters() {
    shootingParameters = new ArrayList<>();
  }

  protected void showUserMessage(String inputMessage) {
    showMessageDialog(null, inputMessage);
  }


}