package com.zakynoor.pertemuan6dan7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BantuDatabase extends SQLiteOpenHelper {


    private static final String DataBase_Buah = "db_buah";
    private static final String Tabel_Buah="tabel_buah";
    private static String KODE = "kode";
    private static final String nama_buah = "nm_buah";


    public BantuDatabase(@Nullable Context context) {
        super(context, DataBase_Buah, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String nama_tabel="create table "+ Tabel_Buah + "(" + KODE + " integer primary key autoincrement," + nama_buah + " text)";
        db.execSQL(nama_tabel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public boolean tambahData(String namabuah){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(nama_buah,namabuah);

        long hasil = db.insert(Tabel_Buah,null,contentValues);
        return hasil != -1;
    }

    public Cursor tampilBuah(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="select * from "+Tabel_Buah;
        Cursor cursor=db.rawQuery(sql,null);
        return cursor;
    }
    public boolean hapusRecord(int id){
        MainActivity.editText.setText(""+id);
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Tabel_Buah, KODE+"="+id, null)>0;
    }
}
