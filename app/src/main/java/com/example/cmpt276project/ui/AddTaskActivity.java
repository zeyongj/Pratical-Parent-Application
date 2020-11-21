package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cmpt276project.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.addtasktoolbar);
        setSupportActionBar(toolbar);

        setBackButton();
    }

    // Create the Save option on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_tasks_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_task_button) {
            saveTaskAdded();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBackButton() {
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void saveTaskAdded() {
        Intent intent = new Intent(this, WhoseTurnActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}