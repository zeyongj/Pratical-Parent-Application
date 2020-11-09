package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

// Fragment that allows the user to create a new Child on ChildListActivity
// Functions as a popup when the user clicks "Add Child" on ChildListActivity
public class AddChildPopup extends AppCompatDialogFragment {
    // Initialize variables
    private Children children;
    private ChildrenAdapter childrenAdapter;
    private EditText addChildName;

    // Constructor
    public AddChildPopup(Children children, ChildrenAdapter childrenAdapter) {
        this.children = children;
        this.childrenAdapter = childrenAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.edit_child_popup, null);

        addChildName = v.findViewById(R.id.editchildname);

        // Add the child when the positive button is clicked
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addChild(children);
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener) // Add child when pressed
                .setNegativeButton(android.R.string.cancel, null) // Close dialog and do nothing else when pressed
                .setTitle(R.string.AddChild)
                .create();
    }

    public void addChild(Children children) {
        // Add child and notify the adapter
        children.addChild(addChildName.getText().toString());
        childrenAdapter.notifyItemInserted(children.getSize()-1);
    }
}
