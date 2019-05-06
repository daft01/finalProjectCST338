package com.example.bookrentalsystemforcsumblibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Month;
import java.util.ArrayList;

public class Database{

    public static final String DBname = "Library";
    public static final int DBversion = 1;

    public static final String TableNameUsers = "Users";  // making the users table

    public static final String Username = "Username";
    public static final int UsernameCol = 0;

    public static final String Password = "Password";
    public static final int PasswordCol = 1;

    public static final String TransactionType = "TransactionType";
    public static final int TransactionTypeCol = 2;

    public static final String Pickup = "Pickup";
    public static final int PickupCol = 3;

    public static final String Return = "Return";
    public static final int ReturnCol = 4;

    public static final String Book = "Book";
    public static final int BookCol = 5;

    public static final String TotalFee = "TotalFee";
    public static final int TotalFEe = 6;

    public static final String CreateUsersTable =
            "CREATE TABLE " + TableNameUsers + " (" +
                    Username         + " TEXT," +
                    Password         + " TEXT," +
                    TransactionType  + " TEXT," +
                    Pickup           + " TEXT," +
                    Return           + " TEXT," +
                    Book             + " TEXT," +
                    TotalFee         + " REAL );";

    public static final String TableNameBooks = "Books"; // Making books table;

    public static final String Title = "Title";
    public static final int TitleCol = 0;

    public static final String Author = "Author";
    public static final int AuthorCol = 1;

    public static final String Fee = "Fee";
    public static final int FeeCol = 2;

    public static final String CreateBooksTable =
            "CREATE TABLE " + TableNameBooks + " (" +
                    Title            + " TEXT," +
                    Author           + " TEXT," +
                    Fee              + " REAL," +
                    Pickup           + " TEXT," +
                    Return           + " TEXT );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TableNameUsers;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CreateUsersTable);
            db.execSQL(CreateBooksTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(Database.DROP_TABLE);
            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public Database(Context context) {
        dbHelper = new DBHelper(context, DBname, null, DBversion);

        insertUser("alice5", "csumb100");
        insertUser("brian7", "123abc");
        insertUser("chris12", "CHRIS12");

        insertBook("Hot Java", "S. Narayanan", .05);
        insertBook("Fun Java", "Y. Byun", 1.00);
        insertBook("Algorithm for Java", "K. Alice", 0.25);
    }

    public boolean insertUser(String username, String password) {

        String where = Username + "= ?";
       String[] whereArgs = { username };

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TableNameUsers, null,
                where, whereArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            return false;
        }

        ContentValues cv = new ContentValues();
        Calendar now = Calendar.getInstance();

        cv.put(Username, username);
        cv.put(Password, password);
        cv.put(TransactionType, "New User");
        cv.put(Pickup, "");
        cv.put(Return, "");
        cv.put(Book, "");
        cv.put(TotalFee, 0);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TableNameUsers, null, cv);

        // close db
        if (db != null)
            db.close();

        return true;
    }

    boolean insertBook(String title, String author, double fee){
        ContentValues cv = new ContentValues();
        Calendar now = Calendar.getInstance();

        cv.put(Title, title);
        cv.put(Author, author);
        cv.put(Fee, fee);
        cv.put(Pickup, "");
        cv.put(Return, "");

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TableNameBooks, null, cv);

        // close db
        if (db != null)
            db.close();

        return true;
    }

    public ArrayList<String> getBooks(String pickupTime, String returnTime) {

         db = dbHelper.getReadableDatabase();
         Cursor cursor = db.query(TableNameBooks, null, null, null, null, null, null);

         ArrayList<String> books = new ArrayList<String>();

        while (cursor.moveToNext()) {
            books.add(getBookFromCursor(cursor, pickupTime, returnTime));
         }

        // close db
        if (db != null)
            db.close();

        if( books.size() == 0)
            return null;

        return books;
     }

    private static String getBookFromCursor(Cursor cursor, String pickupTime, String returnTime) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                String p = cursor.getString(PickupCol);
                String r = cursor.getString(ReturnCol);

                if( checkDays(p, pickupTime) < 0 ) /////////////////////////////////////////////////////// check if the day is not in the middle

//                Book task = new Book(
//                        cursor.getString(TITLE_COL),
//                        cursor.getDouble(FEE_COL));
//                return task;

                return "";
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public static int getMonth(String str){
        return Integer.parseInt(str.substring(0, 2));
    }

    public static int getDay(String str){
        return Integer.parseInt(str.substring(3, 5));
    }

    public static int getYear(String str){
        return Integer.parseInt(str.substring(3, 5));
    }

    public static int getHour(String str){
        return Integer.parseInt(str.substring(11, 13));
    }

    public static int getMin(String str){
        return Integer.parseInt(str.substring(14, 16));
    }

    public static String getZone(String str){
        return str.substring(17, 19);
    }

    public static int checkDays(String pickupTime, String returnTime){
        int DaysinMonth[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

                                                            ////////////// fix the years
        int days = 0;
        int loop = getMonth(returnTime) - getMonth(pickupTime);

        if(loop < 0)
            return -1;

        for(int i=0; i<loop; i++){

            days += DaysinMonth[ (getMonth(pickupTime) + i)%12 ];
        }

        days -= getDay(pickupTime);
        days += getDay(returnTime);

        double pHour = getHour(pickupTime) + (getMin(pickupTime) * .01);
        double rHour = getHour(returnTime) + (getMin(returnTime) * .01);

        if(getZone(pickupTime).equals("pm") || getZone(pickupTime).equals("PM"))
            pHour += 12;

        if(getZone(returnTime).equals("pm") || getZone(returnTime).equals("PM"))
            pHour += 12;

        if(pHour > rHour)
            days++;

        return days;
    }
//
//    public Boolean updateBook(String title, Double fee) {
//        ContentValues cv = new ContentValues();
//
//        cv.put(FEE, fee);
//
//        String where = TITLE + "= ?";
//        String[] whereArgs = { title };
//
//        db = dbHelper.getWritableDatabase();
//        int rowCount = db.update(TABLE_NAME, cv,
//                where, whereArgs);
//
//        if (db != null)
//            db.close();
//
//        Log.d("damn", Integer.toString(rowCount));
//
//        if(rowCount == 0) {
//            insert(new Book(title, fee) );
//            return false;
//        }
//
//        return true;
//    }

}
