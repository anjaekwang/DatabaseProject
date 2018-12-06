package com.example.nam_kikim.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class ActorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actor);

        final SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READONLY);

        final Spinner spi_actor = findViewById(R.id.spi_actor);
        final ImageView img_actor = findViewById(R.id.actor_picture);
        final TextView txt_name = findViewById(R.id.actor_name);
        final TextView txt_birth = findViewById(R.id.actor_birth);
        final TextView txt_nation = findViewById(R.id.nationality);
        final TextView txt_debut = findViewById(R.id.debut);
        final ArrayList<String> arr = new ArrayList<String>();
        final ArrayAdapter<String> adt_actor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);

        arr.add("\"배우를 선택하세요.\"");
        spi_actor.setAdapter(adt_actor);

        // 데이터베이스의 배우 목록을 한 명씩 분리하여 저장
        Cursor c = ReservationActivity.cursor;
        String str = c.getString(6);
        final String actor[] = str.split(", ");
        for(int i = 0; i < actor.length; i++)
            arr.add(actor[i]);
        spi_actor.setAdapter(adt_actor);

        // 배우 선택 이벤트
        spi_actor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos != 0) {

                    Cursor cursor = db.rawQuery("select * from ACTOR where actor_name='" + actor[pos - 1] + "'", null);
                    cursor.moveToFirst();
                    byte[] bt = cursor.getBlob(3);
                    Bitmap bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    img_actor.setImageBitmap(bm);
                    txt_name.setText("이름 : " + cursor.getString(0));
                    txt_birth.setText("출생 : " + cursor.getString(1));
                    txt_nation.setText("국가 : " + cursor.getString(2));
                    txt_debut.setText("데뷔 : " + cursor.getInt(4));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}