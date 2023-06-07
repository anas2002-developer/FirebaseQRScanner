package com.anas.firebaseqrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static TextView txtScanned;
    Button btnFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtScanned=findViewById(R.id.txtScanned);
        btnFirestore = findViewById(R.id.btnFirestore);

        findViewById(R.id.btnScan).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),QRActivity.class));
        });

        btnFirestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scannedText = txtScanned.getText().toString();


                Map<String, Object> data = new HashMap<>();
                data.put("text", scannedText);

                FirebaseFirestore.getInstance().collection("Scanned Texts")
                        .add(data)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(MainActivity.this, "Saved to Firestore", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                        });

            }});
    }
}