package com.example.finalapp.menuitems;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.finalapp.model.Player;
import com.example.finalapp.R;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// class that handles actions in RollDice activity, main purpose is to shuffles images and provide a winner in dice game
public class RollDice extends AppCompatActivity {
    Random rand = new Random();
    ImageView p1;
    ImageView p2;
    ImageView p3;
    ImageView p4;

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;

    ArrayList<TextView> textViewArray = new ArrayList<>();
    ArrayList<ImageView> imageViewArray = new ArrayList<>();
    ArrayList<Player> playerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roll_dice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        Button rollDice = findViewById(R.id.rollButton);

        p1 = findViewById(R.id.image1);
        p2 = findViewById(R.id.image2);
        p3 = findViewById(R.id.image3);
        p4 = findViewById(R.id.image4);

        t1 = findViewById(R.id.player1Hp);
        t2 = findViewById(R.id.player2Name);
        t3 = findViewById(R.id.player2Hp);
        t4 = findViewById(R.id.textView5);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");

        // populate textview arrays and imageview arrays with UI components of the layout
        textViewArray.add(t1);
        textViewArray.add(t2);
        textViewArray.add(t3);
        textViewArray.add(t4);
        imageViewArray.add(image1);
        imageViewArray.add(image2);
        imageViewArray.add(image3);
        imageViewArray.add(image4);

        // make the text and image visible for the number of players provided in intent
        // set the name labels with provided names
        for (int i = 0; i < playerArray.size(); i++)
        {
            textViewArray.get(i).setText(playerArray.get(i).getName());
            textViewArray.get(i).setVisibility(View.VISIBLE);
            imageViewArray.get(i).setVisibility(View.VISIBLE);
        }

        // change the images every 100 milliseconds for 10 times in a worker tread
        rollDice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Runnable runnable = new Runnable()
                {
                    public void run(){
                        for (int i = 0; i <10; i++){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            shuffle(p1);
                            shuffle(p2);
                            shuffle(p3);
                            shuffle(p4);
                        }
                    }
                };
                ExecutorService executor = Executors.newFixedThreadPool(1);
                executor.submit(runnable);
                executor.shutdown();
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
        if (id == R.id.nav_home){
            if (playerArray.size() == 1)
            {
                Intent intent = new Intent(RollDice.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(RollDice.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(RollDice.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(RollDice.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(RollDice.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(RollDice.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(RollDice.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(RollDice.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }
        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(RollDice.this, Login.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(RollDice.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(RollDice.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(RollDice.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        return true;
    }

    // randomly chooses a picture of a dice and updates it in main thread
    public void shuffle(ImageView v)
    {
        int number = rand.nextInt(6) + 1;
        int imageResId = getResources().getIdentifier("dice" + number, "drawable", getPackageName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    v.setImageResource(imageResId);
            }
        });
    }
}