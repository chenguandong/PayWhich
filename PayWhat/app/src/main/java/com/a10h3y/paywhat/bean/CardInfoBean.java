package com.a10h3y.paywhat.bean;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CardInfoBean extends RealmObject{

    public static final String CARD_NAME = "cardName";
    public static final String CARD_NUM = "cardNum";
    public static final String CARD_BILL_DAY = "cardBillDay";
    public static final String CARD_PAY_DAY = "cardPayDay";
    public static final String CARD_DISTANCE_BILL_DAY = "distanceBillDay";
    public static final String CARD_DISTANCE_PAY_DAY = "distancePayDay";

    //银行卡名称
    private String cardName;
    //银行卡卡号
    private String cardNum;
    //账单日
    private int cardBillDay;
    //还款日
    private int cardPayDay;
    //距离账单日
    private int distanceBillDay;
    //距离还款日
    private int distancePayDay;

    public int getDistanceBillDay() {
        return distanceBillDay;
    }

    public void setDistanceBillDay(int distanceBillDay) {
        this.distanceBillDay = distanceBillDay;
    }

    public int getDistancePayDay() {
        return distancePayDay;
    }

    public void setDistancePayDay(int distancePayDay) {
        this.distancePayDay = distancePayDay;
    }

    public int getCardBillDay() {
        return cardBillDay;
    }

    public void setCardBillDay(int cardBillDay) {
        this.cardBillDay = cardBillDay;
    }

    public int getCardPayDay() {
        return cardPayDay;
    }

    public void setCardPayDay(int cardPayDay) {
        this.cardPayDay = cardPayDay;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
