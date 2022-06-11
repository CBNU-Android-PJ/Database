package com.practice.loginregister;
//사용자 버튼 동작
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;


public class UserActivity extends Activity {

    private Button backBtn, loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    public void Back (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }







}
