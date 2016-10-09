package com.a10h3y.paywhat.Manager;

import com.a10h3y.paywhat.bean.CardInfoBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by guandongchen on 2016/10/8.
 */
public class RealmDBManager {

    public enum SortBy{
        SORT_BY_DISTANCE_BILL_DAY,
        SORT_BY_DISTANCE_PAY_DAY
    }


    private static Realm realm;

    private static RealmDBManager ourInstance = new RealmDBManager();

    public static RealmDBManager getInstance() {

        realm = Realm.getDefaultInstance();

        return ourInstance;
    }

    private RealmDBManager() {
    }

    /**
     * 插入卡片数据到数据库
     * @param cardInfoBean
     */
    public static void saveCardInfo(final CardInfoBean cardInfoBean){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CardInfoBean dbBean = realm.createObject(CardInfoBean.class);

                dbBean.setCardName(cardInfoBean.getCardName());
                dbBean.setCardNum(cardInfoBean.getCardNum());
                dbBean.setCardBillDay(cardInfoBean.getCardBillDay());
                dbBean.setCardPayDay(cardInfoBean.getCardPayDay());
                dbBean.setDistancePayDay(cardInfoBean.getDistancePayDay());
                dbBean.setDistanceBillDay(cardInfoBean.getDistanceBillDay());

            }
        });

    }

    /**
     * 查询所有数据
     * @return
     */
    public static List<CardInfoBean> queryAllCardInfoWithSort(SortBy sortBy){

        RealmResults<CardInfoBean> result = realm.where(CardInfoBean.class).findAll();

        if (result.size()==0){

            return null;
        }

        switch (sortBy) {
            case SORT_BY_DISTANCE_BILL_DAY:

                result=result.sort(CardInfoBean.CARD_DISTANCE_BILL_DAY, Sort.DESCENDING);

                break;

            case SORT_BY_DISTANCE_PAY_DAY:

                result=result.sort(CardInfoBean.CARD_DISTANCE_PAY_DAY,Sort.DESCENDING);

                break;

        }

        return result;
    }

    public  static void deleteCardInfo(final CardInfoBean cardInfoBean){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                cardInfoBean.deleteFromRealm();
            }
        });
    }
}
