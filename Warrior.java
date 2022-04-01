public class Warrior extends Character {

    public int fury = 1000;
    private final static String CHAR_CLASS = "Warrior";

    public Warrior(String name, int maxHealth) {
        super(name, CHAR_CLASS, maxHealth);
    }

    public void useFury(int cost) {
        fury -= cost;
        if (fury < 0) {
            fury = 0;
        }
    }

    public int getFury() {
        return fury;
    }

    public void whirlwind(Monster monster) {
        int low = 900;
        int high = 10000;
        int damage = r.nextInt(high - low) + low;

        if (this.isAlive() == true && this.getFury() >= 250) {
            System.out.println(this.getName() + " channels whirlwind and attacks the " + monster.getName());
            this.useFury(250); // uses 250 fury
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
        } else if (!(fury >= 250)) {
            System.out.println(this.getName() + " does not have enough fury to channel whirlwind");
        } else if (this.alive != true) {
            System.out.println(this.getName() + " cannot channel fury because they are dead.");
        }
    }

    @Override
    public String toString() {
        return ("\nName:   " + name + "\nClass:  " + characterClass + "\nHealth: " + currentHealth
                + "\nFury:   " + fury + "\n");
    }
}