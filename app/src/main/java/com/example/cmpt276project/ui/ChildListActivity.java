package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

// Activity to handle the Configure my Children part of Iteration 1
public class ChildListActivity extends AppCompatActivity {
    // Initiate adapter
    ChildrenAdapter childrenAdapter;
    Children children;
    String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        // Set toolbar for future use
        Toolbar toolbar = findViewById(R.id.childrentoolbar);
        setSupportActionBar(toolbar);
        setBackButton();
        // Create children to test with
        children = new Children();
        children.addChild("asasdfsdfasdasdfasdfasdffasdfdf1");
        children.addChild("asdf2");
        children.addChild("3");
        children.addChild("asdf4");
        children.addChild("5");
        children.addChild("6asdf");
        children.addChild("7");
        children.addChild("8");
        children.addChild("9");
        children.addChild("0");
        children.addChild("11");
        children.addChild("12");
        children.addChild("13");
        children.addChild("14");
        children.addChild("15");
        children.addChild("16");

        // Build the RecyclerView
        buildChildView(children);

        // Build the delete buttons on the RecyclerView items
        setDeleteButtons();
    }

    // Method to build the RecyclerView
    public void buildChildView(Children children) {
        // Find ID of RecyclerView
        RecyclerView childRecyclerView = findViewById(R.id.childrenview);
        childRecyclerView.setHasFixedSize(true);

        // Set Layout for the RecyclerView
        RecyclerView.LayoutManager childLayoutManager = new LinearLayoutManager(this);
        childRecyclerView.setLayoutManager(childLayoutManager);

        // Set Adapter for the RecyclerView
        childrenAdapter = new ChildrenAdapter(children);
        childRecyclerView.setAdapter(childrenAdapter);
    }

    // Method to build the Delete buttons on the RecyclerView items
    public void setDeleteButtons() {
        // Use custom OnClickListener to remove items in RecyclerView
        childrenAdapter.setDeleteButtonClickListener(new ChildrenAdapter.OnDeleteButtonClickListener() {
            @Override
            public void editChild(int position) {
                children.editChild(position, childName);
                childrenAdapter.notifyDataSetChanged();
            }

            @Override
            public void deleteChild(int position) {
                // Remove the item from Children class and notify RecyclerView that it was removed
                children.removeChild(position);
                childrenAdapter.notifyItemRemoved(position);
            }
        });
    }

    // Set the back button on the activity
    public void setBackButton() {
        if (getSupportActionBar()!=null) { // Create back(up) button on the toolbar
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}