package com.example.bookrentalsystemforcsumblibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CancelHold extends Activity{


    private EditText username;
    private EditText password;
    private Button submit;
    private Database myDB;
    private TextView deleteRe;
    private ArrayList<Resrvation> r;
    private Button removeButton;
    private EditText remove;
    private TextView justhide;
    private boolean wrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        deleteRe = (TextView) findViewById(R.id.deleteRe);
        removeButton = (Button) findViewById(R.id.removeButton);
        remove = (EditText) findViewById(R.id.remove);
        justhide = (TextView) findViewById(R.id.justhide);

        myDB = new Database(this);
        wrong = false;

        removeButton.setVisibility(View.INVISIBLE);
        remove.setVisibility(View.INVISIBLE);
        justhide.setVisibility(View.INVISIBLE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = username.getText().toString();
                String p = password.getText().toString();
                Log.d("CancilHold", "clicked");

                if (myDB.confirmUser(u, p)) {

                    StringBuilder m = new StringBuilder();
                    r = myDB.getReservations(u);
                    Log.d("CancilHold", "Info was okay");

                    if(r.isEmpty()){

                        Log.d("CancilHold", "No books");
                        AlertDialog alertDialog = new AlertDialog.Builder(CancelHold.this).create();
                        alertDialog.setTitle("");
                        alertDialog.setMessage("There is no books reserve");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CancelHold.this, MainActivity.class));
                            }
                        });
                        alertDialog.show();
                    }

                    for (int i = 0; i < r.size(); i++) {
                        m.append(Integer.toString(i + 1) + ") Book: " + r.get(i).getBook() + " Pickup: " + r.get(i).getPickupTime() + " Return: " + r.get(i).getReturnTime() + " Reservation Number: " + r.get(i).getReservationNum() + "\n\n");
                    }

                    deleteRe.setText(m.toString());
                    removeButton.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
                    justhide.setVisibility(View.VISIBLE);
                    Log.d("CancilHold", "Everything is good");
                } else {

                    if (wrong) {
                        AlertDialog alertDialog = new AlertDialog.Builder(CancelHold.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Number of tries exceeded");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CancelHold.this, MainActivity.class));
                            }
                        });

                        alertDialog.show();
                        Log.d("CancilHold", "wrong 2");
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(CancelHold.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Informaiton was incorrent");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        wrong = true;
                        alertDialog.show();
                        Log.d("CancilHold", "wrong 1");
                    }
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder( CancelHold.this );
                builder1.setMessage("Are you sure you want to cancel?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                myDB.deleteReservation( r.get( Integer.parseInt( remove.getText().toString() ) -1));

                                startActivity(new Intent(CancelHold.this, MainActivity.class));
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}
