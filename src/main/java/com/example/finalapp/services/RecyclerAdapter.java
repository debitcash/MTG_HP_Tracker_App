package com.example.finalapp.services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.R;
import com.example.finalapp.gamelayouts.Game1;
import com.example.finalapp.gamelayouts.Game2;
import com.example.finalapp.gamelayouts.Game3;
import com.example.finalapp.gamelayouts.Game4;
import com.example.finalapp.model.GameSnapshot;
import com.example.finalapp.model.Player;

import java.util.ArrayList;

// recycler adapter for recycler view in SavedGames activity
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.View_Holder> {
    Context context;
    ArrayList<GameSnapshot> snapshotList;

    public RecyclerAdapter(Context context, ArrayList<GameSnapshot> arrayList)
    {
        this.context=context;
        this.snapshotList=arrayList;
    }
    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.saved_game_card,parent,false);
        View_Holder viewHolder = new View_Holder(view);
        return viewHolder;
    }

    // populates recycler view with the data
    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position)
    {
        // get player array from a provided game snapshot
        ArrayList<Player> playerArray = snapshotList.get(position).getPlayerArray();
        String gameInfo = "";

        // create a string that includes all the players and their hp points
        for (Player player : playerArray)
        {
            gameInfo += player.getName() + ", "+ player.getHp() + "\n";
        }
        holder.gameInfo.setText(gameInfo);

        // redirect to a proper game layout after user chooses certain game
        holder.loadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if (playerArray.size() == 1)
                {
                    Intent intent = new Intent(context, Game1.class);
                    intent.putExtra("playerArray", playerArray);
                    context.startActivity(intent);
                }

                if (playerArray.size() == 2)
                {
                    Intent intent = new Intent(context, Game2.class);
                    intent.putExtra("playerArray", playerArray);
                    context.startActivity(intent);
                }

                if (playerArray.size() == 3)
                {
                    Intent intent = new Intent(context, Game3.class);
                    intent.putExtra("playerArray", playerArray);
                    context.startActivity(intent);
                }

                if (playerArray.size() == 4)
                {
                    Intent intent = new Intent(context, Game4.class);
                    intent.putExtra("playerArray", playerArray);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return snapshotList.size();
    }

    // provides information on the UI components of a view holder
    public class View_Holder extends RecyclerView.ViewHolder{

        TextView gameInfo;
        Button loadButton;
        public View_Holder(@NonNull View itemView)
        {
            super(itemView);
            gameInfo = itemView.findViewById(R.id.gameInfo);
            loadButton = itemView.findViewById(R.id.loadButton);
        }
    }


}
