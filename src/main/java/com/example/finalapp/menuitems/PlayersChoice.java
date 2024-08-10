package com.example.finalapp.menuitems;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalapp.model.Player;
import com.example.finalapp.R;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;

import java.util.ArrayList;

// class that handles actions in PlayersChoice activity, a layout that defines how many players will be in current game
public class PlayersChoice extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_players_choice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        EditText providedNumber = findViewById(R.id.playersNumberInput);
        Button continueButton = findViewById(R.id.continueButton);

        // create playyer array
        ArrayList<Player> playerArray = new ArrayList<>();

        // populates player array and redirects to proper Game activity
        continueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int parsedNumber = 0;
                try{
                    String inputString = providedNumber.getText().toString();
                    parsedNumber = Integer.parseInt(inputString);
                }
                catch (Exception e)
                {
                    makeToast("Invalid value, provide 1...4");
                }

                if (parsedNumber > 4 || parsedNumber < 1)
                {
                    makeToast("Invalid value, provide 1...4");
                    return;
                }

                // populate player array with standard values based on number of player provided
                for (int i = 1; i <= parsedNumber; i++)
                {
                    playerArray.add(new Player("Player" + i, 50));
                }

                if (parsedNumber == 1)
                {
                    Intent intent = new Intent(PlayersChoice.this, Game1.class);
                    intent.putExtra("playerArray", playerArray);
                    startActivity(intent);
                }
                else if(parsedNumber == 2)
                {
                    Intent intent = new Intent(PlayersChoice.this, Game2.class);
                    intent.putExtra("playerArray", playerArray);
                    startActivity(intent);
                }
                else if(parsedNumber == 3)
                {
                    Intent intent = new Intent(PlayersChoice.this, Game3.class);
                    intent.putExtra("playerArray", playerArray);
                    startActivity(intent);
                }
                else if(parsedNumber == 4)
                {
                    Intent intent = new Intent(PlayersChoice.this, Game4.class);
                    intent.putExtra("playerArray", playerArray);
                    startActivity(intent);
                }
            }
        });
    }

    // make enlarged toast message with provided string
    public void makeToast(String text)
    {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(3f), 0, text.length(), 0);
        Toast.makeText(PlayersChoice.this, biggerText, Toast.LENGTH_SHORT).show();
    }
}