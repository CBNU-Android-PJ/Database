package com.practice.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.GregorianCalendar;
//DatePicker로 날짜 입력받기
public class DatePickerActivity extends AppCompatActivity {
    private int mYear =0, mMonth=0, mDay=0;
    private Button DateEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_date_picker);





        //
        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = findViewById(R.id.DatePicker);
        datePicker.init(mYear, mMonth, mDay,mOnDateChangedListener);
        DateEnter = findViewById(R.id.DateEnter);   //확인버튼
        DateEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatePickerActivity.this, IngredientDateActivity.class);
                intent.putExtra("mYear", String.valueOf(mYear));
                intent.putExtra("mMonth", String.valueOf(mMonth));
                intent.putExtra("mDay", String.valueOf(mDay));
                startActivity(intent);
            }
        });
    }


//    public void mOnClick(View v){
//
//        Intent intent = new Intent();
//        intent.putExtra("mYear",mYear);
//        intent.putExtra("mMonth", mMonth);
//        intent.putExtra("mDay", mDay);
//        setResult(RESULT_OK, intent);
//        finish();
//    }


    DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener(){
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDay = dd;

        }

    };




}