/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Rogue character for the game.
*/
import java.util.ArrayList;

public class Rogue extends Entity{
    /*
    Subclass variables:
    sneak - (boolean) indicates if character is hiding; increases damage of next ambush action as well as armor_class
    insight - (boolean) indicates if character has advantage for the next attack; more likely to land next ambush
    */
    private boolean sneak;
    private boolean insight;
    public Rogue(String name){
        setName(name);
        setMaxHP(87);
        setCurrHP(getMaxHP());
        setArmorClass(17);
        ArrayList<String> powers = new ArrayList<String>(3);
        powers.add("[1] Ambush - calculated attack; more damage if hidden");
        powers.add("[2] Analyze - gain insight into target; makes next attack more likely to hit");
        powers.add("[3] Hide - become harder for target to hit; engages sneak status");
        setAbilities(powers);
        setStatus(true);
        sneak = false;
        insight = false;
    }

    //accessor methods for new attributes
    public boolean isSneak() {
        return sneak;
    }
    public boolean hasInsight(){
        return insight;
    }

    //mutator methods for new attributes
    public void setSneak(boolean sneak){
        this.sneak = sneak;
    }
    public void setInsight(boolean insight){
        this.insight = insight;
    }

    //Overridden methods
    @Override
    public void takeDMG(int damage){
        super.takeDMG(damage);
        if (sneak && getStatus()){
            setArmorClass(17);
            print_sleep(getName() + " got knocked out of cover!");
            sneak = false;
            insight = false;
            print_sleep("*Stealth and Insight have worn off*");
        }
    }
    @Override
    public void attack(Entity target, int choice){
        /*
        Prompts user to choose from combat options/actions
        Narrates outcome of choice
        */
        if (choice==1){
            ambush(target);
        }
        if (choice==2){
            analyze(target);
        }
        if (choice==3){
            hide();
        }
    }
    @Override
    public void heal(){
        //Rogue heals back HP based on 8-sided die with a modifier of +2
        int newHP = generic_roll(1,8)+2;
        setCurrHP(getMaxHP()+newHP);
        print_sleep(getName() + " tends to their wounds and recovers " + newHP + " hit points.");
    }

    //new methods to implement attack options
    public void ambush(Entity target){
        /*
        Narrates ambush attempt
        insight provides an additional chance for attack to hit
        sneak increases amount of damage dealt
        sets insight and sneak to false
        */
        print_sleep(getName() + " tries to stab the enemy!");
        boolean advantage = false;
        if (insight){
            print_sleep("Attacking a weak point!");
            advantage = check(target.getArmorClass(), 9);
        }
        if (check(target.getArmorClass(), 9) || advantage){
            print_sleep("Successful strike!");
            int damage = generic_roll(1,6)+5;
            if (sneak){
                print_sleep("Critical hit!");
                damage += generic_roll(6,6);
            }
            target.takeDMG(damage);
        }
        else{
            print_sleep("But it's a miss!");
        }
    }
    public void analyze(Entity target){
        /*
        Narrates effort to learn about target
        sets insight to true
        prints out information on target
        */
        if (!insight){
            insight = true;
            print_sleep(getName() + " gains insight into the enemy's next few movements!");
            print_sleep(target.toString());
        }
        else{
            print_sleep(getName() + " already has an opening.");
            print_sleep(target.toString());
        }
    }
    public void hide(){
        //sets sneak to true
        if (!sneak){
            sneak = true;
            setArmorClass(22);
            print_sleep(getName() + " takes cover!");
        }
        else{
            print_sleep(getName() + " is already hiding!");
        }
    }
}