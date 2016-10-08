package com.a10h3y.paywhat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.a10h3y.paywhat.Manager.RealmDBManager;
import com.a10h3y.paywhat.adapter.CardRectclerViewAdapter;
import com.a10h3y.paywhat.bean.CardInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rectclerView)
    RecyclerView rectclerView;

    private CardRectclerViewAdapter cardRectclerViewAdapter;

    private List<CardInfoBean>cardInfoBeanList;
    
    private Context context;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        
        context = this;

        cardInfoBeanList  = new ArrayList<>();

        cardRectclerViewAdapter = new CardRectclerViewAdapter(this,cardInfoBeanList);

        cardRectclerViewAdapter.setOnItemClickLitener(new CardRectclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(view, "setOnItemClickLitener"+position, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onItemLongClick(final View view, final int position) {

                new AlertDialog.Builder(context)
                        .setItems(new String[]{"删除","修改"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which==0){

                            RealmDBManager.getInstance().deleteCardInfo(cardInfoBeanList.get(position));

                            reloadData();

                        }else{
                            Snackbar.make(view, "修改", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        initData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(MainActivity.this,HYAddCardActivity.class);

                startActivityForResult(intent,0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==RESULT_OK){

            reloadData();
        }
    }

    //重新从数据库查询数据
    private void reloadData(){

        cardInfoBeanList.clear();

        List<CardInfoBean>tempList = RealmDBManager.getInstance().queryAllCardInfoWithSort(RealmDBManager.SortBy.SORT_BY_DISTANCE_BILL_DAY);

        if (tempList!=null){
            cardInfoBeanList.addAll(tempList);
        }

        cardRectclerViewAdapter.notifyDataSetChanged();
    }

    //初始化数据
    private void initData(){

        //设置布局管理器
        rectclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        rectclerView.setAdapter(cardRectclerViewAdapter);
        //设置Item增加、移除动画
        rectclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        /*rectclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));*/
        rectclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);

            }
        });

        reloadData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
