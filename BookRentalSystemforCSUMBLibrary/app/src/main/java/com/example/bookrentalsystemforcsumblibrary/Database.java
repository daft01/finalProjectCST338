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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

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

    public static final String CreateUsersTable =
            "CREATE TABLE " + TableNameUsers + " (" +
                    Username         + " TEXT," +
                    Password         + " TEXT," +
                    TransactionType  + " TEXT );";

    public static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + TableNameUsers;

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

    public static final String DROP_BOOK_TABLE =
            "DROP TABLE IF EXISTS " + TableNameBooks;

    public static final String TableNameReservation = "Reservation";

    public static final String UsernameReservation = "Username";
    public static final int UsernameReservationCol = 0;

    public static final String BookReservation = "Book";
    public static final int BookReservationCol = 1;

    public static final String pickupReservation = "Pickup";
    public static final int pickupReservationCol = 2;

    public static final String returnReservation = "Return";
    public static final int returnReservationCol = 3;

    public static final String reservationNumber = "reservationNumber";
    public static final int reservationNumberCol = 4;

    public static final String TotalReservation = "Total";
    public static final int TotalReservationCol = 5;

    public static final String CreateReservationTable =
            "CREATE TABLE " + TableNameReservation + " (" +
                    UsernameReservation            + " TEXT," +
                    BookReservation           + " TEXT," +
                    pickupReservation           + " TEXT," +
                    returnReservation           + " TEXT," +
                    reservationNumber          + " INT," +
                    TotalReservation              + " REAL );";


    public static final String DROP_RESERVATION_TABLE =
            "DROP TABLE IF EXISTS " + TableNameReservation;

    public static final String TableNameManageSystem = "ManageSystem";

    public static final String TransactionSystemType  = "TransactionType";
    public static final int TransactionCol = 0;

    public static final String UsernameManageSystem  = "Username";
    public static final int UsernameManageSystemCol = 1;

    public static final String BookTitle= "Book";
    public static final int BookTitleCol = 2;

    public static final String pickupManageSystem = "Pickup";
    public static final int pickupManageSystemCol = 3;

    public static final String returnManageSystem = "Return";
    public static final int returnManageSystemCol = 4;

    public static final String reservationManageSystem = "reservationNumber";
    public static final int reservationManageSystemCol = 5;

    public static final String TransactionTime = "TransactionTime";
    public static final int TransactionTimeCol = 6;

    public static final String CreateManageSystemTable =
            "CREATE TABLE " + TableNameManageSystem + " (" +
                    TransactionSystemType            + " TEXT," +
                    UsernameManageSystem           + " TEXT," +
                    BookTitle           + " TEXT," +
                    pickupManageSystem           + " TEXT," +
                    returnManageSystem          + " TEXT," +
                    reservationManageSystem          + " TEXT," +
                    TransactionTime              + " TEXT );";


    public static final String DROP_ManageSystem_TABLE =
            "DROP TABLE IF EXISTS " + TableNameManageSystem;

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
            db.execSQL(CreateReservationTable);
            db.execSQL(CreateManageSystemTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(Database.DROP_USER_TABLE);
            db.execSQL(Database.DROP_BOOK_TABLE);
            db.execSQL(Database.DROP_RESERVATION_TABLE);
            db.execSQL(Database.DROP_ManageSystem_TABLE);
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

//    public static final String CreateManageSystemTable =
//            "CREATE TABLE " + TableNameManageSystem + " (" +
//                    TransactionSystemType            + " TEXT," +
//                    UsernameManageSystem           + " TEXT," +
//                    BookTitle           + " TEXT," +
//                    pickupManageSystem           + " TEXT," +
//                    returnManageSystem          + " TEXT," +
//                    reservationManageSystem          + " TEXT," +
//                    TransactionTime              + " TEXT );";

    public void insertManageSystemUser(String username){

        ContentValues cv = new ContentValues();
        DateFormat df = new SimpleDateFormat("MM d yyyy, HH:mm a");
        String date = df.format(Calendar.getInstance().getTime());

        cv.put(TransactionType, "New account");
        cv.put(UsernameManageSystem, username);
        cv.put(BookTitle, "");
        cv.put(pickupManageSystem, "");
        cv.put(returnManageSystem, "");
        cv.put(reservationManageSystem, "");
        cv.put(TransactionTime, date);

        db = dbHelper.getWritableDatabase();
        db.insert(TableNameUsers, null, cv);

        // close db
        if (db != null)
            db.close();
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

        insertManageSystemUser(username);
        ContentValues cv = new ContentValues();
        Calendar now = Calendar.getInstance();

        cv.put(Username, username);
        cv.put(Password, password);
        cv.put(TransactionType, "New User");


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

    public ArrayList<String> getBooks(String pickupTime, String returnTime) {

         db = dbHelper.getReadableDatabase();

         Cursor cursor = db.query(TableNameReservation, null, null, null, null, null, null);

         ArrayList<String> taken = new ArrayList<String>();

         while(cursor.moveToNext()){
             taken.add(getTaken(cursor, pickupTime, returnTime));
         }

         cursor = db.query(TableNameBooks, null, null, null, null, null, null);

         ArrayList<String> books = new ArrayList<String>();

        while (cursor.moveToNext()) {
            books.add(getBookFromCursor(cursor, pickupTime, returnTime, taken));
         }

        // close db
        if (db != null)
            db.close();

        if( books.size() == 0)
            return null;

        return books;
     }

     private static String getTaken(Cursor cursor, String pickupTime, String returnTime) {
         if (cursor == null || cursor.getCount() == 0){
             return null;
         }
         else {
             try {
                String p = cursor.getString(pickupReservationCol);
                String r = cursor.getString(returnReservationCol);

                Log.d("pickup", p);
                Log.d("return", r);

                if( compareDays(r, pickupTime) == 1  || compareDays(returnTime, p) ==  1)
                    return cursor.getString(TitleCol);
                 return "";
             }
             catch(Exception e) {
                 return null;
             }
         }
     }

    private static String getBookFromCursor(Cursor cursor, String pickupTime, String returnTime, ArrayList<String> taken) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                String title = cursor.getString(TitleCol);
                String author = cursor.getString(AuthorCol);
                String fee = Double.toString( cursor.getDouble(FeeCol) );

                if( !taken.contains(title) )
                    return title;
                    //return "'" + title + "'" + " by " + author + " $" + fee;
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

    public static int compareDays(String p, String r){

        int temp = checkNum( getYear(p),  getYear(r) );
        if(temp != 0) return temp;

        temp = checkNum( getMonth(p), getMonth(r) );
        if(temp != 0) return temp;

        temp = checkNum( getDay(p), getDay(r) );
        if(temp != 0) return temp;

        int pHour = getHour(p), rHour = getHour(r);

        if(getZone(p).equals("PM") || getZone(p).equals("pm"))
            pHour += 12;

        if(getZone(r).equals("PM") || getZone(r).equals("pm"))
            rHour += 12;

        temp = checkNum( getHour(p), getHour(r) );
        if(temp != 0) return temp;

        temp = checkNum( getMin(p), getMin(r) );
        return temp;
    }

    public static int checkNum(int p, int r){

        if(p < r)
            return -1;

        if(p == r)
            return 0;

        return 1;
    }

    public boolean confirmUser(String u , String p){

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TableNameUsers, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            boolean temp = getUserFromCursor(cursor, u, p);

            if(temp) {
                return true;
            }
        }

        if (db != null)
            db.close();

        return false;
    }

    private static boolean getUserFromCursor(Cursor cursor, String u, String p) {
        if (cursor == null || cursor.getCount() == 0){
            return false;
        }
        else {
            try {
                String user = cursor.getString(UsernameCol);
                String password = cursor.getString(PasswordCol);

                if( user.equals(u) && password.equals(p))
                    return true;

                return false;
            }
            catch(Exception e) {
                return false;
            }
        }
    }

    public String createReservation(String u, String b, String pickupTime, String returnTime){

        ContentValues cv = new ContentValues();
        Calendar now = Calendar.getInstance();

        Random random = new Random();
        int num = random.nextInt(90000) + 10000;
        double f = getFee(b);
        DateFormat df = new SimpleDateFormat("MM d yyyy, HH:mm a");
        String date = df.format(Calendar.getInstance().getTime());

        cv.put(UsernameReservation, u);
        cv.put(BookReservation, b);
        cv.put(pickupReservation, pickupTime);
        cv.put(returnReservation, returnTime);
        cv.put(reservationNumber, num);
        cv.put(Fee, f);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TableNameBooks, null, cv);

        // close db
        if (db != null)
            db.close();

        return "Transaction Type: Hold" +
               "\nUsername: " + u +
               "\nPickup time: " + pickupTime +
               "\nReturn Time: " + returnTime +
               "\nBook Title: " + b +
               "\nReservation Number: " + f +
               "\nCurrent Date/Time: " + date;
    }

    private double getFee(String b){

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TableNameUsers, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            double temp = getFeeFromCursor(cursor, b);

            if(temp != -1)
                return temp;
        }

        if (db != null)
            db.close();

        return -1;

    }

    private static double getFeeFromCursor(Cursor cursor, String b) {
        if (cursor == null || cursor.getCount() == 0){
            return -1;
        }
        else {
            try {
                String title = cursor.getString(TitleCol);

                if( title.equals(b) )
                    return cursor.getDouble(FeeCol);

                return -1;
            }
            catch(Exception e) {
                return -1;
            }
        }
    }
    public String manageSystem(){

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TableNameManageSystem, null, null, null, null, null, null);

        StringBuilder temp = new StringBuilder();

        while (cursor.moveToNext()) {
            String t = getManageSystemFromCursor(cursor);

            if(!t.isEmpty()) {
                temp.append(t);
            }
        }

        if (db != null)
            db.close();

        return temp.toString();
    }

    private static String getManageSystemFromCursor(Cursor cursor){

        if (cursor == null || cursor.getCount() == 0){
            return "";
        }
        else {
            try {
                String transaction = cursor.getString(TransactionCol);

                if(transaction.equals("New account") ){
                    return "Trac"
                }
                if( title.equals(b) )
                    return cursor.getDouble(FeeCol);

                return "";
            }
            catch(Exception e) {
                return "";
            }
        }

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
