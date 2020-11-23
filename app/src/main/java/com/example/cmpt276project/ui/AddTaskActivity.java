package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.Task;
import com.example.cmpt276project.model.TaskManager;

public class AddTaskActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private Children children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.addtasktoolbar);
        setSupportActionBar(toolbar);

        setBackButton();

        children = Children.getInstance();
        taskManager = TaskManager.getInstance();
        loadTaskManager(taskManager);
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
        EditText taskName = findViewById(R.id.text_add_task_name);
        EditText taskDesc = findViewById(R.id.text_add_task_description);

        if (!checkTaskNameEmpty(taskName)) {
            taskManager.addTask(new Task(taskName.getText().toString(), taskDesc.getText().toString(), children));
            taskManager.saveTaskManager(this);
            Intent intent = new Intent(this, WhoseTurnActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            String success = getString(R.string.AddTaskSuccess);
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
        } else {
            String attention = getString(R.string.TaskNameAttention);
            Toast.makeText(this, attention, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkTaskNameEmpty(EditText taskName) {
        return taskName.getText().toString().equals("");
    }

    public void loadTaskManager(TaskManager taskManager) {
        taskManager.loadTaskManager(this);
        if (taskManager.checkTaskManagerEmpty()) {
            taskManager.reinitializeTaskManager();
        }
        taskManager.updateTasks(children);
    }
}