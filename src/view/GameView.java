package view;

import model.Dungeon;
import model.Monster;

import model.PlayerImpl;

import model.Point2DImpl;

import model.Treasure;
import model.Weapon;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

import java.util.Map;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;

class GameView extends JFrame {

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

  JTextField directionTextField;

  public static JLabel userTreasure;
  public static JLabel userArrows;
  public static JLabel userAlive;
  JLabel directionLabel;

  JPanel mainPanel;

  JButton movePanelButton;
  JButton vitalsPanelButton;
  JButton mazePanelButton;
  JButton shootPanelButton;
  JButton fireTriggerButton;
  JButton pickUpButton;
  JButton anotherMazePanelButton;

  ArrayList<String> shootingParameters;

  private boolean left, right, up, down, letterM, letterP, letterS;



  protected GameView(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                     Point2DImpl playerLocation, PlayerImpl inputPlayer,
                     Map<String, Boolean> visited) {

    mazePanel = new JPanel(null);
    userChoice = "New Game";
    directionTextField  = new JTextField(2);
    directionLabel = new JLabel("Direction (N/S/E/W): ");
    movePanelButton = new JButton("Move");
    vitalsPanelButton = new JButton("Vitals");
    mazePanelButton = new JButton("Maze");
    shootPanelButton = new JButton("Shoot");
    fireTriggerButton = new JButton("Fire!");
    pickUpButton = new JButton("Pick");
    anotherMazePanelButton = new JButton("Maze");
    playerStatsCard = new JPanel();

    initialize(inputDungeon, inputRows, inputCols, inputMessage, playerLocation, inputPlayer, visited);


  }

