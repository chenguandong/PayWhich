package com.a10h3y.paywhat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a10h3y.paywhat.R;
import com.a10h3y.paywhat.bean.CardInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CardRectclerViewAdapter extends RecyclerView.Adapter<CardRectclerViewAdapter.MyViewHolder> {


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private Context context;


    private List<CardInfoBean> cardList;

    private OnItemClickLitener onItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public CardRectclerViewAdapter(Context context, List<CardInfoBean> cardList) {
        this.context = context;
        this.cardList = cardList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder viewHolder =
                new MyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_card_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (onItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickLitener.onItemClick(v,position);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    onItemClickLitener.onItemLongClick(v,position);

                    return false;
                }
            });
        }

        CardInfoBean cardInfoBean = cardList.get(position);

        holder.cardNameTextView.setText(cardInfoBean.getCardName());

        holder.cardNumTextView.setText(cardInfoBean.getCardNum());

        holder.cardBillDayTextView.setText("距账单日："+(cardInfoBean.getDistanceBillDay()==0?"今天":cardInfoBean.getDistanceBillDay()));

        holder.cardPayDayTextView.setText("距还款日："+(cardInfoBean.getDistancePayDay()==0?"今天":cardInfoBean.getDistancePayDay()));
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardNameTextView;

        TextView cardNumTextView;

        TextView cardBillDayTextView;

        TextView cardPayDayTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardNameTextView = (TextView) itemView.findViewById(R.id.cardNameTextView);

            cardNumTextView = (TextView) itemView.findViewById(R.id.cardNumTextView);

            cardBillDayTextView = (TextView) itemView.findViewById(R.id.cardBillDayTextView);

            cardPayDayTextView = (TextView) itemView.findViewById(R.id.cardPayDayTextView);
        }
    }
}
