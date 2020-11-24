package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

// NO LONGER USED
// Fragment that allows the user to edit a Child's name on ChildListActivity
// Functions as a popup when the user clicks the "EDIT" button on the respective Child's RecyclerView
public class EditChildPopup extends AppCompatDialogFragment {
    // Initialize variables
    private Children children;
    private int position;
    private ChildrenAdapter childrenAdapter;
    private EditText editChildName;

    // Constructor
    public EditChildPopup(Children children, int position, ChildrenAdapter childrenAdapter) {
        this.children = children;
        this.position = position;
        this.childrenAdapter = childrenAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.edit_child_popup, null);

        // Display the current name of the child and move the cursor to the end of the name
        editChildName = v.findViewById(R.id.editchildname);
        editChildName.append(children.getChild(position));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Edit the name of the child at the current position
                editChildName(children, position);
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener) // Edit the name of the child when pressed
                .setNegativeButton(android.R.string.cancel, null) // Close dialog and do nothing when pressed
                .setTitle(R.string.EditChildName)
                .create();
    }

    public void editChildName(Children children, int position) {
        // Edit the name of the child at the current position and notify the adapter
        children.editChild(position, editChildName.getText().toString());
        childrenAdapter.notifyDataSetChanged();
    }
}
