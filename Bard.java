
public class Bard extends Character {
    public int mana = 1000;
    private final static String CHAR_CLASS = "Bard";

    public Bard(String name, int maxHealth) {
        super(name, CHAR_CLASS, maxHealth);
    }

    public int getMana() {
        return mana;
    }

    public void useMana(int cost) {
        mana -= cost;
    }

    public void healCharacter(Character character) {
        int low = 500;
        int high = 2500;
        int heal = r.nextInt(high - low) + low;
        if (character.isAlive() == true) {
            this.useMana(250);
            character.setHealth(character.getHealth() + heal);
            System.out.println(this.getName() + " heals " + character.getName() + " for " + heal + " health");
        } else {
            System.out.println(this.getName() + " tries to heal " + character.getName() + " but they are dead");
        }
    }

    @Override
    public String toString() {
        return ("\nName:   " + name + "\nClass:  " + characterClass + "\nHealth: " + currentHealth
                + "\nMana:   " + mana + "\n");
    }

}