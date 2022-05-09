/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Mage character for the game.
*/
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
 
public class Mage extends Entity{
    /*
    Subclass variables:
    mana - (int)
    */
    private int mana;
    private final int MANA_MAX = 32;
    public Mage(String name){
        setName(name);//this.name = name;
        setMaxHP(37);//maxHP = 37;
        setCurrHP(getMaxHP());//currHP = maxHP
        setArmorClass(11);//armor_class = 11;
        dice = new Random();
        ArrayList<String> powers = new ArrayList<String>(4);
        powers.add("Chromatic Orb - Versatile elemental attack; costs 4 mana");
        powers.add("Magic Missile - Low damage but guaranteed hit; costs 4 mana");
        powers.add("Shatter - Thunderous burst; costs 6 mana");
        powers.add("Necrotic Grasp - Hard-to-hit life drain; costs 10 mana");
        setAbilities(powers);
        setStatus(true);
        mana = MANA_MAX;
    }
    public boolean spendMana(int cost){
        //checks if character has enough mana to cast spell
        //if so, subtract mana according to listed costs
        if (cost<=mana){
            mana -= cost;
            System.out.println("This spell leaves " + getName() + " at " + mana + " mana!");
            return true;
        }
        else{
            System.out.println(getName() + " doesn't have enough mana to cast this spell!");
            return false;
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
            ChromaticOrb(target);
        }
        if (choice==2){
            MagicMissile(target);
        }
        if (choice==3){
            Shatter(target);
        }
        if (choice==4){
            NecroticGrasp(target);
        }
        s.close();
    }
    @Override
    public void heal(){
        int newHP = dice.nextInt(6)+1;
        if ((getCurrHP()+newHP)<=getMaxHP()){
            setCurrHP(getMaxHP());
        }
        else{
            setCurrHP(getCurrHP()+newHP);
        }
        if ((mana+newHP)<=MANA_MAX){
            mana = MANA_MAX;
        }
        else{
            mana += newHP;
        }
        System.out.println(getName() + " channels free mana to recover " + newHP + "hit points and mana!");
    }
    @Override
    public String toString() {
        String stats = super.toString();
        stats += "Mana: " + mana + "/" + MANA_MAX;
        return stats;
    }

    public void ChromaticOrb(Entity target){
        //Narrates elemental orb throw
        if (spendMana(4)){
            System.out.println(getName() + " launches an orb of chaotic energy!");
            if (check(target.getArmorClass(),6)){
                int damage = generic_roll(3,8);
                System.out.println("And hits!");
                target.takeDMG(damage);
            }
            else{
                System.out.println("But it misses its target!");
            }
        }
    }
    public void MagicMissile(Entity target){
        //Narrates magical homing attack
        if (spendMana(4)){
            System.out.println(getName() + " sends 3 magical homing bolts from their fingers!");
            int damage = (generic_roll(1,4)+1)*3;
            System.out.println("They dig into the target!");
            target.takeDMG(damage);
        }
    }
    public void Shatter(Entity target){
        //Narrates explosion attack
        if (spendMana(6)){
            System.out.println(getName() + " engulfs the enemy in an explosion!");
            int damage = generic_roll(3,8);
            if (!(check(target.getArmorClass(),6))){
                System.out.println("The enemy used cover to mitigate the damage!");
                damage /= 2;
            }
            target.takeDMG(damage);
        }
    }
    public void NecroticGrasp(Entity target){
        //Narrates necrotic melee attack
        if (spendMana(10)){
            System.out.println(getName() + " envelopes their hands with necrotic energy and swipes!");
            if (check(target.getArmorClass(),6)){
                int damage = target.getCurrHP()/2;
                System.out.println("And hits, draining the target's vitality!");
                target.takeDMG(damage);
                heal();
            }
            else{
                System.out.println("But they miss!");
            }
        }
    }
}