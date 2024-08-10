package com.example.finalapp.menuitems;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalapp.authentication.Login;
import com.example.finalapp.model.Player;
import com.example.finalapp.R;
import com.example.finalapp.fragments.FragmentAdapter;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

// class that handles actions in Settings activity
public class Settings extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ArrayList<Player> playerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup all the UI elements for further interaction
        tabLayout = findViewById(R.id.tab1);
        viewPager2 = findViewById(R.id.viewPager2);

        // setup menu bar and menu options
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // request permissions to write in the feedback file
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        // get player array from the passed explicit intent
        playerArray = (ArrayList<Player>)getIntent().getSerializableExtra("playerArray");


        // create tabs for fragments, and display them
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Sound");
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("About us");
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Feedback");
        tabLayout.addTab(thirdTab);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);

        // navigation and selection overriding
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());}
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}});
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);}
            public void onPageSelected(int position){
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));}
            public void onPageScrollStateChanged(int state){
                super.onPageScrollStateChanged(state);}});
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
                Intent intent = new Intent(Settings.this, Game1.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 2)
            {
                Intent intent = new Intent(Settings.this, Game2.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }

            if (playerArray.size() == 3)
            {
                Intent intent = new Intent(Settings.this, Game3.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
            if (playerArray.size() == 4)
            {
                Intent intent = new Intent(Settings.this, Game4.class);
                intent.putExtra("playerArray", playerArray);
                startActivity(intent);
            }
        }

        else if(id == R.id.nav_dice)
        {
            Intent intent = new Intent(Settings.this, RollDice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_names)
        {
            Intent intent = new Intent(Settings.this, ChangeNames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_saved_games)
        {
            Intent intent = new Intent(Settings.this, SavedGames.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_leaderboard)
        {
            Intent intent = new Intent(Settings.this, Leaderboard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_more)
        {
            Intent intent = new Intent(Settings.this, Settings.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.player_change)
        {
            Intent intent = new Intent(Settings.this, PlayersChoice.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }

        else if (id == R.id.nav_randomcard)
        {
            Intent intent = new Intent(Settings.this, RandomCard.class);
            intent.putExtra("playerArray", playerArray);
            startActivity(intent);
        }


        return true;
    }
}