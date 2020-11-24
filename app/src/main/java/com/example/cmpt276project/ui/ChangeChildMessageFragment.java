package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChangeChildMessageFragment extends AppCompatDialogFragment {

    private Children children = Children.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Inflate the layout for the dialog
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_change_child, null);

        ListView listView = v.findViewById(R.id.ChildName_ListView);

        //Add Children Name to Array List
        ArrayList<String> ChildrenName = new ArrayList<>();
        for(int i = children.getCurrentChildIndex(requireActivity()), j = 0; j < children.getNumChildren(requireActivity()); j++){
            ChildrenName.add(children.getChild(i));
            i = (i + 1) % children.getNumChildren(requireActivity());
        }

        // Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,ChildrenName);
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(v)
                .setPositiveButton(android.R.string.ok, null) // Add child when pressed
                .setNegativeButton(android.R.string.cancel, null) // Close dialog and do nothing else when pressed
                .setTitle(R.string.ChangingChild)
                .create();

        AlertDialog alert = builder.show();
        registerChildClicked(listView, alert);

        return alert;
    }

    private void registerChildClicked(ListView listView, final AlertDialog alert){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                children.setCurrentToClickedChild(requireActivity(), (position + children.getCurrentChildIndex(requireActivity())) % children.getSize());
                TextView textView = requireActivity().findViewById(R.id.childNameTextView);
                textView.setText(getString(R.string.CurrentChildIs, children.getChild(children.getCurrentChildIndex(requireActivity()))));
                alert.dismiss();
            }
        });
    }
}
