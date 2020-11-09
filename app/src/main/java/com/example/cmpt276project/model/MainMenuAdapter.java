package com.example.cmpt276project.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;
import com.example.cmpt276project.ui.ChildListActivity;
import com.example.cmpt276project.ui.FlipCoinActivity;
import com.example.cmpt276project.ui.TimeoutTimerActivity;

// Class to set up the RecyclerView Menu for the Main Menu
// Each button on the RecyclerView leads to its respective activity
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {
    // String array that will contain menu name strings
    private String[] menuNames;

    public static class MainMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView menuName;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Set buttons to be clickable
            switch(getAdapterPosition()) {
                case 0: // Go to Coin Flip Activity
                    Intent flipCoinIntent = new Intent(v.getContext(), FlipCoinActivity.class);
                    v.getContext().startActivity(flipCoinIntent);
                    break;
                case 1: // Go to Timeout Activity
                    Intent timeoutTimerIntent = new Intent(v.getContext(), TimeoutTimerActivity.class);
                    v.getContext().startActivity(timeoutTimerIntent);
                    break;
                case 2: // Go to Child Configuration Activity
                    Intent configChildIntent = new Intent(v.getContext(), ChildListActivity.class);
                    v.getContext().startActivity(configChildIntent);
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set menu name strings and inflate layout
        menuNames = parent.getContext().getResources().getStringArray(R.array.MenuNames);
        View mainMenuLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new MainMenuViewHolder(mainMenuLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder holder, int position) {
        holder.menuName.setText(menuNames[position]);
    }

    @Override
    public int getItemCount() {
        return 3; // Set at 3 as there will only be 3 items in the Main Menu
    }
}

