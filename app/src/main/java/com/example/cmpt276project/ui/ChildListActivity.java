package com.example.cmpt276project.ui;

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
    private RecyclerView childRecyclerView;
    private RecyclerView.Adapter childAdapter;
    private RecyclerView.LayoutManager childLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        Toolbar toolbar = findViewById(R.id.childrentoolbar);
        setSupportActionBar(toolbar);

        Children children = new Children();
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        children.addChild("poop");
        children.addChild("aslkdfjas");
        childRecyclerView = findViewById(R.id.childrenview);
        childRecyclerView.setHasFixedSize(true);
        childLayoutManager = new LinearLayoutManager(this);
        childAdapter = new ChildrenAdapter(children);

        childRecyclerView.setLayoutManager(childLayoutManager);
        childRecyclerView.setAdapter(childAdapter);
    }
}