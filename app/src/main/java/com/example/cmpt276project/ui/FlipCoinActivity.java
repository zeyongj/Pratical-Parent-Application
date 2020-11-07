package com.example.cmpt276project.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276project.model.Children;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.FlipHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class FlipCoinActivity extends AppCompatActivity {

    private static final String DEFAULT = "no child";
    public static final String DATE_FORMATE = "MM dd yyyy, h:mm";
    private String coinSide;


    // Initiate variable
    private Children children;

    // Handling History
    private FlipHistory history;




    // When buttonState == true, Flip is invisible, Head and tail is visible
    // When buttonState == false, Flip is visible, Head and tail is invisible
    private boolean buttonState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Create Children
        children = Children.getInstance();
        children.loadChildren(this);

        history = FlipHistory.getInstance();


        // Display current child name who flip the coin
        displayChildName();

        // Initiate Buttons state
        initiateButtons();

        // Registered for Head and tail buttons
        registerHeadOrTailClicked();
        registerFlipClicked();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu:
        getMenuInflater().inflate(R.menu.menu_flip_coin, menu);
        return true;
    }

    // Flip History
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.History_button) {
            Intent intent = new Intent(FlipCoinActivity.this, FlipCoinHistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Set Button State
    // When buttonState == true, Flip is invisible, Head and tail is visible
    // When buttonState == false, Flip is visible, Head and tail is invisible
    private void setButton() {
        Button headButton = findViewById(R.id.headButton);
        Button tailButton = findViewById(R.id.tailButton);
        Button flipButton = findViewById(R.id.btn_flip);
        if(buttonState) {
            headButton.setVisibility(View.VISIBLE);
            tailButton.setVisibility(View.VISIBLE);
            flipButton.setVisibility(View.GONE);
        }
        else{
            headButton.setVisibility(View.GONE);
            tailButton.setVisibility(View.GONE);
            flipButton.setVisibility(View.VISIBLE);
        }
    }

    // Initiate button state
    // If there is no child, only make flip button visible
    private void initiateButtons() {
        if(children.getNumChildren(this) == 0) {
            buttonState = false;
            setButton();
        }
        else
            setButton();
    }

    // Registered for Head and tail buttons
    private void registerHeadOrTailClicked() {
        Button headButton = findViewById(R.id.headButton);
        Button tailButton = findViewById(R.id.tailButton);
        headButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = false;
                setButton();
            }
        });
        tailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = false;
                setButton();
            }
        });
    }

    // Registered for Flip buttons
    private void registerFlipClicked() {
        Button flipButton = findViewById(R.id.btn_flip);
        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomNumber = ThreadLocalRandom.current().nextInt(1, 3);
                if (randomNumber == 1) {
                    flipCoin(R.drawable.quarter_head, "Heads");
                    coinSide = "Head";
                    // TODO: save flip result to SharedPreference.
                    //TODO: fix bug
                    saveCurrentDateAndTime();
                    //saveChildNames();
                    //saveCoinSide(coinSide);
                }
                else {
                    flipCoin(R.drawable.quarter_tail, "Tails");
                }

                if(children.getNumChildren(FlipCoinActivity.this) != 0) {
                    buttonState = true;
                    setButton();
                }

                // Set current child to next child
                if(children.getNumChildren(FlipCoinActivity.this) != 0)
                    children.setCurrentToNextChild(FlipCoinActivity.this);
            }


        });
    }

    private void saveCurrentDateAndTime() {
        
        DateFormat date = new SimpleDateFormat(DATE_FORMATE);
        String dateFormatted = date.format(Calendar.getInstance().getTime());
        history.setCurrentDateAndTime(dateFormatted);

        // Log message to check if date and time is saved
        Log.d("the date and time saved is:", history.getCurrentDateAndTime());
    }


    private void saveCoinSide(String coinSide) {
        history.getInstance();
        history.setFlipResult(coinSide);
    }


    private void saveChildNames() {
        history.getInstance();

        history.setChildName(children.getChild(children.getCurrentChildIndex(this)));

    }





    // Display current child name who flip the coin
    // If currently has no child, display "No child"
    private void displayChildName() {
        TextView textView = findViewById(R.id.childNameTextView);

        if(children.getNumChildren(this) == 0)
            textView.setText(DEFAULT);
        else {
            if(children.getCurrentChildIndex(this) >= children.getNumChildren(this)) {
                children.setCurrentToFirstChild(this);
                String childName = " The current child is:" + children.getChild(children.getCurrentChildIndex(this));
                textView.setText(childName);
            }
            else {
                String childName = " The current child is:" + children.getChild(children.getCurrentChildIndex(this));
                textView.setText(childName);
            }
        }
    }

    // Flip coin Animation
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





}