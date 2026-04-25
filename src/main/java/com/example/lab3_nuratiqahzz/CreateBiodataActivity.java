package com.example.lab3_nuratiqahzz;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class CreateBiodataActivity extends AppCompatActivity {

    EditText id, name, dob, gender, address;
    Button save, back;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_biodata);

        dbHelper = new DataHelper(this);

        id = findViewById(R.id.editTextId);
        name = findViewById(R.id.editTextName);
        dob = findViewById(R.id.editTextDob);
        gender = findViewById(R.id.editTextGender);
        address = findViewById(R.id.editTextAddress);

        save = findViewById(R.id.buttonSave);
        back = findViewById(R.id.buttonBack);

        save.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.execSQL("INSERT INTO biodata VALUES('" +
                    id.getText().toString() + "','" +
                    name.getText().toString() + "','" +
                    dob.getText().toString() + "','" +
                    gender.getText().toString() + "','" +
                    address.getText().toString() + "')");

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            MainActivity.ma.RefreshList();
            finish();
        });

        back.setOnClickListener(v -> finish());
    }
}