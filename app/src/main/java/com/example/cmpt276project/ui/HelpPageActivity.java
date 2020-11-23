package com.example.cmpt276project.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.cmpt276project.R;

import java.util.Objects;

public class HelpPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        Toolbar toolbar = findViewById(R.id.helppagetoolbar);
        setSupportActionBar(toolbar);

        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        setHyperLinks();
    }

    public void setHyperLinks() {
        TextView backgroundSource = findViewById(R.id.BGSource);
        TextView notificationIconSource = findViewById(R.id.NotifIconSource);
        TextView codeStyleGuideSource = findViewById(R.id.CodeStyleSource);

        backgroundSource.setMovementMethod(LinkMovementMethod.getInstance());
        notificationIconSource.setMovementMethod(LinkMovementMethod.getInstance());
        codeStyleGuideSource.setMovementMethod(LinkMovementMethod.getInstance());
    }
}