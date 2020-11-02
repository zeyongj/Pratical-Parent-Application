package com.example.cmpt276project.ui;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import com.example.cmpt276project.model.Children;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FlipCoinActivity extends AppCompatActivity {

    // Create children
    private Children children = Children.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Display current child name who flip the coin
        // If currently has no child, display "No child"
        displayChildName();

        registerFlipClicked();
    }


    // Display current child name who flip the coin
    private void displayChildName() {
        TextView textView = findViewById(R.id.childNameTextView);
        if(children.getNumChildren() == 0)
            textView.setText("no child");
        else {
            String childName = " The current child is:" + children.getChild(children.getCurrentChildIndex());
            textView.setText(childName);
        }
    }

    private void registerFlipClicked() {
        Button btn = findViewById(R.id.btn_flip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, 3);
                if (randomNumber == 1) {
                    flipCoin(R.drawable.quarter_head, "Heads");
                }
                else {
                    flipCoin(R.drawable.quarter_tail, "Tails");
                }

                // Set current child to next child
                if(children.getNumChildren() != 0)
                    children.setCurrentToNextChild();
            }
        });
    }

    private void flipCoin(final int imageID, final String coinSide) {
        final ImageView coin = findViewById(R.id.img_coin);
        coin.animate()
                .setDuration(1000)
                .rotationYBy(1800f);
        coin.setClickable(false);
        coin.animate().withEndAction(new Runnable() {
            @Override
            public void run() {
                ImageView coin = findViewById(R.id.img_coin);
                coin.setImageResource(imageID);
                Toast.makeText(FlipCoinActivity.this, coinSide, Toast.LENGTH_SHORT).show();
                coin.setClickable(true);
                displayChildName();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu:
        getMenuInflater().inflate(R.menu.menu_flip_coin, menu);
        return true;
    }
}