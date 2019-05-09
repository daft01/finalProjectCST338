package com.example.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addBook extends AppCompatActivity {

    private EditText title, author, fee;
    private Button button;
    private Database myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        myDB = new Database(this);
        title = (EditText) findViewById(R.id.title);
        author = (EditText) findViewById(R.id.author);
        fee = (EditText) findViewById(R.id.fee);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = title.getText().toString(), a = author.getText().toString(), f = fee.getText().toString();

                if(t.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(addBook.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("The title is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }
                else if(a.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(addBook.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("The author is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }
                else if(f.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(addBook.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("The fee is empty");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                    return;
                }

                myDB.insertBook(t, a, Double.parseDouble(f));

                AlertDialog alertDialog = new AlertDialog.Builder(addBook.this).create();
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Title: " + t + " Author: " + a + " Fee: " + f);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(addBook.this, MainActivity.class));
                    }
                });
                alertDialog.show();
            }
        });
    }
}
