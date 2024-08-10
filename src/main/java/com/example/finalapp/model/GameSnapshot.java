package com.example.finalapp.model;

import java.util.ArrayList;

// class that defines GameSnapshot- a holder that contains a snapshot of a game with its players
// created for the purpose of easier game saving
public class GameSnapshot {
    ArrayList<Player> playerArray;

    public GameSnapshot()
    {
        playerArray = new ArrayList<>();
    }

    public ArrayList<Player> getPlayerArray()
    {
        return playerArray;
    }
}
