package com.example.cmpt276project.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Children {
    ArrayList<String > childrenNames = new ArrayList<String>();

    public Children() {

    }

    public void addChild(String childName) {
        childrenNames.add(childName);
    }

    public String getChild(int position) {
        return childrenNames.get(position);
    }

    public void removeChild(int position) {
        childrenNames.remove(position);
    }
}
