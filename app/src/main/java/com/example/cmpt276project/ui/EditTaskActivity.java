package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.Task;
import com.example.cmpt276project.model.TaskManager;
import com.google.gson.Gson;

import java.util.Objects;

// Activity to allow the user to edit the name and description of a task in WhoseTurnActivity
public class EditTaskActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private Children children;
    private int position;
    private EditText taskName;
    private EditText taskDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        position = Objects.requireNonNull(bundle).getInt("position");
        setContentView(R.layout.activity_edit_task);
        Toolbar toolbarEdit = findViewById(R.id.edittasktoolbar);
        setSupportActionBar(toolbarEdit);

        setBackButton();

        children = Children.getInstance();
        taskManager = TaskManager.getInstance();
        loadTaskManager(taskManager);

        setTaskText();
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
            saveTaskEdited(position);
        }

//        String text = "onOptionsItemSelected Current position is " + position;
//        Toast.makeText(this, text , Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public void setTaskText() {
        taskName = findViewById(R.id.text_edit_task_name);
        taskDesc = findViewById(R.id.text_edit_task_description);

        taskName.append(taskManager.getTask(position).getTaskName());
        taskDesc.append(taskManager.getTask(position).getTaskDescription());
    }

    private void setBackButton() {
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void saveTaskEdited(int position) {
//        String text = "saveTaskEdited Current position is " + position;
//        Toast.makeText(this, text , Toast.LENGTH_SHORT).show();

        if (!checkTaskNameEmpty(taskName)) {
            Task editedTask = new Task(taskName.getText().toString(), taskDesc.getText().toString(), children, taskManager.getTask(position).getChildIndex());
            taskManager.editTask(position, editedTask);
            taskManager.saveTaskManager(this);
            Intent intent = new Intent(this, WhoseTurnActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            taskManager.saveTaskManager(this);
            String success = getString(R.string.EditTaskSuccess);
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
