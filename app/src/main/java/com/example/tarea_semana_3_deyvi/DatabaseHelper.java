package com.example.tarea_semana_3_deyvi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "nuevo_horizonte.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_ACTIVIDADES = "actividades";
    public static final String COL_ID = "id";
    public static final String COL_DIA = "dia";
    public static final String COL_HORA = "hora";
    public static final String COL_ACTIVIDAD = "actividad";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ACTIVIDADES + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DIA + " TEXT NOT NULL, " +
                COL_HORA + " TEXT NOT NULL, " +
                COL_ACTIVIDAD + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVIDADES);
        onCreate(db);
    }

    public long registrarActividad(String dia, String hora, String actividad) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DIA, dia);
        values.put(COL_HORA, hora);
        values.put(COL_ACTIVIDAD, actividad);
        return db.insert(TABLE_ACTIVIDADES, null, values);
    }

    public Cursor consultarActividades() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_ACTIVIDADES, null, null, null, null, null, COL_ID + " ASC");
    }

    public int actualizarActividad(int id, String dia, String hora, String actividad) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DIA, dia);
        values.put(COL_HORA, hora);
        values.put(COL_ACTIVIDAD, actividad);
        return db.update(TABLE_ACTIVIDADES, values, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int eliminarActividad(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_ACTIVIDADES, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}
