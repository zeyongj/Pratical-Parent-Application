package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;

import java.util.ArrayList;
import java.util.List;


public class ChangeChildMessageFragment extends AppCompatDialogFragment {

    private Children children;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Initiate children
        children = Children.getInstance();
        children.loadChildren(getActivity());

        // Inflate the layout for the dialog
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_change_child, null);

        ListView listView = v.findViewById(R.id.ChildName_ListView);

        //Add Children Name to Array List
        ArrayList<String> ChildrenName = new ArrayList<>();
        for(int i = children.getCurrentChildIndex(getActivity()), j = 0; j < children.getNumChildren(getActivity()); j++){
            ChildrenName.add(children.getChild(i));
            i = (i + 1) % children.getNumChildren(getActivity());
        }

        // Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,ChildrenName);
        listView.setAdapter(adapter);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, null) // Add child when pressed
                .setNegativeButton(android.R.string.cancel, null) // Close dialog and do nothing else when pressed
                .setTitle("Changing Child")
                .create();
    }
}
