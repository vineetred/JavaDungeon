package view;

import model.Dungeon;
import model.DungeonImpl;

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

    ViewInterface view = new ViewImpl();
    ArrayList<String> userParams = (ArrayList<String>) view.startNewGame();

    Dungeon dungeon = new DungeonImpl(true, Integer.parseInt(userParams.get(0)),
        Integer.parseInt(userParams.get(1)), Integer.parseInt(userParams.get(2)),
        Integer.parseInt(userParams.get(4)),
        Integer.parseInt(userParams.get(3)));

    view.generateHUD(dungeon, Integer.parseInt(userParams.get(0)), Integer.parseInt(userParams.get(1)), null);



  }
}