package com.example.bookrentalsystemforcsumblibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import java.util.ArrayList;

public class PlaceHold extends AppCompatActivity {

    private EditText pickupMonth, pickupDay, pickupYear, pickupHour, pickupMin, pickupZone;
    private EditText returnMonth, returnDay, returnYear, returnHour, returnMin, returnZone;
    private Button searchTime;
    private Database myDB;
    private String pickupTime, returnTime;
    private TextView bookResults;
    private TextView bookTitleTextView;
    private EditText bookTitle;
    private Button rent;
    private TextView usernameTextView, passwordTextView;
    private EditText username, password;
    private Button submit;
    private LinearLayout confirmLayout;
    private TextView confirmMessage;
    private Button confirmButton;

    private boolean userInfo;
    private ArrayList<String> books;
    private String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        myDB = new Database(this);

        pickupMonth = (EditText) findViewById(R.id.pickupMonth);
        pickupDay = (EditText) findViewById(R.id.pickupDay);
        pickupYear = (EditText) findViewById(R.id.pickupYear);
        pickupHour = (EditText) findViewById(R.id.pickupHour);
        pickupMin = (EditText) findViewById(R.id.pickupMin);
        pickupZone = (EditText) findViewById(R.id.pickupZone);

        returnMonth = (EditText) findViewById(R.id.returnMonth) ;
        returnDay = (EditText) findViewById(R.id.returnDay) ;
        returnYear = (EditText) findViewById(R.id.returnYear);
        returnHour = (EditText) findViewById(R.id.returnHour);
        returnMin = (EditText) findViewById(R.id.returnMin);
        returnZone = (EditText) findViewById(R.id.returnZone);

        searchTime = (Button) findViewById(R.id.searchTimeButton);
        bookResults = (TextView) findViewById(R.id.bookResult);

        bookTitleTextView = (TextView) findViewById(R.id.bookTitleTextView);
        bookTitle = (EditText) findViewById(R.id.bookTitle);
        rent = (Button) findViewById(R.id.rentButton);

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submitButton);

        confirmLayout = (LinearLayout) findViewById(R.id.confirmLayout);
        confirmMessage = (TextView) findViewById(R.id.confirmMessage);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        bookTitleTextView.setVisibility(View.INVISIBLE);
        bookTitle.setVisibility(View.INVISIBLE);
        usernameTextView.setVisibility(View.INVISIBLE);
        passwordTextView.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        confirmLayout.setVisibility(View.INVISIBLE);

        userInfo = false;

        searchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickupTime = pickupMonth.getText().toString() + "/"  + pickupDay.getText().toString() + "/" + pickupYear.getText().toString() + " " + pickupHour.getText().toString() + ":" + pickupMin.getText().toString() + " (" + pickupZone.getText().toString() + ")";
                returnTime = returnMonth.getText().toString() + "/"  + returnDay.getText().toString() + "/" + returnYear.getText().toString() + " " + returnHour.getText().toString() + ":" + returnMin.getText().toString() + " (" + returnZone.getText().toString() + ")";

                if( myDB.checkDays(pickupTime, returnTime) > 7 || myDB.checkDays(pickupTime, returnTime) < 0){

                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("The books can’t be reserved due to rental restriction");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    alertDialog.show();
                    return;
                }

                books = myDB.getBooks(pickupTime, pickupTime);

                StringBuilder str = new StringBuilder();
                boolean empty = true;
                for(int i=0; i<books.size(); i++){

                    if( !books.get(i).isEmpty() ){
                        str.append(books.get(i) + "\n");
                        empty = false;
                    }
                }

                if(empty){
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("There is no book available for the dates/hours");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "EXIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PlaceHold.this, MainActivity.class));
                        }
                    });

                    alertDialog.show();
                    return;
                }

                bookResults.setText(str);

                bookTitleTextView.setVisibility(View.VISIBLE);
                bookTitle.setVisibility(View.VISIBLE);
                rent.setVisibility(View.VISIBLE);
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b = bookTitle.getText().toString();

                if( !books.contains(b) ){
                    Toast.makeText(getApplication(), "Book is not in the list", Toast.LENGTH_LONG).show();
                    return;
                }

                usernameTextView.setVisibility(View.VISIBLE);
                passwordTextView.setVisibility(View.VISIBLE);
                username.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = username.getText().toString();
                String p = password.getText().toString();

                if( !myDB.confirmUser(u , p) ){

                    if(userInfo){

                        AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Number of tries exceeded");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(PlaceHold.this, MainActivity.class));
                            }
                        });

                        alertDialog.show();
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceHold.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Wrong username/password");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    alertDialog.show();

                    userInfo = true;
                    return;
                }

                String reservationInfo = myDB.createReservation(u, b, pickupTime, returnTime);

                confirmLayout.setVisibility(View.VISIBLE);
                confirmMessage.setText(reservationInfo);
                confirmMessage.setGravity(Gravity.CENTER);

                submit.setVisibility(View.INVISIBLE);
                rent.setVisibility(View.INVISIBLE);
                searchTime.setVisibility(View.INVISIBLE);

            }
        });
    }


}
