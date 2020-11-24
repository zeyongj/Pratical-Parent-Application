package com.example.cmpt276project.model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;
import com.example.cmpt276project.ui.AddTaskActivity;
import com.example.cmpt276project.ui.EditTaskActivity;
import com.example.cmpt276project.ui.TaskPopUpWindow;
import com.example.cmpt276project.ui.WhoseTurnActivity;

import org.w3c.dom.Text;

// RecyclerView Adapter for the Whose Turn Activity
public class WhoseTurnAdapter extends RecyclerView.Adapter<WhoseTurnAdapter.WhoseTurnViewHolder>{
    // Set variables
    private TaskManager taskManager;
    private WhoseTurnAdapter.OnRecyclerViewClickListener recyclerViewClickListener;
    private Context context;

    // Set interface for RecyclerView items
    public interface OnRecyclerViewClickListener {
        void deleteTask(int position);
        void editTask(int position);
        void showTask(int position);
    }

    // Method to create OnClickListener for RecyclerView items
    public void setRecyclerViewClickListener(WhoseTurnAdapter.OnRecyclerViewClickListener listener) {
        recyclerViewClickListener = listener;
    }

    public static class WhoseTurnViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView nextChildName;
        public Button deleteButton;
        public Button editButton;

        public WhoseTurnViewHolder(@NonNull View itemView, final WhoseTurnAdapter.OnRecyclerViewClickListener listener) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskname);
            deleteButton = itemView.findViewById(R.id.taskdeletebutton);
            editButton = itemView.findViewById(R.id.taskeditbutton);
            nextChildName = itemView.findViewById(R.id.nextchildname);

            // Set OnClickListener for the button specified on the Layout
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the dynamically updated position of button
                    int position = getAdapterPosition();
                    // Prevent clicks when the RecyclerView is in the animation of removing an item
                    if (position != RecyclerView.NO_POSITION) {
                        listener.deleteTask(position);
                    }
                }
            });

            // Set OnClickListener for the button specified on the Layout
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the dynamically updated position of button
                    int position = getAdapterPosition();
                    // Prevent clicks when the RecyclerView is in the animation of removing an item
                    if (position != RecyclerView.NO_POSITION) {
                        listener.editTask(position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.showTask(position);
                    }
                }
            });
        }


    }

    // Constructor
    public WhoseTurnAdapter(TaskManager taskManager, Context context) {
        this.taskManager = taskManager;
        this.context = context;
    }

    // Inflate the specified layout for use
    @NonNull
    @Override
    public WhoseTurnAdapter.WhoseTurnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View childrenLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new WhoseTurnAdapter.WhoseTurnViewHolder(childrenLayout, recyclerViewClickListener);
    }



    // Bind the child names to their relevant positions on the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WhoseTurnAdapter.WhoseTurnViewHolder holder, final int position) {
        String currentTask = context.getString(R.string.TaskNameIs, taskManager.getTask(position).getTaskName());
        String childName = taskManager.getTask(position).getChild();
        if (childName.equals("")) {
            childName = context.getString(R.string.NoChild);
        }
        String nextChild = context.getString(R.string.ChildNameIs, childName);
        holder.taskName.setText(currentTask);
        holder.taskName.setTextColor(Color.BLUE);
        holder.nextChildName.setText(nextChild);
        holder.nextChildName.setTextColor(Color.BLACK);
    }

    // Get the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return taskManager.getSize();
    }
}
