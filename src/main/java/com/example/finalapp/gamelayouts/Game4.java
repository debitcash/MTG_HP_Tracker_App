package com.example.finalapp.gamelayouts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalapp.authentication.Login;
import com.example.finalapp.menuitems.ChangeNames;
import com.example.finalapp.menuitems.Leaderboard;
import com.example.finalapp.menuitems.PlayersChoice;
import com.example.finalapp.menuitems.RandomCard;
import com.example.finalapp.model.Player;
import com.example.finalapp.R;
import com.example.finalapp.menuitems.RollDice;
import com.example.finalapp.menuitems.SavedGames;
import com.example.finalapp.menuitems.Settings;

import java.util.ArrayList;

// class that handles actions in Game4 activity, a layout for a game with four players
public class Game4 extends AppCompatActivity
{
    // array to hold all the player information
    ArrayList<Player> playerArray;

    TextView player1Name;
    TextView player1Hp;
    TextView player2Name;
    TextView player2Hp;
    TextView player3Name;
    TextView player3Hp;
    TextView player4Name;
    TextView player4Hp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        ImageView plus1 = findViewById(R.id.plus1);
        ImageView minus1 = findViewById(R.id.minus1);
        ImageView plus2 = findViewById(R.id.plus2);
        ImageView minus2 = findViewById(R.id.minus2);
        ImageView plus3 = findViewById(R.id.plus3);
        ImageView minus3 = findViewById(R.id.minus3);
        ImageView plus4 = findViewById(R.id.plus4);
        ImageView minus4 = findViewById(R.id.minus4);
        player1Name = findViewById(R.id.player1Name);
        player1Hp = findViewById(R.id.player1Hp);
        player2Name = findViewById(R.id.player2Name);
        player2Hp = findViewById(R.id.player2Hp);
        player3Name = findViewById(R.id.player3Name);
        player3Hp = findViewById(R.id.player3Hp);
        player4Name = findViewById(R.id.player4Name);
        player4Hp = findViewById(R.id.player4Hp);

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>) getIntent().getSerializableExtra("playerArray");

        // display the name and hp of players, get the first, second, third, forth array member, as far as its four players
        player1Name.setText(playerArray.get(0).getName());
        player1Hp.setText("" + playerArray.get(0).getHp());
        player2Name.setText(playerArray.get(1).getName());
        player2Hp.setText("" + playerArray.get(1).getHp());
        player3Name.setText(playerArray.get(2).getName());
        player3Hp.setText("" + playerArray.get(2).getHp());
        player4Name.setText(playerArray.get(3).getName());
        player4Hp.setText("" + playerArray.get(3).getHp());



        // increase the hp of the player on click of plus button and flash green color in background
        plus1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player1Hp.getText().toString()) + 1;
                player1Hp.setText("" + changedHp);
                player1Hp.setBackgroundColor(Color.parseColor("#77DD77"));

                // start new worker thread that is going to retain the green background of hp
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // in main UI thread reset background to nothing
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player1Hp.setBackground(null);
                            }
                        });
                    }
                });
                // start worker thread
                thread.start();
            }
        });

        // decrease the hp of the player on click of minus button and flash red color in background
        minus1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player1Hp.getText().toString()) - 1;
                player1Hp.setText("" + changedHp);
                player1Hp.setBackgroundColor(Color.parseColor("#FF6961"));

                // start new worker thread that is going to retain the red background of hp
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // in main UI thread reset background to nothing
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player1Hp.setBackground(null);
                            }
                        });
                    }
                });
                // start worker thread
                thread.start();
            }
        });

        plus2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player2Hp.getText().toString()) + 1;
                player2Hp.setText("" + changedHp);
                player2Hp.setBackgroundColor(Color.parseColor("#77DD77"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player2Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        minus2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player2Hp.getText().toString()) - 1;
                player2Hp.setText("" + changedHp);
                player2Hp.setBackgroundColor(Color.parseColor("#FF6961"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player2Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        plus3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player3Hp.getText().toString()) + 1;
                player3Hp.setText("" + changedHp);
                player3Hp.setBackgroundColor(Color.parseColor("#77DD77"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player3Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        minus3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player3Hp.getText().toString()) - 1;
                player3Hp.setText("" + changedHp);
                player3Hp.setBackgroundColor(Color.parseColor("#FF6961"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player3Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        plus4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player4Hp.getText().toString()) + 1;
                player4Hp.setText("" + changedHp);
                player4Hp.setBackgroundColor(Color.parseColor("#77DD77"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player4Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        minus4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int changedHp = Integer.parseInt(player4Hp.getText().toString()) - 1;
                player4Hp.setText("" + changedHp);
                player4Hp.setBackgroundColor(Color.parseColor("#FF6961"));

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                player4Hp.setBackground(null);
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }

    // populate menu with options
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // if a certain menu member is chosen- continue to the activity it represents
    // and include along an updated array
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        // go to game view, based on how many players is in the player array
        if (id == R.id.nav_home)
        {
            if (playerArray.size() == 1)
            {
                Intent intent = new Intent(Game4.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(Game4.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(Game4.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(Game4.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(Game4.this, RollDice.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(Game4.this, ChangeNames.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(Game4.this, SavedGames.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(Game4.this, Leaderboard.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }
        if (id == R.id.player_change)
        {
            Intent intent = new Intent(Game4.this, PlayersChoice.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(Game4.this, Login.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(Game4.this, Settings.class);
            repopulateArray();
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(Game4.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        return true;
    }

    // reset the hp in player array
    public void repopulateArray(){
        playerArray.get(0).setHp(Integer.parseInt(player1Hp.getText().toString()));
        playerArray.get(1).setHp(Integer.parseInt(player2Hp.getText().toString()));
        playerArray.get(2).setHp(Integer.parseInt(player3Hp.getText().toString()));
        playerArray.get(3).setHp(Integer.parseInt(player4Hp.getText().toString()));
    }
}