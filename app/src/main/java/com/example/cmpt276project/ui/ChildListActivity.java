package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

        // Build the RecyclerView
        buildChildView(children);

        // Build the delete buttons on the RecyclerView items
        setDeleteButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.configure_children_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_child_button) {
            addChild();
        }
        return super.onOptionsItemSelected(item);
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
                editChildPopup(children, position);
            }

            @Override
            public void deleteChild(int position) {
                // Remove the item from Children class and notify RecyclerView that it was removed
                removeChild(position);
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

    // Add a child to the RecyclerView
    public void addChild() {
        FragmentManager manager = getSupportFragmentManager();
        AddChildPopup addChildPopup = new AddChildPopup(children, childrenAdapter);

        addChildPopup.show(manager, "Add Child");
    }

    public void removeChild(int position) {
        children.removeChild(position);
        childrenAdapter.notifyItemRemoved(position);
    }

    public void editChildPopup(Children children, int position) {
        FragmentManager manager = getSupportFragmentManager();
        EditChildPopup editChildPopup = new EditChildPopup(children, position, childrenAdapter);

        editChildPopup.show(manager, "Edit Child");
    }
}