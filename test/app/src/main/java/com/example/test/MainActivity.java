package com.example.nam_kikim.test;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 로그인 사용자 정보를 다른 클래스와 공유하기 위한 변수
    static String id, pw;
    static int num;

    // 저장 공간에 접근하여 데이터베이스를 읽고 쓰기 위한 권한 획득
    protected boolean shouldAskPermissions() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @TargetApi(23)
    protected void askPermissions() {

        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 레이아웃 설정
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        // 데이터베이스 선언
        final SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READONLY);

        // layout_main 레이아웃에 존재하는 컴포넌트 불러오기
        final EditText text_id = findViewById(R.id.loginId);
        final EditText text_pw = findViewById(R.id.passwordText);
        final Button btn_login = findViewById(R.id.login);
        final Button btn_logout = findViewById(R.id.logout);
        final Button btn_reservation = findViewById(R.id.reservation);
        final Button btn_information = findViewById(R.id.information);
        final Button btn_register = findViewById(R.id.register);
        final TextView txt_main = findViewById(R.id.txt_main);

        // "로그인" 버튼 클릭 이벤트
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 사용자가 입력한 계정 정보 저장
                id = text_id.getText().toString();
                pw = text_pw.getText().toString();

                // SQL : select number, name from CUSTOMER where id="id" and pw="pw"
                Cursor cursor = db.rawQuery("select number, name from CUSTOMER where id='" + id + "' and pw='" + pw + "'", null);
                cursor.moveToFirst();

                // 사용자 계정이 존재할 경우
                try {

                    // 사용자 정보 및 버튼 등록
                    num = cursor.getInt(0);
                    text_id.setText("");
                    text_pw.setText("");
                    btn_login.setVisibility(View.GONE);
                    btn_logout.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.GONE);
                    btn_reservation.setVisibility(View.VISIBLE);
                    btn_information.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    txt_main.setText(cursor.getString(1) + "님 환영합니다. ^^");

                    btn_information.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            Cursor cursor = db.rawQuery("select reservation_number from CUSTOMER where id='" + id + "' and pw='" + pw + "'", null);
                            cursor.moveToFirst();

                            if (cursor.getInt(0) != 0) {

                                Intent intent_info= new Intent(getApplicationContext(), InformationActivity.class);
                                startActivity(intent_info);
                            }
                            else{

                                Toast.makeText(getApplicationContext(), "예매정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // "영화정보" 버튼 클릭 이벤트
                    btn_reservation.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){

                            // layout_reservation 화면으로 전환
                            Intent intent_reservation = new Intent(getApplicationContext(), ReservationActivity.class);
                            startActivity(intent_reservation);
                        }
                    });
                }

                // 사용자 계정이 존재하지 않을 경우
                catch(CursorIndexOutOfBoundsException e) {

                    text_id.setText("");
                    text_pw.setText("");
                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "로그아웃" 버튼 클릭 이벤트
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                // 사용자 데이터 및 버튼 배열 초기화
                id = null;
                pw = null;
                txt_main.setText("영화관에서 인연을 찾는 SOME MOVIE 어플입니다.");
                btn_logout.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                btn_reservation.setVisibility(View.INVISIBLE);
                btn_register.setVisibility(View.VISIBLE);
                btn_information.setVisibility(View.INVISIBLE);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // layout_register 화면으로 전환
                Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent_register);
            }
        });
    }
}