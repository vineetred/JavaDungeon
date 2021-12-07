package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
//    NewGamePrompt obj = new NewGamePrompt("New Game Prompt");
//    System.out.println(obj.getUserParameters());
//    System.out.println(obj.getInputString());
//    Menu.

//    InGameMenu test = new InGameMenu();


//    Scanner input = new Scanner(System.in);
//    PlayerStats menu = new PlayerStats();
//    ArrayList<String> tempString = new ArrayList<>();
//    for (int i = 0; i < 3; i++) {
//      tempString.add(input.next());
//    }
//
//    menu.changePlayerStats(tempString);

//    GameHUD DPAD = new GameHUD();
    SwingUtilities.invokeLater(GameHUD::new);

  }
}