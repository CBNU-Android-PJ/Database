package com.practice.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText id_tv, pw_tv;
    ImageButton login_btn, register_btn;
    String id, pw;
    String SQL;

    //SQLite 연결 관련 변수들
    SQLiteDatabase db;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_tv = findViewById(R.id.id_tv);
        pw_tv = findViewById(R.id.pw_tv);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        db_helper = new DatabaseHelper(this);       //DatabaseHelper 객체
        db = db_helper.getWritableDatabase();

        //user_info 테이블 생성(처음에 실행하고 주석처리)
        //db_helper.onCreate(db);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db_helper.onCreate(db);

                Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_SHORT).show();       //이거 떴으니까 클릭은 된거임
                id = "'" + id_tv.getText().toString() + "'";        //Edittext에서 id 문자열 가져옴
                pw = "'" + pw_tv.getText().toString() + "'";        //Edittext에서 비밀번호 문자열 가져옴

                //아이디와 비밀번호 문자열 DB로 전송하고 회원인지 아닌지 확인하는 코드
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

            }
        });

        //회원가입 버튼 눌렀을 때
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);      //RegisterActivity로 이동
            }
        });
    }

}
