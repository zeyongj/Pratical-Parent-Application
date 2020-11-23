package com.example.cmpt276project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;
import com.example.cmpt276project.ui.ChildListActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Class to create the RecyclerView layout for ChildListActivity
// Has buttons on each layout to allow the user to delete and edit the Children more easily
public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder> {
    // Set variables
    private Children children;
    private OnDeleteButtonClickListener deleteButtonListener;

    // Set interface for Delete button on RecyclerView item
    public interface OnDeleteButtonClickListener {
        void deleteChild(int position);
        void editChild(int position);
    }

    // Method to create OnClickListener for Delete button on RecyclerView item
    public void setDeleteButtonClickListener(OnDeleteButtonClickListener listener) {
        deleteButtonListener = listener;
    }

    public static class ChildrenViewHolder extends RecyclerView.ViewHolder {
        public TextView childName;
        public Button deleteButton;
        public Button editButton;
        public ImageView profilePic;

        public ChildrenViewHolder(@NonNull View itemView, final OnDeleteButtonClickListener listener) {
            super(itemView);
            childName = itemView.findViewById(R.id.childname);
            deleteButton = itemView.findViewById(R.id.deletebutton);
            editButton = itemView.findViewById(R.id.editbutton);
            profilePic = itemView.findViewById(R.id.img_profileImageChildItem);

            // Set OnClickListener for the button specified on the Layout
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the dynamically updated position of button
                    int position = getAdapterPosition();
                    // Prevent clicks when the RecyclerView is in the animation of removing an item
                    if (position != RecyclerView.NO_POSITION) {
                        listener.deleteChild(position);
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
                        listener.editChild(position);
                    }
                }
            });
        }
    }

    // Constructor
    public ChildrenAdapter(Children children) {
        this.children = children;
    }

    // Inflate the specified layout for use
    @NonNull
    @Override
    public ChildrenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View childrenLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);

        return new ChildrenViewHolder(childrenLayout, deleteButtonListener);
    }

    // Bind the child names to their relevant positions on the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ChildrenViewHolder holder, int position) {
        String currentChild = children.getChild(position);

        String currentProfile = children.getChildProfile(position);


        holder.childName.setText(currentChild);



        // TODO: default case when children profile is not entered
        holder.profilePic.setImageBitmap(children.decodeToBase64(currentProfile));


    }

    // Get the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return children.getSize();
    }




    // getInstance
    private static ChildrenAdapter instance;
    public ChildrenAdapter() {
    }

    public static ChildrenAdapter getInstance() {
        if (instance == null) {
            instance = new ChildrenAdapter();
        }
        return instance;
    }




}
