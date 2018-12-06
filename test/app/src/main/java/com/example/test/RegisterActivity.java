package com.example.nam_kikim.test;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    static String id;
    static String pw;
    static String name;
    static String birth;
    static String phone;
    static String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        final SQLiteDatabase db = SQLiteDatabase.openDatabase("/mnt/sdcard/cinema.db", null, SQLiteDatabase.OPEN_READWRITE);

        final TextInputEditText idInput = findViewById(R.id.idInput);
        final TextInputEditText pwInput = findViewById(R.id.pwInput);
        final TextInputEditText nameInput = findViewById(R.id.nameInput);
        final TextInputEditText birthInput = findViewById(R.id.birthInput);
        final TextInputEditText phoneInput = findViewById(R.id.phoneInput);
        final RadioGroup sexInput = findViewById(R.id.sexInput);
        final Button checkId = findViewById(R.id.checkId);
        final Button register = findViewById(R.id.register);

        // "성별" 버튼 선택 이벤트
        sexInput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = findViewById(sexInput.getCheckedRadioButtonId());
                sex = radioButton.getText().toString();
            }
        });

        // "중복확인" 버튼 클릭 이벤트
        checkId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Cursor cursor = db.rawQuery("select number from CUSTOMER where id='" + idInput.getText().toString() + "'", null);
                cursor.moveToFirst();

                // 회원 정보가 존재할 경우
                try {

                    cursor.getInt(0);
                    Toast.makeText(getApplicationContext(), "이미 존재하는 ID 입니다.", Toast.LENGTH_SHORT).show();
                    idInput.setText("");
                }

                // 회원 정보가 존재하지 않을 경우
                catch(CursorIndexOutOfBoundsException e) {

                    Toast.makeText(getApplicationContext(), "사용 가능한 ID 입니다.", Toast.LENGTH_SHORT).show();

                    // "회원가입완료" 버튼 클릭 이벤트
                    register.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            // 회원 정보 수집
                            id = idInput.getText().toString();
                            pw = pwInput.getText().toString();
                            name = nameInput.getText().toString();
                            birth = birthInput.getText().toString();
                            phone = phoneInput.getText().toString();

                            // 데이터베이스에 회원 정보 저장
                            db.execSQL("insert into CUSTOMER (id, pw, name, sex, birth, phone) " +
                                    "values ('" + id + "', '" + pw + "', '" + name + "', '" + sex + "', '" + birth + "', '" + phone+ "')");
                            Toast.makeText(getApplicationContext(), "회원가입을 축하드립니다. ^^", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }
}