package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

// Activity to handle the Configure my Children part of Iteration 1
// Displays the name of all the current Children in the Children class object
// Allows the user to add, edit or delete Children
// Children are saved between application uses
public class ChildListActivity extends AppCompatActivity {
    // Initiate adapter and variables
    ChildrenAdapter childrenAdapter;
    Children children;



    Intent startAddChildActivity;
    Intent startEditChildActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        // Set toolbar for future use
        Toolbar toolbar = findViewById(R.id.childrentoolbar);
        setSupportActionBar(toolbar);
        setBackButton();
        // Create children to test with
        children = Children.getInstance();
        children.loadChildren(this);


        children.loadChildrenProfile(this);


        // Build the RecyclerView
        buildChildView(children);

        // Build the delete buttons on the RecyclerView items
        setDeleteButtons();


        // save child now is an activity
        startAddChildActivity = new Intent(ChildListActivity.this, AddChildActivity.class);

        startEditChildActivity = new Intent(ChildListActivity.this, EditChildActivity.class);

        //Toast.makeText(ChildListActivity.this, children.profileIDs.get(0).toString(), Toast.LENGTH_SHORT).show();

    }

    // Create the Add Child option on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.configure_children_menu, menu);
        return true;
    }

    // Save the children when the activity is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        children.saveChildren(this);

        children.saveChildrenProfile(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        children.saveChildren(this);

        children.saveChildrenProfile(this);
    }

    // Open add child dialog when the add child button is pressed
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
        // Create a popup to add the child
//        FragmentManager manager = getSupportFragmentManager();
//        AddChildPopup addChildPopup = new AddChildPopup(children, childrenAdapter);
//
//        addChildPopup.show(manager, "Add Child");


        startActivity(startAddChildActivity);


    }

    // Remove the child at the current position
    public void removeChild(int position) {
        children.removeChild(position);

        children.removeChildProfile(position);

        childrenAdapter.notifyItemRemoved(position);
    }

    // Edit the child at the current position
    public void editChildPopup(Children children, int position) {
        // Create a popup to edit the current child
//        FragmentManager manager = getSupportFragmentManager();
//        EditChildPopup editChildPopup = new EditChildPopup(children, position, childrenAdapter);
//
//        editChildPopup.show(manager, "Edit Child");

        startActivity(startEditChildActivity);
    }
}