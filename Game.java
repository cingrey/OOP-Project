/*COMP305 - Game
Professor - A. Nuzen
Author: Team COBOL
This is the driver class of the game.
*/

import java.util.NoSuchElementException;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.Random;


public class Game extends Object{
    public static void main(String args[]){
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to C and C.");
        System.out.println("There will be a series of prompts, to answer each simply type a number.");
        System.out.println("At any point you can type \"q\" to quit.");
        String prompt = "Would you like to [1] play with the default character or [2] create your own: ";
        int choice = getChoice(s, 2, prompt);
        Entity player;
        Random rand = new Random();
        if (choice == 1) {
            player = new Fighter("Fighter");
        }
        else{
            player = getCustomCharacter(s);
        }
        System.out.println(player);

        String[][] story = getStory("Stories/Default.csv");

        goToLoc(s, 0, player, story, rand); // Start the game
        Troll troll = new Troll();//create enemy for eventual combat encounter
        battle(player,troll,s, rand);//run combat encounter
        
        System.out.println("Thank you for playing!");
        s.close();
    }  

    private static void goToLoc(Scanner s, int locId, Entity player, String[][] story, Random rand){
        System.out.println(story[locId][2]); // print exposition
        int locType = 0;
        int choice;
        locType = Integer.parseInt(story[locId][1]);
        if (locType == 0){
            choice = getChoice(s, Integer.parseInt(story[locId][4]), story[locId][3]); // Print prompt and get selection
        }
        else if (locType == 2){
            choice = rand.nextInt(Integer.parseInt(story[locId][4])) + 1;
        }
        else{ // is of type one which means go to final boss
            return;
        }
        goToLoc(s, Integer.parseInt(story[locId][choice+4]), player, story, rand);
    }

    private static String[][] getStory(String file_name){
        Scanner sc = new Scanner(System.in); // To initialize
        try{
            sc = new Scanner(new File(file_name)); 
        }
        catch(FileNotFoundException e){
            System.out.println("Story not found");
            System.exit(1);
        }    
        sc.useDelimiter(",");   //sets the delimiter pattern  
        sc.next(); // Dont read first cell this is for formatting
        int num_rows = Integer.parseInt(sc.next());
        sc.next(); // Dont read "Array Width cell"
        int num_cols = Integer.parseInt(sc.next());
        String[][] story = new String[num_rows][num_cols]; // This will hold an array of all story events
        
        for(int x = 0; x < num_cols * 2 - 3; x++){
            sc.next(); // read empty cells we do not need
        }
        // Fill in the entire story matrix with the csv sheet
        for(int x = 0; x < num_rows; x++){
            for(int y = 0; y < num_cols; y++){
                try{
                    story[x][y] = sc.next();  //find and returns the next complete token from this scanner  
                }
                catch(NoSuchElementException e){
                    break; // no more things to read
                }
            }
        }
        sc.close();  //closes this csv reading scanner 
        return story;
    }

     /*
     * returns custom character
     */
    private static Entity getCustomCharacter(Scanner s){
        int choice = getChoice(s, 3, "Would you like to be a [1] Fighter, [2] Mage, [3] Rogue: ");
        System.out.println("Input the name of your character: ");
        String name = s.nextLine();
        if (choice == 1){
            return new Fighter(name);
        }
        if (choice == 2){
            return new Mage(name);
        }
        return new Rogue(name);
    }

    /*
     * returns an integer representing a choice
     * the user makes and parses their input
     */
    private static int getChoice(Scanner s, int num_options, String prompt){
        int choice = 0;
        while(!(1 <= choice && choice <= num_options)){
            System.out.print(prompt);
            String input = s.nextLine();
            try{
                choice = Integer.parseInt(input);
            }
            catch(NumberFormatException e){
                if (input.equals("q")){
                    ask_quit(s);
                }
                else{
                    System.out.println("Invalid input");
                }
            }
        }
        return choice;
    }

    /*
     * Facilitates battle between player and one other entity
     * prints a description when either entity's isAlive flag
     * hits "false"
     * The first entity is the player and the second player is the enemy
    */
    private static void battle(Entity first, Entity second, Scanner s, Random rand){
        System.out.println(first.getName()+" and "+second.getName()+" are now fighting!");
        Random enemyChoice = rand;
        while (first.getStatus()&&second.getStatus()){
            first.display_actions();
            first.attack(second, getChoice(s, first.getAbilities().size(), "What's "+first.getName()+"'s next move? "));
            if (second.getStatus()){
                second.attack(first, enemyChoice.nextInt(2));
            }
        }
        if (!first.getStatus()){
            System.out.println(first.getName()+" has fallen!");
        }
        if (!second.getStatus()){
            System.out.println(second.getName()+" has fallen!");
        }
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
