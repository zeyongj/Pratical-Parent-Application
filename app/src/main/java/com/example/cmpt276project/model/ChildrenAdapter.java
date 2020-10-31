package com.example.cmpt276project.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder> {
    private Children children;

    public static class ChildrenViewHolder extends RecyclerView.ViewHolder {
        public TextView childName;

        public ChildrenViewHolder(@NonNull View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childname);
        }
    }

    public ChildrenAdapter(Children children) {
        this.children = children;
    }

    @NonNull
    @Override
    public ChildrenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
        return new ChildrenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenViewHolder holder, int position) {
        String currentChild = children.getChild(position);

        holder.childName.setText(currentChild);
    }

    @Override
    public int getItemCount() {
        return children.getSize();
    }
}
