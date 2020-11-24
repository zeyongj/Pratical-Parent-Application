package com.example.cmpt276project.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/* When taking a photo with camera, the picture is saved under Android/data/com.example.cmpt276project/files/Pictures
* However, when editing profile with camera, the picture is saved internally. */


public class AddChildActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY_ACCESS = 1000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Children children;
    private ChildrenAdapter childrenAdapter;
    private EditText addChildName;


    // Handling profile Image
    private ImageView profileImage;
    private BitmapDrawable drawableProfile;
    private Bitmap bitmapStored;

    private String currentImagePath = null;
    private boolean isProfileSet = false;

    private int buttonClicks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        // enable 'up' button
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);


        children= Children.getInstance();
        childrenAdapter = ChildrenAdapter.getInstance();
        addChildName = findViewById(R.id.txt_enterChildName);

        profileImage = findViewById(R.id.profileImage);

        // set default image
        profileImage.setImageResource(R.drawable.default_user_profile);

        // request for camera runtime permission
        if (ContextCompat.checkSelfPermission(AddChildActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddChildActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }


        // Saving empty child error handling
        final Button btn = findViewById(R.id.btn_saveChild);
        btn.setEnabled(false);

        addChildName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    btn.setEnabled(false);
                }
                btn.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn.setEnabled(true);
            }
        });

        registerClickedSaveChild();
        registerClickedCancel();
        registerClickedChangeProfile();
        registerClickedCamera();


    }

    public void captureImage(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File imageFile = null;

            try {
                imageFile = getImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageFile != null)
            {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }

    }

    private File getImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentImagePath = image.getAbsolutePath();
        return image;


    }

    private void registerClickedCamera() {
        Button btn = findViewById(R.id.btn_AddChildCamera);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProfileSet = true;
                captureImage(profileImage);
            }
        });
    }
    private void registerClickedChangeProfile() {
        Button btn = findViewById(R.id.btn_changeProfileImage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProfileSet = true;
                // open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_ACCESS) {

            if(resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
                drawableProfile = (BitmapDrawable) profileImage.getDrawable();
                bitmapStored = drawableProfile.getBitmap();
                //Toast.makeText(this, bitmapStored.toString(), Toast.LENGTH_SHORT).show();


            }
        }

        else if (requestCode == REQUEST_IMAGE_CAPTURE) {

            if (resultCode == Activity.RESULT_OK) {
                // get the thumb nail
                Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);
                profileImage.setImageBitmap(bitmap);

                bitmapStored = bitmap;
                //Toast.makeText(this, bitmapStored.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void registerClickedSaveChild() {
        final Button btn = findViewById(R.id.btn_saveChild);

        buttonClicks = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild(children);


            if (isProfileSet) {
                addChildProfile(bitmapStored);
            }
            // default case for not setting profile image
            else {
                profileImage.setImageResource(R.drawable.default_user_profile);
                drawableProfile = (BitmapDrawable) profileImage.getDrawable();
                bitmapStored = drawableProfile.getBitmap();
                addChildProfile(bitmapStored);
            }


            // limiting button clicks
            buttonClicks++;
            if (buttonClicks >= 1) {
                btn.setEnabled(false);
            }

            }
        });
    }

    private void registerClickedCancel() {
        Button btn = findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addChild(Children children) {
        children.addChild(addChildName.getText().toString());
        childrenAdapter.notifyItemInserted(children.getSize()-1);
    }

    public void addChildProfile(Bitmap profileID) {
        children.addChildProfile(profileID);
        childrenAdapter.notifyItemInserted(children.getSize()-1);
    }


}