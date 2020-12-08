package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.FlipCoinChildrenList;
import com.example.cmpt276project.model.FlipHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Popup to allow the user to choose a child from the current queue of children in FlipCoinActivity
public class ChangeChildMessageFragment extends AppCompatDialogFragment {

    private Children children = Children.getInstance();
    private ArrayList<FlipCoinChildrenList> myChildList = new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Inflate the layout for the dialog
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_change_child, null);

        ListView listView = v.findViewById(R.id.ChildName_ListView);

        //Add Children Name to Array List
        int minIndex = 0;
        for(int i = 0; i < children.getNumChildren(requireActivity()); i++) {
            for(int j = 0; j < children.getNumChildren(requireActivity()); j++) {
                if(children.getChildIndex(j) == minIndex) {
                    myChildList.add(new FlipCoinChildrenList(children.getChild(j), children.getChildProfile(j)));
                    minIndex++;
                    break;
                }
            }
        }

        // Create Array Adapter
        ArrayAdapter<FlipCoinChildrenList> adapter = new ChangeChildMessageFragment.myListAdapter();
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

    private class myListAdapter extends ArrayAdapter<FlipCoinChildrenList> {
        public myListAdapter(){
            super(requireActivity(), R.layout.childlist_item, myChildList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.childlist_item, parent, false);
            }

            return getFlipCoinChildrenListView(position, itemView);
        }
    }

    private View getFlipCoinChildrenListView(int position, View itemView) {
        //Find the History to work with
        FlipCoinChildrenList childList = myChildList.get(position);

        ImageView profilePic = itemView.findViewById(R.id.ChildList_Portrait);
        profilePic.setImageBitmap(children.decodeToBase64(childList.getProfile()));

        TextView ChildName = itemView.findViewById(R.id.ChildList_ChildrenName);
        ChildName.setText(childList.getChildName());

        return itemView;
    }

    private void registerChildClicked(ListView listView, final AlertDialog alert){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                children.setCurrentToClickedChild(requireActivity(), position);
                TextView textView = requireActivity().findViewById(R.id.childNameTextView);
                textView.setText(getString(R.string.CurrentChildIs, children.getChild(children.getCurrentChildIndex(requireActivity()))));
                ImageView portrait = requireActivity().findViewById(R.id.Portrait_FlipCoin);
                portrait.setImageBitmap(children.decodeToBase64(children.getChildProfile(children.getCurrentChildIndex(requireActivity()))));
                portrait.setVisibility(View.VISIBLE);
                alert.dismiss();
            }
        });
    }
}