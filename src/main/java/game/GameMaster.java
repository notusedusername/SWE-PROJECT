package game;

import java.util.Scanner;

public class GameMaster {

    public void startNewGame(){
        //TODO grafikus támogatás
        System.out.println("Starting a new game...");
        Board myBoard = new Board();

        Scanner scanner = new Scanner(System.in);

        Integer Field1stIndex = 0;
        Integer Field2ndIndex = 0;
        Boolean flipflop = true;
        while(true){
            System.out.println(myBoard.toString()+"\nx,y:");

            Field2ndIndex = scanner.nextInt();
            Field1stIndex = scanner.nextInt();


            if(myBoard.getBoard().get(Field2ndIndex).get(Field1stIndex).getColor() == Color.NONE) {

                if (flipflop == true) {
                    myBoard.setField(Field1stIndex, Field2ndIndex, Color.PLAYER1);
                    flipflop = false;
                } else {
                    myBoard.setField(Field1stIndex, Field2ndIndex, Color.PLAYER2);
                    flipflop = true;
                }
            }
            else{
                System.out.println("Oda nem tehetsz! Válassz másik helyet!");
            }
        }

    }
}
