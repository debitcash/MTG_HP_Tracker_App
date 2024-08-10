package com.example.finalapp.menuitems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// class that handles actions in Leaderboard activity, a layout for displaying the players with most scores
public class Leaderboard extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<String> sortedLeaderboard = new ArrayList<>();
    TreeMap<Integer, List<String>> map = new TreeMap<>();
    ArrayList<Player> playerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");

        // see methods description
        readFileFromInternalStorageLineByLine(Leaderboard.this);

        // populates map with names that are relatable to each score
        // as far as it is the treemap all the keys are going to be sorted
        for (int i = 0; i < scores.size(); i++)
        {
            populateMap(scores.get(i),names.get(i));
        }

        // get string that shows the score and all the players that currently have it
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            String names = "";
            for (String name: entry.getValue())
            {
                names += name + " ";
            }
            sortedLeaderboard.add(entry.getKey() + " " + names);
        }

        // reverse to show in descending order
        Collections.reverse(sortedLeaderboard);

        // display results in listview
        ListView listView =findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_member, sortedLeaderboard);
        listView.setAdapter(arrayAdapter);
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
                Intent intent = new Intent(Leaderboard.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(Leaderboard.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(Leaderboard.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(Leaderboard.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(Leaderboard.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(Leaderboard.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }
        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(Leaderboard.this, Login.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(Leaderboard.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(Leaderboard.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(Leaderboard.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(Leaderboard.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(Leaderboard.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        return true;
    }

    // populate arrays names and scores from internal storage SavedGames.txt, located in SavedGames folder
    // by splitting each line with comma and putting name and hp value in arrays mentioned
    public void readFileFromInternalStorageLineByLine(Context mcoContext) {
        File dir = new File(mcoContext.getFilesDir(), "SavedGames");
        File file = new File(dir, "SavedGames.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null)
            {
                if (!line.isEmpty())
                {
                    // if the record already contained in names, then add the score to the same index in score array
                    if (names.contains(line.split(",")[0]))
                    {
                        int position = names.indexOf(line.split(",")[0]);
                        scores.set(position, (scores.get(position) + Integer.parseInt(line.split(",")[1])));
                    }
                    // if not - create new records in names and scores, that will have same indexes
                    else
                    {
                        names.add(line.split(",")[0]);
                        scores.add(Integer.parseInt(line.split(",")[1]));
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // populates map with the following template -> score:[array of names with that score]
    public void populateMap(Integer key, String name)
    {
        List<String> values = map.get(key);
        if (values == null)
        {
            values = new ArrayList<>();
            map.put(key, values);
        }
        values.add(name);
    }
}