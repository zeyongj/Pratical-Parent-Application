package com.example.cmpt276project.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276project.model.FlipHistory;
import com.example.cmpt276project.model.FlipHistoryManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;

public class FlipCoinHistoryActivity extends AppCompatActivity {

    private FlipHistoryManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        manager = FlipHistoryManager.getInstance();

        populateListView();

    }

    public void populateListView(){
        ArrayAdapter<FlipHistory> adapter = new myListAdapter();
        ListView list = (ListView) findViewById(R.id.history_ListView);
        list.setAdapter(adapter);
    }

    private class myListAdapter extends ArrayAdapter<FlipHistory> {
        public myListAdapter(){
            super(FlipCoinHistoryActivity.this, R.layout.history_item, manager.getMyHistory());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.history_item, parent, false);
            }

            //Find the History wo work with
            FlipHistory currentHistory = manager.getMyHistory().get(position);

            //Fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_Win);
            imageView.setImageResource(currentHistory.getIconID());

            // ChildName:
            TextView ChildName = (TextView) itemView.findViewById(R.id.item_ChildrenName);
            ChildName.setText(currentHistory.getChildName());

            // CurrentDate:
            TextView CurrentDate = (TextView) itemView.findViewById(R.id.item_CurrentDate);
            CurrentDate.setText(currentHistory.getCurrentDateAndTime());

            // Make:
            TextView FlipResult = (TextView) itemView.findViewById(R.id.item_ResultOfHeadOrTail);
            FlipResult.setText(currentHistory.getFlipResult());

            return itemView;
        }
    }
}