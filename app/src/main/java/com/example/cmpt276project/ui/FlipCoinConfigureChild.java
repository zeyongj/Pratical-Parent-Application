package com.example.cmpt276project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;

public class FlipCoinConfigureChild  extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Create the view to show
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.flip_coin_configure_child, null);

        // Create a button listener
        DialogInterface.OnClickListener lister = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("TAG", "You clicked th dialog button");
            }
        };

        // Build the alert Dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Test")
                .setView(v)
                .setPositiveButton(android.R.string.ok, lister)
                .create();


    }
}
