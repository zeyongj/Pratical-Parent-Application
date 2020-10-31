package com.example.cmpt276project.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Children {
    // ArrayList to keep track of names of Children
    ArrayList<String> childrenNames = new ArrayList<>();

    // Constructor
    public Children() {

    }

    // Add child to childrenNames
    public void addChild(String childName) {
        childrenNames.add(childName);
    }

    // Get child at current childrenNames
    public String getChild(int position) {
        return childrenNames.get(position);
    }

    // Remove child from childrenNames at given position
    public void removeChild(int position) {
        childrenNames.remove(position);
    }

    // Get size of childrenNames
    public int getSize() {
        return childrenNames.size();
    }

    public void editChild(int position, String name) {
        childrenNames.set(position, name);
    }
}
