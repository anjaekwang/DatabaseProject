package com.example.nam_kikim.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.nam_kikim.test.MainActivity.num;

public class CinemaActivity extends AppCompatActivity {

    static int couple;
    static int reservation_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cinema);

        // database
        final SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READWRITE);

        // cinema component
        final Spinner spi_movie = findViewById(R.id.spi_movie);
        final Spinner spi_cinema = findViewById(R.id.spi_cinema);
        final Spinner spi_date = findViewById(R.id.spi_date);
        final Spinner spi_time = findViewById(R.id.spi_time);
        final Spinner spi_seat = findViewById(R.id.spi_seat);
        final ArrayList<String> movie = new ArrayList<String>();
        final ArrayList<String> cinema = new ArrayList<String>();
        final ArrayList<String> date = new ArrayList<String>();
        final ArrayList<String> time = new ArrayList<String>();
        final ArrayList<String> seat = new ArrayList<String>();
        final ArrayAdapter<String> adt_movie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, movie);
        final ArrayAdapter<String> adt_cinema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cinema);
        final ArrayAdapter<String> adt_date = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, date);
        final ArrayAdapter<String> adt_time = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, time);
        final ArrayAdapter<String> adt_seat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, seat);
        final Button btn_ok = findViewById(R.id.btn_ok);

        // spinner setting
        movie.add("\"영화를 선택하세요.\"");
        spi_movie.setAdapter(adt_movie);
        cinema.add("\"영화관을 선택하세요\"");
        spi_cinema.setAdapter(adt_cinema);
        date.add("\"날짜를 선택하세요.\"");
        spi_date.setAdapter(adt_date);
        time.add("\"시간을 선택하세요.\"");
        spi_time.setAdapter(adt_time);
        seat.add("\"좌석을 선택하세요.\"");
        spi_seat.setAdapter(adt_seat);

        // movie spinner
        Cursor cursor = db.rawQuery("select movie_name from MOVIE", null);
        cursor.moveToFirst();
        movie.add(cursor.getString(0));
        while (cursor.moveToNext())
            movie.add(cursor.getString(0));
        spi_movie.setAdapter(adt_movie);
        spi_movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int pos_movie, long l) {

                // cinema spinner
                if (pos_movie != 0) {

                    Cursor cursor = db.rawQuery("select distinct cinema_name from RESERVATION where movie_name='" + movie.get(pos_movie) + "'", null);
                    cursor.moveToFirst();

                    // clear data
                    cinema.clear();
                    cinema.add("\"영화관을 선택하세요\"");
                    spi_cinema.setAdapter(adt_cinema);
                    date.clear();
                    date.add("\"날짜를 선택하세요.\"");
                    spi_date.setAdapter(adt_date);
                    time.clear();
                    time.add("\"시간을 선택하세요.\"");
                    spi_time.setAdapter(adt_time);

                    cinema.add(cursor.getString(0));
                    while (cursor.moveToNext())
                        cinema.add(cursor.getString(0));
                    spi_cinema.setAdapter(adt_cinema);
                    spi_cinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, final int pos_cinema, long l) {

                            // date spinner
                            if (pos_cinema != 0) {

                                Cursor cursor = db.rawQuery("select distinct date from RESERVATION " +
                                        "where movie_name='" + movie.get(pos_movie) +
                                        "' and cinema_name='" + cinema.get(pos_cinema) + "'", null);
                                cursor.moveToFirst();

                                // clear data
                                date.clear();
                                date.add("\"날짜를 선택하세요.\"");
                                spi_date.setAdapter(adt_date);
                                time.clear();
                                time.add("\"시간을 선택하세요.\"");
                                spi_time.setAdapter(adt_time);

                                date.add(cursor.getString(0));
                                while (cursor.moveToNext())
                                    date.add(cursor.getString(0));
                                spi_date.setAdapter(adt_date);
                                spi_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, final int pos_date, long l) {

                                        // time spinner
                                        if (pos_date != 0) {

                                            Cursor cursor = db.rawQuery("select distinct time from RESERVATION " +
                                                    "where movie_name='" + movie.get(pos_movie) +
                                                    "' and cinema_name='" + cinema.get(pos_cinema) +
                                                    "' and date='" + date.get(pos_date) + "'", null);
                                            cursor.moveToFirst();

                                            // clear data
                                            time.clear();
                                            time.add("\"시간을 선택하세요.\"");
                                            spi_time.setAdapter(adt_time);

                                            time.add(cursor.getString(0));
                                            while (cursor.moveToNext())
                                                time.add(cursor.getString(0));
                                            spi_time.setAdapter(adt_time);
                                            spi_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, final int pos_time, long l) {

                                                    if(pos_time != 0) {

                                                        seat.clear();
                                                        seat.add("\"좌석을 선택하세요\"");
                                                        spi_seat.setAdapter(adt_seat);

                                                        Cursor cursorReservation = db.rawQuery("select reservation_number from RESERVATION " +
                                                                "where movie_name='" + movie.get(pos_movie) +
                                                                "' and cinema_name='" + cinema.get(pos_cinema) +
                                                                "' and date='" + date.get(pos_date) +
                                                                "' and time='" + time.get(pos_time) + "'", null);
                                                        cursorReservation.moveToFirst();
                                                        reservation_number = cursorReservation.getInt(0);

                                                        Cursor cursor = db.rawQuery("select seat_number from CUSTOMER where reservation_number=" + reservation_number, null);
                                                        cursor.moveToFirst();
                                                        ArrayList<Integer> seatArray = new ArrayList<>();
                                                        seatArray.add(cursor.getInt(0));
                                                        while (cursor.moveToNext())
                                                            seatArray.add(cursor.getInt(0));
                                                        for (int i = 1; i <= 100; i++)
                                                            if (!seatArray.contains(i))
                                                                seat.add("" + i);
                                                        spi_seat.setAdapter(adt_seat);
                                                        spi_seat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, final int pos_seat, long l) {

                                                                Cursor cursor = db.rawQuery("select reservation_number from RESERVATION " +
                                                                        "where movie_name='" + movie.get(pos_movie) +
                                                                        "' and cinema_name='" + cinema.get(pos_cinema) +
                                                                        "' and date='" + date.get(pos_date) +
                                                                        "' and time='" + time.get(pos_time) + "'", null);
                                                                cursor.moveToFirst();
                                                                reservation_number = cursor.getInt(0);



                                                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {

                                                                        int seat_number = Integer.parseInt(seat.get(pos_seat));

                                                                        // update reservation number
                                                                        db.execSQL("update CUSTOMER set reservation_number=" + reservation_number +
                                                                                ", couple=" + couple +
                                                                                ", seat_number=" + seat_number +
                                                                                " where number=" + num);
                                                                        Toast.makeText(getApplicationContext(), "예매가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        finish();
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                            }
                                                        });
                                                    }
                                                }
                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                }

                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
}