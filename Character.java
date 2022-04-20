/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This programs defines an abstract Character class that will establish base
statistics and options for players in our text-based game system
*/
package CHARACTER;
import java.util.Random;

public abstract class Character {
    /* This class defines base statistics and options for players in our text-based
    game.
    Class variables:
    name - (String) the player's character's name
    currHP - (int) represents the character's current health condition; 0 means
        the character is dead.
    maxHP - (int) represents the character's complete health; currHP cannot exceed this value
    armor_class - (int) represents the character's overall ability to avoid/deflect attacks
    dice - (Random) implements dice generation for random outcomes based on player choices
        and character statistics
    */
    protected String name;
    protected int currHP;
    protected int maxHP;
    protected int armor_class;
    protected Random dice = new Random();

    //accessor methods
    public String getName(){
        return name;
    }
    public int getCurrHP(){
        return currHP;
    }
    public int getMaxHP(){
        return maxHP;
    }
    public int getArmorClass(){
        return armor_class;
    }

    //mutator methods
    public void setName(String name){
        this.name = name;
    }
    public void setCurrHP(int currHP){
        this.currHP = currHP;
    }
    public void setMaxHP(int maxHP){
        this.maxHP = maxHP;
    }
    public void setArmorClass(int armorClass){
        this.armor_class = armorClass;
    }

    public int generic_roll(int num, int base){
        /*
        Rolls a die with sides equal to base parameter; for flexible implementation
        Parameters:
        num - (int) number of dice to roll
        base - (int) the number of sides for the simulated dice
        Returns resulting integer sum from die roll
        */
        int total = 0;
        for (int i=0; i<num; i++){
            total += dice.nextInt(base)+1;
        }
        return total;
    }
    public int roll_d20(){
        /*
        Rolls a 20-sided die, the basis for calculating in-game outcomes
        Parameters - None
        Returns an integer from 1-20 (inclusive) as if the player rolled
        a 20-sided die
        */
        return dice.nextInt(20)+1;
    }

    public abstract void attack(Object target);
    //TODO: adjust parameter type
    //simulates, describes, and resolves an attack between the player and
    //opposing entity
    //to be overridden by subclasses per their unique design features

    public abstract int roll_damage();
    //calculates how much damage a character's attack/ability would deal;
    //should call generic_roll

    public abstract void heal();
    //represents player's attempt to heal character; should call setCurrHP

    @Override
    public String toString(){
        return "Name: " + name + "\n" + "HP: " + currHP + " / " + maxHP;
    }
}