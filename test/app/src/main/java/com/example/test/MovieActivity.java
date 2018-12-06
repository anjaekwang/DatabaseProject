package com.example.nam_kikim.test;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie);

        final ImageView picture = findViewById(R.id.picture);
        final TextView movie_name = findViewById(R.id.movie_name);
        final TextView opening_date = findViewById(R.id.opening_date);
        final TextView running_time = findViewById(R.id.running_time);
        final TextView director_name = findViewById(R.id.director_name);
        final TextView genre = findViewById(R.id.genre);
        final TextView actor = findViewById(R.id.actor);
        final TextView description = findViewById(R.id.description);
        final Button btn_actor = findViewById(R.id.btn_actor);

        // 선택한 영화 정보 불러오기
        Cursor c = ReservationActivity.cursor;

        // 데이터베이스로부터 영화 사진 불러온 뒤 데이터 변환
        byte[] bt = c.getBlob(1);
        Bitmap bm = BitmapFactory.decodeByteArray(bt, 0, bt.length);
        picture.setImageBitmap(bm);

        // 영화 정보 출력
        movie_name.setText("영화 제목 : " + c.getString(0));
        opening_date.setText("개봉 날짜 : " + c.getString(3));
        running_time.setText("상영 시간 : " + c.getInt(4));
        director_name.setText("감독 이름 : " + c.getString(5));
        genre.setText("영화 장르 : " + c.getString(7));
        actor.setText("출연 배우 : " + c.getString(6));
        description.setText(c.getString(2));

        // "배우정보" 버튼 클릭 이벤트
        btn_actor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // layout_actor 화면으로 전환
                Intent intent_actor = new Intent(getApplicationContext(), ActorActivity.class);
                startActivity(intent_actor);
            }
        });
    }
}