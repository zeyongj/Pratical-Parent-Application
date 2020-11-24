package com.example.cmpt276project.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

import java.util.Objects;

public class EditChildActivity extends AppCompatActivity {

    private static final String ACTIVITY_ID = "Id";
    private static final int REQUEST_CAMERA_ACCESS = 101;
    private static final int REQUEST_GALLERY_ACCESS = 1001;

    private Children children;
    private ChildrenAdapter childrenAdapter;
    private int position;
    private EditText editChildName;


    // Handling profile Image
    private ImageView profileImage;
    private BitmapDrawable drawableProfile;
    private Bitmap bitmapStored;

    private boolean isProfileChanged = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);

        children= Children.getInstance();
        childrenAdapter = ChildrenAdapter.getInstance();
        editChildName = findViewById(R.id.txt_editChildNameEdit);

        Intent intent = getIntent();
        if (intent != null) {
            position = Objects.requireNonNull(intent.getExtras()).getInt(ACTIVITY_ID);
        }

        editChildName.append(children.getChild(position));

        profileImage = findViewById(R.id.profileImage);

        // get current children profile
        profileImage.setImageBitmap(children.decodeToBase64(children.getChildProfile(position)));

        if (ContextCompat.checkSelfPermission(EditChildActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EditChildActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }


        registerClickedOk();
        registerClickedCancel();
        registerClickedChangeProfile();
        registerClickedCamera();

    }

    private void registerClickedCamera() {
        Button btn = findViewById(R.id.btn_editChildCamera);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProfileChanged = true;
                Intent enableCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(enableCameraIntent, REQUEST_CAMERA_ACCESS);
            }
        });
    }

    private void registerClickedChangeProfile() {
        Button btn = findViewById(R.id.btn_changeProfileEdit);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isProfileChanged = true;

                // Open Gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_ACCESS) {
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
                drawableProfile = (BitmapDrawable) profileImage.getDrawable();
                bitmapStored = drawableProfile.getBitmap();

            }
        }
        else if (requestCode == REQUEST_CAMERA_ACCESS) {
            if (data != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(bitmap);
                drawableProfile = (BitmapDrawable) profileImage.getDrawable();
                bitmapStored = drawableProfile.getBitmap();
            }
        }
    }


    private void registerClickedOk() {
        Button btn = findViewById(R.id.btn_okEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editChildName(children, position);

                if (isProfileChanged){
                    editChildProfile(bitmapStored, position);
                }

                goBackToChildList();
            }
        });
    }

    private void goBackToChildList() {
        Intent intent = new Intent(this, ChildListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void registerClickedCancel() {
        Button btn = findViewById(R.id.btn_cancelEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void editChildName(Children children, int position) {
        // Edit the name of the child at the current position and notify the adapter
        children.editChild(position, editChildName.getText().toString());
        childrenAdapter.notifyDataSetChanged();
    }
    public void editChildProfile(Bitmap profileID, int position) {
        children.editChildProfile(position, profileID);
        childrenAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


}

