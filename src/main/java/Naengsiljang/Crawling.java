package Naengsiljang;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*;
import java.time.Duration;

public class Crawling {

    private WebDriver webDriver;
    private JavascriptExecutor javascriptExecutor;
    private WebElement element;             //웹 요소를 담을 객체
    private WebDriverWait webDriverWait;    //어떠한 동작 후 명시적으로 기다리는 동작을 수행하는 객체
    private Actions actions;    //키보드, 마우스 클릭같은 이벤트를 다루는 객체
    private String url;         //크롤링할 페이지 url

    private Connection connection;
    private Statement statement;
    private ResultSet resultset;

    //chromedriver.exe 설치 경로
    public static String WEB_DRIVER_ID  = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "chromedriver.exe";

    //생성자
    public Crawling(){
        // WebDriver 경로 설정
        System.setProperty(WEB_DRIVER_ID , WEB_DRIVER_PATH);

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");              //창을 최대로
        options.addArguments("--disable-popup-blocking");       //팝업 차단
        //options.addArguments("headless"); // 창 숨김

        webDriver = new ChromeDriver(options);
        url = "https://wtable.co.kr/recipes";       //우리의 식탁url

        try {           //SQLite데이터베이스 연결
            Class.forName("org.sqlite.JDBC");
            String DBfile_location = "jdbc:sqlite:C:\\sqlitestudio-3.3.3\\naengsiljang.sqlite3";     //db파일 위치
            connection = DriverManager.getConnection(DBfile_location);
            statement =connection.createStatement();
            System.out.println("============Database connection complete(Crawling)============");

        } catch (Exception e){
            System.out.println("생성자: 데이터베이스 연결 오류(sqlite-jdbc): " + e.getMessage());
        }
    }

    //웹사이트 진입
    public void GetWebSite(){
        try {
            webDriver.get(url);
            webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));    //처음 페이지 진입하고 2초 기다림

        } catch (Exception e){
            e.printStackTrace();        //예외 출력
        }
    }

    //레시피 이름, 재료 크롤링
    public void ScrapRecipe(){
        try {
            String menu_link = "";      //메뉴 링크를 저장할 변수
            String menu = "";           //메뉴 이름을 저장할 변수

            javascriptExecutor = (JavascriptExecutor) webDriver;        //자바스크립트를 통해 html을 제어하는 객체
            javascriptExecutor.executeScript("window.scrollBy(0, 1700)");       //y스크롤 1700 내림

            webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/div/div/div/section[3]/ul/li[2]")).click();  //메인요리 버튼 찾고 클릭
            webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/div/div/div/section[3]/div[2]/div/select/option[3]")).click();   //난이도순 클릭
            Thread.sleep(2000);

            for(int i=0; i<21; i++){        //메뉴 총 846개 있는데, 스크롤을 내려야 요리 엘리먼트가 html에 생겨남.
                javascriptExecutor.executeScript("window.scrollBy(0, 5000)");       //y스크롤 1700 내림
                Thread.sleep(1000);
            }
//*[@id="__next"]/div/main/div/div/div/section[3]/section/div/div/div/a[767]
            int cnt = 770;        //메뉴 갯수 셀 변수
            while(cnt <= 830){        //a태그 하나씩 클릭해서 들어가면서 이제 메뉴, 재료 크롤링 해야함.(총 805개)
                ArrayList<String> ingredients = new ArrayList<>();      //재료들을 저장할 ArrayList

                String xpath = "//*[@id=\"__next\"]/div/main/div/div/div/section[3]/section/div/div/div/a[" + cnt + "]";    //a태그가 1부터 846까지 있음.(메뉴)
                menu_link = webDriver.findElement(By.xpath(xpath)).getAttribute("href");        //메뉴 링크
                menu = webDriver.findElement(By.xpath(xpath + "/div/p[2]")).getText();       //메뉴 이름

                webDriver.findElement(By.xpath(xpath)).click();     //메뉴 클릭
                Thread.sleep(1000);     //메뉴 클릭해서 페이지 이동 후 1초 기다림
                System.out.println("메뉴 링크: " + menu_link);  //확인용
                System.out.println("메뉴: " + menu);   //확인용

                List<WebElement> ingredient_elements = webDriver.findElements(By.className("fCbbYE"));  //재료 div태그. 이제 그 아래 div[1]을 가져와야함.
                int ingredient_size = ingredient_elements.size();     //재료 개수

                System.out.println(ingredient_size);        //임의 출력. 몇갠지 확인용

                for(int i=0; i<ingredient_size; i++){
                    String ingredient = ingredient_elements.get(i).findElement(By.xpath("./div[1]")).getText();     //재료 이름
                    ingredients.add(ingredient);        //배열에 삽입
                }
                //임의 출력
                for(int i=0; i<ingredient_size; i++){
                    System.out.println(ingredients.get(i));
                }
                InsertRecipeToDB(menu, menu_link, ingredients, ingredient_size);       //크롤링한 데이터 DB에 저장
                cnt++;
                webDriver.navigate().back();        //뒤로가기
                Thread.sleep(1000);
            }
       } catch (Exception e){
            e.printStackTrace();        //예외 출력
        }
    }

    //크롤링 데이터 데이터베이스에 저장
    public void InsertRecipeToDB(String menu, String menu_link, ArrayList<String> ingredients, int ingredient_size){
        String ingredients_str = "'";
        try {
            String SQL = "INSERT INTO recipe_version2(menu, link, ingredients) ";

            SQL = SQL + "VALUES('" + menu + "', '" + menu_link + "', ";
            for(int i=0; i<ingredient_size; i++){
                if(i==ingredient_size - 1){         //재료 배열의 마지막 원소이면
                    ingredients_str = ingredients_str + ingredients.get(i) + "');";       //마지막 재료 넣고 끝
                }else{
                    ingredients_str = ingredients_str + ingredients.get(i) + "/";      //재료를 하나씩 넣고 /로 구분
                }
            }
            SQL = SQL + ingredients_str;
            System.out.println(SQL);
            int is_updated = statement.executeUpdate(SQL);
            System.out.println("업데이트 건 수 : " + is_updated);

        } catch (Exception e){
            System.out.println("InsertRecipeToDB 함수: 데이터베이스 입력 오류/ Code: " + e.getMessage());
        }
    }


}
