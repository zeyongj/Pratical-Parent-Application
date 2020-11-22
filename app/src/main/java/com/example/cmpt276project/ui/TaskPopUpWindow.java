package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;
import com.example.cmpt276project.model.TaskManager;
import com.example.cmpt276project.model.WhoseTurnAdapter;

public class TaskPopUpWindow extends AppCompatDialogFragment {
    // Initialize variables
    private TaskManager taskManager;
    private int position;
    private WhoseTurnAdapter whoseTurnAdapter;

    // Constructor
    public TaskPopUpWindow(TaskManager taskManager, int position, WhoseTurnAdapter whoseTurnAdapter) {
        this.taskManager = taskManager;
        this.position = position;
        this.whoseTurnAdapter = whoseTurnAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window, null);

        setValues(v);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

    public void setValues(View v) {
        TextView taskName = v.findViewById(R.id.pop_tv_task_name);
        TextView childName = v.findViewById(R.id.pop_tv_child_name);
        TextView taskDesc = v.findViewById(R.id.pop_tv_task_desc_title);
        ImageView childImage = v.findViewById(R.id.pop_tv_child_image);

        taskName.setText(taskManager.getTask(position).getTaskName());
        childName.setText(taskManager.getTask(position).getChild());
        taskDesc.setText(taskManager.getTask(position).getTaskDescription());
        if (taskDesc.getText().equals("")) {
            taskDesc.setText(R.string.NoDesc);
        }
    }
}
