/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Rogue character for the game.
*/
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Rogue extends Entity{
    /*
    Subclass variables:
    sneak - (boolean) indicates if character is
    */
    private boolean sneak;
    private boolean insight;
    public Rogue(String name){
        setName(name);
        setMaxHP(45);
        setCurrHP(getMaxHP());
        setArmorClass(14);
        dice = new Random();
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("Ambush - calculated attack; more damage if hidden");
        powers.add("Analyze - gain insight into target; makes next attack more likely to hit");
        powers.add("Hide - become harder for target to hit; engages sneak status");
        setAbilities(powers);
        setStatus(true);
        sneak = false;
        insight = false;
    }
    @Override
    public void takeDMG(int damage){
        super.takeDMG(damage);
        if (sneak && getStatus()){
            setArmorClass(14);
            System.out.println(getName() + " got knocked out of cover!");
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
            ambush(target);
        }
        if (choice==2){
            analyze(target);
        }
        if (choice==3){
            hide();
        }
        s.close();
    }
    @Override
    public void heal(){
        int newHP = dice.nextInt(8)+2;
        if ((getCurrHP()+newHP)<=getMaxHP()){
            setCurrHP(getMaxHP());
        }
        else{
            setCurrHP(getCurrHP()+newHP);
        }
        System.out.println(getName() + " tends to their wounds and recovers " + newHP + " hit points.");
    }

    public void ambush(Entity target){
        System.out.println(getName() + " tries to stab the enemy!");
        int modifier = 6;
        if (insight){
            System.out.println("Attacking a weak point!");
            modifier = 8;
        }
        if (check(target.getArmorClass(), modifier)){
            System.out.println("Successful strike!");
            int damage = generic_roll(1,6)+3;
            if (sneak){
                System.out.println("Critical hit!");
                damage += generic_roll(4,6);
                sneak = false;
                insight = false;
                System.out.println("*Stealth and Insight have worn off*");
            }
            target.takeDMG(damage);
        }
        else{
            System.out.println("But it's a miss!");
        }
    }
    public void analyze(Entity target){
        if (!insight){
            insight = true;
            System.out.println(getName() + " gains insight into the enemy's next few movements!");
            System.out.println(target);
        }
        else{
            System.out.println(getName() + " already has an opening.");
            System.out.println(target);
        }
    }
    public void hide(){
        //sets sneak to true
        if (!sneak){
            sneak = true;
            setArmorClass(16);
            System.out.println(getName() + " takes cover!");
        }
        else{
            System.out.println(getName() + " is already hiding!");
        }
    }
}