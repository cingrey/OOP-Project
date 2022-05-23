/*COMP305 - Character Abstract Classes
Professor - A. Nuzen
Author: Team COBOL
This class implements a Mage character for the game.
*/
import java.util.ArrayList;
 
public class Mage extends Entity{
    /*
    Subclass variables:
    mana - (int)
    */
    private int mana;
    private final int MANA_MAX = 50;
    public Mage(String name){
        setName(name);//this.name = name;
        setMaxHP(62);//maxHP = 62;
        setCurrHP(getMaxHP());//currHP = maxHP
        setArmorClass(13);//armor_class = 13;
        ArrayList<String> powers = new ArrayList<String>(4);
        powers.add("[1] Chromatic Orb - Versatile elemental attack; costs 5 mana");
        powers.add("[2] Magic Missile - Low damage but guaranteed hit; costs 4 mana");
        powers.add("[3] Shatter - Thunderous burst; costs 6 mana");
        powers.add("[4] Necrotic Grasp - Life drain; costs 10 mana");
        setAbilities(powers);
        setStatus(true);
        mana = MANA_MAX;
    }

    //accessor method
    public int getMana(){
        return mana;
    }

    //mutator method
    public void setMana(int mana){
        if (mana<0){
            this.mana = 0;
        }
        else if (mana>MANA_MAX){
            this.mana = MANA_MAX;
        }
        else{
            this.mana = mana;
        }
    }
    
    //Overridden methods
    @Override
    public void attack(Entity target, int choice){
        /*
        Prompts user to choose from combat options/actions
        Narrates outcome of choice
        */
        switch(choice){
            case 1: ChromaticOrb(target);
                    break;
            case 2: MagicMissile(target);
                    break;
            case 3: Shatter(target);
                    break;
            case 4: NecroticGrasp(target);
                    break;
            default: heal(); //invalid choices handled in driver, but added for assurance
                    break;
        }
    }
    @Override
    public void heal(){
        //Mage heals back HP based on 6-sided die with a modifier of +1
        int newHP = generic_roll(1,6)+1;
        setCurrHP(getCurrHP()+newHP);
        setMana(mana+newHP);
        print_sleep(getName() + " channels free mana to recover " + newHP + " hit points and mana!");
    }
    @Override
    public String toString() {
        //also print mana amount
        String stats = super.toString();
        stats += "Mana: " + mana + "/" + MANA_MAX + "\n";
        return stats;
    }
    @Override
    public void display_actions(){
        //print mana remaining
        super.display_actions();
        System.out.println("Remaining Mana: "+getMana());
    }

    //new methods; implement spellcasting
    public boolean spendMana(int cost){
        /*
        checks if character has enough mana to cast spell
        if so, subtract mana according to listed costs
        */
        if (cost<=mana){
            mana -= cost;
            print_sleep(getName()+" spends "+cost+" mana!");
            return true;
        }
        else{
            print_sleep(getName() + " doesn't have enough mana to cast this spell!");
            return false;
        }
    }
    public void ChromaticOrb(Entity target){
        //Narrates Chromtic Orb casting with a cost of 5 mana
        if (spendMana(5)){
            print_sleep(getName() + " launches an orb of chaotic energy!");
            if (check(target.getArmorClass(),9)){
                int damage = generic_roll(6,8);
                print_sleep("And hits!");
                target.takeDMG(damage);
            }
            else{
                print_sleep("But it misses its target!");
            }
        }
    }
    public void MagicMissile(Entity target){
        /*
        Narrates Magic Missile casting with a cost of 4 mana
        unlike other spells, this always hits target
        */
        if (spendMana(4)){
            print_sleep(getName() + " shoots 5 magical homing bolts from their fingers!");
            int damage = (generic_roll(1,4)+1)*5;
            print_sleep("They dig into the target!");
            target.takeDMG(damage);
        }
    }
    public void Shatter(Entity target){
        /*
        Narrates Shatter casting with a cost of 6 mana
        instead of missing, this spell will do half damage 
        */
        if (spendMana(6)){
            print_sleep(getName() + " engulfs the enemy in an explosion!");
            int damage = generic_roll(5,8);
            if (!(check(target.getArmorClass(),6))){
                print_sleep("The enemy used cover to mitigate the damage!");
                damage /= 2;
            }
            target.takeDMG(damage);
        }
    }
    public void NecroticGrasp(Entity target){
        /*
        Narrates Necrotic Grasp attack with a cost of 10 mana
        calls heal() on "hit" to simulate life drain
        */
        if (spendMana(10)){
            print_sleep(getName() + " envelopes their hands with necrotic energy and reaches out!");
            if (check(target.getArmorClass(),9)){
                int damage = target.getCurrHP()/3;
                print_sleep("And touches, draining the target's vitality!");
                target.takeDMG(damage);
                heal();
            }
            else{
                print_sleep("But they miss!");
            }
        }
    }
}