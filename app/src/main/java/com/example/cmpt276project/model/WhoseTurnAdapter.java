package com.example.cmpt276project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;
import com.example.cmpt276project.ui.WhoseTurnActivity;

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
        public Button deleteButton;
        public Button editButton;

        public WhoseTurnViewHolder(@NonNull View itemView, final WhoseTurnAdapter.OnDeleteButtonClickListener listener) {
            super(itemView);
            taskName = itemView.findViewById(R.id.childname);
            deleteButton = itemView.findViewById(R.id.deletebutton);
            editButton = itemView.findViewById(R.id.editbutton);

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

    // Constructor
    public WhoseTurnAdapter(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    // Inflate the specified layout for use
    @NonNull
    @Override
    public WhoseTurnAdapter.WhoseTurnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View childrenLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
        return new WhoseTurnAdapter.WhoseTurnViewHolder(childrenLayout, deleteButtonListener);
    }

    // Bind the child names to their relevant positions on the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WhoseTurnAdapter.WhoseTurnViewHolder holder, int position) {
        String currentTask = taskManager.getTask(position).getTaskName();

        holder.taskName.setText(currentTask);
    }

    // Get the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return taskManager.getSize();
    }
}
