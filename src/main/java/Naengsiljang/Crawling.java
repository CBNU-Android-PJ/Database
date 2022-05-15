package Naengsiljang;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Crawling {

    private WebDriver webDriver;
    private JavascriptExecutor javascriptExecutor;
    private WebElement element;
    private String url;         //크롤링할 페이지 url

    //chromedriver.exe 설치 경로로
   public static String WEB_DRIVER_ID  = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:\\InteliJ_workspace\\crawling\\recipe_crawling\\chromedriver.exe";

    //생성자
    public Crawling(){
        // WebDriver 경로 설정
        System.setProperty(WEB_DRIVER_ID , WEB_DRIVER_PATH);

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");      //창을 최대호
        options.addArguments("--disable-popup-blocking");       //팝업 차단
        //options.addArguments("headless"); // 창 숨김

        webDriver = new ChromeDriver(options);
        url = "https://www.10000recipe.com/recipe/list.html";       //만개의 레시피 url
    }

    public void GetWebSite(){
        try {
            webDriver.get(url);
            Thread.sleep(2000);     //2초간 로딩시간 기다림

        } catch (Exception e){
            e.printStackTrace();        //예외 출력
        }
    }
}
