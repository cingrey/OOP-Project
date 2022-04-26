/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Fighter character for the game.
*/
package COBOL;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Fighter extends Entity{
    /*
    Subclass variables:
    bracing - (boolean) indicates if character is bracing for an incoming attack
    fury - (boolean) resource/indicator for empowered attacks
    */
    private boolean bracing = false;
    private boolean fury = false;
    public Fighter(String name){
        this.name = name;
        maxHP = 58;
        currHP = maxHP;
        armor_class = 17;
        dice = new Random();
        abilities = new ArrayList<String>(3);
        abilities.add("Sword - basic melee strike; empowered by fury");
        abilities.add("Crossbow - basic ranged attack");
        abilities.add("Brace - reduces incoming damage; prepares fury");
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
            swordATK(target);
        }
        if (choice==2){
            crossbowATK(target);
        }
        if (choice==3){
            brace();
        }
        s.close();
    }

    public void swordATK(Entity target){
        //Narrates melee attack with sword
        //fury will double attack damage;
        System.out.println(name + " swings their sword!");
        if (roll_d20()+7 >= target.getArmorClass()){
            System.out.println("And lands a solid hit!");
            int damage = generic_roll(1,8)+3;
            if(fury){
                System.out.println("Fury empowers the attack!");
                damage *= 2;
                fury = false;
            }
            target.takeDMG(damage);
        }
        else{
            System.out.println("But misses!");
        }
    }

    public void crossbowATK(Entity target){
        //Narrates ranged attack with crossbow
        //fury will allow immediate second attack
        System.out.println(name + " fires their crossbow!");
        if (roll_d20()+6 >= target.getArmorClass()){
            int damage = generic_roll(1,10)+2;
            System.out.println("And lands a solid hit!");
            target.takeDMG(damage);
        }
        else{
            System.out.println("But misses!");
        }
        if (fury){
            System.out.println("Furious volley!");
            fury = false;
            crossbowATK(target);
        }
    }

    public void brace(){
        //set bracing to true if it is false
        if (!bracing){
            bracing = true;
            System.out.println(name + " braces for the next attack!"); 
        }
        else{
            System.out.println("Oops! " + name + " is already bracing!");
        }
    }

    @Override
    public void takeDMG(int damage){
        //bracing flag allows character to halve next incoming damage and trigger fury flag
        if (bracing){
            damage /= 2;
            fury = true;
            System.out.println("The blow heightens " + name + "'s fury!");
            bracing = false;
        }
        System.out.println(name + " takes " + damage + " damage!");
        setCurrHP(currHP-damage);
        System.out.println(name + " has " + currHP + " remaining!");
        if (currHP<0){
            isAlive = false;
            System.out.println(name + " has died!");
        }
    }

    @Override
    public void heal(){
        int newHP = dice.nextInt(10)+3;
        if ((currHP+newHP)<=maxHP){
            currHP = maxHP;
        }
        else{
            currHP += newHP;
        }
        System.out.println(name + " tends to their wounds and recovers " + newHP + " hit points.");
    }  
}