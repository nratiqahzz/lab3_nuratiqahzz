package com.example.lab3_nuratiqahzz;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    String[] register;
    ListView listView;
    Cursor cursor;
    DataHelper dbHelper;

    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ma = this;
        dbHelper = new DataHelper(this);

        listView = findViewById(R.id.listView1);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateBiodataActivity.class))
        );

        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);

        register = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                register[i] = cursor.getString(0) + " - " + cursor.getString(1);
                cursor.moveToNext();
            }
        }

        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, register));

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String selectedId = register[position].split(" - ")[0];

            String[] menu = {"View Biodata", "Update Biodata", "Delete Biodata"};

            new AlertDialog.Builder(this)
                    .setTitle("Select Action")
                    .setItems(menu, (dialog, which) -> {

                        if (which == 0) {
                            Intent i = new Intent(this, ViewBiodataActivity.class);
                            i.putExtra("no", selectedId);
                            startActivity(i);
                        }

                        if (which == 1) {
                            Intent i = new Intent(this, UpdateBiodataActivity.class);
                            i.putExtra("no", selectedId);
                            startActivity(i);
                        }

                        if (which == 2) {
                            db.execSQL("DELETE FROM biodata WHERE no='" + selectedId + "'");
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            RefreshList();
                        }
                    }).show();
        });
    }
}