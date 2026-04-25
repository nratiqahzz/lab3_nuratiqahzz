package com.example.lab3_nuratiqahzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBiodataActivity extends AppCompatActivity {

    EditText id, name, dob, gender, address;
    Button update, back;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_biodata);

        dbHelper = new DataHelper(this);

        id = findViewById(R.id.editTextId);
        name = findViewById(R.id.editTextName);
        dob = findViewById(R.id.editTextDob);
        gender = findViewById(R.id.editTextGender);
        address = findViewById(R.id.editTextAddress);

        update = findViewById(R.id.buttonUpdate);
        back = findViewById(R.id.buttonBack);

        String no = getIntent().getStringExtra("no");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM biodata WHERE no='" + no + "'", null);

        if (c.moveToFirst()) {
            id.setText(c.getString(0));
            name.setText(c.getString(1));
            dob.setText(c.getString(2));
            gender.setText(c.getString(3));
            address.setText(c.getString(4));
        }

        update.setOnClickListener(v -> {
            db.execSQL("UPDATE biodata SET " +
                    "name='" + name.getText().toString() + "', " +
                    "dob='" + dob.getText().toString() + "', " +
                    "gender='" + gender.getText().toString() + "', " +
                    "address='" + address.getText().toString() + "' " +
                    "WHERE no='" + id.getText().toString() + "'");

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            MainActivity.ma.RefreshList();
            finish();
        });

        back.setOnClickListener(v -> finish());
    }
}