  protected void initialize(Dungeon inputDungeon, int inputRows, int inputCols, String inputMessage,
                            Point2DImpl playerLocation, PlayerImpl inputPlayer, Map<String, Boolean> visited) {


    if (mainFrame != null) {
      mainFrame.setVisible(false);
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
    mainFrame.setFocusable(true);
    mainFrame.setVisible(true);

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

    reuseButton.addActionListener(e -> this.userChoice = "Reuse Game");

    newGameButton.addActionListener(e -> this.userChoice = "New Game");

    restartButton.addActionListener(e -> this.userChoice = "Restart Game");



  }

  private JFrame initializeGameHUD(Dungeon inputDungeon, int inputRows, int inputCols,
                                   String inputMessage, Point2DImpl playerLocation,
                                   PlayerImpl inputPlayer, Map<String, Boolean> visited) {

    JFrame mainFrameBuff = new JFrame("Dungeon HUD");
    CardLayout cardLayoutBuff = new CardLayout(20, 20);
    mainPanel = new JPanel(cardLayoutBuff);

    initializePlayerStats(inputMessage, inputPlayer.getPlayerWeapons().size(),
        inputPlayer.getPlayerTreasure().size(), inputPlayer.isAlive(), inputPlayer);
    JPanel dPadCard = initializeDPad();
    generateDungeonGraphics(inputDungeon, inputRows, inputCols,
        visited, playerLocation);
    JPanel shootingPromptCard = initializePlayerShootingPrompt();

    JScrollPane scrollPane = new JScrollPane(mazePanel);
    mazePanel.setPreferredSize(new Dimension(new Dimension(Constants.OFFSET * (inputCols + 4),
        Constants.OFFSET * (inputRows + 6))));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();


    scrollPane.getViewport().setPreferredSize(new Dimension((int) width, (int) height));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    mainPanel.add(playerStatsCard, "Player Vitals");
    mainPanel.add(dPadCard, "DPad");
    mainPanel.add(scrollPane, "Maze");
    mainPanel.add(shootingPromptCard, "Shoot");

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

    northArrowButton.addActionListener(e -> this.userInputDirection = "N");


    southArrowButton.addActionListener(e -> this.userInputDirection = "S");

    eastArrowButton.addActionListener(e -> this.userInputDirection = "E");

    westArrowButton.addActionListener(e -> this.userInputDirection = "W");

    return card;
  }

  protected void initializePlayerStats(String inputMessage, int numArrows, int numTreasure,
                                       boolean playerAlive, PlayerImpl inputPlayer) {

    playerStatsCard.setVisible(false);
    playerStatsCard.removeAll();

    // userArrows
    userArrows = new JLabel("Arrows: " + numArrows, SwingConstants.CENTER);
    playerStatsCard.add(userArrows);

    // userTreasure
    userTreasure = new JLabel("Treasure:");
    userTreasure.setForeground(Color.RED);

    int rubies = 0;
    int diamonds = 0;
    int sapphires = 0;

    if (inputPlayer.getPlayerTreasure() != null) {
      for (Treasure treasure : inputPlayer.getPlayerTreasure()) {
        if (Objects.equals(treasure.toString(), "Ruby")) {
          rubies++;
        }

        if (Objects.equals(treasure.toString(), "Diamond")) {
          diamonds++;
        }

        if (Objects.equals(treasure.toString(), "Sapphire")) {
          sapphires++;
        }
      }
    }

    JLabel rubiesInRoom = new JLabel("Rubies: " + rubies, SwingConstants.CENTER);
    JLabel diamondsInRoom = new JLabel("Diamonds: " + diamonds, SwingConstants.CENTER);
    JLabel sapphiresInRoom = new JLabel("Sapphires: " + sapphires, SwingConstants.CENTER);
    playerStatsCard.add(rubiesInRoom);
    playerStatsCard.add(diamondsInRoom);
    playerStatsCard.add(sapphiresInRoom);

    // userTreasure
    userTreasure = new JLabel("Treasure:");
    userTreasure.setForeground(Color.RED);

    // userAlive
    userAlive = new JLabel("Alive: " + playerAlive);
    playerStatsCard.add(userAlive);

    // inputMessageLabel
    JLabel inputMessageLabel = new JLabel(inputMessage, SwingConstants.CENTER);
    playerStatsCard.add(inputMessageLabel);

    playerStatsCard.add(anotherMazePanelButton);


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
    mazePanel.setVisible(false);
    mazePanel.removeAll();
    ArrayList<String> cavePossibleDirection;
    ArrayList<Treasure> cavePossibleTreasure;
    ArrayList<Weapon> cavePossibleArrows;
    ArrayList<Monster> cavePossibleMonsters;
    ArrayList<String> outputString;

    int count = 0;

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
                arrowLabel.setBounds(Constants.OFFSET * (col + 1) + 10,
                    Constants.OFFSET * (row + 1),
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

                  if (cavePossibleMonsters.get(0).monsterType() == 0) {
                    ImageIcon monster = new ImageIcon(Constants.MONSTER_IMAGE_FILEPATH);
                    JLabel monsterLabel = new JLabel();
                    monsterLabel.setIcon(monster);
                    monsterLabel.setBounds(Constants.OFFSET * (col + 1) + 20,
                        Constants.OFFSET * (row + 1),
                        45, 45);
                    mazePanel.add(monsterLabel);
                  }

                  else if (cavePossibleMonsters.get(0).monsterType() == 1) {
                    ImageIcon monster = new ImageIcon(Constants.THIEF_IMAGE_FILEPATH);
                    JLabel monsterLabel = new JLabel();
                    monsterLabel.setIcon(monster);
                    monsterLabel.setBounds(Constants.OFFSET * (col + 1) + 20,
                        Constants.OFFSET * (row + 1),
                        45, 45);
                    mazePanel.add(monsterLabel);
                  }

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

    vitalsPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 30, 100, 25);
    shootPanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows ) + 60, 100, 25);
    pickUpButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 90, 100, 25);
    movePanelButton.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 120, 100, 25);


    int numArrow = 0;

    if (dungeonModel.peekCaveWeapons(playerLocation) != null) {
      numArrow = dungeonModel.peekCaveWeapons(playerLocation).size();
    }
    String caveArrowSize = "Arrows: " + numArrow;
    JLabel arrowsInRoom = new JLabel(caveArrowSize, SwingConstants.CENTER);

    int rubies = 0;
    int diamonds = 0;
    int sapphires = 0;

    cavePossibleTreasure = (ArrayList<Treasure>)
        dungeonModel.peekCaveTreasure(playerLocation);

    if (cavePossibleTreasure != null) {
      for (Treasure treasure : cavePossibleTreasure) {
        if (Objects.equals(treasure.toString(), "Ruby")) {
          rubies++;
        }

        if (Objects.equals(treasure.toString(), "Diamond")) {
          diamonds++;
        }

        if (Objects.equals(treasure.toString(), "Sapphire")) {
          sapphires++;
        }
      }
    }

    ImageIcon rubyImage = new ImageIcon(Constants.RUBY_IMAGE_FILEPATH);
    JLabel rubyLabel = new JLabel();
    rubyLabel.setIcon(rubyImage);

    ImageIcon diamondImage = new ImageIcon(Constants.DIAMOND_IMAGE_FILEPATH);
    JLabel diamondLabel = new JLabel();
    diamondLabel.setIcon(diamondImage);

    ImageIcon sapphireImage = new ImageIcon(Constants.EMERALD_IMAGE_FILEPATH);
    JLabel sapphireLabel = new JLabel();
    sapphireLabel.setIcon(sapphireImage);



    JLabel treasureInRoom = new JLabel("In Room: ", SwingConstants.CENTER);
    JLabel rubiesInRoom = new JLabel("" + rubies, SwingConstants.CENTER);
    JLabel diamondsInRoom = new JLabel("" + diamonds, SwingConstants.CENTER);
    JLabel sapphiresInRoom = new JLabel("" + sapphires, SwingConstants.CENTER);

    JLabel smellInRoom = new JLabel("Smell: None", SwingConstants.CENTER);

   if (dungeonModel.isMajorSmell(playerLocation)) {
     smellInRoom = new JLabel("Smell: Super bad", SwingConstants.CENTER);
    }

    if (dungeonModel.isMinorSmell(playerLocation)) {
      smellInRoom = new JLabel("Smell: Bad", SwingConstants.CENTER);

    }



    treasureInRoom.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 140, 100, 25);

    arrowsInRoom.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 160, 100, 25);

    rubyLabel.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 180, 25, 25);

    rubiesInRoom.setBounds(Constants.OFFSET * (inputCols + 1) + 10,
    Constants.OFFSET * (inputRows) + 180, 100, 25);

    diamondLabel.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 200, 25, 25);

    diamondsInRoom.setBounds(Constants.OFFSET * (inputCols + 1) + 10,
        Constants.OFFSET * (inputRows) + 200, 100, 25);

    sapphireLabel.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 220, 25, 25);

    sapphiresInRoom.setBounds(Constants.OFFSET * (inputCols + 1) + 10,
        Constants.OFFSET * (inputRows) + 220, 100, 25);

    smellInRoom.setBounds(Constants.OFFSET * (inputCols + 1),
        Constants.OFFSET * (inputRows) + 240, 100, 25);


    pickUpButton.addActionListener(e -> this.userPickUp = true);



    movePanelButton.addActionListener(e -> this.userMove = true);

    shootPanelButton.addActionListener(e -> this.userShootPrompt = true);


    mazePanel.add(vitalsPanelButton);
    mazePanel.add(shootPanelButton);
    mazePanel.add(pickUpButton);
    mazePanel.add(movePanelButton);

    mazePanel.add(treasureInRoom);
    mazePanel.add(arrowsInRoom);
    mazePanel.add(rubiesInRoom);
    mazePanel.add(rubyLabel);
    mazePanel.add(diamondLabel);
    mazePanel.add(sapphireLabel);
    mazePanel.add(diamondsInRoom);
    mazePanel.add(sapphiresInRoom);
    mazePanel.add(smellInRoom);


    mazePanel.addMouseListener(new MouseListener() {
      @Override
      public synchronized void mouseClicked(MouseEvent e) {

        // Check for North
        if (e.getY() > ((playerLocation.getRow() % (inputRows + 1)) * Constants.OFFSET)
            && e.getY() <  ((playerLocation.getRow() % (inputRows + 1)) * Constants.OFFSET + 100)
        && e.getX() >  (((playerLocation.getColumn() + 1) % (inputCols + 1)) * Constants.OFFSET)
        && e.getX() <  (((playerLocation.getColumn() + 1) % (inputCols + 1)) * Constants.OFFSET + 100)) {
          userMove = true;
          userInputDirection = "N";
        }

        // Check for South
        else if (e.getY() > (((playerLocation.getRow() + 2) % (inputRows + 1)) * Constants.OFFSET)
            && e.getY() <  (((playerLocation.getRow() + 2) % (inputRows + 1)) * Constants.OFFSET + 100)
            && e.getX() >  (((playerLocation.getColumn() + 1) % (inputCols + 1)) * Constants.OFFSET)
            && e.getX() <  (((playerLocation.getColumn() + 1) % (inputCols + 1)) * Constants.OFFSET + 100)) {
          userMove = true;
          userInputDirection = "S";
        }

        // Check for East
        else if (e.getY() > (((playerLocation.getRow() + 1) % (inputRows + 1)) * Constants.OFFSET)
            && e.getY() <  (((playerLocation.getRow() + 1) % (inputRows + 1)) * Constants.OFFSET + 100)
            && e.getX() >  (((playerLocation.getColumn() + 2) % (inputCols + 1)) * Constants.OFFSET)
            && e.getX() <  (((playerLocation.getColumn() + 2) % (inputCols + 1)) * Constants.OFFSET + 100)) {
          userMove = true;
          userInputDirection = "E";
        }

        // Check for West
        else if (e.getY() > (((playerLocation.getRow() + 1) % (inputRows + 1)) * Constants.OFFSET)
            && e.getY() <  (((playerLocation.getRow() + 1) % (inputRows + 1)) * Constants.OFFSET + 100)
            && e.getX() >  (((playerLocation.getColumn()) % (inputCols + 1)) * Constants.OFFSET)
            && e.getX() <  (((playerLocation.getColumn()) % (inputCols + 1)) * Constants.OFFSET + 100)) {
          userMove = true;
          userInputDirection = "W";
        }

      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });



    mazePanel.revalidate();
    mazePanel.setVisible(true);
    mazePanel.setFocusable(true);

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

  protected void setListeners(KeyListener keys, MouseListener clicks) {
    this.addKeyListener(keys);
    this.addMouseListener(clicks);
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