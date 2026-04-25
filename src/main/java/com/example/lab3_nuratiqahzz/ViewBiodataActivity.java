package com.example.lab3_nuratiqahzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class ViewBiodataActivity extends AppCompatActivity {

    TextView id, name, dob, gender, address;
    Button back;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_biodata);

        dbHelper = new DataHelper(this);

        id = findViewById(R.id.textViewId);
        name = findViewById(R.id.textViewName);
        dob = findViewById(R.id.textViewDob);
        gender = findViewById(R.id.textViewGender);
        address = findViewById(R.id.textViewAddress);
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

        back.setOnClickListener(v -> finish());
    }
}