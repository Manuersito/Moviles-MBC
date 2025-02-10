package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCocheActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSIONS = 100;

    private EditText etNombreCoche, etDescripcionCoche;
    private RatingBar ratingBar;
    private DatePicker datePicker;
    private ImageView imgCoche;
    private Button btnAddCoche, btnTomarFoto;
    private DBHelper dbHelper;
    private String currentPhotoPath;  // Ruta de la imagen capturada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coche);

        // Inicializar vistas
        etNombreCoche = findViewById(R.id.etNombreCoche);
        etDescripcionCoche = findViewById(R.id.etDescripcionCoche);
        ratingBar = findViewById(R.id.ratingBar);
        datePicker = findViewById(R.id.datePicker);
        imgCoche = findViewById(R.id.imgCoche);
        btnAddCoche = findViewById(R.id.btnAddCoche);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);

        // Inicializar DBHelper
        dbHelper = new DBHelper(this);

        // Verificar permisos
        checkPermissions();

        // Botón para tomar una foto
        btnTomarFoto.setOnClickListener(v -> abrirCamara());

        // Botón para agregar el coche
        btnAddCoche.setOnClickListener(v -> guardarCoche());
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_PERMISSIONS);
            }
        }
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Crear el archivo donde se guardará la imagen
            File photoFile = null;
            try {
                photoFile = crearArchivoImagen();
            } catch (IOException ex) {
                Toast.makeText(this, "Error al crear el archivo de imagen", Toast.LENGTH_SHORT).show();
                return;
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.myapplication.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "No se pudo crear el archivo de imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay aplicación de cámara disponible", Toast.LENGTH_SHORT).show();
        }
    }

    private File crearArchivoImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (storageDir != null && !storageDir.exists()) {
            storageDir.mkdirs();
        }

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();  // Guardar la ruta de la imagen
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()) {
                imgCoche.setImageURI(Uri.fromFile(imgFile));  // Mostrar imagen correctamente
            } else {
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Se requieren permisos para usar la cámara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarCoche() {
        String nombre = etNombreCoche.getText().toString();
        String descripcion = etDescripcionCoche.getText().toString();
        float valoracion = ratingBar.getRating();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        // Convertir la fecha a formato String
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date fecha = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(fecha);

        // Verificar si se tomó una foto
        if (currentPhotoPath == null || currentPhotoPath.isEmpty()) {
            Toast.makeText(this, "Por favor, toma una foto del coche", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo coche con la foto
        Coches nuevoCoche = new Coches(nombre, descripcion, currentPhotoPath, false, valoracion, "https://example.com", fechaString);

        // Insertar el coche en la base de datos
        long id = dbHelper.addCoche(nuevoCoche);

        if (id != -1) {
            Toast.makeText(this, "Coche agregado con éxito", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("coche", nuevoCoche);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Error al agregar coche", Toast.LENGTH_SHORT).show();
        }
    }
}
