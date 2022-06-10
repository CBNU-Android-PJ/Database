package com.practice.loginregister;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String db_name = "NaengsiljangUserData.sqlite3";        //디비명
    static final String table_name = "user_info";


    Cursor cursor;

    public DatabaseHelper(Context context){
        super(context, db_name, null, 1);
    }

    @Override       //테이블 생성 관련 함수
    public void onCreate(SQLiteDatabase db){
        try {
            //create table user_info (id integer primary key, name text, user_id text, password text)
            db.execSQL("create table " + "user_info" + " (id integer primary key, name text, user_id text, password text);");
            System.out.println("user_info 테이블 생성");
        } catch (Exception e){
            System.out.println("onCreate 함수: 데이터베이스 테이블 생성 오류/ Code: " + e.getMessage());
        }

    }
    @Override       //테이블 삭제, 변경 관련 함수
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    //execSQL(String SQL)은 SELECT를 제외한 모든 문장 실행
    //rawQuery(String SQL, null)은 SELECT를 실행할 때 쓰는데, 이때 문장에 ?를 넣을거면 null 대신 다른거

    //로그인
    protected String Login(SQLiteDatabase db, String user_id, String password){
        String name = "Undefined User";     //초기값은 못찾은 유저라는 문자열.
        try {
            String SQL = "SELECT * FROM user_info WHERE user_id='" + user_id + "' AND password='" + password + "';";
            cursor = db.rawQuery(SQL, null);

            while(cursor.moveToNext()){     //존재하기 때문에 찾은 것임.
                name = cursor.getString(1);     //1번째 칼럼인 이름 가져옴
            }
        } catch (Exception e){
            System.out.println("Login 함수: 데이터베이스 검색 오류/ Code: " + e.getMessage());
        }
        cursor.close();
        return name;        //이름 출력.
    }

    //회원가입(user_info 테이블에 행 삽입 / user 나의 냉장고 테이블 생성
    protected boolean Register(SQLiteDatabase db, String name, String user_id, String password){
        int id = 0;
        try {
            String SQL = "INSERT INTO user_info(name, user_id, password) VALUES('"+ name +"', '" + user_id + "', '" + password + "');";
            //db.execSQL(SQL);        //user_info 테이블에 새로운 유저 정보 입력하는 쿼리 실행

            String user_select_SQL = "SELECT * FROM user_info WHERE user_id='" + user_id + "';";
            cursor = db.rawQuery(user_select_SQL, null);        //user_info에서 새로 저장된 유저의 id값 가져오는 쿼리 실행
            while(cursor.moveToNext()){     //존재하기 때문에 찾은 것임.
                id = Integer.parseInt(cursor.getString(0));     //0번째 칼럼인 아이디 가져옴
            }
            System.out.println(id);
            if(id != 0){        //id에 어떤 유저의 id값이 제대로 들어가 있을 때 유저의 나의 냉장고 테이블 생성
                //create table user_info (id integer primary key, name text, user_id text, password text)
                String create_table_SQL = "CREATE TABLE " + Integer.toString(id) + "_refrigerator (id integer primary key, ingredient text, period text);";
                db.execSQL(create_table_SQL);
            }
            else{
                System.out.println("Register 함수: id값이 0임. 유저의 id값이 제대로 들어가지 않았음.");
            }
            cursor.close();
            return true;

        } catch (Exception e){
            System.out.println("Login 함수: 데이터베이스 검색 오류/ Code: " + e.getMessage());
            return false;
        }
    }



}
