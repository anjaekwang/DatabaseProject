package com.example.nam_kikim.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ReservationActivity extends AppCompatActivity {

    static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reservation);

        // database
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READONLY);

        // reservation activity
        final ImageButton img1 = findViewById(R.id.imageButton1);
        final ImageButton img2 = findViewById(R.id.imageButton2);
        final ImageButton img3 = findViewById(R.id.imageButton3);
        final ImageButton img4 = findViewById(R.id.imageButton4);
        final ImageButton img5 = findViewById(R.id.imageButton5);
        final Button btn = findViewById(R.id.doReservation);

        // movie picture
        cursor = db.rawQuery("select * from MOVIE", null);
        cursor.moveToPosition(0);
        byte[] bt = cursor.getBlob(1);
        Bitmap bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        img1.setImageBitmap(bm);
        cursor.moveToPosition(1);
        bt = cursor.getBlob(1);
        bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        img2.setImageBitmap(bm);
        cursor.moveToPosition(2);
        bt = cursor.getBlob(1);
        bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        img3.setImageBitmap(bm);
        cursor.moveToPosition(3);
        bt = cursor.getBlob(1);
        bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        img4.setImageBitmap(bm);
        cursor.moveToPosition(4);
        bt = cursor.getBlob(1);
        bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        img5.setImageBitmap(bm);

        // image button event
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(0);
                Intent intent_movie = new Intent(getApplicationContext(), MovieActivity.class);
                startActivity(intent_movie);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(1);
                Intent intent_movie = new Intent(getApplicationContext(), MovieActivity.class);
                startActivity(intent_movie);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(2);
                Intent intent_movie = new Intent(getApplicationContext(), MovieActivity.class);
                startActivity(intent_movie);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(3);
                Intent intent_movie = new Intent(getApplicationContext(), MovieActivity.class);
                startActivity(intent_movie);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(4);
                Intent intent_movie = new Intent(getApplicationContext(), MovieActivity.class);
                startActivity(intent_movie);
            }
        });

        // reservation button event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_cinema = new Intent(getApplicationContext(), CinemaActivity.class);
                startActivity(intent_cinema);
                finish();
            }
        });
    }
}