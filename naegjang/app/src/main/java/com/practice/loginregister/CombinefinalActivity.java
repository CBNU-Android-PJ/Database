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
        combineData=intent.getExtras().getStringArrayList("data"); //Intent로 넘겨받은 데이터 재료 데이터에 넣기
        /*String joinIngredient=String.join("/",combineData); //배열 안에 있는 재료들을 감자/김치 형태로 join하기*/


        //DB에 있는 추천된 레시피 가져와서 listview에 배열로 넣기
        //리스트 속 item 연결
        // 리스트를 생성한다.
        menulist = new ArrayList<String>();
        FindMenu(combineData); //재료 값을 입력 받아 메뉴를 출력해주는 함수
        FinalList = (ListView) findViewById(R.id.final_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menulist);
        System.out.println(menulist);
        FinalList.setAdapter(adapter); //adapter를 list에 set. >> ListView와 Adapter 연결
        adapter.notifyDataSetChanged();
        combineData.clear();
       /* System.out.println(combineData);*/

    }
    public void FindMenu(ArrayList menu) //재료를 입력받으면 메뉴를 추천해주는 함수
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        menulist = databaseAccess.GetMenu(menu);
    }

}

