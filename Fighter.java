/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Fighter character for the game.
*/
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Fighter extends Entity{
    /*
    Subclass variables:
    bracing - (boolean) indicates if character is bracing for an incoming attack
    fury - (boolean) resource/indicator for empowered attacks
    */
    private boolean bracing;
    private boolean fury;
    public Fighter(String name){
        setName(name);//this.name = name;
        setMaxHP(58);//maxHP = 58;
        setCurrHP(getMaxHP());//currHP = maxHP;
        setArmorClass(17);//armor_class = 17;
        dice = new Random();
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("Sword - basic melee strike; empowered by fury");
        powers.add("Crossbow - basic ranged attack");
        powers.add("Brace - reduces incoming damage; prepares fury");
        setAbilities(powers);
        setStatus(true);//isAlive = true;
        bracing = false;
        fury = false;
    }
    @Override
    public void takeDMG(int damage){
        //bracing flag allows character to halve next incoming damage and trigger fury flag
        if (bracing){
            damage /= 2;
            fury = true;
            System.out.println("The blow heightens " + getName() + "'s fury!");
            bracing = false;
        }
        System.out.println(getName() + " takes " + damage + " damage!");
        setCurrHP(getCurrHP()-damage);
        System.out.println(getName() + " has " + getCurrHP() + " remaining!");
        if (getCurrHP()<0){
            setStatus(false);
            //System.out.println(getName() + " is dead!");
        }
    }
    @Override
    public void attack(Entity target){
        /*
        Prompts user to choose from combat options/actions
        Narrates outcome of choice
        */
        Scanner s = new Scanner(System.in);
        System.out.println("What is "+getName()+"'s next move?");
        display_actions();
        int choice = s.nextInt();
        while (choice>getAbilities().size()){ //TODO: implement better validating and error handling
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
    @Override
    public void heal(){
        int newHP = dice.nextInt(10)+3;
        if ((getCurrHP()+newHP)<=getMaxHP()){
            setCurrHP(getMaxHP());
        }
        else{
            setCurrHP(getCurrHP()+newHP);
        }
        System.out.println(getName() + " tends to their wounds and recovers " + newHP + " hit points.");
    }
    public void swordATK(Entity target){
        //Narrates melee attack with sword
        //fury will double attack damage;
        System.out.println(getName() + " swings their sword!");
        if (check(target.getArmorClass(),7)){
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
        System.out.println(getName() + " fires their crossbow!");
        if (check(target.getArmorClass(), 6)){
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
            System.out.println(getName() + " braces for the next attack!"); 
        }
        else{
            System.out.println("Oops! " + getName() + " is already bracing!");
        }
    }  
}