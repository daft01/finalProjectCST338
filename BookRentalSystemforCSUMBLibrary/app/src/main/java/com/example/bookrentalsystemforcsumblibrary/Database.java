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
                    Fee              + " REAL );";

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

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TableNameBooks, null, cv);

        // close db
        if (db != null)
            db.close();

        return true;
    }

//    public Book getBook(String bookName) {
//        String where = TITLE + "= ?";
//        String[] whereArgs = { bookName };
//
//        db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, null,
//                where, whereArgs,
//                null, null, null);
//
//        ArrayList<Book> books = new ArrayList<Book>();
//
//        while (cursor.moveToNext()) {
//            books.add(getBookFromCursor(cursor));
//        }
//
//        // close db
//        if (db != null)
//            db.close();
//
//        if( books.size() == 0)
//            return null;
//
//        return books.get(0);
//    }
//
//    private static Book getBookFromCursor(Cursor cursor) {
//        if (cursor == null || cursor.getCount() == 0){
//            return null;
//        }
//        else {
//            try {
//                Book task = new Book(
//                        cursor.getString(TITLE_COL),
//                        cursor.getDouble(FEE_COL));
//                return task;
//            }
//            catch(Exception e) {
//                return null;
//            }
//        }
//    }
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
