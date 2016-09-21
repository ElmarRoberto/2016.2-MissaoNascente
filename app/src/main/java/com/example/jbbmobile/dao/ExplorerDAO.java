package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jbbmobile.model.Explorers;

import java.util.ArrayList;
import java.util.List;

public class ExplorerDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public ExplorerDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public void createExplorerTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS EXPLORER (nickname text unique, email text primary key not null, password text)");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE EXPLORER");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getExplorerData(Explorers explorer) {
        ContentValues data = new ContentValues();
        data.put("nickname", explorer.getNickname());
        data.put("email", explorer.getEmail());
        data.put("password", explorer.getPassword());
        return data;
    }

    public int insertExplorer(Explorers explorer) {

        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getExplorerData(explorer);
        try{

            insertReturn = (int) db.insert("EXPLORER", null, data);
        }catch (SQLiteConstraintException e){

        }

        return  insertReturn;
    }

    public Explorers findExplorer(Explorers explorer){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("EXPLORER",new String[] { "nickname" ,"email", "password"}, "email ='" + explorer.getEmail()+"'",null, null , null ,null);

        Explorers explorer1 = new Explorers();
        if(c.moveToFirst()){
            explorer1.setNickname(c.getString(c.getColumnIndex("nickname")));
            explorer1.setEmail(c.getString(c.getColumnIndex("email")));
            explorer1.setPassword(c.getString(c.getColumnIndex("password")));
        }
        c.close();
        return explorer1;
    }

    public Explorers findExplorerLogin(Explorers explorer){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("EXPLORER",new String[] { "nickname" ,"email", "password"}, "email ='" + explorer.getEmail()+"' AND password ='" + explorer.getPassword()+"'",null, null , null ,null);

        Explorers explorer1 = new Explorers();
        if(c.moveToFirst()){
            explorer1.setNickname(c.getString(c.getColumnIndex("nickname")));
            explorer1.setEmail(c.getString(c.getColumnIndex("email")));
            explorer1.setPassword(c.getString(c.getColumnIndex("password")));
        }
        c.close();
        return explorer1;
    }

    public void updateExplorer(Explorers explorer) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getExplorerData(explorer);

        String[] params = {explorer.getEmail().toString()};

        db.update("EXPLORER", data, "email = ?", params);
    }

    public void deleteExplorer(Explorers explorer){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {explorer.getEmail().toString()};
        db.delete("EXPLORER", "email = ?", params);

    }

    public List<Explorers> findExplorers() {
        String sql = "SELECT * FROM EXPLORER;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Explorers> explorers = new ArrayList<Explorers>();
        while (c.moveToNext()) {
            Explorers explorer = new Explorers();

            explorer.setNickname(c.getString(c.getColumnIndex("nickname")));
            explorer.setEmail(c.getString(c.getColumnIndex("email")));
            explorer.setPassword(c.getString(c.getColumnIndex("password")));


            explorers.add(explorer);
        }
        c.close();

        return explorers;
    }
}