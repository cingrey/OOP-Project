/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This programs defines a Troll class that will act as the enemy for the player in our prototype.
Unlike the subclasses of Entity meant for player control. This class should operate in the game
without user input.
*/
import java.util.ArrayList;

public class Troll extends Entity{
    /*
    Subclass variables:
    bloodied - (boolean) indicates if currHP is below half maxHP to trigger Flurry ability
    */
    private boolean bloodied;
    public Troll(){
        setName("Troll");//name = "Troll";
        setMaxHP(84);//maxHP = 84;
        setCurrHP(getMaxHP());//currHP = maxHP;
        setArmorClass(15);//armor_class = 15;
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("bite - minor attack with teeth");
        powers.add("claw - brutal attack with sharp claws");
        powers.add("flurry - enraged flurry of attacks");
        setAbilities(powers);
        bloodied = false;
        setStatus(true);
    }

    //accessor method
    public boolean isBloodied(){
        return bloodied;
    }
    
    //mutator method
    public void setBloodied(boolean bloodied){
        this.bloodied = bloodied;
    }

    //Overridden methods
    @Override
    public void takeDMG(int damage){
        //if currHP falls below half maxHP, trigger bloodied flag
        print_sleep("The troll takes " + damage + " damage!");
        setCurrHP(getCurrHP()-damage);
        if (getCurrHP()==0){
            setStatus(false);
        }
        if (getStatus() && getCurrHP()<=(getMaxHP()/3) && !bloodied){
            bloodied = true;
            print_sleep("It's wounds are no longer healing!\nBut it's now rampaging!");
        }
    }
    @Override
    public void attack(Entity target, int choice){
        /*
        Narrates Troll attack
        if Troll has sufficent HP, it can heal before it attacks
        chooses attack option randomly
        if bloodied flag is true, always uses flurry
        */
        if (getCurrHP()<getMaxHP() & !bloodied){
            heal();
        }
        if (!bloodied){
            if (choice==0){
                bite(target, 7);
            }
            if (choice==1){
                claw(target, 7);
            }
        }
        else{
            flurry(target);
        }
    }
    @Override
    public void heal(){
        //Troll heals flat 7 HP on its turn
        print_sleep("Some of the troll's wounds are closing up!");
        setCurrHP(getCurrHP()+7);
        if (getCurrHP()>=(getMaxHP()/3)){
            bloodied = false;
        }
    }

    //new methods to implement attack options
    public void bite(Entity target, int modifier){
        //Narrates attempt to bite target
        print_sleep("The troll goes for a bite!");
        if (check(target.getArmorClass(), modifier)){
            print_sleep("It chomps on " + target.getName() + "!");
            int damage = generic_roll(1, 6)+4;
            target.takeDMG(damage);
        }
        else{
            print_sleep("And misses!");
        }
    }
    public void claw(Entity target, int modifier){
        //Narrates attempt to slash target with claws
        print_sleep("The troll swipes with a claw!");
        if (check(target.getArmorClass(), modifier)){
            print_sleep("The claw scrapes " + target.getName() + "!");
            int damage = generic_roll(2,6)+4;
            target.takeDMG(damage);
        }
        else{
            print_sleep("But misses!");
        }
    }
    public void flurry(Entity target){
        //Narrates series of desperate attacks; more attacks but each is less likely to hit target
        print_sleep("The troll makes wild attacks in its bloodied state!");
        claw(target, 5);
        if (target.getStatus()){
            claw(target, 5);
        }
        if (target.getStatus()){
            bite(target, 5);
        }
    }
}