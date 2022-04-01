import java.util.ArrayList;
import java.util.Random;

public class Character extends Object {

    public static Random r = new Random();
    public String name;
    public String characterClass;
    public int maxHealth;
    public int currentHealth;
    public boolean alive = true;
    public static ArrayList<Character> characters = new ArrayList<Character>();

    public Character(String name, String characterClass, int maxHealth) {
        this.name = name;
        this.characterClass = characterClass;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        characters.add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public void setHealth(int health) {
        this.currentHealth = health;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        } else if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

    }

    public String getName() {
        return name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public int getHealth() {
        return currentHealth;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public String toString() {
        return ("\nName:   " + name + "\nClass:  " + characterClass + "\nHealth: " + currentHealth + "\n");
    }
}