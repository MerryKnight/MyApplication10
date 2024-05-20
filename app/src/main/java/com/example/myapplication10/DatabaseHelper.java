package com.example.myapplication10;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_SX = "sx";
    private static final String COLUMN_AGE = "age";
    private static final String PREFS_NAME = "db_prefs";
    private static final String PREFS_KEY_VERSION = "db_version";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, getNewVersion(context));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SURNAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_AGE + " TEXT, " +
                COLUMN_SX + " TEXT)";
        db.execSQL(createTable);
    }
    private static int getNewVersion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentVersion = prefs.getInt(PREFS_KEY_VERSION, 1);
        int newVersion = currentVersion + 1;
        prefs.edit().putInt(PREFS_KEY_VERSION, newVersion).apply();
        return newVersion;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Добавление нового контакта
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, contact.getName());
        cv.put(COLUMN_SURNAME, contact.getSurname());
        cv.put(COLUMN_PHONE, contact.getPhone());
        cv.put(COLUMN_AGE, contact.getAge());
        cv.put(COLUMN_SX, contact.getSx());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }

    // Удаление контакта по номеру телефона
    public boolean deleteContact(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_PHONE + " = ?", new String[]{phone});
        db.close();
        return result > 0;
    }

    // Поиск контакта по номеру телефона
    public Contact findContact(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME,
                        COLUMN_SURNAME, COLUMN_PHONE, COLUMN_AGE, COLUMN_SX},
                COLUMN_PHONE + " = ?", new String[]{phone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Contact contact = new Contact(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5));
            cursor.close();
            db.close();
            return contact;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    // Получение всех контактов
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    // Обновление контакта по старому номеру телефона
    public boolean updateContact(String oldPhone, String newName, String newSurname, String newPhone, String newAge, String newSx) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newName);
        cv.put(COLUMN_SURNAME, newSurname);
        cv.put(COLUMN_PHONE, newPhone);
        cv.put(COLUMN_AGE, newAge);
        cv.put(COLUMN_SX, newSx);
        int result = db.update(TABLE_NAME, cv, COLUMN_PHONE + " = ?", new String[]{oldPhone});
        db.close();
        return result > 0;
    }
}
