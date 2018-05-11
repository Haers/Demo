package com.acticitytest.demo.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acticitytest.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemActivity extends AppCompatActivity {

    @BindView(R.id.help)
    Button help;
    @BindView(R.id.itemPic)
    ImageView itemPic;
    @BindView(R.id.item_sender_id)
    TextView sender_id;
    @BindView(R.id.item_send_time)
    TextView send_time;
    @BindView(R.id.item_send_message)
    TextView send_message;
    @BindView(R.id.item_fetch_location)
    TextView fetch_location;
    @BindView(R.id.item_send_location)
    TextView send_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        int picId=bundle.getInt("picId");
        itemPic.setImageResource(picId);
        String senderId = bundle.getString("senderId");
        String sendDate = bundle.getString("sendDate");
        String sendTime = bundle.getString("sendTime");
        String Time = sendDate + "-" + sendTime;
        String msg = bundle.getString("msg");
        String fetchLocation = bundle.getString("fetchLocation");
        String sendLocation = bundle.getString("sendLocation");
        sender_id.setText(senderId);
        send_time.setText(Time);
        send_message.setText(msg);
        fetch_location.setText(fetchLocation);
        send_location.setText(sendLocation);
    }

    @OnClick(R.id.help)
    public void onClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ItemActivity.this);
        dialog.setMessage("确定帮助？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

}
