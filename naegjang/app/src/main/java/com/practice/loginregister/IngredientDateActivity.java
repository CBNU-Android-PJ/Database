package com.practice.loginregister;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class IngredientDateActivity extends Activity {
    private Button renew_btn;
    private String mYear, mMonth, mDay;
    DateListViewItem date_listview_item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        IngredientDateAdapter adapter;

        ListView dateListView;
        DatePicker datePicker;

        adapter = new IngredientDateAdapter();

        dateListView = (ListView) findViewById(R.id.date_listview);
        dateListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dateListView.setAdapter(adapter);

        Intent getIntent = getIntent();
        mYear = getIntent.getStringExtra("mYear");
        mMonth = getIntent.getStringExtra("mMonth");
        mDay = getIntent.getStringExtra("mDay");

        //예시
        adapter.addDateItem("소고기", "22-06-13");

        //완료버튼 누를시 나의 냉장고 페이지로 이동
        Button Complete = (Button) findViewById(R.id.date_complete);

        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭시 나의 냉장고 페이지 이동 구현
                Intent intent = new Intent(getApplicationContext(), MyRefrigeratorActivity.class);
                startActivity(intent);
            }

        });

        renew_btn = findViewById(R.id.renew_btn);
        renew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_listview_item.setDateExpiry(String.format("%d-%d-%d", mYear,mMonth+3,mDay));
            }
        });

        //날짜 선택 버튼 누르면 datePicker창으로 전환하여 날짜 값 입력받기
        Button dateSet = (Button) findViewById(R.id.date_select);
        TextView Date = findViewById(R.id.date_message);

        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //datepicker intent 열고 결과 값 가져오기
                Intent intent = new Intent(IngredientDateActivity.this, DatePickerActivity.class);
                startActivityForResult(intent, 1);
//                DatePicker.OnDateChangedListener OnDateChangedListener = new DatePicker.OnDateChangedListener(){
//                    @Override
//                    public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
//
//                        //DatePickerActivity에서 날짜 값을 가져오기
//                        yy = datePicker.getYear();
//                        mm = datePicker.getMonth();
//                        dd = datePicker.getDayOfMonth();
//
//                        //날짜 입력 값으로 바꾸기
//                        Date.setText(String.format("%d-%d-%d", yy,mm+3,dd));
//                    }
//                };

            }
        });
    }
}


