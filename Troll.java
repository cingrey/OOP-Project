/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This programs defines a Troll class that will act as the enemy for the player in our prototype.
Unlike the subclasses of Entity meant for player control. This class should operate in the game
without user input.
*/
import java.util.Random;
import java.util.ArrayList;

public class Troll extends Entity{
    /*
    Subclass variables:
    bloodied - (boolean) indicates if currHP is below half maxHP to trigger Flurry ability
    */
    private boolean bloodied;
    public Troll(){
        name = "Troll";
        maxHP = 84;
        currHP = maxHP;
        armor_class = 15;
        dice = new Random();
        abilities = new ArrayList<String>();
        abilities.add("bite - minor attack with teeth");
        abilities.add("claw - brutal attack with sharp claws");
        abilities.add("flurry - enraged flurry of attacks");
        bloodied = false;
    }
    @Override
    public void takeDMG(int damage){
        //if currHP falls below half maxHP, trigger bloodied flag
        System.out.println(name + " takes " + damage + " damage!");
        setCurrHP(currHP-damage);
        if (currHP <= (maxHP/2)){
            bloodied = true;
            System.out.println("It's wounds are no longer healing!\nBut it's now rampaging!");
        }
        if (currHP<0){
            isAlive = false;
            //System.out.println(name + " is dead!");
        }
    }
    @Override
    public void attack(Entity target){
        if (currHP<=maxHP & !bloodied){
            heal();
        }
        if (!bloodied){
            int attack = dice.nextInt(2);
            if (attack==0){
                bite(target, 7);
            }
            if (attack==1){
                claw(target, 7);
            }
        }
        else{
            flurry(target);
        }
    }
    @Override
    public void heal(){
        System.out.println("Some of the troll's wounds are closing up!");
        if ((currHP+10)>=maxHP){
            currHP = maxHP;
        }
        else{
            currHP += 10;
        }
    }
    public void bite(Entity target, int modifier){
        System.out.println("The troll goes for a bite!");
        if (check(target.getArmorClass(), modifier)){
            System.out.println("It chomps on " + target.getName() + "!");
            int damage = generic_roll(1, 6)+4;
            target.takeDMG(damage);
        }
        else{
            System.out.println("And misses!");
        }
    }
    public void claw(Entity target, int modifier){
        System.out.println("The troll swipes with a claw!");
        if (check(target.getArmorClass(), modifier)){
            System.out.println("The claw scrapes " + target.getName() + "!");
            int damage = generic_roll(2,6)+4;
            target.takeDMG(damage);
        }
        else{
            System.out.println("But misses!");
        }
    }
    public void flurry(Entity target){
        System.out.println("The troll makes wild attacks in its bloodied state!");
        claw(target, 3);
        claw(target, 3);
        bite(target, 3);
    }
}