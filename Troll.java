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
        super.takeDMG(damage);
        if (getStatus() && getCurrHP()<=(getMaxHP()/2)){
            bloodied = true;
            System.out.println("It's wounds are no longer healing!\nBut it's now rampaging!");
        }
    }
    @Override
    public void attack(Entity target){
        /*
        Narrates Troll attack
        if Troll has sufficent HP, it can heal before it attacks
        chooses attack option randomly
        if bloodied flag is true, always uses flurry
        */
        if (getCurrHP()<=getMaxHP() & !bloodied){
            heal();
        }
        if (!bloodied){
            int attack = generic_roll(1,2);
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
        //Troll heals flat 10 HP
        System.out.println("Some of the troll's wounds are closing up!");
        setCurrHP(getMaxHP()+10);
        if (getCurrHP()>=(getMaxHP()/2)){
            bloodied = false;
        }
    }

    //new methods to implement attack options
    public void bite(Entity target, int modifier){
        //Narrates attempt to bite target
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
        //Narrates attempt to slash target with claws
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
        //Narrates series of desperate attacks; more attacks but each is less likely to hit target
        System.out.println("The troll makes wild attacks in its bloodied state!");
        claw(target, 3);
        claw(target, 3);
        bite(target, 3);
    }
}