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
        setName(name);
        setMaxHP(112);//maxHP = 112;
        setCurrHP(getMaxHP());//currHP = maxHP;
        setArmorClass(18);//armor_class = 18;
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("[1] Sword - basic melee strike; empowered by fury");
        powers.add("[2] Crossbow - basic ranged attack; loads faster with fury");
        powers.add("[3] Brace - reduces incoming damage; prepares fury");
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
            print_sleep("The blow heightens " + getName() + "'s fury!");
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
        print_sleep(getName() + " draws upon willpower and recovers " + newHP + " hit points.");
    }
    @Override
    public void display_actions(){
        //include fury and bracing status
        super.display_actions();
        if (isBracing()){
            System.out.println("Bracing for next attack");
        }
        if (hasFury()){
            System.out.println("Furious!");
        }
    }

    //new methods to implement attack options
    public void swordATK(Entity target){
        /*
        Narrates melee attack with sword
        fury will double attack damage;
        */
        print_sleep(getName() + " swings their sword!");
        if (check(target.getArmorClass(),8)){
            print_sleep("And lands a solid hit!");
            int damage = generic_roll(1,10)+4;
            if(fury){
                print_sleep("Fury empowers the attack!");
                damage *= 3;
                fury = false;
            }
            target.takeDMG(damage);
        }
        else{
            print_sleep("But misses!");
        }
    }
    public void crossbowATK(Entity target){
        /*
        Narrates ranged attack with crossbow
        fury will allow immediate additional attacks
        */
        print_sleep(getName() + " fires their crossbow!");
        if (check(target.getArmorClass(), 6)){
            int damage = generic_roll(1,12)+2;
            print_sleep("And lands a solid hit!");
            target.takeDMG(damage);
        }
        else{
            print_sleep("But misses!");
        }
        if (fury){
            print_sleep("Furious volley! (4 more shots!)");
            fury = false;
            int volley = 4;
            while (target.getStatus()&&volley>0){//implement volley; stop if target dies
                volley--;
                crossbowATK(target);
            }
        }
    }
    public void brace(){
        //set bracing to true if it is false
        if (!bracing){
            bracing = true;
            print_sleep(getName() + " braces for the next attack!");
            if (getCurrHP()<(getMaxHP()/2)){
                heal();
            }
        }
        else{
            print_sleep("Oops! " + getName() + " is already bracing!");
        }
    }  
}