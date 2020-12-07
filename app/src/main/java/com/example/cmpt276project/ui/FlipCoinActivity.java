package com.example.cmpt276project.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.cmpt276project.model.Children;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

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
import com.example.cmpt276project.model.FlipHistoryManager;
import com.example.cmpt276project.model.TaskManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

// Activity to handle the Flip Coin part of iteration 1 of the project
// Lets the current child pick heads or tails
// Remember the order of the Children who have already picked heads or tails
// Saves the history of the flips in the current session
public class FlipCoinActivity extends AppCompatActivity {

    public static final String DATE_FORMAT = "MM dd yyyy, h:mm:s";
    private String coinSide;
    private String profile;

    // Initiate variable
    private Children children;
    private FlipHistoryManager historyManager;
    private MediaPlayer flipSound;

    // When buttonState == true, Flip is invisible, Head and tail is visible
    // When buttonState == false, Flip is visible, Head and tail is invisible
    private boolean buttonState = true;
    private String choose;
    private boolean WinOrLoss;
    private boolean NobodyTurn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        // Create Children
        children = Children.getInstance();
        children.loadChildren(this);

        // Handling history
        historyManager = FlipHistoryManager.getInstance();
        loadFlipHistory(historyManager);

        // Handling tossing coin sound
        flipSound = MediaPlayer.create(this,R.raw.coin_toss_sound);

        //Setup Buttons
        displayChildName();
        initiateButtons();
        registerHeadOrTailClicked();
        registerFlipClicked();
        registerChangeDefaultClicked();
        registerNobodyClicked();

        //Setup Message Fragment
        setupChangeChildMessage();

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
        //return false;
        return super.onOptionsItemSelected(item);
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

    // Set Button State
    // Toggle visibility of the Flip button
    private void setButton() {
        Button headButton = findViewById(R.id.headButton);
        Button tailButton = findViewById(R.id.tailButton);
        Button flipButton = findViewById(R.id.btn_flip);
        Button changeChildButton = findViewById(R.id.changeChildButton);
        Button changeNobodyButton = findViewById(R.id.changeNobodyButton);
        Button changeDefaultButton = findViewById(R.id.changeDefaultButton);
        changeChildButton.setVisibility(View.GONE);
        changeNobodyButton.setVisibility(View.GONE);
        changeDefaultButton.setVisibility(View.VISIBLE);
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

    // Registered for Head and tail buttons
    private void registerHeadOrTailClicked() {
        Button headButton = findViewById(R.id.headButton);
        Button tailButton = findViewById(R.id.tailButton);
        headButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = false;
                setButton();
                choose = getString(R.string.head);
            }
        });
        tailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = false;
                setButton();
                choose = getString(R.string.tail);
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
                    flipCoinAnimation(R.drawable.quarter_head, getString(R.string.head));
                    coinSide = getString(R.string.head);
                    flipSound.start();
                }
                else {
                    flipCoinAnimation(R.drawable.quarter_tail, getString(R.string.tail));
                    coinSide = getString(R.string.tail);
                    flipSound.start();
                }
                if(children.getNumChildren(FlipCoinActivity.this) != 0 && !NobodyTurn) {
                    buttonState = true;
                    setButton();
                    WinOrLoss = choose.equals(coinSide);
                    profile = children.getChildProfile(children.getCurrentChildIndex(FlipCoinActivity.this));
                    historyManager.addHistory(saveCurrentDateAndTime(), saveChildNames(),choose, WinOrLoss, profile);
                    historyManager.saveFlipHistory(FlipCoinActivity.this);
                    children.setCurrentToNextChild(FlipCoinActivity.this);
                    ImageView portrait = findViewById(R.id.Portrait_FlipCoin);
                    portrait.setVisibility(View.VISIBLE);
                }

                if(NobodyTurn) {
                    NobodyTurn = false;
                    buttonState = true;
                    setButton();
                }

            }
        });
    }

    // Registered for Change Default buttons
    private void registerChangeDefaultClicked() {
        final Button changeDefaultBtn = findViewById(R.id.changeDefaultButton);
        final Button changeChildBtn = findViewById(R.id.changeChildButton);
        final Button changeNobodyBtn = findViewById(R.id.changeNobodyButton);
        final Button headButton = findViewById(R.id.headButton);
        final Button tailButton = findViewById(R.id.tailButton);
        final Button flipButton = findViewById(R.id.btn_flip);
        changeDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDefaultBtn.setVisibility(View.GONE);
                changeChildBtn.setVisibility(View.VISIBLE);
                changeNobodyBtn.setVisibility(View.VISIBLE);
                headButton.setVisibility(View.GONE);
                tailButton.setVisibility(View.GONE);
                flipButton.setVisibility(View.GONE);
            }
        });
    }

    // Registered for Nobody buttons
    private void registerNobodyClicked() {
        final Button changeNobodyBtn = findViewById(R.id.changeNobodyButton);
        changeNobodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = true;
                NobodyTurn = true;
                TextView textView = findViewById(R.id.childNameTextView);
                textView.setText(getString(R.string.NobodysTurn));
                ImageView portrait = findViewById(R.id.Portrait_FlipCoin);
                portrait.setVisibility(View.GONE);
                initiateButtons();
            }
        });
    }

    private String saveCurrentDateAndTime() {
        @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat(DATE_FORMAT);
        String dateFormatted = date.format(Calendar.getInstance().getTime());
        Log.d("the date and time saved is:",dateFormatted);
        return dateFormatted;
    }

    private String saveChildNames() {
        if (children.getNumChildren(this) != 0) {
            Log.d("saved Child is: ", children.getChild(children.getCurrentChildIndex(this)) );
            return children.getChild(children.getCurrentChildIndex(this));
        }
        return null;
    }

    // Display current child name who flip the coin
    // If currently has no child, display "No child"
    private void displayChildName() {
        TextView textView = findViewById(R.id.childNameTextView);
        ImageView portrait = findViewById(R.id.Portrait_FlipCoin);

        if(children.getNumChildren(this) == 0)
            textView.setText(R.string.NoChild);
        else {
            String childName = getString(R.string.ChildNameIs, children.getChild(children.getCurrentChildIndex(this)));
            textView.setText(childName);
            portrait.setImageBitmap(children.decodeToBase64(children.getChildProfile(children.getCurrentChildIndex(FlipCoinActivity.this))));
        }
    }

    private void flipCoinAnimation(final int imageID, final String coinSide) {
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
                ImageView portrait = findViewById(R.id.Portrait_FlipCoin);
                portrait.setVisibility(View.VISIBLE);
            }
        });
    }

    // Register Change Child Button clicked
    // Setup Message Fragment
    private void setupChangeChildMessage() {
        Button button = findViewById(R.id.changeChildButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                ChangeChildMessageFragment dialog = new ChangeChildMessageFragment();
                dialog.show(manager,"MessageDialog");
                buttonState = true;
                initiateButtons();
                NobodyTurn = false;
            }
        });
    }

    public void loadFlipHistory(FlipHistoryManager flipHistoryManager) {
        flipHistoryManager.loadFlipHistory(this);
        if (flipHistoryManager.checkFlipHistoryEmpty()) {
            flipHistoryManager.reinitializeFlipHistory();
        }
    }
}