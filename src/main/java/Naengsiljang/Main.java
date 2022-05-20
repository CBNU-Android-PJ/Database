package Naengsiljang;

import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) {
        UserDataManage manager = new UserDataManage();
        String name = manager.Login("'kksshh0612'", "'kksshh1735'");        //여기 아이디 비번 나중에 받아오기
        //System.out.println(name);
        //manager.Join("'김호호'", "'sdfsdㅇㄹfs'", "'sdfs123'");
        //manager.ModifyUserInfo("'김성호'", "'kksshh1735'");

        //크롤링
        Crawling crawling =  new Crawling();
        crawling.GetWebSite();
        crawling.ScrapRecipe();
    }
}
