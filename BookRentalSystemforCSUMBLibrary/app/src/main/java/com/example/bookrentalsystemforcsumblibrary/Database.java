package com.example.bookrentalsystemforcsumblibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class Database{

    public static final String DBname = "Library";
    public static final int DBversion = 1;

    public static final String TableNameUsers = "Users";

    public static final String Username = "Username";
    public static final int UsernameCol = 0;

    public static final String Password = "Password";
    public static final int PasswordCol = 1;

    public static final String CreateUsersTable =
            "CREATE TABLE " + TableNameUsers + " (" +
                    Username     + " TEXT," +
                    Password        + " TEXT );";

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
    }

    public long insert(String username, String password) {
        ContentValues cv = new ContentValues();
        cv.put(Username, username);
        cv.put(Password, password);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TableNameUsers, null, cv);

        // close db
        if (db != null)
            db.close();

        return rowID;
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
