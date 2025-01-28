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

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "cochesDB";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla
    private static final String TABLE_COCHES = "coches";

    // Columnas de la tabla
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

    // Crear la tabla coches
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_COCHES = "CREATE TABLE " + TABLE_COCHES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_DESCRIPCION + " TEXT,"
                + COLUMN_PORTADA_RES_ID + " INTEGER,"
                + COLUMN_ENCONTRADO + " INTEGER,"
                + COLUMN_VALORACION + " REAL,"
                + COLUMN_WEB + " TEXT,"
                + COLUMN_FECHA_ENCONTRADO + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_COCHES);
    }

    // Actualizar la base de datos si es necesario
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCHES);
        onCreate(db);
    }

    // Método para insertar coche
    public long addCoche(Coches coche) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, coche.getNombre());
        values.put(COLUMN_DESCRIPCION, coche.getDescripcion());
        values.put(COLUMN_PORTADA_RES_ID, coche.getPortadaResId());
        values.put(COLUMN_ENCONTRADO, coche.getEncontrado() ? 1 : 0);
        values.put(COLUMN_VALORACION, coche.getValoracion());
        values.put(COLUMN_WEB, coche.getWeb());
        values.put(COLUMN_FECHA_ENCONTRADO, coche.getFechaEncontrado());

        long id = db.insert(TABLE_COCHES, null, values);
        db.close();
        return id;
    }

    // Obtener todos los coches de la base de datos
    public List<Coches> getAllCoches() {
        List<Coches> cochesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COCHES, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
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
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return cochesList;
    }

    // Obtener un coche por su ID
    @SuppressLint("Range")
    public Coches getCocheById(long cocheId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COCHES, null, "id = ?", new String[]{String.valueOf(cocheId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Coches coche = new Coches();
            coche.setId(cursor.getInt(cursor.getColumnIndex("id")));
            coche.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            coche.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
            coche.setWeb(cursor.getString(cursor.getColumnIndex("web")));
            coche.setFechaEncontrado(cursor.getString(cursor.getColumnIndex("fecha_encontrado")));
            coche.setValoracion(cursor.getFloat(cursor.getColumnIndex("valoracion")));
            cursor.close();
            return coche;
        }
        return null;
    }


    // Actualizar coche
    public int updateCoche(Coches coche) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, coche.getNombre());
        values.put(COLUMN_DESCRIPCION, coche.getDescripcion());
        values.put(COLUMN_PORTADA_RES_ID, coche.getPortadaResId());
        values.put(COLUMN_ENCONTRADO, coche.getEncontrado() ? 1 : 0);
        values.put(COLUMN_VALORACION, coche.getValoracion());
        values.put(COLUMN_WEB, coche.getWeb());
        values.put(COLUMN_FECHA_ENCONTRADO, coche.getFechaEncontrado());

        int rowsAffected = db.update(TABLE_COCHES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(coche.getId())});
        db.close();
        return rowsAffected;
    }

    // Eliminar coche
    public void deleteCoche(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COCHES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
