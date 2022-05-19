/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Fighter character for the game.
*/
import java.util.ArrayList;

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
        setMaxHP(112);//maxHP = 112;
        setCurrHP(getMaxHP());//currHP = maxHP;
        setArmorClass(19);//armor_class = 19;
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("Sword - basic melee strike; empowered by fury");
        powers.add("Crossbow - basic ranged attack");
        powers.add("Brace - reduces incoming damage; prepares fury");
        setAbilities(powers);
        setStatus(true);//isAlive = true;
        bracing = false;
        fury = false;
    }

    //accessor methods for new attributes
    public boolean isBracing(){
        return bracing;
    }
    public boolean hasFury(){
        return fury;
    }

    //mutator methods for new attributes
    public void setBracing(boolean bracing){
        this.bracing = bracing;
    }
    public void setFury(boolean fury){
        this.fury = fury;
    }

    //Overridden methods
    @Override
    public void takeDMG(int damage){
        //bracing flag allows character to halve next incoming damage and trigger fury flag
        if (bracing){
            damage /= 2;
            fury = true;
            System.out.println("The blow heightens " + getName() + "'s fury!");
            bracing = false;
        }
        super.takeDMG(damage);
    }
    @Override
    public void attack(Entity target, int choice){
        /*
        Narrates outcome of choice
        */
        if (choice==1){
            swordATK(target);
        }
        if (choice==2){
            crossbowATK(target);
        }
        if (choice==3){
            brace();
        }
    }
    @Override
    public void heal(){
        //Fighter heals back HP based on 10-sided die with a modifier of +3
        int newHP = generic_roll(1,10)+3;
        setCurrHP(getCurrHP()+newHP);
        System.out.println(getName() + " draws upon willpower and recovers " + newHP + " hit points.");
    }

    //new methods to implement attack options
    public void swordATK(Entity target){
        /*
        Narrates melee attack with sword
        fury will double attack damage;
        */
        System.out.println(getName() + " swings their sword!");
        if (check(target.getArmorClass(),8)){
            System.out.println("And lands a solid hit!");
            int damage = generic_roll(1,10)+4;
            if(fury){
                System.out.println("Fury empowers the attack!");
                damage *= 3;
                fury = false;
            }
            target.takeDMG(damage);
        }
        else{
            System.out.println("But misses!");
        }
    }
    public void crossbowATK(Entity target){
        /*
        Narrates ranged attack with crossbow
        fury will allow immediate second attack
        */
        System.out.println(getName() + " fires their crossbow!");
        if (check(target.getArmorClass(), 6)){
            int damage = generic_roll(2,8)+2;
            System.out.println("And lands a solid hit!");
            target.takeDMG(damage);
        }
        else{
            System.out.println("But misses!");
        }
        if (fury){
            System.out.println("Furious volley! (2 more shots!)");
            fury = false;
            crossbowATK(target);
            crossbowATK(target);

        }
    }
    public void brace(){
        //set bracing to true if it is false
        if (!bracing){
            bracing = true;
            System.out.println(getName() + " braces for the next attack!");
            heal(); 
        }
        else{
            System.out.println("Oops! " + getName() + " is already bracing!");
        }
    }  
}