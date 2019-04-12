package game;

import java.util.Scanner;

public class GameMaster {
    /**
     * A játékosok színét állítja be
     */
    public void selectPlayerColors(){

        System.out.println("Select color!\nPlayerONE:");
        Scanner scanner = new Scanner(System.in);

        String Player1 = scanner.nextLine();
        System.out.println("PlayerTWO:");
        String Player2 = scanner.nextLine();

    }

    public void startNewGame(){

        Board myBoard = new Board();

    }
}
