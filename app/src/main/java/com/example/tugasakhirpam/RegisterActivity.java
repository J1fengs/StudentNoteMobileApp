package com.example.tugasakhirpam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private EditText registEmail, registPassword;
    private ImageButton createAcc, loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registEmail = findViewById(R.id.etRegisterEmail);
        registPassword = findViewById(R.id.etRegisterPassword);
        createAcc = findViewById(R.id.imageButtonCreateAccount);
        loginNow = findViewById(R.id.imageButtonLoginNow);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt = registEmail.getText().toString();
                String passwordtxt = registPassword.getText().toString();

                if(emailtxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailtxt)){
                                Toast.makeText(RegisterActivity.this, "email already registered", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("users").child(emailtxt).child("password").setValue(passwordtxt);

                                Toast.makeText(RegisterActivity.this, "register successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}