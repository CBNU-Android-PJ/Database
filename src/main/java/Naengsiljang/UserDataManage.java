package Naengsiljang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDataManage {

    private Connection connection;
    private Statement statement;
    private ResultSet resultset;

    //생성자
    public UserDataManage(){
        try {
            Class.forName("org.sqlite.JDBC");
            String DBfile_location = "jdbc:sqlite:naengsiljang.sqlite3";     //db파일 위치
            connection = DriverManager.getConnection(DBfile_location);
            statement =connection.createStatement();
            System.out.println("============Database connection complete(UserDataManage)============");

        } catch (Exception e){
            System.out.println("생성자: 데이터베이스 연결 오류(sqlite-jdbc): " + e.getMessage());
        }
    }

    //아이디 중복확인 함수
   protected boolean IsUserExist(String user_id){
        try{
            String SQL = "SELECT * FROM user_info WHERE user_id=" + user_id + ";";
            resultset = statement.executeQuery(SQL);
            while(resultset.next()){
                return true;        //일치하는 아이디 찾았으면 true
            }
        } catch (Exception e){
            System.out.println("IsUserExist 함수: 데이터베이스 검색 오류/ Code: " + e.getMessage());
        }
        return false;       //일치하는 아이디 찾지 못했으면 false
    }

    //회원가입 함수
    protected boolean Join(String name, String user_id, String password){
        try{
            int id = 0;     //회원의 id 저장할 변수. (user_id 아님. PRIMARY KEY)

            if(IsUserExist(user_id)){       //이미 존재하는 유저이면
                return false;     //함수 탈출
            }
            String SQL = "INSERT INTO user_info(name, user_id, password) VALUES(" + name + ", " + user_id + ", " + password + ");";
            int is_updated = statement.executeUpdate(SQL);
            System.out.println("업데이트 건 수 : " + is_updated);
            String SQL2 = "SELECT id from user_info WHERE user_id=" + user_id + ";";        //새로 입력한 회원정보의 id값 가져옴
            resultset = statement.executeQuery(SQL2);
            if(resultset.next()){
                id = resultset.getInt(1);       //새로 회원가입한 유저의 id값
            }
            System.out.println(id);     //임시 출력
            CreateRefrigerator(id);     //회원가입 성공하면, 나의 냉장고 테이블 생성.
        } catch (Exception e){
            System.out.println("Join 함수: 데이터베이스 입력 오류/ Code: " + e.getMessage());
        }
        return true;    //정상적으로 회원가입 되면 true
    }

    //로그인 함수
    protected String Login(String user_id, String password){
        String name = "Undefined User";     //초기값은 못찾은 유저라는 문자열.
        try {
            String SQL = "SELECT * FROM user_info WHERE user_id=" + user_id + " AND password=" + password + ";";
            resultset = statement.executeQuery(SQL);
            while(resultset.next()){
                name = resultset.getString("name");     //일치하면 이름 입력
            }
        } catch (Exception e){
            System.out.println("Login 함수: 데이터베이스 검색 오류/ Code: " + e.getMessage());
        }
        return name;        //이름 출력.
    }

    //회원정보 수정 함수(비밀번호만 수정)
    protected void ModifyUserInfo(String name, String password){
        try {
            String SQL = "UPDATE user_info SET password= " + password + " WHERE name="  + name + ";";
            int is_updated = statement.executeUpdate(SQL);
            System.out.println("ModifyUserInfo 함수: 업데이트 건 수 : " + is_updated);
        } catch (Exception e){
            System.out.println("ModifyUserInfo 함수: 데이터베이스 입력 오류/ Code: " + e.getMessage());
        }
    }

    //나의 냉장고 테이블 생성 함수
    protected void CreateRefrigerator(int id){
        try {
            String SQL = "CREATE TABLE user" + id + "(id INTEGER primary key, user_id INTEGER, ingredient TEXT, deadline TEXT);";
            int is_updated = statement.executeUpdate(SQL);
            System.out.println("CreateRefrigerator 함수: 업데이트 건 수 : " + is_updated);
        } catch (Exception e){
            System.out.println("CreateRefrigerator 함수: 테이블 생성 오류/ Code: " + e.getMessage());
        }

    }

}

