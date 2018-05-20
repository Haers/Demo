package com.acticitytest.demo.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.NavigationView;
import android.widget.TextView;
import android.widget.Toast;
import com.acticitytest.demo.R;
import com.acticitytest.demo.adapter.ListViewAdapter;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.ProgressSubscriber;
import com.acticitytest.demo.http.SubscriberOnNextListener;
import com.acticitytest.demo.http.presenter.MessagePresenter;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_menu)
    NavigationView navView;
    @BindView(android.R.id.list)
    ListView list;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private long mExitTime;
    private SubscriberOnNextListener getMessageOnNext;
    public static final int MAIN_ACTIVITY = 1;
    public static final int USER_MESSAGE_ACTIVITY = 2;
    public static final int USER_ORDER_ACTIVITY = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item);
        ButterKnife.bind(this);
        Resources resource = getBaseContext().getResources();
        ColorStateList csl = resource.getColorStateList(R.color.navigation_menu_item_color);
        navView.setItemTextColor(csl);
        navView.setItemIconTintList(null);//此处是设置menu图标的颜色为图标本身的颜色
        navView.getMenu().add(1, 1, 1, "完善用户信息");//动态添加menu
        navView.getMenu().add(2, 2, 2, "我发布的消息");
        navView.getMenu().add(3, 3, 3, "我帮助的人");
        //菜单的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getOrder()) {
                    case 1:
                        Intent intent_info = new Intent("android.intent.action.USER_INFO");
                        startActivity(intent_info);
                        break;
                    case 2:
                        Intent intent_message = new Intent("android.intent.action.USER_MESSAGE");
                        startActivity(intent_message);
                        break;
                    case 3:
                        Intent intent_order = new Intent("android.intent.action.USER_ORDER");
                        startActivity(intent_order);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_user_info:
                Intent intent_info = new Intent("android.intent.action.USER_INFO");
                startActivity(intent_info);
                break;
            case R.id.menu_my_message:
                Intent intent_message = new Intent("android.intent.action.USER_MESSAGE");
                startActivity(intent_message);
                break;
            case R.id.menu_my_order:
                Intent intent_order = new Intent("android.intent.action.USER_ORDER");
                startActivity(intent_order);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        final ArrayList<String> msg = new ArrayList<>();
        final ArrayList<Boolean> check = new ArrayList<>();
        getMessageOnNext = new SubscriberOnNextListener<List<Message>>(){
            @Override
            public void onNext(final List<Message> messages){
                for(int i = 0; i < messages.size(); i++){
                    check.add(messages.get(i).isCaught());
                    msg.add(messages.get(i).getMsg());
                }
                final String[] m = msg.toArray(new String[msg.size()]);
                final Boolean[] b = check.toArray(new Boolean[check.size()]);
                //ListViewAdapter listAdapter = new ListViewAdapter(getActivity(), countries);
                ListViewAdapter listAdapter = new ListViewAdapter(MainActivity.this, m, b);
                list.setAdapter(listAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("activity", MAIN_ACTIVITY);
                        bundle.putInt("id", messages.get(position).getId());
                        bundle.putString("senderId", messages.get(position).getSenderId());
                        bundle.putString("sendDate", messages.get(position).getSendDate());
                        bundle.putString("sendTime", messages.get(position).getSendTime());
                        bundle.putString("msg", msg.get(position));
                        bundle.putString("fetchLocation", messages.get(position).getFetchLocation());
                        bundle.putString("sendLocation", messages.get(position).getSendLocation());
                        bundle.putBoolean("isCaught", messages.get(position).isCaught());
                        bundle.putBoolean("isDone", messages.get(position).isDone());
                        Intent intent=new Intent("android.intent.action.NEW_VIEW");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                fab.attachToListView(list, new ScrollDirectionListener() {
                    @Override
                    public void onScrollDown() {
                        Log.d("ItemFragment",
                                "onScrollDown()");
                    }

                    @Override
                    public void onScrollUp() {
                        Log.d("ItemFragment",
                                "onScrollUp()");
                    }
                }, new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        Log.d("ItemFragment", "onScrollStateChanged()");
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d("ItemFragment", "onScroll()");
                    }
                });
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.EDIT_MESSAGE");
                        startActivity(intent);
                    }
                });
            }
        };
        getAllMessage();
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if((System.currentTimeMillis() - mExitTime) > 5000){
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getAllMessage(){
        HttpMethods.getInstance();
        MessagePresenter.showAllMessage(new
                ProgressSubscriber<List<Message>>(getMessageOnNext, this));
    }

}

