/*
package cli.game;

import game.Board;
import game.Color;
import game.Field;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMasterOld {

    public void startNewGame(){

        System.out.println("Starting a new game...");
        Board myBoard = new Board();

        Scanner scanner = new Scanner(System.in);

        int Field1stIndex;
        int Field2ndIndex;
        String currentPlayer = Color.PLAYER1.toString();
        boolean flipflop = true;
        while(true) {
            System.out.println(myBoard.toString() + "\n" + currentPlayer + "  x,y:");

            Field2ndIndex = scanner.nextInt();
            Field1stIndex = scanner.nextInt();

            try {
                if (myBoard.getBoard().get(Field1stIndex).get(Field2ndIndex).getColor() == Color.NONE) {

                    if (flipflop) {
                        myBoard.setField(Field1stIndex, Field2ndIndex, Color.PLAYER1);
                        flipflop = false;

                        if(isThereWinner(myBoard, getColumn(myBoard, Field2ndIndex))){
                            System.out.println(currentPlayer+" won");
                            break;
                        }
                        currentPlayer = Color.PLAYER2.toString();
                    } else {
                        myBoard.setField(Field1stIndex, Field2ndIndex, Color.PLAYER2);
                        flipflop = true;

                        if(isThereWinner(myBoard, myBoard.getBoard().get(Field1stIndex))){
                            System.out.println(currentPlayer+" won");
                            break;
                        }
                        currentPlayer = Color.PLAYER1.toString();
                    }
                } else {
                    System.out.println("Oda nem tehetsz! Válassz másik helyet!");
                }

            }
            catch (Exception e){
                continue;
            }
        }
    }

    private ArrayList<Field> getColumn(Board myBoard, int column){
        ArrayList <Field> toReturn = new ArrayList<>();
        for (int i = 0; i < myBoard.getBoard().size(); i++){
            toReturn.add(myBoard.getBoard().get(i).get(column));
        }
        return toReturn;
    }
    private boolean isThereWinner(Board board, ArrayList<Field> fields){

        Color previous = Color.NONE;

        for (int i = 0; i < fields.size(); i++){

            if(i == 0){
                previous = fields.get(i).getColor();
                continue;
            }
            else{
                if(previous != fields.get(i).getColor()){
                    return false;
                }
            }

        }
        return true;
    }
}

*/