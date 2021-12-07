package view;

import model.Dungeon;
import model.DungeonImpl;
import model.Point2DImpl;

import view.Constants;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

class GameHUD extends JFrame {

  JMenu menu;
  String userChoice;

  private String userInputDirection;

  public static JLabel userTreasure;
  public static JLabel userArrows;
  public static JLabel userAlive;

  JPanel mainPanel;
  public static CardLayout cardLayoutBuff;

  JButton movePanelButton = new JButton("Move");
  JButton vitalsPanelButton = new JButton("Vitals");
  JButton mazePanelButton = new JButton("Maze");

  protected GameHUD(Dungeon inputDungeon, int inputRows, int inputCols) {

    JFrame mainFrame = initializeGameHUD(inputDungeon, inputRows, inputCols);
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

  private JFrame initializeGameHUD(Dungeon inputDungeon, int inputRows, int inputCols) {
    JFrame mainFrame = new JFrame("Dungeon HUD");
    cardLayoutBuff = new CardLayout(20, 20);

    mainPanel = new JPanel(cardLayoutBuff);
    JPanel card1 = initializePlayerStats();
    JPanel card2 = initializeDPad();
    JPanel card3 = generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        new ArrayList<Boolean>());
    mainPanel.add(card1, "Player Vitals");
    mainPanel.add(card2, "DPad");
    mainPanel.add(card3, "Maze");


    movePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "DPad"));
    vitalsPanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Player Vitals"));
    mazePanelButton.addActionListener(e -> cardLayoutBuff.show(mainPanel, "Maze"));


    mainFrame.add(mainPanel);
    cardLayoutBuff.show(mainPanel, "Home");
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//    mainFrame.setSize(1500, 1700);
    mainFrame.setVisible(true);
    mainFrame.pack();
    mainFrame.setVisible(true);

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

  private JPanel initializePlayerStats() {
    JPanel card = new JPanel();

    // userArrows
    userArrows = new JLabel("Arrows: 0 ", SwingConstants.CENTER);
    card.add(userArrows);


    // userTreasure
    userTreasure = new JLabel("Treasure: None ");
    userTreasure.setForeground(Color.RED);
    card.add(userTreasure);

    // userAlive
    userAlive = new JLabel("Alive: True ");
    card.add(userAlive);
    card.add(movePanelButton);
    return card;


  }

  private JPanel generateDungeonGraphics(Dungeon dungeonModel, int inputRows, int inputCols,
                                       ArrayList<Boolean> wrapped) {
    JPanel mazePanel = new JPanel(null);
    ArrayList<String> cavePossibleDirection;
    ArrayList<String> outputString;

    for (int row = 0; row < inputRows; row++) {
      for (int col = 0; col < inputCols; col++) {
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
        label.setBounds(Constants.OFFSET * (col + 1), Constants.OFFSET * (row + 1), 100, 100);

        mazePanel.add(label);
      }
    }

    vitalsPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows + 1), 100, 25);
    mazePanel.add(vitalsPanelButton);
    return mazePanel;

  }



  // Will always return empty string unless some choice is made
  protected String getUserInputDirection() {
    return new String(userInputDirection);
  }

  protected void resetUserDirection() {
    this.userInputDirection = "";
  }
}