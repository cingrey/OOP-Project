/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Mage character for the game.
*/
package COBOL;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Mage extends Entity{
    /*
    Subclass variables:
    mana - (int) 
    */
    public int mana;
    public Mage(String name){
        this.name = name;

        abilities = new ArrayList<String>(1);
        abilities.add("Cast Spell - ");
        isAlive = true;
    }

    @Override
    public void attack(Entity target){
        /*
        Prompts user to choose from combat options/actions
        Narrates outcome of choice
        */

        Scanner s = new Scanner(System.in);
        System.out.println("What is "+name+"'s next move?");
        display_actions();
        int choice = s.nextInt();

        while (choice>abilities.size()){ //TODO: implement better validating and error handling
            System.out.println("Improper input. Please choose a numbered option:");
            display_actions();
            choice = s.nextInt();
        }
        if (choice==1){
            cast_spell();
        }
    
    }

    public void cast_spell(){
        /* A form of attack  */
        
    }
}