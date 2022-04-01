import java.util.Random;

public class Monster extends Object {
    public static Random r = new Random();
    String[] monsterNames = { "Goblin", "Troll", "Demon", "Vampire" };
    int[] healths = { 100, 1000, 10000, 100000 };
    public String name;
    public int health;

    public Monster() {
        name = monsterNames[r.nextInt(monsterNames.length)];
        health = healths[r.nextInt(healths.length)];
    }

    public int getDamage() {
        int low = 900;
        int high = 10000;
        int damage = r.nextInt(high - low) + low;
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return ("Name:   " + name + "\nHealth: " + health);
    }

}