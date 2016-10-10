package com.a10h3y.paywhat;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.a10h3y.paywhat.Manager.RealmDBManager;
import com.a10h3y.paywhat.Tools.DateTools;
import com.a10h3y.paywhat.bean.CardInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HYAddCardActivity extends AppCompatActivity {

    private Context context;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cardNameEditText)
    EditText cardNameEditText;
    @BindView(R.id.cardNumEditText)
    EditText cardNumEditText;
    @BindView(R.id.cardBillEditText)
    EditText cardBillEditText;
    @BindView(R.id.cardPayEditText)
    EditText cardPayEditText;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_hyadd_card);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setTitle("添加银行卡");

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //验证数据无误存入数据库
                if (valadateInputcardInfo(view)){


                    //距账单日天数
                    int distanceBillDay;

                    //距还款日天数
                    int distancePayDay;

                    distanceBillDay = DateTools.getDistanceDays(new Integer(getStringFromEditText(cardBillEditText)));

                    distancePayDay  = DateTools.getDistanceDays(new Integer(getStringFromEditText(cardPayEditText)));



                    CardInfoBean cardInfoBean = new CardInfoBean();

                    cardInfoBean.setCardName(getStringFromEditText(cardNameEditText));

                    cardInfoBean.setCardNum(getStringFromEditText(cardNumEditText));


                    cardInfoBean.setCardBillDay(new Integer(getStringFromEditText(cardBillEditText)));

                    cardInfoBean.setCardPayDay(new Integer(getStringFromEditText(cardPayEditText)));

                    cardInfoBean.setDistanceBillDay(distanceBillDay);

                    cardInfoBean.setDistancePayDay(distancePayDay);

                    RealmDBManager.getInstance().saveCardInfo(cardInfoBean);


                    setResult(RESULT_OK);

                    finish();
                }

            }
        });




        cardBillEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               showNumDialog(context,cardBillEditText);
            }


        });

        cardPayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumDialog(context,cardPayEditText);
            }
        });



    }


    private void showNumDialog(Context context, final EditText editText){

        RelativeLayout linearLayout = new RelativeLayout(context);
        final NumberPicker aNumberPicker = new NumberPicker(context);
        aNumberPicker.setMaxValue(28);
        aNumberPicker.setMinValue(1);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker,numPicerParams);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("请选择日期")
                .setView(linearLayout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        editText.setText(aNumberPicker.getValue()+"");

                    }
                });

        builder.show();
    }

    /**
     * 验证输入内通是否为空
     * @param editText
     * @return true 空  false 不为空
     */
    private boolean valadateEditTextEmpty(EditText editText){

        return editText.getText().toString().isEmpty();
    }

    /**
     * 验证所有卡片信息时候正确
     * @param view
     * @return
     */
    private boolean valadateInputcardInfo(View view){

        if (valadateEditTextEmpty(cardNameEditText)){

            Snackbar.make(view, "卡名不能为空！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }

        if (valadateEditTextEmpty(cardNumEditText)){

            Snackbar.make(view, "卡号不能为空！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }

        if (valadateEditTextEmpty(cardBillEditText)){

            Snackbar.make(view, "账单日不能为空！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }

        if (valadateEditTextEmpty(cardPayEditText)){

            Snackbar.make(view, "还款日能为空！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        }

        return true;
    }

    private String getStringFromEditText(EditText editText){

        return editText.getText().toString();
    }

}
