package com.example.bookingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    EditText Email, pass;
    Button Login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Email=(EditText) findViewById(R.id.ed1);
        pass=(EditText) findViewById(R.id.ed2);
        Login=(Button) findViewById(R.id.btn1);

Login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent m= new Intent(Login.this,Main_Activity.class);
        startActivity(m);
    }
});


        };

    }
