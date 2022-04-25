/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This programs defines an abstract Entity class that will establish base
statistics and options for all active entities in our text-based game system
*/
package COBOL;
import java.util.Random;
import java.util.ArrayList;

public abstract class Entity extends Object{
    /* This class defines base statistics and options in our text-based
    game.
    Class variables:
    name - (String) the entity's name
    currHP - (int) represents current health condition; 0 means the entity is dead for combat/narrative purposes.
    maxHP - (int) represents complete health; currHP cannot exceed this value
    armor_class - (int) represents the character's overall ability to avoid/deflect attacks
    dice - (Random) implements dice generation for random outcomes based on player choices
        and character statistics
    abilities - (String array) stores specific action options
    isAlive - (boolean) indicates if currHP is <= 0
    */
    protected String name;
    protected int currHP;
    protected int maxHP;
    protected int armor_class;
    protected Random dice;
    protected ArrayList<String> abilities;
    protected boolean isAlive;

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
    public ArrayList<String> getAbilities(){
        return abilities;
    }
    public boolean getStatus(){
        return isAlive;
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
    public void setAbilities(ArrayList<String> abilities){
        this.abilities = abilities;
    }
    public void addAbility(String ability){
        abilities.add(ability);
    }
    public void removeAbility(String ability){
        abilities.remove(ability);
    }

    public int generic_roll(int num, int base){
        /*
        Rolls num parameter dice, each with sides equal to base parameter; for flexible implementation
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
    public void display_options(){
        System.out.println("Abilities");
        for(int i=0; i<abilities.size();i++){
            System.out.println(abilities.get(i)+"\n");
        }
    }

    public void takeDMG(int damage){
        System.out.println(name + " takes " + damage + " damage!");
        setCurrHP(currHP-damage);
        System.out.println(name + " has " + currHP + " remaining!");
        if (currHP<0){
            isAlive = false;
            System.out.println(name + " has died!");
        }
    }

    public abstract void attack(Entity target);
    //simulates, describes, and resolves an attack between the player and
    //opposing entity
    //to be overridden by subclasses per their unique design features

    public abstract void heal();
    //represents player's attempt to heal character; should call setCurrHP
    //should not allow currHP to exceed maxHP

    @Override
    public String toString(){
        String stats = "Name: " + name + "\nHP: " + currHP + " / " + maxHP + "\nAC: " + armor_class + "\nAbilities:\n";
        for(int i=0; i<abilities.size();i++){
            stats += abilities.get(i)+"\n";
        }
        return stats;
    }
}