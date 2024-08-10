package com.example.finalapp.model;

import java.io.Serializable;

// class that holds player's information
public class Player implements Serializable {
    String name;
    int hp;

    public Player(String name, int hp){
        this.name= name;
        this.hp = hp;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }
}
