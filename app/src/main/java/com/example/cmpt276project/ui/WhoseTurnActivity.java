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
import com.example.cmpt276project.model.WhoseTurnAdapter;

import java.util.Objects;

public class WhoseTurnActivity extends AppCompatActivity {
    // Initiate adapter and variables
    WhoseTurnAdapter whoseTurnAdapter;
    Children children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);
        Toolbar toolbar = findViewById(R.id.whoseturntoolbar);
        setSupportActionBar(toolbar);

        setBackButton();

        // Create children to test with
        children = Children.getInstance();
        children.loadChildren(this);

        // Build the RecyclerView
        buildWhoseTurnView(children);

        // Build the delete buttons on the RecyclerView items
        setDeleteButtons();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        children.saveChildren(this);
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
    public void buildWhoseTurnView(Children children) {
        // Find ID of RecyclerView
        RecyclerView whoseTurnRecyclerView = findViewById(R.id.whoseturnview);
        whoseTurnRecyclerView.setHasFixedSize(true);

        // Set Layout for the RecyclerView
        RecyclerView.LayoutManager childLayoutManager = new LinearLayoutManager(this);
        whoseTurnRecyclerView.setLayoutManager(childLayoutManager);

        // Set Adapter for the RecyclerView
        whoseTurnAdapter = new WhoseTurnAdapter(children);
        whoseTurnRecyclerView.setAdapter(whoseTurnAdapter);
    }

    // Method to build the Delete buttons on the RecyclerView items
    public void setDeleteButtons() {
        // Use custom OnClickListener to remove items in RecyclerView
        whoseTurnAdapter.setDeleteButtonClickListener(new ChildrenAdapter.OnDeleteButtonClickListener() {
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

    private void setBackButton() {
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }


    // Add a child to the RecyclerView
    public void addChild() {
/*        // Create a popup to add the child
        FragmentManager manager = getSupportFragmentManager();
        AddChildPopup addChildPopup = new AddChildPopup(children, whoseTurnAdapter);

        addChildPopup.show(manager, "Add Child");*/
    }

    // Remove the child at the current position
    public void removeChild(int position) {
        children.removeChild(position);
        whoseTurnAdapter.notifyItemRemoved(position);
    }

    // Edit the child at the current position
    public void editChildPopup(Children children, int position) {
/*        // Create a popup to edit the current child
        FragmentManager manager = getSupportFragmentManager();
        EditChildPopup editChildPopup = new EditChildPopup(children, position, whoseTurnAdapter);

        editChildPopup.show(manager, "Edit Child");*/
    }
}