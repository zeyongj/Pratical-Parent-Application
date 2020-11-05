package com.example.cmpt276project.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276project.model.Children;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276project.R;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FlipCoinActivity extends AppCompatActivity {

    private static final String DEFAULT = "no child";
    private String coinSide;
    private View mView;


    // Initiate variable
    private Children children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Create Children
        children = Children.getInstance();
        children.loadChildren(this);

        // Display current child name who flip the coin
        // If currently has no child, display "No child"
        displayChildName();



        // TODO: Configure Children Alert Dialog
        chooseSideDialog();




        registerFlipClicked();
    }

    private void chooseSideDialog() {
//        FragmentManager manager = getSupportFragmentManager();
//        FlipCoinChooseCoinSide dialog = new FlipCoinChooseCoinSide();
//        dialog.show(manager, "MessageDialog");
        AlertDialog.Builder dialog = new AlertDialog.Builder(FlipCoinActivity.this);
        mView = getLayoutInflater().inflate(R.layout.flip_coin_choose_sides, null);

        // TODO: add listener - that is when click ok, data is saved then can be accessed by historyActivity
        dialog.setTitle("Choose Side")
            .setView(mView)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show();


        chooseSide(R.id.spinnerHead);
        chooseSide(R.id.spinnerTail);


    }
    private void chooseSide(int spinnerID) {
        Spinner headSpinner = (Spinner) mView.findViewById(spinnerID);
        List<String> childrenList = children.getListChildren(FlipCoinActivity.this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlipCoinActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                childrenList);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        headSpinner.setAdapter(adapter);
    }


    // Display current child name who flip the coin
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
                if(children.getNumChildren(FlipCoinActivity.this) != 0)
                    children.setCurrentToNextChild(FlipCoinActivity.this);
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

    public static Intent makeIntent(Context context) {
        return new Intent(context, FlipCoinActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu:
        getMenuInflater().inflate(R.menu.menu_flip_coin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.History_button:
                Intent intent = FlipHistoryActivity.makeIntent(FlipCoinActivity.this);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}