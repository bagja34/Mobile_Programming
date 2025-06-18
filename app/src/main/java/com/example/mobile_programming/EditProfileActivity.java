package com.example.mobile_programming;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.jspecify.annotations.NonNull;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;
    Button gantiPP, editProfile;
    ImageView PP, back;
    EditText etNama, etEmail;
    int id;
    String nama, email, uriIMG;
    Uri uri, selectedImage;
    Intent intent;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        intent = getIntent();
        id = intent.getIntExtra("id", 1);
        nama = intent.getStringExtra("nama");
        email = intent.getStringExtra("email");
        uriIMG = intent.getStringExtra("uri");
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        PP = findViewById(R.id.ivProfile);
        back = findViewById(R.id.EPback);
        gantiPP = findViewById(R.id.btnEditPP);
        editProfile = findViewById(R.id.btnEditProfile);
        dh = new DatabaseHelper(this);
        gantiPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
        }
        uri = Uri.parse(uriIMG);
        Glide.with(this).load(uri).into(PP);
        etNama.setText(nama);
        etEmail.setText(email);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.updateUser(id, etNama.getText().toString(), etEmail.getText().toString(), selectedImage.toString());
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImage = result.getData().getData();
                    PP.setImageURI(selectedImage);

                }
            });
}