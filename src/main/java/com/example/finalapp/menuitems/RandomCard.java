package com.example.finalapp.menuitems;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.authentication.Login;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;
import com.example.finalapp.model.Player;

import java.util.ArrayList;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

public class RandomCard extends AppCompatActivity {

    ImageView cardHolder;
    ArrayList<Player> playerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_random_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button populateButton = findViewById(R.id.populateButton);
        cardHolder = findViewById(R.id.cardHolder);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateCardHolder();;

        populateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateCardHolder();
            }
        });

    }

    // populate menu with options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent intent = new Intent(RandomCard.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(RandomCard.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(RandomCard.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(RandomCard.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(RandomCard.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(RandomCard.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(RandomCard.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(RandomCard.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(RandomCard.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(RandomCard.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(RandomCard.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(RandomCard.this, Login.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        return true;
    }

    public void populateCardHolder()
    {
        // use API to get the image of randomly chosen
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Card> cardsWithImageList = null;

                try {
                    cardsWithImageList = CardAPI.getAllCards(List.of("page=1", "pageSize=1","random=true","contains=imageUrl"));
                }
                catch (Exception e)
                {
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          cardHolder.setImageResource(R.drawable.error_image);}
                                      });
                    return;
                }

                String imageUrl = cardsWithImageList.get(0).getImageUrl().replace("http","https");

                // use Glide library to display image from provided url
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(RandomCard.this)
                                .load(imageUrl)
                                .into(cardHolder);
                    }
                });
            }
        }).start();
    }
}