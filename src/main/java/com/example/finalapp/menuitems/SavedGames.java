package com.example.finalapp.menuitems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.authentication.Login;
import com.example.finalapp.model.GameSnapshot;
import com.example.finalapp.model.Player;
import com.example.finalapp.R;
import com.example.finalapp.services.RecyclerAdapter;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

// class that handles actions in SavedGames activity, main purpose is to save games into internal
// storage and display previously saved game snapshots with the players and their hp
public class SavedGames extends AppCompatActivity {
    ArrayList<Player> playerArray;
    Button saveButton;
    String gameSnapshot = "";
    ArrayList<GameSnapshot> snapshotList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_games);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        saveButton = findViewById(R.id.saveButton);

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");

        // create list that is going to contain all of the previously saved games
        snapshotList = new ArrayList<>();

        // populates snapshotList array with the lists of players
        readFileFromInternalStorageLineByLine(SavedGames.this);

        // create string that represents a game snapshot for current game
        for (Player player: playerArray)
        {
            gameSnapshot += player.getName() + "," + player.getHp() + "\n";
        }
        gameSnapshot += "\n";

        // provide the current game snapshot to the internal storage file
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFileOnInternalStorage(SavedGames.this, "SavedGames.txt", gameSnapshot);

                makeToast("Game saved");
                Intent intent = new Intent(SavedGames.this, SavedGames.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        });

        // displays recycler view with game snapshots of previously saved games; newer games on the top of the list
        RecyclerView recyclerView = findViewById(R.id.recView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        Collections.reverse(snapshotList);
        RecyclerAdapter adapter = new RecyclerAdapter(this, snapshotList);
        recyclerView.setAdapter(adapter);
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
                Intent intent = new Intent(SavedGames.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(SavedGames.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(SavedGames.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(SavedGames.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(SavedGames.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(SavedGames.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(SavedGames.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(SavedGames.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(SavedGames.this, Login.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(SavedGames.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(SavedGames.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(SavedGames.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        return true;
    }


    // records provided string in SavedGames.txt into SavedGames folder
    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File dir = new File(mcoContext.getFilesDir(), "SavedGames");
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // populates snapshots array with player lists based on the player information from the SavedGames.txt
    public void readFileFromInternalStorageLineByLine(Context mcoContext)
    {
        File dir = new File(mcoContext.getFilesDir(), "SavedGames");
        File file = new File(dir, "SavedGames.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            GameSnapshot newSnapshot = new GameSnapshot();

            while (line != null)
            {
                if (line.equals(""))
                {
                    snapshotList.add(newSnapshot);
                    newSnapshot = new GameSnapshot();
                }
                else {
                    newSnapshot.getPlayerArray().add(new Player(line.split(",")[0], Integer.parseInt(line.split(",")[1])));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // make an enlarged toast with a provided string
    public void makeToast(String text)
    {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(3f), 0, text.length(), 0);
        Toast.makeText(SavedGames.this, biggerText, Toast.LENGTH_SHORT).show();
    }
}