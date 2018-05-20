package com.acticitytest.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.acticitytest.demo.R;
import com.acticitytest.demo.adapter.ListViewAdapter;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.ProgressSubscriber;
import com.acticitytest.demo.http.SubscriberOnNextListener;
import com.acticitytest.demo.http.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.acticitytest.demo.activity.MainActivity.USER_ORDER_ACTIVITY;

public class UserOrderActivity extends AppCompatActivity{

    @BindView(android.R.id.list)
    ListView list;

    private SubscriberOnNextListener getMessageOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
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
                ListViewAdapter listAdapter = new ListViewAdapter(
                        UserOrderActivity.this, m, b);
                list.setAdapter(listAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        /*String value[]=countries[position].split(",");
                        int picId=getActivity().getResources().getIdentifier(value[1],
                        "drawable",getActivity().getPackageName());
                        ;*/
                        Bundle bundle = new Bundle();
                        bundle.putInt("activity", USER_ORDER_ACTIVITY);
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
            }
        };
        getMessageByReceiver();
    }

    private void getMessageByReceiver(){
        HttpMethods.getInstance();
        MessagePresenter.getMessageByReceiverId(new
                        ProgressSubscriber<List<Message>>(getMessageOnNext, this),
                LoginActivity.stuNum);
    }
}
