package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coches.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_COCHES = "coches";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_PORTADA_RES_ID = "portadaResId";
    private static final String COLUMN_ENCONTRADO = "encontrado";
    private static final String COLUMN_VALORACION = "valoracion";
    private static final String COLUMN_WEB = "web";
    private static final String COLUMN_FECHA_ENCONTRADO = "fechaEncontrado";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_COCHES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRE + " TEXT, "
                + COLUMN_DESCRIPCION + " TEXT, "
                + COLUMN_PORTADA_RES_ID + " INTEGER, "
                + COLUMN_ENCONTRADO + " INTEGER, "
                + COLUMN_VALORACION + " REAL, "
                + COLUMN_WEB + " TEXT, "
                + COLUMN_FECHA_ENCONTRADO + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_COCHES + " ADD COLUMN nueva_columna TEXT DEFAULT ''");
        }
    }

    public long addCoche(Coches coche) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOMBRE, coche.getNombre());
            values.put(COLUMN_DESCRIPCION, coche.getDescripcion());
            values.put(COLUMN_PORTADA_RES_ID, coche.getPortadaResId());
            values.put(COLUMN_ENCONTRADO, booleanToInt(coche.getEncontrado()));
            values.put(COLUMN_VALORACION, coche.getValoracion());
            values.put(COLUMN_WEB, coche.getWeb());
            values.put(COLUMN_FECHA_ENCONTRADO, coche.getFechaEncontrado());

            return db.insert(TABLE_COCHES, null, values);
        } finally {
            db.close();
        }
    }

    public List<Coches> getAllCoches() {
        List<Coches> cochesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COCHES, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") Coches coche = new Coches(
                            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_PORTADA_RES_ID)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_ENCONTRADO)) == 1,
                            cursor.getFloat(cursor.getColumnIndex(COLUMN_VALORACION)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_WEB)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_ENCONTRADO))
                    );
                    cochesList.add(coche);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return cochesList;
    }

    @SuppressLint("Range")
    public Coches getCocheById(long cocheId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COCHES, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(cocheId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                Coches coche = new Coches(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_PORTADA_RES_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ENCONTRADO)) == 1,
                        cursor.getFloat(cursor.getColumnIndex(COLUMN_VALORACION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_WEB)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_ENCONTRADO))
                );
                return coche;
            } finally {
                cursor.close();
            }
        }
        db.close();
        return null;
    }

    public int updateCoche(Coches coche) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOMBRE, coche.getNombre());
            values.put(COLUMN_DESCRIPCION, coche.getDescripcion());
            values.put(COLUMN_PORTADA_RES_ID, coche.getPortadaResId());
            values.put(COLUMN_ENCONTRADO, booleanToInt(coche.getEncontrado()));
            values.put(COLUMN_VALORACION, coche.getValoracion());
            values.put(COLUMN_WEB, coche.getWeb());
            values.put(COLUMN_FECHA_ENCONTRADO, coche.getFechaEncontrado());

            return db.update(TABLE_COCHES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(coche.getId())});
        } finally {
            db.close();
        }
    }

    public void deleteCoche(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_COCHES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            db.close();
        }
    }

    private int booleanToInt(boolean value) {
        return value ? 1 : 0;
    }
}
