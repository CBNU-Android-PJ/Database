package com.practice.loginregister;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombinefinalActivity extends AppCompatActivity {

    private ListView FinalList; //리스트뷰 객체
    private ArrayList<String> combineData; //Intent로 부터 받은 재료 데이터
    public ArrayList<String> menulist; //메뉴를 추천받으면 넣을 리스트
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_combine_final);
        //선택된 재료 데이터 가져오기
        combineData = new ArrayList<>();
        Intent intent = getIntent();
        combineData=intent.getExtras().getStringArrayList("data");
        String joinIngredient=String.join(" ",combineData);


        //DB에 있는 추천된 레시피 가져와서 listview에 배열로 넣기
        //리스트 속 item 연결
        // 리스트를 생성한다.
        menulist = new ArrayList<String>();
        FindMenu(joinIngredient);
        FinalList = (ListView) findViewById(R.id.final_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menulist);
        FinalList.setAdapter(adapter); //adapter를 list에 set. >> ListView와 Adapter 연결




    }public void FindMenu(String menu) //재료를 입력받으면 메뉴를 추천해주는 함수
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        System.out.println(menu);
        menulist = databaseAccess.GetMenu(menu);
    }

}

