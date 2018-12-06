package com.example.nam_kikim.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information);

        final SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READONLY);

        final EditText cinema = findViewById(R.id.cinemaName);
        final EditText movie = findViewById(R.id.movieName);
        final EditText datetime = findViewById(R.id.reservDate);
        final EditText seatNum = findViewById(R.id.seatNum);
        final int num = MainActivity.num;

        Cursor cursor = db.rawQuery("select cinema_name, movie_name, date, time, seat_number from RESERVATION, CUSTOMER" +
                " where CUSTOMER.reservation_number=RESERVATION.reservation_number and number=" + num, null);
        cursor.moveToFirst();

        cinema.setText(cursor.getString(0));
        cinema.setFocusable(false);
        cinema.setClickable(false);
        movie.setText(cursor.getString(1));
        movie.setFocusable(false);
        movie.setClickable(false);
        datetime.setText(cursor.getString(2) + " / " + cursor.getString(3));
        datetime.setFocusable(false);
        datetime.setClickable(false);
        seatNum.setText("" + cursor.getInt(4));
        seatNum.setFocusable(false);
        seatNum.setClickable(false);
    }
}