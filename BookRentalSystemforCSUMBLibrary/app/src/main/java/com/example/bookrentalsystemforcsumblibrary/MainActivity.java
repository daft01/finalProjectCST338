package com.example.bookrentalsystemforcsumblibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button CreateAccountButton = (Button)findViewById(R.id.CreateAccountButton);
        Button PlaceHold = (Button) findViewById(R.id.PlaceHoldButton);
        Button ManageSystemButton = (Button) findViewById(R.id.ManageSystemButton);
        Button CancelHoldButton = (Button) findViewById(R.id.CancelHoldButton);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
            }
        });

        PlaceHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlaceHold.class));
            }
        });

        ManageSystemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManageSystem.class));
            }
        });

        CancelHoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CancelHold.class));
            }
        });
    }
}
