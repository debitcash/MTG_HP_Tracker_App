package com.example.finalapp.menuitems;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

// class that handles actions in Change_Names activity
public class ChangeNames extends AppCompatActivity
{

    // array to hold all the player information
    ArrayList<Player> playerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_names);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        EditText name1 = findViewById(R.id.editTextText1);
        EditText name2 = findViewById(R.id.editTextText2);
        EditText name3 = findViewById(R.id.editTextText3);
        EditText name4 = findViewById(R.id.editTextText4);

        TextView label1 = findViewById(R.id.textView1);
        TextView label2 = findViewById(R.id.player1Hp);
        TextView label3 = findViewById(R.id.player2Name);
        TextView label4 = findViewById(R.id.player2Hp);

        Button changeNamesButton = findViewById(R.id.changeNamesButton);

        // create array that holds input fields for new names
        ArrayList<EditText> inputFieldsArray = new ArrayList<>();
        inputFieldsArray.add(name1);
        inputFieldsArray.add(name2);
        inputFieldsArray.add(name3);
        inputFieldsArray.add(name4);

        // create array that holds name labels with current names
        ArrayList<TextView> labelsArray = new ArrayList<>();
        labelsArray.add(label1);
        labelsArray.add(label2);
        labelsArray.add(label3);
        labelsArray.add(label4);

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");

        // iterate through player array and populate name labels with current player names
        // also unhide the number of labels to show all the passed player names
        for (int i = 0; i < playerArray.size(); i++)
        {
            inputFieldsArray.get(i).setVisibility(View.VISIBLE);
            labelsArray.get(i).setVisibility(View.VISIBLE);
            labelsArray.get(i).setText(playerArray.get(i).getName());
        }

        // record the name change to player array, if any update was provided
        changeNamesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                for (int i = 0; i < playerArray.size();i++)
                {
                    if (!inputFieldsArray.get(i).getText().toString().isEmpty())
                        playerArray.get(i).setName(inputFieldsArray.get(i).getText().toString());
                }
                makeToast("Updated");
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
                Intent intent = new Intent(ChangeNames.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(ChangeNames.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(ChangeNames.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(ChangeNames.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(ChangeNames.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(ChangeNames.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(ChangeNames.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(ChangeNames.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }
        else if (id == R.id.logOut)
        {
            Intent intent = new Intent(ChangeNames.this, Login.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(ChangeNames.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(ChangeNames.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(ChangeNames.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }
        return true;
    }

    // show a string in an enlarged toast message
    public void makeToast(String text)
    {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(3f), 0, text.length(), 0);
        Toast.makeText(ChangeNames.this, biggerText, Toast.LENGTH_SHORT).show();
    }
}