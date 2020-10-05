package com.example.a201606100110041.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsManager";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_CONTACT_NO = "ContactNo";
    private static final String TABLE_CONTACTS = "tblContact";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT," + KEY_CONTACT_NO + " TEXT" + ")";

        db.execSQL(Create_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS );
        onCreate(sqLiteDatabase);
    }

    //Add Contacts
    public void AddContacts(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_CONTACT_NO,contact.getContactNo());

        db.insert(TABLE_CONTACTS,null,values);
    }

    //List Contacts
    public ArrayList<String> GetAllContacts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> ContactList = new ArrayList<String>();
        String Query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(Query,null);
        if(cursor.moveToFirst())
        {
            do {

                ContactList.add(cursor.getString(0) + "," + cursor.getString(1)
                        + "," + cursor.getString(2));

            }while (cursor.moveToNext());
        }
        return ContactList;
    }

    //Update Contact
    public int UpdateContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_CONTACT_NO,contact.getContactNo());

        return db.update(TABLE_CONTACTS,values,KEY_ID + " =?",new String[]{String.valueOf(contact.getID())});
    }

    //Delete Contact
    public void DeleteContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " =?", new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    //Get Details
    Contact getContactDetails(int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS,new String[]{KEY_ID,KEY_NAME,KEY_CONTACT_NO},KEY_ID + " =?",
                new String[]{String.valueOf(ID)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2));
        return contact;
    }
}
