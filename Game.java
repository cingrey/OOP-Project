/*COMP305 - Game
Professor - A. Nuzen
Author: Team COBOL
This is the driver class of the game.
*/

import java.util.Scanner;
import java.lang.NumberFormatException;


public class Game {
    public static void main(String args[]){
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to C and C.");
        System.out.println("There will be a series of prompts, to answer each simply type a number.");
        System.out.println("At any point you can type \"q\" to quit.");
        String prompt = "Would you like to [1] play with the default character or [2] create your own: ";
        int choice = getChoice(s, 2, prompt);
        Entity entity;
        if (choice == 1) {
            entity = new Fighter("Fighter");
        }
        if (choice == 2) {
            entity = new Mage("Mage");
        }
        else{
            entity = getCustomCharacter();
        }
        System.out.println(entity);
        entity.attack(entity);
        System.out.println("Thank you for playing!");
        s.close();
    }  

     /*
     * returns custom character
     */
    private static Entity getCustomCharacter(){
        //TODO implement this function in sprint 2
        return new Fighter("Fighter");
    }

    /*
     * returns an integer representing the choice
     * the user makes and parses their input
     */
    private static int getChoice(Scanner s, int num_options, String prompt){
        int choice = 0;
        while(!(1 <= choice && choice <= num_options)){
            System.out.print(prompt);
            try{
                choice = Integer.parseInt(s.nextLine());
            }
            catch(NumberFormatException e){
                ask_quit(s);
            }
        }
        return choice;
    }

    /*
     * Prompts user if they would really like to quit.
     */
    private static void ask_quit(Scanner s){
        System.out.print("Are you sure you would like to quit? [y/n]: ");
        char c = s.nextLine().charAt(0);
        if (c == 'y'){
            System.out.println("\n");
            System.out.println("Thanks for playing!");
            s.close();
            System.exit(0);
        }
        else{
            return;
        }
    }
}
