package com.practice.loginregister;


import android.graphics.drawable.Drawable;

public class DateListViewItem {
    // 아이템 타입을 구분하기 위한 type 변수 인데 굳이 필요없을듯 수정하기엔 다 바꿔야해서 못할거같음
    // 원래는 type 별로 두개 나눠서 기한 임박할때 viewtype  STRS, WARNING 나눠서 타입 받을 예정이였음
    // ( 기간 얼마 안남은 데이터면 다른방식으로 나누려 했는데 시간 없어서 여기 JAVA 코드는 사실상 의미가 없음)
    private int type ;

    private String dateIngredientStr ;
    private String dateExpiryStr ;

    public void setType(int type) { // 위에 설명한 바와 같음
        this.type = type ;
    }
    public void setDateIngredient(String dateIngredient) { //재료 이름 리턴해주는 클래스
        dateIngredientStr = dateIngredient ;
    }
    public void setDateExpiry(String dateExpiry) {
        dateExpiryStr = dateExpiry ;
    }


    public int getType() {
        return this.type ;
    }

    public String getDateIngredient() {
        return this.dateIngredientStr ;
    }
    public String getDateExpiry() {
        return this.dateExpiryStr ;
    }

}