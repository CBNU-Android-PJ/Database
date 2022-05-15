package Naengsiljang;

public class Main {
    public static void main(String[] args) {
        UserDataManage manager = new UserDataManage();
        String name = manager.Login("'kksshh0612'", "'kksshh1735'");
        System.out.println(name);
        //manager.Join("'김호호'", "'sdfsdㅇㄹfs'", "'sdfs123'");
        //manager.ModifyUserInfo("'김성호'", "'kksshh1735'");
       Crawling crawling =  new Crawling();
       crawling.GetWebSite();
    }
}
