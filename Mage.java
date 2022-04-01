
public class Mage extends Character {
    public int mana = 1000;
    private final static String CHAR_CLASS = "Mage";

    public Mage(String name, int maxHealth) {
        super(name, CHAR_CLASS, maxHealth);
    }

    public int getMana() {
        return mana;
    }

    public void useMana(int cost) {
        mana -= cost;
        if (mana < 0) {
            mana = 0;
        }
    }

    public void castFireball(Monster monster) {
        int low = 900;
        int high = 10000;
        int damage = r.nextInt(high - low) + low;

        if (this.isAlive() == true && this.getMana() >= 250) {
            System.out.println(this.getName() + " casts fireball at the " + monster.getName());
            this.useMana(250); // uses 250 mana
            int monsterDam = monster.getDamage();
            if (monster.getHealth() <= 0) {
                System.out.println("The monster is already dead!");
            } else if (damage > monster.getHealth()) {
                System.out.println(this.name + " killed the " + monster.getName());
                monster.setHealth(monster.getHealth() - damage);
            } else {
                this.setHealth(this.getHealth() - monsterDam);
                monster.setHealth(monster.getHealth() - damage);
                System.out.println(this.name + " does " + damage + " damage to the " + monster.getName()
                        + ", their health is now at " + monster.getHealth());
                System.out.println(monster.getName() + " does " + monsterDam + " damage to " + this.getName());
                if (this.getHealth() == 0) {
                    System.out.println(this.getName() + " has died.");
                    this.setAlive(false);
                }
            }
        } else if (!(mana >= 250)) {
            System.out.println(this.getName() + " does not have enough mana to cast fireball");
        } else if (this.alive != true) {
            System.out.println(this.getName() + " cannot cast fireball because they are dead.");
        }
    }

    @Override
    public String toString() {
        return ("\nName:   " + name + "\nClass:  " + characterClass + "\nHealth: " + currentHealth
                + "\nMana:   " + mana + "\n");
    }
}