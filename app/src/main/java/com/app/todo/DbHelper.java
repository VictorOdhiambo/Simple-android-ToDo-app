package com.app.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private String DATABASE_NAME;
    private int version;

    public DbHelper(@Nullable Context context, @Nullable String DATABASE_NAME,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, version);

        this.context = context;
        this.DATABASE_NAME = DATABASE_NAME;
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todos " +
                "(icon INTEGER," +
                "title TEXT PRIMARY KEY," +
                "text TEXT," +
                "period TEXT," +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todos");

        onCreate(db);
    }

    public boolean addToDo(int icon, String title, String text, String period, String time){
        ContentValues values = new ContentValues(4);
        values.put("icon", icon);
        values.put("title", title);
        values.put("text", text);
        values.put("period", period);
        values.put("time", time);

        getWritableDatabase().insert("todos","icon", values);

        return true;
    }

    public Cursor retrieveTodos(){
        Cursor cursor = getReadableDatabase().rawQuery("select * from todos", null);
        return cursor;
    }

    public boolean deleteToDo(String title){
        getWritableDatabase().delete("todos", "title=?", new String[]{String.valueOf(title)});
        return true;
    }

    public boolean updateTodo(String title, String text, String date, String time){
        ContentValues values = new ContentValues();
        values.put("text", text);
        values.put("date", date);

        getWritableDatabase().execSQL("UPDATE todos SET text='"+text+"' WHERE title = '"+ title+"'");
        getWritableDatabase().execSQL("UPDATE todos SET period='"+date+"' WHERE title = '"+ title+"'");
        getWritableDatabase().execSQL("UPDATE todos SET time='"+time+"' WHERE title = '"+ title+"'");

//        getWritableDatabase().update("todos", values, "title=?", new String[]{String.valueOf(title)});
        return true;
    }
}
