package com.example.team24dungeoncrawler.model;
import java.util.ArrayList;
import java.util.List;

public class Player implements Observable {
    private String name;
    private int health;
    private String direction;
    private int speed;
    private int score;
    private int row;
    private int col;

    private double damageMultiplier;
    private static Player instance;
    private List<Attempt> attemptHistory;
    private List<EnemyObserver> enemyObserverList;


    private Player(String name, String difficulty) {
        this.name = name;
        this.direction = "";
        this.speed = 0;
        this.score = 0;
        this.attemptHistory = new ArrayList<>();
        this.score = 0;
        this.row = 3;
        this.col = 1;
        enemyObserverList = new ArrayList<EnemyObserver>();

        // Set health and damageMultiplier based on the selected difficulty
        switch (difficulty) {
        case "Easy":
            this.health = 150;
            this.damageMultiplier = 0.8;
            break;
        case "Medium":
            this.health = 100;
            this.damageMultiplier = 1.0;
            break;
        case "Hard":
            this.health = 50;
            this.damageMultiplier = 1.2;
            break;
        default:
            this.health = 100; // Default values for invalid difficulty
            this.damageMultiplier = 1.0;
            break;
        }
    }

    public static Player getInstance(String name, String difficulty) {
        if (instance == null) {
            instance = new Player(name, difficulty);
        }
        return instance;
    }


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
        notifyObservers();
    }

    public void setRow(int row) {
        this.row = row;
        notifyObservers();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;


    }
    public void reset(String name, String difficulty) {
        this.name = name;
        this.direction = "";
        this.speed = 0;
        this.score = 0;
        this.row = 3;
        this.col = 1;

        // Reset health and damageMultiplier based on the selected difficulty
        switch (difficulty) {
            case "Easy":
                this.health = 150;
                this.damageMultiplier = 0.8;
                break;
            case "Medium":
                this.health = 100;
                this.damageMultiplier = 1.0;
                break;
            case "Hard":
                this.health = 50;
                this.damageMultiplier = 1.2;
                break;
            default:
                this.health = 100; // Default values for invalid difficulty
                this.damageMultiplier = 1.0;
                break;
        }
    }
    public void decreaseHealth() {
        // Decrease health based on the damageMultiplier
        this.health -= (int) (10 * damageMultiplier); // You can adjust the damage as needed

        // Ensure health doesn't go below 0
        if (health < 0) {
            health = 0;
        }
    }

    public void decreaseHealth(int damage) {
        this.health -= damage;

        // Ensure health doesn't go below 0
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public void addObserver(EnemyObserver observer) {
        enemyObserverList.add(observer);
    }

    @Override
    public void removeObserver(EnemyObserver observer) {
        enemyObserverList.remove(observer);
    }

    public void removeObservers() {enemyObserverList = new ArrayList<EnemyObserver>(); }

    @Override
    public void notifyObservers() {
        for (EnemyObserver enemy: enemyObserverList) {
            enemy.update(this);
        }
    }
}

