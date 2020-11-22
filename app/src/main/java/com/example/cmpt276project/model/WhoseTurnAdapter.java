package com.example.cmpt276project.model;

import android.app.Dialog;
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
    private WhoseTurnAdapter.OnDeleteButtonClickListener deleteButtonListener;

    // Set interface for Delete button on RecyclerView item
    public interface OnDeleteButtonClickListener {
        void deleteTask(int position);
        void editTask(int position);
    }

    // Method to create OnClickListener for Delete button on RecyclerView item
    public void setDeleteButtonClickListener(WhoseTurnAdapter.OnDeleteButtonClickListener listener) {
        deleteButtonListener = listener;
    }

    public static class WhoseTurnViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;
        public TextView nextChildName;
        public Button deleteButton;
        public Button editButton;

        public WhoseTurnViewHolder(@NonNull View itemView, final WhoseTurnAdapter.OnDeleteButtonClickListener listener) {
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
        }


    }

    public static interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    private OnItemClickListener monItemClickListener=null;
    public void setonItemClickListener(OnItemClickListener listener){
        this.monItemClickListener=listener;
    }

    // Constructor
    public WhoseTurnAdapter(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    // Inflate the specified layout for use
    @NonNull
    @Override
    public WhoseTurnAdapter.WhoseTurnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View childrenLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new WhoseTurnAdapter.WhoseTurnViewHolder(childrenLayout, deleteButtonListener);
    }



    // Bind the child names to their relevant positions on the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WhoseTurnAdapter.WhoseTurnViewHolder holder, final int position) {
        String currentTask = "Task Name: " + taskManager.getTask(position).getTaskName();
        String nextChild = "Next Child: " + taskManager.getTask(position).getChild();
        holder.taskName.setText(currentTask);
        holder.taskName.setTextColor(Color.BLUE);
        holder.nextChildName.setText(nextChild);
        holder.nextChildName.setTextColor(Color.BLACK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                }
            });
        }

    // Get the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return taskManager.getSize();
    }
}
