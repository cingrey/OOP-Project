/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Rogue character for the game.
*/
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Rogue extends Entity{
    /*
    Subclass variables:
    sneak - (boolean) indicates if character is
    */
    private boolean sneak;
    public Rogue(String name){
        this.name = name;

        //abilities = new ArrayList<String>(3);
        isAlive = true;
    }

    @Override
    public void attack(Entity target){
        /*
        Prompts user to choose from combat options/actions
        Narrates outcome of choice
        */

        /*Scanner s = new Scanner(System.in);
        System.out.println("What is "+name+"'s next move?");
        display_actions();
        int choice = s.nextInt();*/

    }

    public void sneak(){
        /*   */
    }

    @Override
    public void heal(){
         
    }
}