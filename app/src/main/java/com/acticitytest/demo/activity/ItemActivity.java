package com.acticitytest.demo.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.User;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.presenter.UserPresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ItemActivity extends AppCompatActivity {

    public static final int MAKE_CALL = 1;
    private String senderId;
    @BindView(R.id.item_help)
    Button help;
    @BindView(R.id.item_call)
    Button call;
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        int picId=bundle.getInt("picId");
        itemPic.setImageResource(picId);
        senderId = bundle.getString("senderId");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.item_call, R.id.item_help})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.item_call:
                AlertDialog.Builder dialog = new AlertDialog.Builder(ItemActivity.this);
                dialog.setMessage("确定帮助，将拨打求助者电话？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(ItemActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(ItemActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},1);

                        }else {
                            makeCall();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            case R.id.item_help:

                break;
        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case MAKE_CALL:
                if(grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED){
                    makeCall();
                }else{
                    Toast.makeText(this, "Make call failed",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void makeCall(){
        try{
            HttpMethods.getInstance();
            UserPresenter.getUser(new Subscriber<List<User>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof SocketTimeoutException) {
                        Toast.makeText(ItemActivity.this,
                                "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof ConnectException) {
                        Toast.makeText(ItemActivity.this,
                                "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ItemActivity.this,
                                "error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("ProgressSubscriber", e.getMessage());
                    }
                }

                @Override
                public void onNext(List<User> user) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+user.get(0).getTelephone()));
                    ItemActivity.this.startActivity(intent);
                }
            }, senderId);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

}
